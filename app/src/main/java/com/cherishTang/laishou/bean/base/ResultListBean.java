package com.cherishTang.laishou.bean.base;

import com.cherishTang.laishou.bean.Code.ResponseCode;

import java.util.List;

/**
 * Created by 方舟
 */
public class ResultListBean<T> {

    private List<T> data;
    private int code;
    private String message;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return code == ResponseCode.SUCCESS;
    }

    @Override
    public String toString() {
        return "code:" + code
                + " + message:" + message
                + " + result:" + data.toString();
    }
}
