package com.cherishTang.laishou.laishou.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cherishTang.laishou.base.BaseViewPagerFragment;
import com.cherishTang.laishou.laishou.club.fragment.ClubListFragment;
import com.cherishTang.laishou.laishou.consultant.fragment.ConsultantListFragment;

/**
 * Created by CherishTang on 2019/2/28.
 * 浏览记录
 */

public class HistorySimpleFragment extends BaseViewPagerFragment {
    @Override
    protected PagerInfo[] getPagers() {
        PagerInfo[] infoList = new PagerInfo[3];
        infoList[0] = new PagerInfo("顾问", ConsultantListFragment.class, getBundle(0));
        infoList[1] = new PagerInfo("俱乐部", ClubListFragment.class, getBundle(1));
        infoList[2] = new PagerInfo("莱瘦圈", LaiShowFragment.class, getBundle(2));
        return infoList;
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new HistorySimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public Bundle getBundle(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(LaiShowFragment.ARG_PAGE, page);
        return bundle;
    }

}
