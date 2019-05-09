package com.cherishTang.laishou.custom.swiperefreshlayout.bean;

import java.io.Serializable;

/**
 * Created by CherishTang on 2018/7/3.
 * 刷新加载操作时间
 */

public class RefreshDateBean implements Serializable{
    private static final long serialVersionUID = -6167432530216364332L;
    private String tag;
    private String time;

    public RefreshDateBean(String tag, String time) {
        this.tag = tag;
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
