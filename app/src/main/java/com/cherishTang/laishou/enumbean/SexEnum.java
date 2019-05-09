package com.cherishTang.laishou.enumbean;

import com.cherishTang.laishou.bean.PopupWindowBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CherishTang on 2019/1/24.
 * 性别
 */
public enum SexEnum {
    female(0, "女"),
    male(1, "男");

    private String name;
    private Integer index;

    private SexEnum(Integer index, String name) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }


}
