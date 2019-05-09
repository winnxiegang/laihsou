package com.cherishTang.laishou.laishou.user.bean;

/**
 * Created by CherishTang on 2019/3/1.
 * describe
 */
public class RegisterBean {
    private String userName;
    private String password;
    private String authCode;//验证码
    private String clubId;//非必传，俱乐部id

    public RegisterBean(String userName, String password, String authCode) {
        this.userName = userName;
        this.password = password;
        this.authCode = authCode;
    }

    public RegisterBean(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public RegisterBean(String userName) {
        this.userName = userName;
    }
}
