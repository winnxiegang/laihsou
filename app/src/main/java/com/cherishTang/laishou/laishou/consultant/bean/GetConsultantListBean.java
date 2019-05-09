package com.cherishTang.laishou.laishou.consultant.bean;

/**
 * Created by CherishTang on 2019/3/2.
 * describe
 */
public class GetConsultantListBean {
    private int page;
    private int rows;
    private String substationId;
    private String counselorName;
    private int orderType;//1:综合;2:人气;3:会员数;4:距离

    public GetConsultantListBean(int page, int rows, String substationId, String counselorName) {
        this.page = page;
        this.rows = rows;
        this.substationId = substationId;
        this.counselorName = counselorName;
    }

    public GetConsultantListBean(int page, int rows, String substationId, String counselorName, int orderType) {
        this.page = page;
        this.rows = rows;
        this.substationId = substationId;
        this.counselorName = counselorName;
        this.orderType = orderType;
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

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }
}
