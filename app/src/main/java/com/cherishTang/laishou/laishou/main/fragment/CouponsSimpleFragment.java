package com.cherishTang.laishou.laishou.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cherishTang.laishou.base.BaseViewPagerFragment;

/**
 * Created by CherishTang on 2019/3/1.
 * 折扣券
 */

public class CouponsSimpleFragment extends BaseViewPagerFragment {
    @Override
    protected PagerInfo[] getPagers() {
        PagerInfo[] infoList = new PagerInfo[3];
        infoList[0] = new PagerInfo("未使用", CouponsFragment.class, getBundle(0));
        infoList[1] = new PagerInfo("已使用", CouponsFragment.class, getBundle(1));
        infoList[2] = new PagerInfo("已过期", CouponsFragment.class, getBundle(2));
        return infoList;
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new CouponsSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    public Bundle getBundle(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(CouponsFragment.ARG_PAGE, page);
        return bundle;
    }
}
