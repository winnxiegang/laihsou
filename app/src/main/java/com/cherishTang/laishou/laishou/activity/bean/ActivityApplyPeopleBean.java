package com.cherishTang.laishou.laishou.activity.bean;

import java.io.Serializable;

/**
 * Created by CherishTang on 2019/3/13.
 * describe
 */
public class ActivityApplyPeopleBean implements Serializable {
    private String mobile;
    private String headImg;
    private String name;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }


    public String getMobile() {
        return mobile;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
