package com.cherishTang.laishou.laishou.user.bean;

/**
 * Created by CherishTang on 2019/3/9.
 * describe
 */
public class MyWeightRecordBean {

    /**
     * id : 843c6b2e-2b85-45e2-9258-749c4fcd41ea
     * weight : 70
     * creater : 5607a4eb-ca72-42f4-a3dc-005956d95cf1
     * createTime : 2019-03-09T22:08:30.35
     */

    private String id;
    private float weight;
    private String creater;
    private String createTime;
    private String strCreateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
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

    public String getStrCreateTime() {
        return strCreateTime;
    }

    public void setStrCreateTime(String strCreateTime) {
        this.strCreateTime = strCreateTime;
    }
}
