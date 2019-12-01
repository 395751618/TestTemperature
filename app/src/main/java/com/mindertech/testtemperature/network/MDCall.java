package com.mindertech.testtemperature.network;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.network
 * @anthor xiangxia
 * @time 2019-11-26 16:44
 * @description 描述
 */
public interface MDCall<T> {

    //取消
    void cancel();
    //克隆
    MDCall<T> clone();
    //执行
    void enqueue(MDCallback<T> callback);
}
