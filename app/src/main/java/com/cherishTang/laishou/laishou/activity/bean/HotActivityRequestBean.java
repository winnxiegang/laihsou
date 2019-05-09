package com.cherishTang.laishou.laishou.activity.bean;

/**
 * Created by CherishTang on 2019/3/2.
 * describe
 */
public class HotActivityRequestBean {
    private int page;
    private int rows;
    private String substationId;
    private int activityType	;//1:毅行;2:公益;3:聚会;4:培训;5:旅游;6:运动;7:美容;8:才艺;9:电影;0：全部

    public HotActivityRequestBean(int page, int rows, String substationId, int activityType) {
        this.page = page;
        this.rows = rows;
        this.substationId = substationId;
        this.activityType = activityType;
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

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }
}
