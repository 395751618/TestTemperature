package com.mindertech.testtemperature.network;

import com.google.gson.annotations.SerializedName;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.network
 * @anthor xiangxia
 * @time 2019-11-26 17:07
 * @description 描述
 */
public class MDAsk<T> implements MDIbs<T> {

    public boolean ret;
    @SerializedName("message") public String msg;
    public T data;

    @Override
    public int code() {
        return 0;
    }

    @Override
    public String msg() {
        return msg;
    }

    @Override
    public boolean success() {
        return ret;
    }

    @Override
    public T data() {
        return data;
    }
}
