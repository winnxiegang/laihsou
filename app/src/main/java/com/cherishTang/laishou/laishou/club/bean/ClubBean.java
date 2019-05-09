package com.cherishTang.laishou.laishou.club.bean;

import com.cherishTang.laishou.laishou.consultant.bean.ConsultantBean;

import java.util.List;

/**
 * Created by CherishTang on 2019/3/3.
 * describe
 */
public class ClubBean {

    /**
     * id : fe6de80d-249c-49b4-8b5c-b09e80664aff
     * substationId : 9784e38e-2b9f-4b04-8326-1f1eadb00837
     * clubName : 合肥百脑汇
     * clubAddress : 安徽合肥包河区112号
     * states : 0
     * creater : 0785166c-fb6f-4f90-801f-f8dba372bc07
     * createTime : 2019-03-02T10:50:37.527
     * isRecommend : 1
     * recommendTime : 2019-03-03T09:11:20.953
     */

    private String id;
    private String substationId;
    private String clubName;
    private String clubAddress;
    private int states;//状态（0:正常;1:删除）
    private String creater;
    private String createTime;
    private int isRecommend;//是否推荐(0:非;1:是)
    private String recommendTime;
    private float score;
    private String userNumber;
    private String label;
    private String logo;
    private String clubPhone;
    private String iconType;//1:活;2:惠;3:团;4:火
    private String businessTime;
    private Double lng;//纬度
    private Double lat;//经度
    private String clubConent;//经度

    public String getClubConent() {
        return clubConent;
    }

    public void setClubConent(String clubConent) {
        this.clubConent = clubConent;
    }

    private List<ConsultantBean> counselorList;

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

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
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

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(String recommendTime) {
        this.recommendTime = recommendTime;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getClubPhone() {
        return clubPhone;
    }

    public void setClubPhone(String clubPhone) {
        this.clubPhone = clubPhone;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public List<ConsultantBean> getCounselorList() {
        return counselorList;
    }

    public void setCounselorList(List<ConsultantBean> counselorList) {
        this.counselorList = counselorList;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
