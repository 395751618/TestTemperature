package com.mindertech.testtemperature.network;

import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.network
 * @anthor xiangxia
 * @time 2019-11-26 16:42
 * @description 描述
 */
public class MDCallFactory extends CallAdapter.Factory {

    private String errorCodeKey;
    private String errorMsgKey;

    public MDCallFactory(String errorCodeKey, String errorMsgKey) {
        this.errorCodeKey = errorCodeKey;
        this.errorMsgKey = errorMsgKey;
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        TypeToken<?> token = TypeToken.get(returnType);
        if (token.getRawType() != MDCall.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException("Call must have generic type (e.g., Call<ResponseBody>)");
        }
        final Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);

        return new CallAdapter<Object, MDCall<?>>() {
            @Override public Type responseType() {
                return responseType;
            }

            @Override public MDCall<?> adapt(retrofit2.Call<Object> call) {
                return new MDCallAdapter<>(call,errorCodeKey,errorMsgKey);
            }
        };
    }
}
