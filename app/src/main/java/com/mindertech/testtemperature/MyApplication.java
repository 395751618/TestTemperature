package com.mindertech.testtemperature;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.mindertech.testtemperature.utils.RealmUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import io.realm.Realm;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature
 * @anthor xiangxia
 * @time 2019-11-27 11:52
 * @description 描述
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmUtils.init(this);
        initBugly();
    }

    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前渠道名
        String channel = "App";
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        strategy.setAppChannel(channel);
        CrashReport.initCrashReport(this, "1959aeacf3", true);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
