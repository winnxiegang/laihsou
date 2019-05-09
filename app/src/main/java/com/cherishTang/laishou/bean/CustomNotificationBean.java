package com.cherishTang.laishou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 方舟 on 2017/11/24.
 * 推送消息
 */

public class CustomNotificationBean implements Parcelable {
    private String title;
    private String contents;

    public CustomNotificationBean(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public CustomNotificationBean() {
    }

    protected CustomNotificationBean(Parcel in) {
        title = in.readString();
        contents = in.readString();
    }

    public static final Creator<CustomNotificationBean> CREATOR = new Creator<CustomNotificationBean>() {
        @Override
        public CustomNotificationBean createFromParcel(Parcel in) {
            return new CustomNotificationBean(in);
        }

        @Override
        public CustomNotificationBean[] newArray(int size) {
            return new CustomNotificationBean[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(contents);
    }
}
