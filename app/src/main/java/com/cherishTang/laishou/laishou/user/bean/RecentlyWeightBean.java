package com.cherishTang.laishou.laishou.user.bean;

import java.util.List;

/**
 * Created by CherishTang on 2019/3/22.
 * describe
 */
public class RecentlyWeightBean {
    private double weightDiff;
    private List<MyWeightRecordBean> list;

    public double getWeightDiff() {
        return weightDiff;
    }

    public void setWeightDiff(double weightDiff) {
        this.weightDiff = weightDiff;
    }

    public List<MyWeightRecordBean> getList() {
        return list;
    }

    public void setList(List<MyWeightRecordBean> list) {
        this.list = list;
    }
}
