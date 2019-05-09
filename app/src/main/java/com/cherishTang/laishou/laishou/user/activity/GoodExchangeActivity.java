package com.cherishTang.laishou.laishou.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.laishou.user.fragment.GoodExchangeFragment;
import com.cherishTang.laishou.laishou.user.fragment.SwithchFriendFragment;

import butterknife.OnClick;

/**
 * Created by CherishTang on 2019/3/8.
 * describe：商品兑换
 */
public class GoodExchangeActivity extends BaseActivity {

    private Bundle bundle;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    public final static int PK_REQUEST_CODE = 202;//请求pk
    @Override
    public String setTitleBar() {
        return null;
    }

    @Override
    public int initLayout() {
        return R.layout.base_fragment_container;
    }

    @Override
    public void initView() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        mFragmentManager = getSupportFragmentManager();
        setFragment(GoodExchangeFragment.instantiate(bundle));
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
    @Override
    public void initData() {

    }

    @OnClick({})
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public static void show(Activity context, Bundle bundle) {
        Intent intent = new Intent(context, GoodExchangeActivity.class);
        intent.putExtras(bundle);
        context.startActivityForResult(intent,PK_REQUEST_CODE);
    }

}
