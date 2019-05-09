package com.cherishTang.laishou.welcome;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.LockViewActivity;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.api.AppManager;
import com.cherishTang.laishou.api.AppSettingHelper;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.laishou.main.activity.MainActivity;
import com.cherishTang.laishou.util.interceptor.LoginNavigationCallbackImpl;
import com.cherishTang.laishou.util.log.ToastUtils;

import java.util.Timer;

import butterknife.BindView;

/**
 * Created by 方舟 on 2017/5/15.
 * 启动页
 */

public class LaunchActivity extends BaseActivity {
    @BindView(R.id.launcher_img)
    ImageView img;
    private Runnable delayRunable;
    private Handler handler = new Handler();
    private int countDown = 3;
    private Timer timer;
    // 所需的全部权限
    static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

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
        return R.layout.launch_layout_new;
    }

    @Override
    public void initView() {
        Glide.with(this)
                .load(R.mipmap.launch_image)
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)//设置画质
                .into(img);
        if (checkPermission(PERMISSIONS)) {
            requestPermission(ConstantsHelper.REQUEST_CODE, this, PERMISSIONS);
            return;
        }
        init();
    }

    @Override
    public void initData() {

    }

    private void init() {
        if (UserAccountHelper.isFirstRun()) {
            WelcomeActivity.show(this);
        } else {
            initDataView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ConstantsHelper.REQUEST_CODE:
                if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED)
                    init();
                else {
                    if (checkPermission(PERMISSIONS)) {
                        requestPermission(ConstantsHelper.REQUEST_CODE, this, PERMISSIONS);
                        return;
                    }
                }
                break;
            case 0:
                if (resultCode == RESULT_OK && data != null) {
                    initDataView();
                }
                break;
            case ConstantsHelper.LOCKVIEW_ISOPEN:
                if (resultCode == RESULT_OK) {
                    MainActivity.show(LaunchActivity.this);
                    finish();
                } else if (resultCode == RESULT_CANCELED) {
                    finish();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initDataView() {
        handler.postDelayed(runnable, 1000);
    }

    /**
     * show the LaunchActivity
     *
     * @param context context
     */
    public static void show(Context context) {
        Intent intent = new Intent(context, LaunchActivity.class);
        context.startActivity(intent);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            countDown--;
            handler.postDelayed(this, 1000);
            if (countDown == 0) {
                intentActivity();
            }
        }
    };

    /**
     * 判断跳转的activity
     * 如果开启手势密码则跳转手势密码页面
     * 如果没有开启手势密码则跳转到主页
     */
    private void intentActivity() {
        if (AppSettingHelper.getGesturesIsOpen() && !AppManager.isActivityTop(LockViewActivity.class, LaunchActivity.this)) {
            Intent intent = new Intent(LaunchActivity.this, LockViewActivity.class);
            startActivityForResult(intent, ConstantsHelper.LOCKVIEW_ISOPEN);
        } else {
            ARouter.getInstance().build(ConstantsHelper.MAIN)
                    .with(new Bundle())
                    .navigation(this, new LoginNavigationCallbackImpl());
            finish();
        }
    }
}
