package com.cherishTang.laishou.enumbean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CherishTang on 2019/3/5.
 * 拼团状态
 */
public enum SignStateEnum {
    @SerializedName("1")
    spelling(1, "金额","元"),
    @SerializedName("2")
    spelled(2, "积分","莱币");

    private String name;
    private Integer index;
    private String unit;

    private SignStateEnum(Integer index, String name, String unit) {
        this.name = name;
        this.index = index;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public String getUnit() {
        return unit;
    }
}
