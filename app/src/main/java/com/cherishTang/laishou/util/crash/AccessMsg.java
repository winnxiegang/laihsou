package com.cherishTang.laishou.util.crash;

/**
 * Created by 方舟 on 2017/11/20.
 * 访问日志
 */

public class AccessMsg {
    /// 用户手机号码
    public String Mobile;
    /// 用户IP
    public String Ip;
    /// 消息事件类型
    public String AccessType;
    /// APP版本名称
    public String VersionName;
    /// APP版本号
    public String VersionCode;
    /// 手机品牌
    public String Brand;
    /// 手机型号
    public String Model;
    /// 访问时间
    public String AccessTime;
    /// 手机MAC地址
    public String MacAddress;
    /// IMEI号
    public String PhoneInfo;
    public Integer Login_Status;

    public AccessMsg(String mobile, String ip, String accessType, String versionName, String versionCode, String brand, String model,
                     String accessTime, String macAddress, String phoneInfo, Integer login_Status) {
        Mobile = mobile;
        Ip = ip;
        AccessType = accessType;
        VersionName = versionName;
        VersionCode = versionCode;
        Brand = brand;
        Model = model;
        AccessTime = accessTime;
        MacAddress = macAddress;
        PhoneInfo = phoneInfo;
        Login_Status = login_Status;
    }

    public Integer getLogin_Status() {
        return Login_Status;
    }

    public void setLogin_Status(Integer login_Status) {
        Login_Status = login_Status;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getAccessType() {
        return AccessType;
    }

    public void setAccessType(String accessType) {
        AccessType = accessType;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String versionName) {
        VersionName = versionName;
    }

    public String getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(String versionCode) {
        VersionCode = versionCode;
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

    public String getAccessTime() {
        return AccessTime;
    }

    public void setAccessTime(String accessTime) {
        AccessTime = accessTime;
    }

    public String getMacAddress() {
        return MacAddress;
    }

    public void setMacAddress(String macAddress) {
        MacAddress = macAddress;
    }

    public String getPhoneInfo() {
        return PhoneInfo;
    }

    public void setPhoneInfo(String phoneInfo) {
        PhoneInfo = phoneInfo;
    }
}
