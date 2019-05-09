package com.cherishTang.laishou.laishou.activity.bean;

/**
 * Created by CherishTang on 2019/3/11.
 * describe
 */
public class ActivityApplyPeopleRequestBean {
    private String activityId;
    private int page;
    private int rows;

    public ActivityApplyPeopleRequestBean(String activityId, int page, int rows) {
        this.activityId = activityId;
        this.page = page;
        this.rows = rows;
    }
}
