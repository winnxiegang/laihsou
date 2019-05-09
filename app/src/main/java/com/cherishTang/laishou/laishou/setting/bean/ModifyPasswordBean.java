package com.cherishTang.laishou.laishou.setting.bean;

/**
 * Created by CherishTang on 2019/3/12.
 * describe
 */
public class ModifyPasswordBean {
    private String oldPwd;
    private String newPwd;

    public ModifyPasswordBean(String oldPwd, String newPwd) {
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
