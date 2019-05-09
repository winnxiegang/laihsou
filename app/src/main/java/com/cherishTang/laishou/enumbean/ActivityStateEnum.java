package com.cherishTang.laishou.enumbean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CherishTang on 2019/3/5.
 * 活动状态（1:未开始;2:进行中;3:已结束;）
 */
public enum ActivityStateEnum {
    @SerializedName("1")
    unStart(1, "未开始"),
    @SerializedName("2")
    goIn(2, "进行中"),
    @SerializedName("3")
    end(3, "已结束");

    private String name;
    private Integer index;

    private ActivityStateEnum(Integer index, String name) {
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
