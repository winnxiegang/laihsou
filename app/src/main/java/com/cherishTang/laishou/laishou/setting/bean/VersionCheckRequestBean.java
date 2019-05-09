package com.cherishTang.laishou.laishou.setting.bean;

/**
 * Created by CherishTang on 2019/3/15.
 * describe
 */
public class VersionCheckRequestBean {
    private int versionCode;
    private String appType;
    private String versionNumber;

    public VersionCheckRequestBean() {
    }

    public VersionCheckRequestBean(int versionCode, String appType, String versionNumber) {
        this.versionCode = versionCode;
        this.appType = appType;
        this.versionNumber = versionNumber;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
}
