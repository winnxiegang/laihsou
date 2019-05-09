package com.cherishTang.laishou.laishou.consultant.bean;

import com.cherishTang.laishou.laishou.user.bean.ArticleBean;

import java.util.List;

/**
 * Created by CherishTang on 2019/3/2.
 * describe
 */
public class ConsultantBean {

    /**
     * id : 394d0c0b-78c4-483c-aab2-74b047565409
     * clubId : c919de8b-6bfd-43b1-bb67-1e810a74c0f7
     * loginId : 0785166c-fb6f-4f90-801f-f8dba372bc07
     * name : 张三
     * mobile : 12331111
     * label : 11,112,3，2，3
     * states : 0
     * creater : 0785166c-fb6f-4f90-801f-f8dba372bc07
     * createTime : 2019-02-27T11:46:14.153
     * isRecommend : 1
     * recommendTime : 2019-02-28T14:08:23.453
     * substationId : 9784e38e-2b9f-4b04-8326-1f1eadb00837
     */

    private String id;//俱乐部id
    private String clubId;//分站id
    private String loginId;
    private String name;
    private String mobile;
    private String label;
    private int states;//状态（0:正常;1:删除）
    private String creater;
    private String createTime;
    private int isRecommend;//是否推荐(0:非;1:是)
    private String recommendTime;//推荐时间
    private String substationId;//分站id
    private float score;
    private String userNumber;
    private String introduce;
    private String clubName;
    private List<ArticleBean> articleList;
    private List<ElegantBean> mienList;
    private List<UserListBean> userList;

    private String headImg;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(String recommendTime) {
        this.recommendTime = recommendTime;
    }

    public String getSubstationId() {
        return substationId;
    }

    public void setSubstationId(String substationId) {
        this.substationId = substationId;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public List<ArticleBean> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<ArticleBean> articleList) {
        this.articleList = articleList;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public List<ElegantBean> getMienList() {
        return mienList;
    }

    public void setMienList(List<ElegantBean> mienList) {
        this.mienList = mienList;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }
}
