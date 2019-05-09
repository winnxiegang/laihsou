package com.cherishTang.laishou.laishou.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cherishTang.laishou.base.BaseViewPagerFragment;
import com.cherishTang.laishou.laishou.activity.fragment.HotActivityFragment;

/**
 * Created by CherishTang on 2019/2/25.
 * 莱瘦圈
 */

public class LaiShowSimpleFragment extends BaseViewPagerFragment {
    @Override
    protected PagerInfo[] getPagers() {
        PagerInfo[] infoList = new PagerInfo[4];
        infoList[0] = new PagerInfo("达人", TalentShowFragment.class, getBundle(4));
        infoList[1] = new PagerInfo("攻略", StoryFragment.class, getBundle(2));
        infoList[2] = new PagerInfo("莱课堂", LaiShowFragment.class, getBundle(1));
        infoList[3] = new PagerInfo("莱视界", ExclusiveInterviewFragment.class, getBundle(3));
        return infoList;
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new LaiShowSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    public Bundle getBundle(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(LaiShowFragment.ARG_PAGE, page);
        return bundle;
    }
}
