package com.cherishTang.laishou.laishou.main.bean;

/**
 * Created by CherishTang on 2019/3/22.
 * describe
 */
public class GetPkBean {
    private String dayNumber;
    private String PkId;

    public GetPkBean() {
    }

    public GetPkBean(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getPkId() {
        return PkId;
    }

    public void setPkId(String pkId) {
        PkId = pkId;
    }
}
