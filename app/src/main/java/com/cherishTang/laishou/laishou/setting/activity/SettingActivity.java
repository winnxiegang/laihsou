package com.cherishTang.laishou.laishou.setting.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.laishou.user.activity.LoginActivity;
import com.cherishTang.laishou.laishou.user.activity.SettingWebViewActivity;
import com.cherishTang.laishou.util.cache.GlideCacheUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by CherishTang on 2019/3/10.
 * describe
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_privacy)
    TextView tvPrivacy;
    @BindView(R.id.tv_modify_password)
    TextView tvModifyPassword;
    @BindView(R.id.tv_clean_up)
    TextView tvCleanUp;
    @BindView(R.id.tv_WeChat)
    TextView tvWeChat;
    @BindView(R.id.tv_QQ)
    TextView tvQQ;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.setting_exit)
    Button settingExit;

    @Override
    public String setTitleBar() {
        return "设置";
    }

    @Override
    public int initLayout() {
        return R.layout.setting_activity;
    }

    @Override
    public void initView() {
        try {
            tvCleanUp.setText(getCacheSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
       try{
           if (!UserAccountHelper.isLogin())
               settingExit.setVisibility(View.GONE);
           else
               settingExit.setVisibility(View.VISIBLE);
       }catch (Exception e){
           e.printStackTrace();
       }

    }

    @Override
    public void initData() {

    }

    private void onClickCleanUp() {
        DialogHelper.getConfirmDialog(this, "是否确定清理缓存?",
                (dialogInterface, i) -> {
                    try {
                        clearCache();
                        tvCleanUp.setText(getCacheSize());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).show();
    }

    /**
     * 计算缓存大小
     */
    private String getCacheSize() {
        String size = null;
        try {
            long cacheSize = GlideCacheUtil.getInstance().getFolderSize(
                    new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + File.separator + ConstantsHelper.ROOTFILEPATH));
            cacheSize += GlideCacheUtil.getInstance().getFolderSize(
                    new File(getCacheDir() + "/" +
                            InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR));
            cacheSize += GlideCacheUtil.getInstance().getFolderSize(getCacheDir());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheSize += GlideCacheUtil.getInstance().getFolderSize(getExternalCacheDir());
            }

            size = GlideCacheUtil.getFormatSize(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 清理换攒
     */
    private void clearCache() {
        GlideCacheUtil.getInstance().clearImageAllCache(this);
        GlideCacheUtil.getInstance().deleteFolderFile(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + ConstantsHelper.ROOTFILEPATH, false);
        GlideCacheUtil.getInstance().deleteFolderFile(getCacheDir().getAbsolutePath(), false);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            GlideCacheUtil.getInstance().deleteFolderFile(getExternalCacheDir().getAbsolutePath(), false);
        }
    }


    @OnClick({R.id.setting_exit, R.id.tv_clean_up,  R.id.tv_notice, R.id.tv_modify_password,
            R.id.tv_about_us,R.id.tv_privacy})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_privacy:
                bundle.putString("loadUrl","http://share.laiscn.com/agreement");
                bundle.putString("title","隐私");
                SettingWebViewActivity.show(this,bundle);
                break;
            case R.id.tv_modify_password:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(this, this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                ModifyPasswordActivity.show(this);
                break;
            case R.id.tv_notice:
                NoticeActivity.show(this);
                break;
            case R.id.setting_exit:
                DialogHelper.getConfirmDialog(this, "确定退出当前账号吗？", (dialogInterface, i) -> {
                    UserAccountHelper.exit();
                    setResult(RESULT_OK);
                    LoginActivity.show(this);
                }).show();
                break;
            case R.id.tv_clean_up:
                onClickCleanUp();
                break;
            case R.id.tv_about_us:
//                AboutUsActivity.show(this);
                bundle.putString("loadUrl","http://share.laiscn.com/about");
                bundle.putString("title","关于莱瘦");
                SettingWebViewActivity.show(this,bundle);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantsHelper.LOGIN_SUCCESS && data != null &&
                data.getBooleanExtra("result", false)) {
            updateView();
            return;
        }
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    public static void show(Fragment context) {
        Intent intent = new Intent(context.getActivity(), SettingActivity.class);
        context.startActivityForResult(intent, ConstantsHelper.LOGIN_EXIT);
    }

}
