package com.cherishTang.laishou.enumbean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CherishTang on 2019/3/5.
 * 拼团状态
 */
public enum SpellStateEnum {
    @SerializedName("1")
    spelling(1, "拼团中"),
    @SerializedName("2")
    spellSuccess(1, "拼团成功"),
    @SerializedName("3")
    spelled(2, "已领取");

    private String name;
    private Integer index;

    private SpellStateEnum(Integer index, String name) {
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
