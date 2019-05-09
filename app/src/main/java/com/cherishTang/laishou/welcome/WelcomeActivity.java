package com.cherishTang.laishou.welcome;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.custom.customlayout.CustomViewPager;
import com.cherishTang.laishou.custom.customlayout.MyViewPager;
import com.cherishTang.laishou.util.log.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 方舟 on 2016/10/5.
 * <p>应用第一次安装显示的页面，会有立即体验的提示，后来就没有了
 */
public class WelcomeActivity extends BaseActivity implements MyViewPager.OnImageItemClickListener {
    @BindView(R.id.runApp_rightNow)
    ImageView runAppRightNow;
    @BindView(R.id.customViewPager)
    CustomViewPager customViewPager;
    // 所需的全部权限
    static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int REQUEST_CODE = 0; // 请求码

    private
    @DrawableRes
    int[] imgs = new int[]{R.mipmap.welcome_image};

    @Override
    public String setTitleBar() {
        return null;
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    @Override
    public int initLayout() {
        return R.layout.welcome_layout;
    }

    @Override
    public void initView() {
        if (checkPermission(PERMISSIONS)) {
            requestPermission(this, PERMISSIONS);
        }
    }

    public void initData() {
        customViewPager.init(imgs, this);
//        if(imgs.length==1) {
//            runAppRightNow.setVisibility(View.VISIBLE);
//        }else{
//            customViewPager.getViewPager().setOnImageItemClickListener(this);
//        }
        UserAccountHelper.setNotFirstRun();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            ToastUtils.showShort(this, "权限拒绝可能会导致部分功能无法正常使用");
            finish();
        } else if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            initData();
        }
    }


    public static void show(Activity context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivityForResult(intent, 0);
    }

    @OnClick(R.id.customViewPager)
    public void onClick(View view) {
        setResult(true);
    }

    private void setResult(boolean result) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("result", result);
        setResult(RESULT_OK, getIntent().putExtras(bundle));
        finish();
    }

    @Override
    public void onItemClick(int itemPosition) {

    }

    @Override
    public void getPosition(int itemPosition) {
//        if(itemPosition == imgs.length - 1) runAppRightNow.setVisibility(View.VISIBLE);
//        else runAppRightNow.setVisibility(View.GONE);
    }
}

