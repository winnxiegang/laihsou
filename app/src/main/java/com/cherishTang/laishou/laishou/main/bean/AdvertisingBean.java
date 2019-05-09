package com.cherishTang.laishou.laishou.main.bean;

/**
 * Created by CherishTang on 2019/3/4.
 * describe
 */
public class AdvertisingBean {

    /**
     * id : 420ba9a3-501b-4ed7-b5c2-140a40bbeeb0
     * substationId : 9784e38e-2b9f-4b04-8326-1f1eadb00837
     * advName : 广告3
     * advUrl : 20190302094646.jpg
     * advType : 2
     * creater : 0785166c-fb6f-4f90-801f-f8dba372bc07
     * createTime : 2019-03-02T09:53:13.047
     */

    private String id;
    private String substationId;
    private String advName;
    private String advUrl;
    private int advType;//广告类型（1:首页顶部，2：中间广告大图，3/4：中间小图，5：俱乐部顶部图片，6：顾问顶部图片，7：活动，8-莱购）
    private String creater;
    private String createTime;
    private String url;
    private String advUrlType;
    private String urlType;

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getAdvUrlType() {
        return advUrlType;
    }

    public void setAdvUrlType(String advUrlType) {
        this.advUrlType = advUrlType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubstationId() {
        return substationId;
    }

    public void setSubstationId(String substationId) {
        this.substationId = substationId;
    }

    public String getAdvName() {
        return advName;
    }

    public void setAdvName(String advName) {
        this.advName = advName;
    }

    public String getAdvUrl() {
        return advUrl;
    }

    public void setAdvUrl(String advUrl) {
        this.advUrl = advUrl;
    }

    public int getAdvType() {
        return advType;
    }

    public void setAdvType(int advType) {
        this.advType = advType;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
