package com.cherishTang.laishou.laishou.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.AppManager;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.custom.recyclerview.GridSpacingItemDecoration;
import com.cherishTang.laishou.laishou.main.activity.PlaceOrderResultActivity;
import com.cherishTang.laishou.laishou.user.adapter.MyCollectListAdapter;
import com.cherishTang.laishou.laishou.user.adapter.SignInLineAdapter;
import com.cherishTang.laishou.laishou.user.bean.PageRequestBean;
import com.cherishTang.laishou.laishou.user.fragment.MyCollectionFragment;
import com.cherishTang.laishou.laishou.user.fragment.OrderListSimpleFragment;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by CherishTang on 2019/3/5.
 * describe：我的收藏
 */
public class MyCollectActivity extends BaseActivity {

    private Bundle bundle;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

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
        setFragment(MyCollectionFragment.instantiate(bundle));
        mToolbar.setNavigationOnClickListener(v -> {
            setResult(RESULT_OK);
            AppManager.getAppManager().finishActivity(this);
        });
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void initData() {

    }

    @OnClick({})
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MyCollectActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public static void show(Fragment context, Bundle bundle) {
        Intent intent = new Intent(context.getActivity(), MyCollectActivity.class);
        intent.putExtras(bundle);
        context.startActivityForResult(intent,ConstantsHelper.COLLECT);
    }
}
