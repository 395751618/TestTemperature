package com.mindertech.testtemperature.model;

import java.util.List;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.model
 * @anthor xiangxia
 * @time 2019-11-26 17:04
 * @description 描述
 */
public class Sign {
    public String id;
    public String token;

    public String locale;

    public List<String> roles;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getLocale() {
        return locale;
    }

    public List<String> getRoles() {
        return roles;
    }
}
