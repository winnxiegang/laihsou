package com.cherishTang.laishou.laishou.user.bean;

/**
 * Created by CherishTang on 2019/3/8.
 * describe
 */
public class GoodExchangeBean {

    /**
     * id : ce6f7ae0-ad81-44b6-8d05-16dd1840a6fb
     * goodsName : 积分产品2
     * goodsContent : 好用到爆
     * integral : 30
     * receiveAddress : 安徽省合肥市瑶海区
     * receivePhone : 0551-7777779
     * creater : 0785166c-fb6f-4f90-801f-f8dba372bc07
     * createTime : 2019-03-02T15:49:42.443
     * status : 0
     * goodsImg : http://47.94.172.208:20192/file/showimg?filename=20190302094640.jpg
     */

    private String id;
    private String goodsName;
    private String goodsContent;
    private int integral;
    private String receiveAddress;
    private String receivePhone;
    private String creater;
    private String createTime;
    private int status;//状态(0:正常;9:删除)
    private String goodsImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsContent() {
        return goodsContent;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }
}
