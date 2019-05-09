package com.cherishTang.laishou.bean;

import java.util.List;

/**
 * Created by 方舟 on 2018/1/26.
 * 反馈
 */

public class FeedBackBean {
    public  String FedbackType;
    public  String ProblemDescribe;
    public  String Linkman;
    public  String Linkway;
    public List<String> UrlBase64;
    private String IpAddress;

    public FeedBackBean(String fedbackType, String problemDescribe, String linkman, String linkway, List<String> urlBase64,
                        String ipAddress) {
        FedbackType = fedbackType;
        ProblemDescribe = problemDescribe;
        Linkman = linkman;
        Linkway = linkway;
        UrlBase64 = urlBase64;
        IpAddress = ipAddress;
    }

    public String getIpAddress() {
        return IpAddress;
    }

    public void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }

    public String getFedbackType() {
        return FedbackType;
    }

    public void setFedbackType(String fedbackType) {
        FedbackType = fedbackType;
    }

    public String getProblemDescribe() {
        return ProblemDescribe;
    }

    public void setProblemDescribe(String problemDescribe) {
        ProblemDescribe = problemDescribe;
    }

    public String getLinkman() {
        return Linkman;
    }

    public void setLinkman(String linkman) {
        Linkman = linkman;
    }

    public String getLinkway() {
        return Linkway;
    }

    public void setLinkway(String linkway) {
        Linkway = linkway;
    }

    public List<String> getUrlBase64() {
        return UrlBase64;
    }

    public void setUrlBase64(List<String> urlBase64) {
        UrlBase64 = urlBase64;
    }
}
