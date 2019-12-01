package com.mindertech.testtemperature.model;

import java.sql.Timestamp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.model
 * @anthor xiangxia
 * @time 2019-11-27 13:08
 * @description 描述
 */
public class Temperature extends RealmObject {

    public Integer id;

    public String userId;
    public String point;
    public String temperature;
    public long timestamp;

    public Temperature() {
    }

    public Temperature(String userId, String point, String temperature, long timestamp) {
        this.userId = userId;
        this.point = point;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
