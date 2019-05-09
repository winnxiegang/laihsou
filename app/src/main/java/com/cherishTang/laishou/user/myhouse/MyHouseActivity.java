package com.cherishTang.laishou.user.myhouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 方舟 on 2017/10/21.
 * 我发布的
 */

public class MyHouseActivity extends BaseActivity {
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.lay_content)
    LinearLayout layContent;
    private Gson gson;
    private Fragment myHouseViewPagerFragment;

    @Override
    public int initLayout() {
        return R.layout.base_fragment_container;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initData() {
        if (myHouseViewPagerFragment == null)
            myHouseViewPagerFragment = MyHouseViewPagerFragment.instantiate(new Bundle());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, myHouseViewPagerFragment).commit();
    }

    @OnClick({})
    public void OnClick(View view) {
    }

    @Override
    public void initView() {
    }


    public static void show(Context context) {
        Intent intent = new Intent(context, MyHouseActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String setTitleBar() {
        return "房源管理";
    }

}
