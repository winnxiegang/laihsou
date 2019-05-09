package com.cherishTang.laishou.bean.Code;

/**
 * Created by CherishTang on 2018/5/25.
 * app版本检测
 */

public class VersionMessage {

    /**
     * id : f629c0ba-3009-46ba-86ff-5007f5e524fe
     * appType : android
     * versionNumber : 1.0.1
     * logContent :
     * versionCode : 2
     */

    private String id;
    private String appType;
    private String versionNumber;
    private String logContent;
    private int versionCode;
    private String downloadUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
