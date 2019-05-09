package com.cherishTang.laishou.util.crash;

/**
 * Created by 方舟 on 2017/11/20.
 * 错误日志
 */

public class ErrorMsg {
    private String Brand;
    private String Model;
    private String VersionCode;
    private String VersionName;
    private String Ip;
    private String Mobile;
    private String AccessTime;
    private String SystemVersion;
    private String ErrorMsg;
    private String PhoneSystem;
    private String UploadTime;

    public ErrorMsg(String brand, String model, String versionCode, String versionName, String ip, String mobile, String accessTime,
                    String systemVersion, String errorMsg, String phoneSystem, String uploadTime) {
        Brand = brand;
        Model = model;
        VersionCode = versionCode;
        VersionName = versionName;
        Ip = ip;
        Mobile = mobile;
        AccessTime = accessTime;
        SystemVersion = systemVersion;
        ErrorMsg = errorMsg;
        PhoneSystem = phoneSystem;
        UploadTime = uploadTime;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(String versionCode) {
        VersionCode = versionCode;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String versionName) {
        VersionName = versionName;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAccessTime() {
        return AccessTime;
    }

    public void setAccessTime(String accessTime) {
        AccessTime = accessTime;
    }

    public String getSystemVersion() {
        return SystemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        SystemVersion = systemVersion;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public String getPhoneSystem() {
        return PhoneSystem;
    }

    public void setPhoneSystem(String phoneSystem) {
        PhoneSystem = phoneSystem;
    }

    public String getUploadTime() {
        return UploadTime;
    }

    public void setUploadTime(String uploadTime) {
        UploadTime = uploadTime;
    }
}
