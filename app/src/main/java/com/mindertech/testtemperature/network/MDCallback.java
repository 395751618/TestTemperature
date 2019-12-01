package com.mindertech.testtemperature.network;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.network
 * @anthor xiangxia
 * @time 2019-11-26 16:45
 * @description 描述
 */
public interface MDCallback<T> {
    /**
     * 成功回调
     */
    void success(T t);

    /**
     * 失败回调
     *
     * @param code http失败code < 0 ,业务失败code > 0, -1 未知错误
     * @param failure 失败提示信息
     */
    void failure(int code, String failure);
}
