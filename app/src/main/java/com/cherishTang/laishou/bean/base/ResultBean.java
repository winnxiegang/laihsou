package com.cherishTang.laishou.bean.base;

import com.cherishTang.laishou.bean.Code.ResponseCode;
import com.cherishTang.laishou.util.apiUtil.StringUtil;

/**
 * Created by CherishTang
 * on 16-5-23.
 */
public class ResultBean<T> {

    private T data;
    private int code;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return StringUtil.isEmpty(message) ? (code == ResponseCode.SUCCESS ? "操作成功" : "操作失败") : message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
