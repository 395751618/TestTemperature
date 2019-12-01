package com.mindertech.testtemperature.network;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.network
 * @anthor xiangxia
 * @time 2019-11-26 16:47
 * @description 描述
 */
public class MDCallAdapter<T> implements MDCall<T> {
    private final retrofit2.Call call;
    private String errorCodeKey;
    private String errorMsgKey;

    MDCallAdapter(retrofit2.Call call, String codeKey, String msgKey) {
        this.call = call;
        this.errorCodeKey = codeKey;
        this.errorMsgKey = msgKey;
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public MDCall<T> clone() {
        return new MDCallAdapter<>(call.clone(), errorCodeKey, errorMsgKey);
    }

    @Override
    public void enqueue(MDCallback<T> callback) {

        call.enqueue(new retrofit2.Callback<T>() {
            @Override public void onResponse(retrofit2.Call<T> call, final Response<T> response) {
                MDThread.runOnMainThreadAsync(new Runnable() {
                    @Override public void run() {
                        int code = 0;
                        String msg = "";
                        if (response != null) {
                            code = -response.code();
                            if (code > -300 && code <= -200) {
                                if (response.body() instanceof MDIbs) {
                                    if (response.body() != null) {
                                        MDIbs ibs = (MDIbs) response.body();
                                        if (ibs.success()) {
                                            callback.success(response.body());
                                            return;
                                        } else {
                                            code = ibs.code();
                                            msg = ibs.msg();
                                        }
                                    }
                                } else {
                                    callback.success(response.body());
                                    return;
                                }
                            } else {
                                try {
                                    String errorBody = response.errorBody()
                                            .source()
                                            .buffer()
                                            .clone()
                                            .readString(Charset.forName("UTF-8"));
                                    try {
                                        JSONObject errorObj = new JSONObject(errorBody);
                                        code = errorObj.getInt("statusCode");
                                        JSONArray array1 = errorObj.getJSONArray("message");
                                        if (null != array1 && array1.length() > 0) {
                                            JSONObject object1 = (JSONObject) array1.get(0);
                                            if (null != object1) {
                                                JSONArray array2 = object1.getJSONArray("messages");
                                                if (null != array2 && array2.length() > 0) {
                                                    JSONObject object2 = (JSONObject) array2.get(0);
                                                    String message1  = object2.getString("message");
                                                    callback.failure(code, message1);
                                                    return;
                                                }

                                            }
                                        }
                                    } catch (Exception e) {
                                        msg = errorBody;
                                    }
                                } catch (Exception e) {

                                }
                            }
                        }
                        if (null == msg || msg.length() == 0 || msg.equals("null")) {
                            msg = "数据异常";
                        }
                        callback.failure(code, msg);
                    }
                });
            }

            @Override public void onFailure(retrofit2.Call<T> call, final Throwable t) {
                MDThread.runOnMainThreadAsync(new Runnable() {
                    @Override public void run() {
                        t.printStackTrace();
                        if (t instanceof SocketTimeoutException) {
                            callback.failure(-1, "网络超时");
                        } else if (t instanceof ConnectException) {
                            callback.failure(-2, "连接异常");
                        } else {
                            callback.failure(-3, "网络异常");
                        }
                    }
                });
            }
        });
    }
}
