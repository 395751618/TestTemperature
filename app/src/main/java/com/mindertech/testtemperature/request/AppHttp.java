package com.mindertech.testtemperature.request;

import com.mindertech.testtemperature.model.MDTemperature;
import com.mindertech.testtemperature.model.Regist;
import com.mindertech.testtemperature.model.Sign;
import com.mindertech.testtemperature.model.Temperature;
import com.mindertech.testtemperature.network.MDAsk;
import com.mindertech.testtemperature.network.MDCall;
import com.mindertech.testtemperature.network.MDCallback;
import com.mindertech.testtemperature.network.MDHttp;
import com.mindertech.testtemperature.utils.Utils;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.request
 * @anthor xiangxia
 * @time 2019-11-26 17:00
 * @description 描述
 */
public class AppHttp extends MDHttp<AppHttp.Ask> {
    @Override
    protected String bindUrl() {
        return "http://stp-api.mindertech.net/";//"http://ccic-staging.mindertech.net/server/cgi/";//
    }

    /**
     * 注册
     *
     * @author xiangxia
     * @createAt 2019-11-26 17:26
     */
    public MDCall<Regist> regist(String username, String emial, String password) {
        return http().regist(username, emial, password);
    }

    /**
     * 登录
     *
     * @author xiangxia
     * @createAt 2019-11-26 17:26
     */
    public MDCall<Regist> signIn(String email, String password) {
        return http().signIn(email, password);
    }

    public MDCall<MDTemperature> addTemperature(String userId, String point, String image, String temperature) {
        return http().addTemperature(userId, point, image, temperature, Utils.getCurrentDate());
    }

    /**
     * test
     *
     * @author xiangxia
     * @createAt 2019-11-27 10:38
     */
    public MDCall<MDAsk<Sign>> signIn11(String email, String password) {

        return http().signIn(email, password, "CN");
    }

    public interface Ask {

        @FormUrlEncoded
        @POST("auth/local/register")
        MDCall<Regist> regist(@Field("username") String username, @Field("email") String email, @Field("password") String password);

        @FormUrlEncoded
        @POST("auth/local")
        MDCall<Regist> signIn(@Field("identifier") String identifier, @Field("password") String password);

        @FormUrlEncoded
        @POST("temperatures")
        MDCall<MDTemperature> addTemperature(@Field("user") String user, @Field("point") String point, @Field("image") String image, @Field("temperature") String temperature, @Field("date") String date);

        @FormUrlEncoded @POST("auth/login") MDCall<MDAsk<Sign>> signIn(@Field("email") String email, @Field("password") String password, @Field("locale") String locale);
    }
}
