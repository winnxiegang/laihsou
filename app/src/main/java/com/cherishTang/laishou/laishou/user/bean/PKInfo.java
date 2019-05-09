package com.cherishTang.laishou.laishou.user.bean;

import com.cherishTang.laishou.enumbean.PKResultEnum;

/**
 * Created by CherishTang on 2019/3/23.
 * describe
 */
public class PKInfo {

    /**
     * headImg : http://47.99.100.88:81/file/showimg?filename=20190318173653.jpg
     * id : 35fc8bc5-d4a2-4841-ba5e-19d934b675d7
     * loginId : 08c6f902-6548-4882-b845-75be2b1e2491
     * pkId : 08c6f902-6548-4882-b845-75be2b1e2491
     * roleType : 1
     * startTime : 2019-03-22T23:29:38.227
     * endTime : 2019-03-24T23:29:38.227
     * result : 0
     */

    private String headImg;
    private String id;
    private String loginId;
    private String pkId;
    private int roleType;//1-发起者，2-接受者
    private String startTime;
    private String endTime;
    private PKResultEnum result;
    private float bmi;
    private float weight;
    private float fatRate;
    private int age;
    private String birthday;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public PKResultEnum getResult() {
        return result;
    }

    public void setResult(PKResultEnum result) {
        this.result = result;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getFatRate() {
        return fatRate;
    }

    public void setFatRate(float fatRate) {
        this.fatRate = fatRate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
