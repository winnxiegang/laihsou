package com.cherishTang.laishou.laishou.activity.activity;

import android.os.Bundle;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.laishou.main.fragment.VedioListFragment;

public class VedioListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String setTitleBar() {
        return "视频列表";
    }

    @Override
    public int initLayout() {
        return R.layout.activity_vedio_list;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.fragment_container, new VedioListFragment())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
    }
}
