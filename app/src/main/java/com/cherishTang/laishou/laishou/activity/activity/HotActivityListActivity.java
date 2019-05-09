package com.cherishTang.laishou.laishou.activity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.laishou.activity.fragment.HotActivityFragment;

/**
 * Created by CherishTang on 2019/3/11.
 * 热门活动
 */

public class HotActivityListActivity extends BaseActivity {
    private Bundle bundle;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

    @Override
    public String setTitleBar() {
        return "活动";
    }

    @Override
    public int initLayout() {
        return R.layout.base_fragment_container;
    }

    @Override
    public void initView() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        mFragmentManager = getSupportFragmentManager();
        setFragment(HotActivityFragment.instantiate(bundle));
    }

    @Override
    public void initData() {

    }

    public void setFragment(Fragment fragment) {
        if (fragment == null) return;
        if (mCurrentFragment != fragment) {
            if (fragment.isAdded()) {
                mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commit();
            } else {
                mFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
            mCurrentFragment = fragment;
        }
    }


    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, HotActivityListActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
