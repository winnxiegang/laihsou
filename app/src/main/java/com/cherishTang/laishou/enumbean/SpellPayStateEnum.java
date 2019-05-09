package com.cherishTang.laishou.enumbean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CherishTang on 2019/3/5.
 * 拼团状态
 */
public enum SpellPayStateEnum {
    @SerializedName("1")
    unPay(1, "未支付"),
    @SerializedName("2")
    payed(2, "已支付"),
    @SerializedName("9")
    delete(9, "删除");

    private String name;
    private Integer index;

    private SpellPayStateEnum(Integer index, String name) {
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
