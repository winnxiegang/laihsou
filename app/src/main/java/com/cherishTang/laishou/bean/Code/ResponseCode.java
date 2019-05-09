package com.cherishTang.laishou.bean.Code;

/**
 * Created by 方舟 on 2017/10/16.
 * 服务器返回码
 */

public class ResponseCode {
    public static final String LOGIN_PAST = "201";//登录过期
    public static final String UNLOGIN = "202";//未登录
    public static final int SUCCESS = 200; //成功
    public static final String TOKEN_ERROR = "203"; //用户令牌信息不一致，请重新登陆
    public static final int TOKEN_NULL = 204; //令牌信息错误

}
