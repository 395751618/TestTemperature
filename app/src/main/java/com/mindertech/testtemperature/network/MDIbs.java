package com.mindertech.testtemperature.network;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.network
 * @anthor xiangxia
 * @time 2019-11-26 16:53
 * @description 描述
 */
public interface MDIbs<T> {

    int code();

    String msg();

    boolean success();

    T data();
}
