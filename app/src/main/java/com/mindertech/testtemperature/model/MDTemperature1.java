package com.mindertech.testtemperature.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.model
 * @anthor xiangxia
 * @time 2019-11-29 14:12
 * @description 描述
 */
public class MDTemperature1 implements Parcelable {

    @SerializedName("_id") public String _id;
    @SerializedName("point") public String point;
    @SerializedName("image") public String image;
    @SerializedName("temperature") public String temperature;
    @SerializedName("date") public String date;
    @SerializedName("createdAt") public String createdAt;
    @SerializedName("updatedAt") public String updatedAt;
    @SerializedName("__v") public String __v;
    @SerializedName("user") public String user;
    @SerializedName("id") public String id;

    public MDTemperature1() {

    }

    protected MDTemperature1(Parcel in) {
        _id = in.readString();
        point = in.readString();
        image = in.readString();
        temperature = in.readString();
        date = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        __v = in.readString();
        user = in.readParcelable(Regist.User.class.getClassLoader());
        id = in.readString();
    }

    public static final Creator<MDTemperature1> CREATOR = new Creator<MDTemperature1>() {
        @Override
        public MDTemperature1 createFromParcel(Parcel in) {
            return new MDTemperature1(in);
        }

        @Override
        public MDTemperature1[] newArray(int size) {
            return new MDTemperature1[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(point);
        dest.writeString(image);
        dest.writeString(temperature);
        dest.writeString(date);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(__v);
        dest.writeString(user);
        dest.writeString(id);
    }
}
