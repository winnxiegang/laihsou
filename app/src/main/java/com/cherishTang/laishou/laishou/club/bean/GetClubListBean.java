package com.cherishTang.laishou.laishou.club.bean;

/**
 * Created by CherishTang on 2019/3/2.
 * describe
 */
public class GetClubListBean {
    private int page;
    private int rows;
    private String substationId;
    private String clubName;
    private int orderType;//1:综合;2:人气;3:会员数;4:距离
    private Double lng;//经度
    private Double lat;//维度
    public GetClubListBean(int page, int rows, String substationId, String clubName) {
        this.page = page;
        this.rows = rows;
        this.substationId = substationId;
        this.clubName = clubName;
    }

    public GetClubListBean(int page, int rows, String substationId, String clubName, int orderType) {
        this.page = page;
        this.rows = rows;
        this.substationId = substationId;
        this.clubName = clubName;
        this.orderType = orderType;
    }

    public GetClubListBean(int page, int rows, String substationId, String clubName, int orderType, Double lng, Double lat) {
        this.page = page;
        this.rows = rows;
        this.substationId = substationId;
        this.clubName = clubName;
        this.orderType = orderType;
        this.lng = lng;
        this.lat = lat;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
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
}
