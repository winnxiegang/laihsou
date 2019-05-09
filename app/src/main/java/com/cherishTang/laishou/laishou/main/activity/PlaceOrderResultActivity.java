package com.cherishTang.laishou.laishou.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/2/28.
 * describe
 */
public class PlaceOrderResultActivity extends BaseActivity {
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_result_image)
    ImageView tvResultImage;
    @BindView(R.id.tv_result_tips)
    TextView tvResultTips;
    @BindView(R.id.button_share)
    Button buttonShare;
    @BindView(R.id.button_talking_goods)
    Button buttonTalkingGoods;
    private Bundle bundle;
    private int type;//1-拼团，2-兑换
    private String id;
    private CustomProgressDialog customProgressDialog;

    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.place_order_result_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        type = bundle.getInt("type");
        id = bundle.getString("id");
        tvTitle.setText(bundle.getString("title"));
        tvResult.setText(bundle.getString("result"));
        tvResultTips.setText(bundle.getString("resultTips"));
        if (bundle.getBoolean("isShowShareButton", false))
            buttonShare.setVisibility(View.VISIBLE);
        else
            buttonShare.setVisibility(View.GONE);

        if(type==1){
            buttonTalkingGoods.setVisibility(View.VISIBLE);
        }else{
            buttonTalkingGoods.setVisibility(View.GONE);
        }
    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PlaceOrderResultActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @OnClick({R.id.button_share})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_share:
                if (type == 1)
                    getShareLink();
                else
                    ToastUtils.showShort(PlaceOrderResultActivity.this, "暂不支持分享");
                break;
        }
    }

    /**
     * 获取分享链接
     */
    private void getShareLink() {
        ApiHttpClient.getSpellShareLink(new Gson().toJson(new IdBean(id)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(PlaceOrderResultActivity.this, R.style.loading_dialog);
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
                ToastUtils.showShort(PlaceOrderResultActivity.this, "网络状态不佳，请稍后再试！");
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

    /**
     * 调用友盟分享
     * 分享平台：QQ,QQ空间，微信好友，微信朋友圈
     * 分享方式：web链接
     */
    private void share(String shareLink) {
        //初始化web分享参数
        UMWeb web = new UMWeb(shareLink + "&auth=" + (ApiHttpClient.getToken() == null ? "" : ApiHttpClient.getToken()));//链接
        web.setTitle("邀请你参与拼团");//标题
        web.setThumb(new UMImage(this, R.mipmap.ic_launcher));  //缩略图
        web.setDescription("点击跳转" + getResources().getString(R.string.app_name) +
                "app应用程序");//描述

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
            ToastUtils.showShort(PlaceOrderResultActivity.this, "分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort(PlaceOrderResultActivity.this, "分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort(PlaceOrderResultActivity.this, "你取消了分享");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
