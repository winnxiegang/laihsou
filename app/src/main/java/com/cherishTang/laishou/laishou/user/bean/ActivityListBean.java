package com.cherishTang.laishou.laishou.user.bean;

import com.cherishTang.laishou.enumbean.ActivityStateEnum;
import com.cherishTang.laishou.enumbean.ActivityTypeEnum;
import com.cherishTang.laishou.enumbean.AppStateEnum;
import com.cherishTang.laishou.enumbean.SignStateEnum;
import com.cherishTang.laishou.enumbean.SpellPayStateEnum;

import java.io.Serializable;

/**
 * Created by CherishTang on 2019/3/6.
 * describe:活动订单
 */
public class ActivityListBean implements Serializable {

    /**
     * activityTitle : 分站的活动
     * activityType : 1
     * id : cc352d2c-ef82-4b18-983a-92fa82f289ac
     * activityId : 12dc7b4e-ef30-4658-bf29-71a8e85e8460
     * signType : 2
     * price : 20
     * status : 0
     * creater : 5607a4eb-ca72-42f4-a3dc-005956d95cf1
     * createTime : 2019-03-02T16:17:36.297
     */

    private String activityTitle;
    private String activityContent;
    private String clubAddress;
    private String clubName;
    private String clubPhone;
    private ActivityTypeEnum activityType;
    private String id;
    private String activityId;
    private String startTime;
    private String endTime;
    private String img;
    private AppStateEnum appStatus;
    private ActivityStateEnum appActivityStatus;
    private SignStateEnum signType;
    private String price;
    private SpellPayStateEnum status;
    private String creater;
    private String createTime;

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public ActivityTypeEnum getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityTypeEnum activityType) {
        this.activityType = activityType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public AppStateEnum getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(AppStateEnum appStatus) {
        this.appStatus = appStatus;
    }

    public ActivityStateEnum getAppActivityStatus() {
        return appActivityStatus;
    }

    public void setAppActivityStatus(ActivityStateEnum appActivityStatus) {
        this.appActivityStatus = appActivityStatus;
    }

    public SignStateEnum getSignType() {
        return signType;
    }

    public void setSignType(SignStateEnum signType) {
        this.signType = signType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public SpellPayStateEnum getStatus() {
        return status;
    }

    public void setStatus(SpellPayStateEnum status) {
        this.status = status;
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

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public String getClubAddress() {
        return clubAddress;
    }

    public void setClubAddress(String clubAddress) {
        this.clubAddress = clubAddress;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubPhone() {
        return clubPhone;
    }

    public void setClubPhone(String clubPhone) {
        this.clubPhone = clubPhone;
    }
}
