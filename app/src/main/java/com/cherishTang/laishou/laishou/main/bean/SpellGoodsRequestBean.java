package com.cherishTang.laishou.laishou.main.bean;

/**
 * Created by CherishTang on 2019/3/2.
 * describe：拼团商品列表
 */
public class SpellGoodsRequestBean {
    private int page;
    private int rows;
    private String substationId;
    private Integer spellType;//1:热销;2:特惠;3:化妆品;4：健身

    public SpellGoodsRequestBean(int page, int rows, String substationId, Integer spellType) {
        this.page = page;
        this.rows = rows;
        this.substationId = substationId;
        this.spellType = spellType;
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

    public Integer getSpellType() {
        return spellType;
    }

    public void setSpellType(Integer spellType) {
        this.spellType = spellType;
    }
}
