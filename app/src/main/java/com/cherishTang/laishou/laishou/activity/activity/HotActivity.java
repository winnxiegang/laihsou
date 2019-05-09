package com.cherishTang.laishou.laishou.activity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.bean.base.ResultListBean;
import com.cherishTang.laishou.custom.customlayout.CustomBannerPicture;
import com.cherishTang.laishou.custom.customlayout.CustomTextView;
import com.cherishTang.laishou.custom.empty.EmptyLayout;
import com.cherishTang.laishou.custom.recyclerview.FullyLinearLayoutManager;
import com.cherishTang.laishou.laishou.activity.adapter.HotActivityAdapter;
import com.cherishTang.laishou.laishou.activity.bean.HotActivityBean;
import com.cherishTang.laishou.laishou.activity.bean.HotActivityRequestBean;
import com.cherishTang.laishou.laishou.activity.fragment.HotActivityFragment;
import com.cherishTang.laishou.laishou.main.bean.AdvertisingBean;
import com.cherishTang.laishou.laishou.main.bean.RequestSubstationBean;
import com.cherishTang.laishou.laishou.main.fragment.LaiShowFragment;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.sqlite.BannerBean;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/2/25.
 * 热门活动
 */

public class HotActivity extends BaseActivity {

    private Bundle bundle;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    @Override
    public String setTitleBar() {
        return "热门活动";
    }

    @Override
    public int initLayout() {
        return R.layout.base_fragment_container;
    }

    @Override
    public void initView() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        bundle.putBoolean("isShowHeadView",true);
        mFragmentManager = getSupportFragmentManager();
        setFragment(HotActivityFragment.instantiate(bundle));
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


    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, HotActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
