package com.mindertech.testtemperature.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @project TestTemperature
 * @package：com.mindertech.testtemperature.model
 * @anthor xiangxia
 * @time 2019-11-26 17:41
 * @description 描述
 */
public class Regist implements Parcelable {

    @SerializedName("jwt") public String jwt;
    @SerializedName("user") public User user;

    public Regist() {

    }

    protected Regist(Parcel in) {
        jwt = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Regist> CREATOR = new Creator<Regist>() {
        @Override
        public Regist createFromParcel(Parcel in) {
            return new Regist(in);
        }

        @Override
        public Regist[] newArray(int size) {
            return new Regist[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jwt);
        dest.writeParcelable(user, flags);
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
        @SerializedName("role") public Role role;
        @SerializedName("temperatures") public List<MDTemperature1> temperatures;
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
            role = in.readParcelable(Role.class.getClassLoader());
            temperatures = in.createTypedArrayList(MDTemperature1.CREATOR);
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
            dest.writeParcelable(this.role, flags);
            dest.writeTypedList(this.temperatures);
            dest.writeString(this.id);
        }
    }

    public static class Role implements Parcelable {
        @SerializedName("_id") public String _id;
        @SerializedName("name") public String name;
        @SerializedName("description") public String description;
        @SerializedName("type") public String type;
        @SerializedName("createdAt") public String createdAt;
        @SerializedName("updatedAt") public String updatedAt;
        @SerializedName("__v") public String __v;
        @SerializedName("id") public String id;

        public Role() {

        }

        protected Role(Parcel in) {
            _id = in.readString();
            name = in.readString();
            description = in.readString();
            type = in.readString();
            createdAt = in.readString();
            updatedAt = in.readString();
            __v = in.readString();
            id = in.readString();
        }

        public static final Creator<Role> CREATOR = new Creator<Role>() {
            @Override
            public Role createFromParcel(Parcel in) {
                return new Role(in);
            }

            @Override
            public Role[] newArray(int size) {
                return new Role[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.name);
            dest.writeString(this.description);
            dest.writeString(this.type);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
            dest.writeString(this.__v);
            dest.writeString(this.id);
        }
    }
}
