package com.cherishTang.laishou.laishou.main.bean;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Created by CherishTang on 2018/7/12.
 * 个人中心
 */

public class PersonalCenterItemMenuBean {
    // 成员变量
    private @StringRes
    int strRes;
    private @DrawableRes
    int imgRes;
    private String tag;
    private Class<?> clx;
    private int requestCode;
    private int flag;

    public PersonalCenterItemMenuBean(@DrawableRes int imgRes, @StringRes int strRes, String tag) {
        this.strRes = strRes;
        this.imgRes = imgRes;
        this.tag = tag;
    }

    public PersonalCenterItemMenuBean(@DrawableRes int imgRes, @StringRes int strRes,
                                      String tag, Class<?> clx, int requestCode, int flag) {
        this.strRes = strRes;
        this.imgRes = imgRes;
        this.tag = tag;
        this.clx = clx;
        this.requestCode = requestCode;
        this.flag = flag;
    }

    public Class<?> getClx() {
        return clx;
    }

    public void setClx(Class<?> clx) {
        this.clx = clx;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getStrRes() {
        return strRes;
    }

    public void setStrRes(int strRes) {
        this.strRes = strRes;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
