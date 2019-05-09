package com.cherishTang.laishou.laishou.user.bean;

/**
 * Created by CherishTang on 2019/3/13.
 * describe
 */
public class ElegantUploadImageBean {
    private String fileName;
    private String fileBase64;
    private String picture;

    public ElegantUploadImageBean() {
    }

    public ElegantUploadImageBean(String picture) {
        this.picture = picture;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileBase64() {
        return fileBase64;
    }

    public void setFileBase64(String fileBase64) {
        this.fileBase64 = fileBase64;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
