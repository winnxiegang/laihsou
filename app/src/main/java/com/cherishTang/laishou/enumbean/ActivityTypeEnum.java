package com.cherishTang.laishou.enumbean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CherishTang on 2019/3/6.
 * 活动类型（1:毅行;2:公益;3:聚会;4:培训;5:旅游;6:运动;7:美容;8:才艺;9:电影;）
 */
public enum ActivityTypeEnum {
    @SerializedName("1")
    yx(1, "毅行"),
    @SerializedName("2")
    gy(2, "公益"),
    @SerializedName("3")
    jh(3, "聚会"),
    @SerializedName("4")
    px(4, "培训"),
    @SerializedName("5")
    ly(5, "旅游"),
    @SerializedName("6")
    sport(6, "运动"),
    @SerializedName("7")
    mr(7, "美容"),
    @SerializedName("8")
    cy(8, "才艺"),
    @SerializedName("9")
    movie(9, "电影");

    private String name;
    private Integer index;

    private ActivityTypeEnum(Integer index, String name) {
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
