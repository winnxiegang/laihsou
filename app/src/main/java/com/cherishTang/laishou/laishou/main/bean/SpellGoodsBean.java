package com.cherishTang.laishou.laishou.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CherishTang on 2019/3/9.
 * describe
 */
public class SpellGoodsBean implements Serializable {

    /**
     * id : e6ba7245-5635-4ac1-a8bc-1909cf17d3b5
     * goodsName : 拼团商品1
     * goodsNote : 拼团介绍
     * goodsUrl : http://47.94.172.208:20192/file/showimg?filename=20190302094654.jpg
     * spellNumber : 12
     * receiveAddress : 安徽省合肥是瑶海区
     * linkPhone : 0551-8888888
     * status : 2
     * creater : 5607a4eb-ca72-42f4-a3dc-005956d95cf1
     * createTime : 2019-03-02T17:12:25.107
     * substationId : 9784e38e-2b9f-4b04-8326-1f1eadb00837
     * clubId : ccf2effb-8f4d-421f-9f13-63e8405d125c
     * price : 10.99
     * rawPrice : 20.56
     * unit : 120g/盒
     * inventory : 100
     * thenGroup : 0
     * merchantId : c562a881-bf69-4b65-b20d-06b4ff68974c
     */

    private String id;
    private String goodsName;
    private String goodsNote;
    private String goodsUrl;
    private int spellNumber;//每团人数
    private String receiveAddress;
    private String linkPhone;
    private int status;
    private String creater;
    private String createTime;
    private String substationId;
    private String clubId;
    private double price;
    private double rawPrice;
    private String unit;
    private String inventory;
    private String thenGroup;
    private String merchantId;
    private int likeNumber;
    private int browseNumber;

    private List<SpellUserBean> userList;
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

    public String getGoodsNote() {
        return goodsNote;
    }

    public void setGoodsNote(String goodsNote) {
        this.goodsNote = goodsNote;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public int getSpellNumber() {
        return spellNumber;
    }

    public void setSpellNumber(int spellNumber) {
        this.spellNumber = spellNumber;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getSubstationId() {
        return substationId;
    }

    public void setSubstationId(String substationId) {
        this.substationId = substationId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRawPrice() {
        return rawPrice;
    }

    public void setRawPrice(double rawPrice) {
        this.rawPrice = rawPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getThenGroup() {
        return thenGroup;
    }

    public void setThenGroup(String thenGroup) {
        this.thenGroup = thenGroup;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public List<SpellUserBean> getUserList() {
        return userList;
    }

    public void setUserList(List<SpellUserBean> userList) {
        this.userList = userList;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getBrowseNumber() {
        return browseNumber;
    }

    public void setBrowseNumber(int browseNumber) {
        this.browseNumber = browseNumber;
    }
}
