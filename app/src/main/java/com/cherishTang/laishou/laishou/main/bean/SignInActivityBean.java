package com.cherishTang.laishou.laishou.main.bean;

import com.cherishTang.laishou.laishou.user.bean.GoodExchangeBean;

import java.util.List;

/**
 * Created by CherishTang on 2019/3/11.
 * describe:签到页面总返回值
 */
public class SignInActivityBean {
    private List<SignInDayBean> signLogList;
    private String integral;
    private boolean dayIsSign;
    private List<GoodExchangeBean> list;

    public List<SignInDayBean> getSignLogList() {
        return signLogList;
    }

    public void setSignLogList(List<SignInDayBean> signLogList) {
        this.signLogList = signLogList;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public boolean isDayIsSign() {
        return dayIsSign;
    }

    public void setDayIsSign(boolean dayIsSign) {
        this.dayIsSign = dayIsSign;
    }

    public List<GoodExchangeBean> getList() {
        return list;
    }

    public void setList(List<GoodExchangeBean> list) {
        this.list = list;
    }
}
