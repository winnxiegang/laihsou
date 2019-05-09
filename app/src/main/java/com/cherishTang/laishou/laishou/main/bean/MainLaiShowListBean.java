package com.cherishTang.laishou.laishou.main.bean;

/**
 * Created by CherishTang on 2019/3/2.
 * describe
 */
public class MainLaiShowListBean {
    private int page;
    private int rows;
    private String substationId;
    private int UrlType	;//1:精选;2:活动;3:专访

    public MainLaiShowListBean(int page, int rows, String substationId) {
        this.page = page;
        this.rows = rows;
        this.substationId = substationId;
    }

    public MainLaiShowListBean(int page, int rows, String substationId, int UrlType) {
        this.page = page;
        this.rows = rows;
        this.substationId = substationId;
        this.UrlType = UrlType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSubstationId() {
        return substationId;
    }

    public void setSubstationId(String substationId) {
        this.substationId = substationId;
    }

    public int getArticleType() {
        return UrlType;
    }

    public void setArticleType(int articleType) {
        this.UrlType = articleType;
    }
}
