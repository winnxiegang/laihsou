package com.cherishTang.laishou.custom.picDialog.bean;


import android.support.annotation.DrawableRes;

/**
 * 圈子：九宫格图片展示，Image封装
 * Created by xiaoke on 2016/5/9.
 */
public class ImageInfo {

    private Object url;
    private int width;
    private int height;
    private @DrawableRes int imgRes;
    public ImageInfo(Object url, int width, int height) {
        if (url != null && url instanceof Integer) {
            this.url = url;
            this.imgRes = (int) url;
        } else {
            this.url = url;
        }
        this.width = width;
        this.height = height;
    }

    public ImageInfo(int imgRes, int width, int height ) {
        this.width = width;
        this.height = height;
        this.imgRes = imgRes;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {

        return "image---->>url="+url+"width="+width+"height"+height;
    }
}
