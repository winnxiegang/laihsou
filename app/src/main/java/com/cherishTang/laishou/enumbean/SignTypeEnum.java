package com.cherishTang.laishou.enumbean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CherishTang on 2019/3/5.
 * 报名方式（1:钱;2:积分）
 */
public enum SignTypeEnum {
    @SerializedName("1")
    money(1, "钱"),
    @SerializedName("2")
    integral(2, "积分");

    private String name;
    private Integer index;

    private SignTypeEnum(Integer index, String name) {
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
