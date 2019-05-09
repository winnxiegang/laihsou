package com.cherishTang.laishou.laishou.user.bean;

import com.cherishTang.laishou.enumbean.SexEnum;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {
    private String clubName;
    private String clubAddress;
    private String counselorName;
    private String counselorMobile;
    private String label;
    private String id;
    private String clubId;
    private String loginId;
    private String name;
    private String mobile;
    private String counselorId;
    private String recommendId;
    private String integral;
    private String lastSignTime;
    private Integer states;
    private String creater;
    private String createTime;
    private Integer sex;
    private String birthday;
    private String height;
    private String weight;
    private String befond;
    private String industry;
    private String job;
    private String registerDay;
    private String targetWeight;
    private int indentNumber;
    private int collectNumber;
    private String headImg;
    private String counselorHeadImg;
    private String chestWai;//胸围
    private String waistWai;//腰围
    private String hipWai;//臀围
    private String bmi;//bmi
    private String fatRate;//
    private String thenLose;//
    private int clubNumber;//排名

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubAddress() {
        return clubAddress;
    }

    public void setClubAddress(String clubAddress) {
        this.clubAddress = clubAddress;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public String getCounselorMobile() {
        return counselorMobile;
    }

    public void setCounselorMobile(String counselorMobile) {
        this.counselorMobile = counselorMobile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(String counselorId) {
        this.counselorId = counselorId;
    }

    public String getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(String recommendId) {
        this.recommendId = recommendId;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getLastSignTime() {
        return lastSignTime;
    }

    public void setLastSignTime(String lastSignTime) {
        this.lastSignTime = lastSignTime;
    }

    public Integer getStates() {
        return states;
    }

    public void setStates(Integer states) {
        this.states = states;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBefond() {
        return befond;
    }

    public void setBefond(String befond) {
        this.befond = befond;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(String targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getIndentNumber() {
        return indentNumber;
    }

    public void setIndentNumber(int indentNumber) {
        this.indentNumber = indentNumber;
    }

    public int getCollectNumber() {
        return collectNumber;
    }

    public void setCollectNumber(int collectNumber) {
        this.collectNumber = collectNumber;
    }

    public String getRegisterDay() {
        return registerDay;
    }

    public void setRegisterDay(String registerDay) {
        this.registerDay = registerDay;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getCounselorHeadImg() {
        return counselorHeadImg;
    }

    public void setCounselorHeadImg(String counselorHeadImg) {
        this.counselorHeadImg = counselorHeadImg;
    }

    public String getChestWai() {
        return chestWai;
    }

    public void setChestWai(String chestWai) {
        this.chestWai = chestWai;
    }

    public String getWaistWai() {
        return waistWai;
    }

    public void setWaistWai(String waistWai) {
        this.waistWai = waistWai;
    }

    public String getHipWai() {
        return hipWai;
    }

    public void setHipWai(String hipWai) {
        this.hipWai = hipWai;
    }

    public int getClubNumber() {
        return clubNumber;
    }

    public void setClubNumber(int clubNumber) {
        this.clubNumber = clubNumber;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getFatRate() {
        return fatRate;
    }

    public void setFatRate(String fatRate) {
        this.fatRate = fatRate;
    }

    public String getThenLose() {
        return thenLose;
    }

    public void setThenLose(String thenLose) {
        this.thenLose = thenLose;
    }
}

