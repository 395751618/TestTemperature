package com.mindertech.testtemperature.request;

import com.mindertech.testtemperature.network.MDHttp;

import java.util.HashMap;
import java.util.Map;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.request
 * @anthor xiangxia
 * @time 2019-11-26 17:14
 * @description 描述
 */
public class Api {
    private static Map<Class, MDHttp> sAsk = new HashMap();

    private Api() {
    }

    /**
     * 得到自己的实例化对象
     */
    public static <T extends MDHttp> T self(Class<T> ask) {
        MDHttp request = sAsk.get(ask);
        if (request == null) {
            try {
                request = ask.newInstance();
                sAsk.put(ask, request);
            } catch (Exception var3) {
                var3.printStackTrace();
                throw new Error("instance api error:" + var3.getMessage());
            }
        }
        return (T) request;
    }

    /**
     * 得到自己ask的实例化对象
     */
    public static <E, T extends MDHttp<E>> E ask(Class<T> ask) {
        MDHttp request = self(ask);
        return (E) request.http();
    }
}
