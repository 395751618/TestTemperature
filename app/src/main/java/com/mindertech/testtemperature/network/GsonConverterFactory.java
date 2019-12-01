package com.mindertech.testtemperature.network;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.network
 * @anthor xiangxia
 * @time 2019-11-26 16:34
 * @description 描述
 */
public class GsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    public static GsonConverterFactory create() {
        return create(new Gson());
    }

    private GsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    public static GsonConverterFactory create(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        return new GsonConverterFactory(gson);
    }

    @Nullable
    @Override public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                                    Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonConverterRequest<>(gson, adapter);
    }

    @Nullable @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonConverterResponse<>(gson, adapter);
    }
}
