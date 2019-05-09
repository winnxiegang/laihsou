package com.cherishTang.laishou.laishou.main.bean;

import com.cherishTang.laishou.laishou.club.bean.ClubBean;
import com.cherishTang.laishou.laishou.consultant.bean.ConsultantBean;

import java.util.List;

/**
 * Created by CherishTang on 2019/3/4.
 * describe
 */
public class RecommendBean {
    private List<ClubBean> clubList;
    private List<ConsultantBean> counselorList;

    public List<ClubBean> getClubList() {
        return clubList;
    }

    public void setClubList(List<ClubBean> clubList) {
        this.clubList = clubList;
    }

    public List<ConsultantBean> getCounselorList() {
        return counselorList;
    }

    public void setCounselorList(List<ConsultantBean> counselorList) {
        this.counselorList = counselorList;
    }
}
