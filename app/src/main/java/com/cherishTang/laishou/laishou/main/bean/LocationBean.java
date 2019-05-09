package com.cherishTang.laishou.laishou.main.bean;

/**
 * Created by CherishTang on 2019/3/4.
 * describe
 */
public class LocationBean {
    private Double lng;
    private Double lat;

    public LocationBean(Double lng, Double lat) {
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
}
