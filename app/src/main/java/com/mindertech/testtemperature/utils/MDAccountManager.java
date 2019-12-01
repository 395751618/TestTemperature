package com.mindertech.testtemperature.utils;

import com.mindertech.testtemperature.model.MDAccount;
import com.mindertech.testtemperature.model.MDTemperature1;
import com.mindertech.testtemperature.model.Regist;
import com.mindertech.testtemperature.model.Temperature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import okhttp3.internal.Util;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.utils
 * @anthor xiangxia
 * @time 2019-11-27 14:52
 * @description 描述
 */
public class MDAccountManager {

    private boolean islogin = false;
    private MDAccount user;
    private List<MDTemperature1> temperatures = new ArrayList<>();

    private List<Temperature> list_shoulder_left = new ArrayList<>();
    private List<Temperature> list_shoulder_right = new ArrayList<>();
    private List<Temperature> list_arm_left = new ArrayList<>();
    private List<Temperature> list_arm_right = new ArrayList<>();

    private static class Holder {
        private static MDAccountManager INSTANCE = new MDAccountManager();
    }

    private MDAccountManager() {

    }

    public static MDAccountManager getInstance() {
        return MDAccountManager.Holder.INSTANCE;
    }

    public void autologin(MDAccount account) {
        this.user = account;
        this.islogin = true;
    }

    public void login(Regist regist, String password) {
        MDAccount account = new MDAccount();
        account.setUserId(regist.user.id);
        account.setUsername(regist.user.username);
        account.setEmail(regist.user.email);
        account.setPassword(password);
        this.user = account;
        this.islogin = true;

        temperatures = regist.user.temperatures;
        formatTemperatureList();
    }

    public void logout() {
        this.user = null;
        this.islogin = false;
    }

    public String getUserId() {
        if (null == user.userId) {
            return "";
        }
        return user.userId;
    }

    public String getUserName() {
        return user.username;
    }

    public String getPassword() {
        return user.password;
    }

    public List<MDTemperature1> getTemperatures() {
        return temperatures;
    }

    private List<Temperature> getTemperatures(String point) {
        List<Temperature> list = new ArrayList<>();
        List<MDTemperature1> list1 = temperatures;
        for (int i = 0;i < list1.size();i ++) {
            MDTemperature1 mdTemperature1 = list1.get(i);
            if (Utils.getPointString(point).equals(mdTemperature1.point)) {
                Temperature temperature = new Temperature();
                temperature.setTemperature(mdTemperature1.temperature);
                temperature.setTimestamp(Utils.dateToStamp(mdTemperature1.date));
                list.add(temperature);
            }
        }
        sortData((ArrayList<Temperature>) list);
        return list;
    }

    private void sortData(ArrayList<Temperature> mList) {
        Collections.sort(mList, new Comparator<Temperature>() {
            @Override
            public int compare(Temperature temperature1, Temperature temperature2) {
                Date date1 = Utils.stringToDate(temperature1.timestamp);
                Date date2 = Utils.stringToDate(temperature2.timestamp);
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
    }

    private void formatTemperatureList() {
        list_shoulder_left = getTemperatures(Utils.temperature_point_shoulder_left);
        list_shoulder_right = getTemperatures(Utils.temperature_point_shoulder_right);
        list_arm_left = getTemperatures(Utils.temperature_point_arm_left);
        list_arm_right = getTemperatures(Utils.temperature_point_arm_right);
    }

    public void addTemperature(String point, String temperature, long timestamp) {
        if (false == Utils.isChoosePoint(point)) {
            return;
        }
        Temperature temperature1 = new Temperature();
        temperature1.setPoint(point);
        temperature1.setTemperature(temperature);
        temperature1.setTimestamp(timestamp);

        if (point.equals(Utils.temperature_point_shoulder_left)) {
            list_shoulder_left.add(0, temperature1);
        }
        if (point.equals(Utils.temperature_point_shoulder_right)) {
            list_shoulder_right.add(0, temperature1);
        }
        if (point.equals(Utils.temperature_point_arm_left)) {
            list_arm_left.add(0, temperature1);
        }
        if (point.equals(Utils.temperature_point_arm_right)) {
            list_arm_right.add(0, temperature1);
        }
    }

    public List<Temperature> getTemperatureList(String point) {
        if (point.equals(Utils.temperature_point_shoulder_left)) {
            return list_shoulder_left;
        }
        if (point.equals(Utils.temperature_point_shoulder_right)) {
            return list_shoulder_right;
        }
        if (point.equals(Utils.temperature_point_arm_left)) {
            return list_arm_left;
        }
        if (point.equals(Utils.temperature_point_arm_right)) {
            return list_arm_right;
        }
        return null;
    }
}
