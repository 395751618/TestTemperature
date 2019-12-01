package com.mindertech.testtemperature.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.model
 * @anthor xiangxia
 * @time 2019-11-27 14:25
 * @description 描述
 */
public class MDAccount extends RealmObject {

    public Integer id;

    public String userId;
    public String username;
    public String email;
    public String password;

    public MDAccount() {

    }

    public MDAccount(String userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
