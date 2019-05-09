package com.cherishTang.laishou.bean;

import java.util.List;

/**
 * Created by CherishTang on 2018/7/11.
 * popupWindow
 */

public class PopupWindowBean {
    private Integer index;
    private String name;
    private Integer selectId;
    private boolean isSingleSelected;

    private List<PopupWindowBean> childList;

    public PopupWindowBean() {
    }

    public PopupWindowBean(Integer index, String name) {
        this.index = index;
        this.name = name;
    }

    public PopupWindowBean(Integer index, String name, boolean isSingleSelected) {
        this.index = index;
        this.name = name;
        this.isSingleSelected = isSingleSelected;
    }

    public Integer getSelectId() {
        return selectId;
    }

    public void setSelectId(Integer selectId) {
        this.selectId = selectId;
    }

    public boolean isSingleSelected() {
        return isSingleSelected;
    }

    public void setSingleSelected(boolean singleSelected) {
        isSingleSelected = singleSelected;
    }

    public List<PopupWindowBean> getChildList() {
        return childList;
    }

    public void setChildList(List<PopupWindowBean> childList) {
        this.childList = childList;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
