package com.cherishTang.laishou.laishou.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.laishou.club.fragment.LaiShouCircleFragment;
import com.cherishTang.laishou.laishou.main.fragment.VedioListFragment;

public class WenZhanListActivity extends BaseActivity {


    @Override
    public String setTitleBar() {
        return "热门列表";
    }

    @Override
    public int initLayout() {
        return R.layout.activity_wen_zhan_list;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.fragment_container, new LaiShouCircleFragment())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
    }
}
