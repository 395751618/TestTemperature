package com.mindertech.testtemperature.utils;

import android.content.Context;

import com.mindertech.testtemperature.model.MDAccount;
import com.mindertech.testtemperature.model.Regist;
import com.mindertech.testtemperature.model.Temperature;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.utils
 * @anthor xiangxia
 * @time 2019-11-27 11:54
 * @description 描述
 */
public class RealmUtils {

    public static void init(Context context) {
        Realm.init(context);
        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        // 文件名
                        .name("temperature.realm")
                        // 版本号
                        .schemaVersion(1)
                        .build();
        Realm.setDefaultConfiguration(config);
    }

    /**
     * 查找上一次登录的账户
     *
     * @author xiangxia
     * @createAt 2019-11-28 14:25
     */
    public static MDAccount findAccount() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<MDAccount> userList = realm.where(MDAccount.class).findAll();
        if (null != userList && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    /**
     * 登录
     *
     * @author xiangxia
     * @createAt 2019-11-28 14:24
     */
    public static void updateAccount(Regist regist, String password) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<MDAccount> userList = realm.where(MDAccount.class).findAll();
        if (null == userList || userList.size() == 0) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    MDAccount account = realm.createObject(MDAccount.class);
                    account.setUserId(regist.user.id);
                    account.setUsername(regist.user.username);
                    account.setEmail(regist.user.email);
                    account.setPassword(password);
                }
            });
        } else {
            RealmAsyncTask transaction =  realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    MDAccount account = realm.createObject(MDAccount.class);
                    account.setUserId(regist.user.id);
                    account.setUsername(regist.user.username);
                    account.setEmail(regist.user.email);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    //成功回调
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    //失败回调
                }
            });
        }
    }

    /**
     * 退出账户
     *
     * @author xiangxia
     * @createAt 2019-11-28 14:24
     */
    public static void deleteAccount() {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<MDAccount> account = realm.where(MDAccount.class).findAll();
        if (null == account || 0 == account.size()) {
            return;
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                account.deleteAllFromRealm();
            }
        });
    }

    /**
     * 添加数据
     *
     * @author xiangxia
     * @createAt 2019-11-28 14:52
     */
    public static void addTemperature(String userId, String point, String temperature) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Temperature temperature1 = realm.createObject(Temperature.class);
                temperature1.setUserId(userId);
                temperature1.setPoint(point);
                temperature1.setTemperature(temperature);
                temperature1.setTimestamp(System.currentTimeMillis());
                System.out.println("temperature1:" + temperature1.getTimestamp());
            }
        });
    }

    /**
     * 获取指定的数据
     *
     * @author xiangxia
     * @createAt 2019-11-28 14:52
     */
    public static List<Temperature> findAllTemperature(String userId, String point) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Temperature> userList = realm.where(Temperature.class).equalTo("userId", userId).and().equalTo("point", point).sort("timestamp", Sort.DESCENDING).findAll();
        return userList;
    }

}
