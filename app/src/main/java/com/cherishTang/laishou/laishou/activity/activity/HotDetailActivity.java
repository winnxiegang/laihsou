package com.cherishTang.laishou.laishou.activity.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CallDialog;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.custom.recyclerview.MyLayoutManager;
import com.cherishTang.laishou.enumbean.ActivityStateEnum;
import com.cherishTang.laishou.enumbean.AppStateEnum;
import com.cherishTang.laishou.enumbean.LoginTypeEnum;
import com.cherishTang.laishou.enumbean.SignStateEnum;
import com.cherishTang.laishou.laishou.activity.bean.ActivityApplyPeopleRequestBean;
import com.cherishTang.laishou.laishou.activity.bean.HotActivityBean;
import com.cherishTang.laishou.laishou.activity.adapter.ApplyMessageAdapter;
import com.cherishTang.laishou.laishou.main.activity.DiscountDetailActivity;
import com.cherishTang.laishou.laishou.user.activity.PayResultActivity;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.DateUtil;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/2/25.
 *  todo 点击banner后的详情页面
 */

public class HotDetailActivity extends BaseActivity implements Html.ImageGetter {
    @BindView(R.id.mRecyclerViewApply)
    RecyclerView mRecyclerViewApply;
    @BindView(R.id.hot_activity_detail_image)
    ImageView hotActivityDetailImage;
    @BindView(R.id.tv_activity_title)
    TextView tvActivityTitle;
    @BindView(R.id.tv_browse)
    TextView tvBrowse;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_activity_collect)
    TextView tvActivityCollect;
    @BindView(R.id.tv_deal_security)
    TextView tvDealSecurity;
    @BindView(R.id.tv_refund)
    TextView tvRefund;
    @BindView(R.id.tv_business_date)
    TextView tvBusinessDate;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_apply_people)
    TextView tvApplyPeople;
    @BindView(R.id.tv_activity_price)
    TextView tvActivityPrice;
    @BindView(R.id.tv_activity_owner)
    TextView tvActivityOwner;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.tv_apply_recycler_people)
    TextView tvApplyRecyclerPeople;
    @BindView(R.id.tv_more_apply)
    TextView tvMoreApply;
    @BindView(R.id.pay_submit)
    TextView paySubmit;
    private ApplyMessageAdapter applyMessageAdapter;
    private CustomProgressDialog customProgressDialog;
    private Bundle bundle;
    private String id;
    private HotActivityBean hotActivityBean;
    private boolean isActivityOrder = false;
    private String orderId = null;
    private int payState;

    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.banner_detail_activity;
    }

    @Override
    public void initView() {
        applyMessageAdapter = new ApplyMessageAdapter(this);
        mRecyclerViewApply.setLayoutManager(new MyLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewApply.setAdapter(applyMessageAdapter);

        mToolbar.inflateMenu(R.menu.toolbar_menu_hto_share);
        mToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.toolbar_share:
                    if (StringUtil.isEmpty(id)) {
                        ToastUtils.showShort(HotDetailActivity.this, "加载失败，请稍后再试！");
                    } else {
                        getShareLink();
                    }
                    break;
            }
            return false;
        });
    }

    @Override
    public void initData() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();

        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            String schemeParams = uri.getScheme();
            id = uri.getQueryParameter("id");
            payState = bundle.getInt("appStatus", AppStateEnum.unPay.getIndex());
            LogUtil.show("参数：" + uri.getQuery());
            if (!StringUtil.isEmpty(schemeParams) && StringUtil.isEmpty(id)) {
                ToastUtils.showShort(this, "分享已失效");
            }
        } else {
            id = bundle.getString("id");
            isActivityOrder = bundle.getBoolean("isActivityOrder", false);
            orderId = bundle.getString("orderId");
            payState = bundle.getInt("appStatus", AppStateEnum.unPay.getIndex());
        }
        getActivityDetail();
    }

    /**
     * 邀请好友
     */
    private void getShareLink() {
        ApiHttpClient.getActivityShareLink(new Gson().toJson(new IdBean(id)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(HotDetailActivity.this, R.style.loading_dialog);
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
                ToastUtils.showShort(HotDetailActivity.this, "网络状态不佳，请稍后再试！");
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

    @OnClick({R.id.tv_more_apply, R.id.activity_image_tel, R.id.pay_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more_apply:
                ApplyUserDetailActivity.show(this, bundle);
                break;
            case R.id.activity_image_tel:
                if (hotActivityBean == null) return;
                new CallDialog(this, hotActivityBean.getClubPhone()).call();
                break;
            case R.id.pay_submit:
                if (hotActivityBean == null) return;
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(this, this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                if (UserAccountHelper.getLoginType() == LoginTypeEnum.consultant.getIndex()) {
                    ToastUtils.showShort(this, "这是莱瘦会员服务，管家请注意");
                    return;
                }
                bundle.putSerializable("itemData", hotActivityBean);
                if (paySubmit.getText().toString().equals("立即报名") || paySubmit.getText().toString().equals("立即支付")) {
                    if ((hotActivityBean.getSignType() != null && hotActivityBean.getSignType().getIndex() == SignStateEnum.spelled.getIndex())
                            || tvActivityPrice.getText().toString().equals("免费")) {
                        DialogHelper.getConfirmDialog(this, "确定利用积分参加此次活动吗？", (dialog, which) -> {
                            if (customProgressDialog == null || !customProgressDialog.isShowing())
                                customProgressDialog = new CustomProgressDialog(HotDetailActivity.this, R.style.loading_dialog);
                            customProgressDialog.setMessage("正在加载，请稍后...");
                            customProgressDialog.show();
                            signActivity();
                        }).show();
                    } else
                        ActivityDetailPayActivity.show(this, bundle);
                } else {
                    new CallDialog(this, hotActivityBean.getClubPhone()).call();
                }
                break;
        }
    }

    /**
     * 活动下详情
     */
    private void getActivityDetail() {
        ApiHttpClient.getHotActivityDetail(new Gson().toJson(new IdBean(id)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(HotDetailActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在加载，请稍后...");
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
                ToastUtils.showShort(HotDetailActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<HotActivityBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s,
                        new TypeToken<ResultBean<HotActivityBean>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    updateView(resultBean.getData());
                } else {
                    errorMsgShow(resultBean.getCode() + "", resultBean.getMessage(), ConstantsHelper.REQUEST_ERROR_LOGIN);
                }
            }
        });
    }


    /**
     * 积分参加活动
     */
    private void signActivity() {
        ApiHttpClient.postIntegralSign(new Gson().toJson(new IdBean(id)), this, new StringCallback() {

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(HotDetailActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<HotActivityBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s,
                        new TypeToken<ResultBean<HotActivityBean>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    PayResultActivity.show(HotDetailActivity.this, new Bundle());
                    paySubmit.setClickable(true);
                    paySubmit.setText("已报名");
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
        web.setTitle((hotActivityBean == null || StringUtil.isEmpty(hotActivityBean.getActivityTitle())) ? "有人邀请你参加活动" : hotActivityBean.getActivityTitle());//标题
        if (hotActivityBean == null || StringUtil.isEmpty(hotActivityBean.getImg())) {
            web.setThumb(new UMImage(this, R.mipmap.ic_launcher));  //缩略图
        } else {
            web.setThumb(new UMImage(this, hotActivityBean.getImg()));  //缩略图
        }
        web.setDescription("点击跳转" + getResources().getString(R.string.app_name) +
                "app应用程序查看活动详情");//描述

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
            ToastUtils.showShort(HotDetailActivity.this, "分享成功");
            finish();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort(HotDetailActivity.this, "分享失败");
            finish();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort(HotDetailActivity.this, "你取消了分享");
        }
    };

    /**
     * 刷新布局
     */
    private void updateView(HotActivityBean hotActivityBean) {
        if (hotActivityBean == null) return;
        this.hotActivityBean = hotActivityBean;
        Glide.with(this)
                .load(hotActivityBean.getImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(hotActivityDetailImage);
        tvActivityTitle.setText(hotActivityBean.getActivityTitle());
        tvAddress.setText(hotActivityBean.getClubAddress());
//        tvDescribe.setText(hotActivityBean.getActivityContent());

        Spanned spanned = Html.fromHtml(hotActivityBean.getActivityContent(), this, null);
        tvDescribe.setText(spanned);
        tvActivityOwner.setText(hotActivityBean.getClubName());
        tvApplyPeople.setText("已报名" + hotActivityBean.getSignTotal() + "人");
        StringBuffer sb = new StringBuffer();
        sb.append("合计：");
        sb.append(NumberUtils.decimalFormatInteger(hotActivityBean.getPrice()));
        if (hotActivityBean.getSignType() != null) {
            sb.append(hotActivityBean.getSignType().getUnit());
        }
        if (StringUtil.isEmpty(hotActivityBean.getPrice()) ||
                NumberUtils.decimalFormatInteger(hotActivityBean.getPrice()).equals("0") ||
                hotActivityBean.getPrice().equals("免费")) {
            tvActivityPrice.setText("免费");
        } else {
            tvActivityPrice.setText(sb.toString());
        }

//        tvActivityPrice.setText("￥" + NumberUtils.decimalFormat(hotActivityBean.getPrice()));
        tvBusinessDate.setText(hotActivityBean.getStrStartTime() + "至" + hotActivityBean.getStrEndTime());
        if (isActivityOrder) {
            if (payState == AppStateEnum.unPay.getIndex()) {
                paySubmit.setText("立即支付");
                paySubmit.setClickable(true);
                paySubmit.setBackgroundColor(ContextCompat.getColor(this, R.color.themeColor));
            } else {
                paySubmit.setText("联系电话");
                paySubmit.setClickable(true);
                paySubmit.setBackgroundColor(ContextCompat.getColor(this, R.color.themeColor));
            }
        } else {

            try {
                if (DateUtil.stringToLong(hotActivityBean.getStrStartTime(), "yyyy-MM-dd hh:mm:ss") > Calendar.getInstance().getTimeInMillis()) {
                    paySubmit.setText(ActivityStateEnum.unStart.getName());
                    paySubmit.setClickable(false);
                    paySubmit.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
                } else if (DateUtil.stringToLong(hotActivityBean.getStrEndTime(), "yyyy-MM-dd hh:mm:ss") < Calendar.getInstance().getTimeInMillis()) {
                    paySubmit.setText(ActivityStateEnum.end.getName());
                    paySubmit.setClickable(false);
                    paySubmit.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
                } else {
                    paySubmit.setText("立即报名");
                    paySubmit.setClickable(true);
                    paySubmit.setBackgroundColor(ContextCompat.getColor(this, R.color.themeColor));
                }
            } catch (ParseException e) {
                e.printStackTrace();
                paySubmit.setText("立即报名");
                paySubmit.setClickable(true);
                paySubmit.setBackgroundColor(ContextCompat.getColor(this, R.color.themeColor));
            }

        }

        applyMessageAdapter.setList(hotActivityBean.getSignList());
        applyMessageAdapter.notifyDataSetChanged();

    }

    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.drawable.ic_launcher);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);

        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = tvDescribe.getText();
                tvDescribe.setText(t);
            }
        }
    }

    /**
     * 获取报名人数
     */
    private void getActivityApplyPeopleDetail() {
        ApiHttpClient.getHotActivityApplyPeopleDetail(new Gson().toJson(new ActivityApplyPeopleRequestBean(id, 1, 5)), this, new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(HotDetailActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<PageBean<Object>> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s,
                        new TypeToken<ResultBean<PageBean<Object>>>() {
                        }.getType());
                if (resultBean.isSuccess()) {

                } else {
                    errorMsgShow(resultBean.getCode() + "", resultBean.getMessage(), ConstantsHelper.REQUEST_ERROR_LOGIN);
                }
            }
        });
    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, HotDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
