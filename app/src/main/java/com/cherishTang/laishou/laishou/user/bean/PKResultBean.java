package com.cherishTang.laishou.laishou.user.bean;

/**
 * Created by CherishTang on 2019/3/23.
 * describe
 */
public class PKResultBean {
    private PKInfo myPk;
    private PKInfo otherPk;
    private UserInfo myInfo;
    private String normChestWai;
    private String normWaistWai;
    private String normHipWai;
    private String strStartTime;
    private String strEndTime;

    public PKInfo getMyPk() {
        return myPk;
    }

    public void setMyPk(PKInfo myPk) {
        this.myPk = myPk;
    }

    public PKInfo getOtherPk() {
        return otherPk;
    }

    public void setOtherPk(PKInfo otherPk) {
        this.otherPk = otherPk;
    }

    public UserInfo getMyInfo() {
        return myInfo;
    }

    public void setMyInfo(UserInfo myInfo) {
        this.myInfo = myInfo;
    }

    public String getNormChestWai() {
        return normChestWai;
    }

    public void setNormChestWai(String normChestWai) {
        this.normChestWai = normChestWai;
    }

    public String getNormWaistWai() {
        return normWaistWai;
    }

    public void setNormWaistWai(String normWaistWai) {
        this.normWaistWai = normWaistWai;
    }

    public String getNormHipWai() {
        return normHipWai;
    }

    public void setNormHipWai(String normHipWai) {
        this.normHipWai = normHipWai;
    }

    public String getStrStartTime() {
        return strStartTime;
    }

    public void setStrStartTime(String strStartTime) {
        this.strStartTime = strStartTime;
    }

    public String getStrEndTime() {
        return strEndTime;
    }

    public void setStrEndTime(String strEndTime) {
        this.strEndTime = strEndTime;
    }
}
