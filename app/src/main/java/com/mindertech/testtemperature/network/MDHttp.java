package com.mindertech.testtemperature.network;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.network
 * @anthor xiangxia
 * @time 2019-11-26 16:21
 * @description 描述
 */
public abstract class MDHttp<T> {

    private T http;

    protected abstract String bindUrl();

    private Class getTClass() {
        return (Class) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T http() {
        return this.http;
    }

    public MDHttp() {

        //OkHttp 初始化
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        //请求缓存
//        int cacheSize = 10 * 1024 * 1024; // 10 MiB
//        File file = KitSystem.cacheDir();
//        Cache cache = new Cache(file, cacheSize);
//
//        int timeout = this.bindOkHttpTimeout() / 2;
//        okHttpBuilder.connectTimeout(timeout, TimeUnit.MILLISECONDS)
//                .readTimeout(timeout, TimeUnit.MILLISECONDS)
//                .writeTimeout(timeout * 2, TimeUnit.MILLISECONDS)
//                .retryOnConnectionFailure(true)
//                .cache(cache);

        //设置其他拦截器
        Interceptor[] otherInterceptors = this.bindOkHttpInterceptor();
        if (otherInterceptors != null) {
            for (Interceptor otherInterceptor : otherInterceptors) {
                okHttpBuilder.addInterceptor(otherInterceptor);
            }
        }
        //https 证书
        SSLSocketFactory sslSocket = bindSSLSocketFactory();
        if (sslSocket != null) {
            okHttpBuilder.socketFactory(sslSocket);
        }

        //okHttpBuilder.sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager);

        //HostnameVerifier
        HostnameVerifier hostnameVerifier = bindHostnameVerifier();
        if (hostnameVerifier != null) {
            okHttpBuilder.hostnameVerifier(hostnameVerifier);
        }
        //有打印才添加 打印信息
//        if (KitLog.canLog()) {
//            LogInterceptor logging = new LogInterceptor();
//            logging.setLevel(LogInterceptor.Level.BODY);
//            okHttpBuilder.addInterceptor(logging);
//        }

        bindOkHttpBuilder(okHttpBuilder);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(bindUrl());
        retrofitBuilder.addCallAdapterFactory(
                new MDCallFactory(bindErrorBodyCodeKey(), bindErrorBodyMsgKey()));
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());

        Converter.Factory converter = bindConverterFactory();
        if (converter != null) {
            retrofitBuilder.addConverterFactory(converter);
        }

        retrofitBuilder.client(okHttpBuilder.build());
        bindRetrofitBuilder(retrofitBuilder);
        Retrofit retrofit = retrofitBuilder.build();
        this.http = (T) retrofit.create(this.getTClass());
    }

    /**
     * 超时时间 毫秒
     *
     * @return 超时毫秒
     */
    public int bindOkHttpTimeout() {
        return 30000;
    }

    /**
     * 其他 拦截器
     *
     * @return 拦截器
     */
    public Interceptor[] bindOkHttpInterceptor() {
        return null;
    }

    /**
     * SSL
     *
     * @return 转换器
     */
    public SSLSocketFactory bindSSLSocketFactory() {
        return null;
    }

    /**
     * HostnameVerifier
     *
     * @return HostnameVerifier
     */
    public HostnameVerifier bindHostnameVerifier() {
        return null;
    }

    /**
     * OkHttpClient.Builder
     *
     * @param builder OkHttpClient.Builder
     */
    public void bindOkHttpBuilder(OkHttpClient.Builder builder) {
    }

    /**
     * ErrorBody code key
     *
     * @return key
     */
    public String bindErrorBodyCodeKey() {
        return "code";
    }

    /**
     * ErrorBody msg key
     *
     * @return key
     */
    public String bindErrorBodyMsgKey() {
        return "msg";
    }

    /**
     * 转换器
     *
     * @return 转换器
     */
    public Converter.Factory bindConverterFactory() {
        return null;
    }

    /**
     * Retrofit.Builder
     *
     * @param builder Retrofit.Builder
     */
    public void bindRetrofitBuilder(Retrofit.Builder builder) {

    }
}
