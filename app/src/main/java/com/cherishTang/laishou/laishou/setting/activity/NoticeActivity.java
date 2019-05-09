package com.cherishTang.laishou.laishou.setting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by CherishTang on 2019/3/10.
 * describe
 */
public class NoticeActivity extends BaseActivity {

    @BindView(R.id.tv_clean_up)
    TextView tvCleanUp;
    @BindView(R.id.tglSound_no_disturb)
    ToggleButton tglSoundNoDisturb;
    @BindView(R.id.tglSound_sound)
    ToggleButton tglSoundSound;
    @BindView(R.id.tglSound_shake)
    ToggleButton tglSoundShake;

    @Override
    public String setTitleBar() {
        return "消息通知";
    }

    @Override
    public int initLayout() {
        return R.layout.notice_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    /**
     * @param view 当前视图
     */
    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, NoticeActivity.class);
        context.startActivity(intent);
    }


}
