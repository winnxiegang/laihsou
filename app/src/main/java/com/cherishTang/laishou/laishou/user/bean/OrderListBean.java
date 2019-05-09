package com.cherishTang.laishou.laishou.user.bean;

import com.cherishTang.laishou.enumbean.GoodStateEnum;

/**
 * Created by CherishTang on 2019/3/5.
 * describe
 */
public class OrderListBean {

    /**
     * goodsName : 积分产品2
     * goodsImg : http://47.94.172.208:20192/file/showimg?filename=20190302094640.jpg
     * id : 0c14e2a7-1526-42b4-872c-4ab2e1dd8ea8
     * goodsId : ce6f7ae0-ad81-44b6-8d05-16dd1840a6fb
     * integral : 30
     * creater : 5607a4eb-ca72-42f4-a3dc-005956d95cf1
     * createTime : 2019-03-02T15:51:52.263
     * status : 1
     * receiveTime : null
     * operatorId : null
     * note : null
     * indentCode : 201903061538
     * number : 1
     */

    private String goodsName;
    private String goodsImg;
    private String id;
    private String goodsId;
    private int integral;
    private String creater;
    private String createTime;
    private GoodStateEnum status;
    private String receiveTime;
    private String operatorId;
    private String note;
    private String indentCode;
    private int number;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
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

    public GoodStateEnum getStatus() {
        return status;
    }

    public void setStatus(GoodStateEnum status) {
        this.status = status;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIndentCode() {
        return indentCode;
    }

    public void setIndentCode(String indentCode) {
        this.indentCode = indentCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

