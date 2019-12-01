package com.mindertech.testtemperature.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.utils
 * @anthor xiangxia
 * @time 2019-11-27 11:14
 * @description 描述
 */
public class Utils {

    public static boolean hasSystemFeature = false;

    public static String temperature_point_shoulder_left = "shoulder_left";
    public static String temperature_point_shoulder_right = "shoulder_right";
    public static String temperature_point_arm_left = "arm_left";
    public static String temperature_point_arm_right = "arm_right";

    public static boolean isChoosePoint(String point) {
        if (null == point) {
            return false;
        }

        if (point.equals(temperature_point_shoulder_left)) {
            return true;
        } else if (point.equals(temperature_point_shoulder_right)) {
            return true;
        } else if (point.equals(temperature_point_arm_left)) {
            return true;
        } else if (point.equals(temperature_point_arm_right)) {
            return true;
        }
        return false;
    }

    public static String getPointString(String point) {
        if (point.equals(temperature_point_shoulder_left)) {
            return "Shoulder Left";
        } else if (point.equals(temperature_point_shoulder_right)) {
            return "Shoulder Right";
        } else if (point.equals(temperature_point_arm_left)) {
            return "Arm Left";
        } else if (point.equals(temperature_point_arm_right)) {
            return "Arm Right";
        }
        return "";
    }

    public static String getImageString(String point) {
        if (point.equals(temperature_point_shoulder_left)) {
            return "ShoulderLeft";
        } else if (point.equals(temperature_point_shoulder_right)) {
            return "ShoulderRight";
        } else if (point.equals(temperature_point_arm_left)) {
            return "ArmLeft";
        } else if (point.equals(temperature_point_arm_right)) {
            return "ArmRight";
        }
        return "";
    }

    public static String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    public static String getCurrentDate() {
        long timeMillis = System.currentTimeMillis();
        return stampToDate(timeMillis);
    }

    public static long dateToStamp(String date) {
        try {
            if (date.length() < 19) {
                return 0;
            }
            String string = date.substring(0, 19);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return simpleDateFormat.parse(string).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Date stringToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return date;
    }
}
