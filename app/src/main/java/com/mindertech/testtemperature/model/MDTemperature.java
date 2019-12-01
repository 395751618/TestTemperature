package com.mindertech.testtemperature.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.model
 * @anthor xiangxia
 * @time 2019-11-28 16:21
 * @description 描述
 */
public class MDTemperature implements Parcelable {

    @SerializedName("_id") public String _id;
    @SerializedName("point") public String point;
    @SerializedName("image") public String image;
    @SerializedName("temperature") public String temperature;
    @SerializedName("date") public String date;
    @SerializedName("createdAt") public String createdAt;
    @SerializedName("updatedAt") public String updatedAt;
    @SerializedName("__v") public String __v;
    @SerializedName("user") public MDTemperature.User user;
    @SerializedName("id") public String id;

    public MDTemperature() {

    }

    protected MDTemperature(Parcel in) {
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

    public static final Creator<MDTemperature> CREATOR = new Creator<MDTemperature>() {
        @Override
        public MDTemperature createFromParcel(Parcel in) {
            return new MDTemperature(in);
        }

        @Override
        public MDTemperature[] newArray(int size) {
            return new MDTemperature[size];
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
        dest.writeParcelable(user, flags);
        dest.writeString(id);
    }

    public static class User implements Parcelable {
        @SerializedName("confirmed") public String confirmed;
        @SerializedName("blocked") public String blocked;
        @SerializedName("_id") public String _id;
        @SerializedName("username") public String username;
        @SerializedName("email") public String email;
        @SerializedName("provider") public String provider;
        @SerializedName("createdAt") public String createdAt;
        @SerializedName("updatedAt") public String updatedAt;
        @SerializedName("__v") public String __v;
        @SerializedName("role") public String role;
        @SerializedName("id") public String id;

        public User() {

        }

        protected User(Parcel in) {
            confirmed = in.readString();
            blocked = in.readString();
            _id = in.readString();
            username = in.readString();
            email = in.readString();
            provider = in.readString();
            createdAt = in.readString();
            updatedAt = in.readString();
            __v = in.readString();
            role = in.readString();
            id = in.readString();
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.confirmed);
            dest.writeString(this.blocked);
            dest.writeString(this._id);
            dest.writeString(this.email);
            dest.writeString(this.provider);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
            dest.writeString(this.__v);
            dest.writeString(this.role);
            dest.writeString(this.id);
        }
    }
}
