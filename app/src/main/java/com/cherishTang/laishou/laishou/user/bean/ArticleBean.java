package com.cherishTang.laishou.laishou.user.bean;

/**
 * Created by CherishTang on 2019/3/5.
 * describe
 */
public class ArticleBean {

    /**
     * id : 6fab5d8e-31dc-49bf-a8f6-c291e3d74f9c
     * substationId : 9784e38e-2b9f-4b04-8326-1f1eadb00837
     * articleTitle : 好人
     * articleContent : 呵呵呵
     * status : 2
     * shareNumber : 0
     * likeNumber : 0
     * collectNumber : 0
     * noPassNote : 1
     * creater : 0785166c-fb6f-4f90-801f-f8dba372bc07
     * createrType : 1
     * createTime : 2019-02-28T15:23:46.223
     * clubId : null
     * articleType : 1
     * isVideo : 0
     * img : http://47.94.172.208:20192/file/showimg?filename=20190302094640.jpg
     * counselorId : null
     */

    private String id;
    private String substationId;
    private String articleTitle;
    private String articleContent;
    private int status;
    private int shareNumber;
    private int likeNumber;
    private int collectNumber;
    private String noPassNote;
    private String creater;
    private int createrType;
    private String createTime;
    private Object clubId;
    private int articleType;
    private int isVideo;
    private String img;
    private Object counselorId;
    private String simple;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubstationId() {
        return substationId;
    }

    public void setSubstationId(String substationId) {
        this.substationId = substationId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getShareNumber() {
        return shareNumber;
    }

    public void setShareNumber(int shareNumber) {
        this.shareNumber = shareNumber;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getCollectNumber() {
        return collectNumber;
    }

    public void setCollectNumber(int collectNumber) {
        this.collectNumber = collectNumber;
    }

    public String getNoPassNote() {
        return noPassNote;
    }

    public void setNoPassNote(String noPassNote) {
        this.noPassNote = noPassNote;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public int getCreaterType() {
        return createrType;
    }

    public void setCreaterType(int createrType) {
        this.createrType = createrType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getClubId() {
        return clubId;
    }

    public void setClubId(Object clubId) {
        this.clubId = clubId;
    }

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public int getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(int isVideo) {
        this.isVideo = isVideo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Object getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(Object counselorId) {
        this.counselorId = counselorId;
    }

    public String getSimple() {
        return simple;
    }

    public void setSimple(String simple) {
        this.simple = simple;
    }
}
