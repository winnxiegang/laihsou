package com.cherishTang.laishou.laishou.user.bean;

/**
 * Created by CherishTang on 2019/3/5.
 * describe
 */
public class PageRequestBean {
    private int page;
    private int rows;

    public PageRequestBean(int page, int rows) {
        this.page = page;
        this.rows = rows;
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
}
