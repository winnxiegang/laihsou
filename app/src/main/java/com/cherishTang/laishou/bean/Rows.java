package com.cherishTang.laishou.bean;

/**
 * Created by 方舟 on 2018/3/15.
 *
 */

public class Rows {
    private Long HouseId;
    private Long ReleaseId;
    private String Title;
    private String Area;
    private String LeaseMoney;
    private Double HouseArea;
    private String DoorModel;
    private String Orientation;
    private String ItemName;
    private String HouseSupport;
    private String LeaseMode;
    private String ReleaseType;
    private String CheckFlag;
    private String PaymentMode;
    private String CoverImage;
    private String Renovation;
    private String ReleaseDate;
    private String ReleaseFlag;
    private Integer ReleaseFlagId;//0 已下架，1发布中
    private Double XAxis;
    private Double YAxis;
    private Integer AuditStatus;//0、审核中，1、审核通过

    public Long getHouseId() {
        return HouseId;
    }

    public void setHouseId(Long houseId) {
        HouseId = houseId;
    }

    public Long getReleaseId() {
        return ReleaseId;
    }

    public void setReleaseId(Long releaseId) {
        ReleaseId = releaseId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getLeaseMoney() {
        return LeaseMoney;
    }

    public void setLeaseMoney(String leaseMoney) {
        LeaseMoney = leaseMoney;
    }

    public Double getHouseArea() {
        return HouseArea;
    }

    public void setHouseArea(Double houseArea) {
        HouseArea = houseArea;
    }

    public String getDoorModel() {
        return DoorModel;
    }

    public void setDoorModel(String doorModel) {
        DoorModel = doorModel;
    }

    public String getOrientation() {
        return Orientation;
    }

    public void setOrientation(String orientation) {
        Orientation = orientation;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getHouseSupport() {
        return HouseSupport;
    }

    public void setHouseSupport(String houseSupport) {
        HouseSupport = houseSupport;
    }

    public String getLeaseMode() {
        return LeaseMode;
    }

    public void setLeaseMode(String leaseMode) {
        LeaseMode = leaseMode;
    }

    public String getReleaseType() {
        return ReleaseType;
    }

    public void setReleaseType(String releaseType) {
        ReleaseType = releaseType;
    }

    public String getCheckFlag() {
        return CheckFlag;
    }

    public void setCheckFlag(String checkFlag) {
        CheckFlag = checkFlag;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getCoverImage() {
        return CoverImage;
    }

    public void setCoverImage(String coverImage) {
        CoverImage = coverImage;
    }

    public String getRenovation() {
        return Renovation;
    }

    public void setRenovation(String renovation) {
        Renovation = renovation;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getReleaseFlag() {
        return ReleaseFlag;
    }

    public void setReleaseFlag(String releaseFlag) {
        ReleaseFlag = releaseFlag;
    }

    public Integer getReleaseFlagId() {
        return ReleaseFlagId;
    }

    public void setReleaseFlagId(Integer releaseFlagId) {
        ReleaseFlagId = releaseFlagId;
    }

    public Double getXAxis() {
        return XAxis;
    }

    public void setXAxis(Double XAxis) {
        this.XAxis = XAxis;
    }

    public Double getYAxis() {
        return YAxis;
    }

    public void setYAxis(Double YAxis) {
        this.YAxis = YAxis;
    }

    public Integer getAuditStatus() {
        return AuditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        AuditStatus = auditStatus;
    }
}
