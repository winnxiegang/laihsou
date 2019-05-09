package com.cherishTang.laishou.laishou.user.bean;

/**
 * Created by CherishTang on 2019/3/20.
 * describe:上传图片
 */
public class AddPhotoBean {
    private String picture;//图片集合用;隔开
    private String introduce;//简介

    public AddPhotoBean() {
    }

    public AddPhotoBean(String picture, String introduce) {
        this.picture = picture;
        this.introduce = introduce;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
