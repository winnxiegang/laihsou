package com.cherishTang.laishou.laishou.user.bean;

import java.util.List;

/**
 * Created by CherishTang on 2019/3/20.
 * describe
 */
public class MyCircleBean {

    /**
     * id : a073aba6-3ead-4e0e-8b2e-cc22fe96b915
     * introduce : 这是一条秋裤
     * picture : 20190320180737.jpg;20190320180737.jpg;20190320180737.jpg;20190320180737.jpg;20190320180738.jpg;
     * photoList : ["http://47.99.100.88:81/file/showimg?filename=20190320180737.jpg","http://47.99.100.88:81/file/showimg?filename=20190320180737.jpg","http://47.99.100.88:81/file/showimg?filename=20190320180737.jpg","http://47.99.100.88:81/file/showimg?filename=20190320180737.jpg","http://47.99.100.88:81/file/showimg?filename=20190320180738.jpg","http://47.99.100.88:81/file/showimg?filename="]
     * name : 方爸爸
     * headImg : http://47.99.100.88:81/file/showimg?filename=20190318173653.jpg
     * createTime : 2019-03-20T18:10:47.22
     * strCreateTime : 2019年03月20日发表
     */

    private String id;
    private String introduce;
    private String picture;
    private String name;
    private String headImg;
    private String createTime;
    private String strCreateTime;
    private List<String> photoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
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

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }
}
