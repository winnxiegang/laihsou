package com.cherishTang.laishou.enumbean;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.google.gson.annotations.SerializedName;
import com.cherishTang.laishou.R;

/**
 * Created by 方舟 on 2018/3/20.
 * 个人中心九宫格菜单
 */

public enum PersonalCenterItemMenuEnum {
    @SerializedName("4")
    certificationManager(R.mipmap.icon_certificationmanager, R.string.certificationManager, "1"),
    @SerializedName("2")
    houseResource(R.mipmap.icon_certificationmanager, R.string.houseResource, "1"),
    @SerializedName("5")
    contract(R.mipmap.icon_certificationmanager, R.string.contract, "1"),

    @SerializedName("1")
    order(R.mipmap.icon_certificationmanager, R.string.order, "1"),
    @SerializedName("6")
    bankCard(R.mipmap.icon_certificationmanager, R.string.bankCard, "1"),
    @SerializedName("9")
    pay(R.mipmap.icon_certificationmanager, R.string.pay, "1"),


    @SerializedName("3")
    collect(R.mipmap.icon_certificationmanager, R.string.collect, "1"),
    @SerializedName("7")
    evaluate(R.mipmap.icon_certificationmanager, R.string.evaluate, "1"),
    @SerializedName("8")
    record(R.mipmap.icon_certificationmanager, R.string.record, "1");

    // 成员变量
    private @StringRes
    int strRes;
    private @DrawableRes
    int imgRes;
    private String tag;//用来标记是否是国有企业的账号专属menu
    private Class<?> clx;
    private int requestCode = 0;
    private int flag;//0、不用登陆，1、需要登录

    // 构造方法
    private PersonalCenterItemMenuEnum(@DrawableRes int imgRes, @StringRes int strRes, String tag,int flag) {
        this.strRes = strRes;
        this.imgRes = imgRes;
        this.tag = tag;
    }

    // 构造方法
    private PersonalCenterItemMenuEnum(@DrawableRes int imgRes, @StringRes int strRes,
                                       String tag, Class<?> clx, int requestCode,int flag) {
        this.strRes = strRes;
        this.imgRes = imgRes;
        this.tag = tag;
        this.clx = clx;
        this.requestCode = requestCode;
        this.flag = flag;
    }

    PersonalCenterItemMenuEnum(@DrawableRes int imgRes, @StringRes int strRes,  String tag) {
        this.strRes = strRes;
        this.imgRes = imgRes;
        this.tag = tag;
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
