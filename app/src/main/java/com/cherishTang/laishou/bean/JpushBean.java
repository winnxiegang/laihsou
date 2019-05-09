package com.cherishTang.laishou.bean;

/**
 * Created by 方舟 on 2017/11/28.
 * 极光推送
 */

public class JpushBean {
    private String title;
    private String contents;
    private String extra;
    private Long readedDate;
    private Long acceptDate;
    private long notifactionId;
    private int isReaded;

    public JpushBean() {
    }

    public JpushBean(String title, String contents, String extra, Long readedDate, Long acceptDate, long notifactionId, int isReaded) {
        this.title = title;
        this.contents = contents;
        this.extra = extra;
        this.readedDate = readedDate;
        this.acceptDate = acceptDate;
        this.notifactionId = notifactionId;
        this.isReaded = isReaded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Long getReadedDate() {
        return readedDate;
    }

    public void setReadedDate(Long readedDate) {
        this.readedDate = readedDate;
    }

    public Long getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Long acceptDate) {
        this.acceptDate = acceptDate;
    }

    public long getNotifactionId() {
        return notifactionId;
    }

    public void setNotifactionId(long notifactionId) {
        this.notifactionId = notifactionId;
    }

    public int getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(int isReaded) {
        this.isReaded = isReaded;
    }
}
