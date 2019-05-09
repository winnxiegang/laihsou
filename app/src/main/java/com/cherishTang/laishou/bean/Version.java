package com.cherishTang.laishou.bean;

/**
 * Created by 方舟 on 2016/12/7.
 * 版本信息
 */

public class Version {
    private boolean status;
    private String msg;
    private String code;
    private Result data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Result getData() {
        return data;
    }

    public void setData(Result data) {
        this.data = data;
    }

    public class  Result{
        private int VersionCode;
        private String VersionName;
        private String AppUrl;
        private String Content;
        private String Status;
        private String AddUser;
        private String AddTime;
        private Integer SeqId;

        public int getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(int versionCode) {
            VersionCode = versionCode;
        }

        public String getVersionName() {
            return VersionName;
        }

        public void setVersionName(String versionName) {
            VersionName = versionName;
        }

        public String getAppUrl() {
            return AppUrl;
        }

        public void setAppUrl(String appUrl) {
            AppUrl = appUrl;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getAddUser() {
            return AddUser;
        }

        public void setAddUser(String addUser) {
            AddUser = addUser;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String addTime) {
            AddTime = addTime;
        }

        public Integer getSeqId() {
            return SeqId;
        }

        public void setSeqId(Integer seqId) {
            SeqId = seqId;
        }
    }
}
