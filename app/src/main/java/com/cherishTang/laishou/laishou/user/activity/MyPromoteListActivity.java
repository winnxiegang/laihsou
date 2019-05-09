package com.cherishTang.laishou.laishou.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.QrCodeDialog;
import com.cherishTang.laishou.laishou.activity.activity.HotDetailActivity;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.laishou.user.fragment.MyCircleListFragment;
import com.cherishTang.laishou.laishou.user.fragment.MyPromoteListFragment;
import com.cherishTang.laishou.laishou.user.wight.PromoteQrCodeDialog;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/3/10.
 * 我的推广
 */

public class MyPromoteListActivity extends BaseActivity {
    private Bundle bundle;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private CustomProgressDialog customProgressDialog;
    @Override
    public String setTitleBar() {
        return "我的推广";
    }

    @Override
    public int initLayout() {
        return R.layout.base_fragment_container;
    }

    @Override
    public void initView() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        mFragmentManager = getSupportFragmentManager();
        setFragment(MyPromoteListFragment.instantiate(bundle));
        mToolbar.inflateMenu(R.menu.toolbar_menu_share);
        mToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.toolbar_share:
                    getShareLink();
                    break;
                case R.id.toolbar_download:
                    createQrCode();
                    break;
            }
            return false;
        });
    }


    private void getShareLink() {
        ApiHttpClient.getMyPromoteShareLink(this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(MyPromoteListActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在生成分享链接，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(MyPromoteListActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<String> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s,
                        new TypeToken<ResultBean<String>>() {
                        }.getType());
                if (resultBean.isSuccess() && !StringUtil.isEmpty(resultBean.getData())) {
                    share(resultBean.getData());
                } else {
                    errorMsgShow(resultBean.getCode() + "", resultBean.getMessage(), ConstantsHelper.REQUEST_ERROR_LOGIN);
                }
            }
        });
    }


    private void createQrCode() {
        ApiHttpClient.getQrCode(this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(MyPromoteListActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在生成分享链接，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(MyPromoteListActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<String> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s,
                        new TypeToken<ResultBean<String>>() {
                        }.getType());
                if (resultBean.isSuccess() && !StringUtil.isEmpty(resultBean.getData())) {
                    new PromoteQrCodeDialog(MyPromoteListActivity.this)
                            .setMessage(resultBean.getData())
                            .builder()
                            .show();
                } else {
                    errorMsgShow(resultBean.getCode() + "", resultBean.getMessage(), ConstantsHelper.REQUEST_ERROR_LOGIN);
                }
            }
        });
    }

    /**
     * 调用友盟分享
     * 分享平台：QQ,QQ空间，微信好友，微信朋友圈
     * 分享方式：web链接
     */
    private void share(String sharelink) {
        UMWeb web = new UMWeb(sharelink);//链接
        web.setTitle("要想瘦，找莱瘦");//标题
        web.setThumb(new UMImage(this, R.mipmap.ic_launcher));  //缩略图
        web.setDescription("点击跳转" + getResources().getString(R.string.app_name) +
                "app应用程序查看详情");//描述

        new ShareAction(this)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ,
                        SHARE_MEDIA.QZONE,
                        SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener).open();

    }

    /**
     * 分享回调监听
     * 开始、分享成功、分享失败、取消分享
     */
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
//            if (shareDialog != null && shareDialog.isShowing())
//                shareDialog.dismiss();
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShort(MyPromoteListActivity.this, "分享成功");
            finish();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort(MyPromoteListActivity.this, "分享失败");
            finish();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort(MyPromoteListActivity.this, "你取消了分享");
        }
    };

    @Override
    public void initData() {

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

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MyPromoteListActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
