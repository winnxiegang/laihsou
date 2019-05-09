package com.cherishTang.laishou.laishou.setting.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.util.apiUtil.GetVersion;
import com.cherishTang.laishou.util.cache.GlideCacheUtil;
import com.cherishTang.laishou.util.log.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by CherishTang on 2019/3/10.
 * describe:关于莱瘦
 */
public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_go_to_score)
    TextView tvGoToScore;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.tv_cer_info)
    TextView tvCerInfo;
    @BindView(R.id.tv_plan)
    TextView tvPlan;

    @Override
    public String setTitleBar() {
        return "关于莱瘦";
    }

    @Override
    public int initLayout() {
        return R.layout.about_us_activity;
    }

    @Override
    public void initView() {
        tvVersion.setText("莱瘦"+ GetVersion.getVersion(this));
    }

    @Override
    public void initData() {

    }

    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

}
