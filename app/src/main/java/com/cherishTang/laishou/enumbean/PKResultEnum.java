package com.cherishTang.laishou.enumbean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CherishTang on 2019/1/24.
 * 性别
 */
public enum PKResultEnum {
    @SerializedName("0")
    ongoing(0, "进行中"),
    @SerializedName("1")
    win(1, "赢"),
    @SerializedName("2")
    Failure(2, "输"),
    @SerializedName("3")
    flat(3, "平");
    private String name;
    private Integer index;

    private PKResultEnum(Integer index, String name) {
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
