package com.cherishTang.laishou.laishou.activity.bean;

import com.cherishTang.laishou.enumbean.ActivityStateEnum;
import com.cherishTang.laishou.enumbean.ActivityTypeEnum;
import com.cherishTang.laishou.enumbean.AppStateEnum;
import com.cherishTang.laishou.enumbean.SignStateEnum;
import com.cherishTang.laishou.enumbean.SignTypeEnum;
import com.cherishTang.laishou.enumbean.SpellPayStateEnum;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CherishTang on 2019/3/8.
 * describe
 */
public class HotActivityBean implements Serializable {

    /**
     * strStartTime : 2019-03-05 00:00:00
     * strEndTime : 2019-03-07 00:00:00
     * id : f923c54b-73a6-419f-af9d-454aa1beaf3d
     * substationId : 00000000-0000-0000-0000-000000000000
     * clubId : 00000000-0000-0000-0000-000000000000
     * activityTitle : 平台的活动
     * activityContent : 都可以参加
     * signType : 2
     * price : 20
     * status :
     * noPassNote : null
     * creater : 0785166c-fb6f-4f90-801f-f8dba372bc07
     * createrType : 2
     * createTime : 2019-03-02T16:07:34.07
     * startTime : 2019-03-05T00:00:00
     * endTime : 2019-03-07T00:00:00
     * activityType : 1
     * img : http://47.94.172.208:20192/file/showimg?filename=20190302094640.jpg
     */

    private String strStartTime;
    private String strEndTime;
    private String id;
    private String substationId;
    private String clubId;
    private String activityTitle;
    private String activityContent;
    private SignStateEnum signType;
    private String activityId;
    private String price;
    private SpellPayStateEnum status;
    private Object noPassNote;
    private String creater;
    private int createrType;
    private String createTime;
    private String startTime;
    private String endTime;
    private ActivityTypeEnum activityType;
    private AppStateEnum appStatus;
    private String img;
    private String clubAddress;
    private String clubPhone;
    private String clubName;
    private long signNumber;
    private ActivityStateEnum appActivityStatus;
    private long signTotal;
    private List<ActivityApplyPeopleBean> signList;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubstationId() {
        return substationId;
    }

    public void setSubstationId(String substationId) {
        this.substationId = substationId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
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

    public Object getNoPassNote() {
        return noPassNote;
    }

    public void setNoPassNote(Object noPassNote) {
        this.noPassNote = noPassNote;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public int getCreaterType() {
        return createrType;
    }

    public void setCreaterType(int createrType) {
        this.createrType = createrType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public ActivityTypeEnum getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityTypeEnum activityType) {
        this.activityType = activityType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getClubAddress() {
        return clubAddress;
    }

    public void setClubAddress(String clubAddress) {
        this.clubAddress = clubAddress;
    }

    public long getSignNumber() {
        return signNumber;
    }

    public void setSignNumber(long signNumber) {
        this.signNumber = signNumber;
    }

    public ActivityStateEnum getAppActivityStatus() {
        return appActivityStatus;
    }

    public void setAppActivityStatus(ActivityStateEnum appActivityStatus) {
        this.appActivityStatus = appActivityStatus;
    }

    public String getClubPhone() {
        return clubPhone;
    }

    public void setClubPhone(String clubPhone) {
        this.clubPhone = clubPhone;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public AppStateEnum getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(AppStateEnum appStatus) {
        this.appStatus = appStatus;
    }

    public long getSignTotal() {
        return signTotal;
    }

    public void setSignTotal(long signTotal) {
        this.signTotal = signTotal;
    }

    public List<ActivityApplyPeopleBean> getSignList() {
        return signList;
    }

    public void setSignList(List<ActivityApplyPeopleBean> signList) {
        this.signList = signList;
    }
}
