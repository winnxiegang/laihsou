package com.cherishTang.laishou.laishou.activity.bean;

/**
 * Created by CherishTang on 2019/3/14.
 * describe
 */
public class PayRequestBean {
    private String id;
    private int payType;

    public PayRequestBean() {
    }

    public PayRequestBean(String id, int payType) {
        this.id = id;
        this.payType = payType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
