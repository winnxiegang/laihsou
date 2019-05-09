package com.cherishTang.laishou.laishou.user.bean;

import com.cherishTang.laishou.enumbean.LoginTypeEnum;

/**
 * Created by CherishTang on 2019/3/12.
 * describe
 */
public class LoginResultBean {
    private String token;
    private LoginTypeEnum loginType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginTypeEnum getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginTypeEnum loginType) {
        this.loginType = loginType;
    }
}
