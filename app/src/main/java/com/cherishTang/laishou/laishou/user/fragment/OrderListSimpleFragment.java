package com.cherishTang.laishou.laishou.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseViewPagerFragment;
import com.cherishTang.laishou.laishou.main.fragment.LaiShowFragment;

/**
 * Created by CherishTang on 2019/3/4.
 * 我的订单
 */

public class OrderListSimpleFragment extends BaseViewPagerFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.base_tablayout_viewpager;
    }


    @Override
    protected PagerInfo[] getPagers() {
        PagerInfo[] infoList = new PagerInfo[3];
        infoList[0] = new PagerInfo("积分订单", OrderListFragment.class, getBundle(1));
        infoList[1] = new PagerInfo("拼团订单", SpellGroupOrderListFragment.class, getBundle(2));
        infoList[2] = new PagerInfo("活动订单", ActivityOrderListFragment.class, getBundle(3));
        return infoList;
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new OrderListSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public Bundle getBundle(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(LaiShowFragment.ARG_PAGE, page);
        return bundle;
    }
}
