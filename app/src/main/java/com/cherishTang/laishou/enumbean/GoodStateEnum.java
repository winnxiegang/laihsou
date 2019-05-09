package com.cherishTang.laishou.enumbean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CherishTang on 2019/3/5.
 * 商品状态：//状态(1:未领取;2:已领取;3:不能领取;9:删除)
 */
public enum GoodStateEnum {
    @SerializedName("1")
    unReceive(1, "未领取"),
    @SerializedName("2")
    received(2, "已领取"),
    @SerializedName("3")
    notReceive(3, "不能领取"),
    @SerializedName("9")
    delete(2, "删除");

    private String name;
    private Integer index;

    private GoodStateEnum(Integer index, String name) {
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
