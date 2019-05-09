package com.cherishTang.laishou.sqlite;

import android.support.annotation.DrawableRes;

/**
 * Created by 方舟 on 2017/6/14.
 * 首页banner图
 */

public class BannerBean {


    private long modifyTime;
    private String path;//图片地址
    private String imgDiscribe;//图片描述
    private String linkPath;//点击图片跳转的页面地址
    private @DrawableRes
    int localPath;
    private String urlType = "1";//点击图片跳转类型
    private String Typeid = "1";//跳转界面需要的id

    public String getTypeid() {
        return Typeid;
    }

    public void setTypeid(String typeid) {
        Typeid = typeid;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public BannerBean(String path) {
        this.path = path;
    }

    public BannerBean(String path, String linkPath) {
        this.path = path;
        this.linkPath = linkPath;
    }

    public BannerBean(String path, String linkPath, String urlType, String Typeid) {
        this.path = path;
        this.linkPath = linkPath;
        this.urlType = urlType;
        this.Typeid = Typeid;
    }

    public BannerBean() {
    }

    public BannerBean(int localPath) {
        this.localPath = localPath;
    }

    public int getLocalPath() {
        return localPath;
    }

    public void setLocalPath(int localPath) {
        this.localPath = localPath;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }

    public String getImgDiscribe() {
        return imgDiscribe;
    }

    public void setImgDiscribe(String imgDiscribe) {
        this.imgDiscribe = imgDiscribe;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
