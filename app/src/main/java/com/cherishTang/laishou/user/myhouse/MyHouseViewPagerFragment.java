package com.cherishTang.laishou.user.myhouse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cherishTang.laishou.base.BaseViewPagerFragment;


public class MyHouseViewPagerFragment extends BaseViewPagerFragment {
    private Bundle bundle;

    @Override
    protected PagerInfo[] getPagers() {
        PagerInfo[] infoList = new PagerInfo[1];
        infoList[0] = new PagerInfo("我的房源", MyHousePageFragment.class, getBundle(0));
        return infoList;
    }

    public Bundle getBundle(int page) {
        bundle = bundle == null ? new Bundle() : bundle;
        bundle.putInt("ARG_PAGE", page);
        return bundle;
    }

    public BaseViewPagerAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        bundle = getArguments();
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new MyHouseViewPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}