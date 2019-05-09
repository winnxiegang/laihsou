package com.cherishTang.laishou.enumbean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CherishTang on 2019/3/12.
 * 	登录角色（5:顾问;6:会员）
 */
public enum LoginTypeEnum {
    @SerializedName("5")
    consultant(5, "顾问"),
    @SerializedName("6")
    members(6, "会员");

    private String name;
    private Integer index;

    private LoginTypeEnum(Integer index, String name) {
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
