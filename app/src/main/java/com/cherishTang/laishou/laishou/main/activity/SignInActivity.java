package com.cherishTang.laishou.laishou.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.customlayout.CustomTextView;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.enumbean.LoginTypeEnum;
import com.cherishTang.laishou.laishou.activity.activity.HotActivity;
import com.cherishTang.laishou.laishou.activity.activity.HotActivityListActivity;
import com.cherishTang.laishou.laishou.main.bean.SignInActivityBean;
import com.cherishTang.laishou.laishou.main.wight.CustomScrollView;
import com.cherishTang.laishou.laishou.user.activity.GoodExchangeActivity;
import com.cherishTang.laishou.laishou.user.adapter.ExChangeGoodsAdapter;
import com.cherishTang.laishou.laishou.user.adapter.SignInLineAdapter;
import com.cherishTang.laishou.laishou.user.bean.GoodExchangeRequestBean;
import com.cherishTang.laishou.laishou.user.bean.PageRequestBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/2/28.
 * describe：每日签到
 */
public class SignInActivity extends BaseActivity implements ExChangeGoodsAdapter.OnExchangeClickListener,
        SwipeRefreshLayout.OnRefreshListener, CustomScrollView.OnScrollEndChangeListener {

    @BindView(R.id.mRecyclerView_day)
    RecyclerView mRecyclerViewDay;
    @BindView(R.id.button_sign_in)
    Button buttonSignIn;
    @BindView(R.id.custom_activity)
    CustomTextView customActivity;
    @BindView(R.id.custom_article)
    CustomTextView customArticle;
    @BindView(R.id.custom_lpt)
    CustomTextView customLpt;
    @BindView(R.id.custom_integral)
    CustomTextView customIntegral;
    @BindView(R.id.mRecyclerView_goods)
    RecyclerView mRecyclerViewGoods;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.mCustomScrollView)
    CustomScrollView mCustomScrollView;
    private ExChangeGoodsAdapter exChangeGoodsAdapter;
    private SignInLineAdapter signInLineAdapter;
    private CustomProgressDialog customProgressDialog;
    private int mCurrentPage = 1;
    public StringCallback stringCallback;

    @Override
    public String setTitleBar() {
        return null;
    }

    @Override
    public int initLayout() {
        return R.layout.sign_in_activity;
    }

    @Override
    public void initView() {
        customActivity.init(R.mipmap.sign_in_activity, R.string.custom_activity, "1");
        customArticle.init(R.mipmap.sign_in_article, R.string.custom_article, "2");
        customLpt.init(R.mipmap.sign_in_lpt, R.string.custom_lpt, "3");
        customIntegral.init(R.mipmap.sign_in_integral, R.string.custom_integral, "4");

        exChangeGoodsAdapter = new ExChangeGoodsAdapter(this);
        mRecyclerViewGoods.setAdapter(exChangeGoodsAdapter);
        mRecyclerViewGoods.setLayoutManager(new FullyGridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        exChangeGoodsAdapter.setOnExchangeClickListener(this);

        signInLineAdapter = new SignInLineAdapter(this);
        mRecyclerViewDay.setAdapter(signInLineAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        mRecyclerViewDay.setLayoutManager(linearLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mCustomScrollView.setOnScrollEndChangeListener(this);
    }


    @Override
    public void initData() {
        getGoodsPage();
    }

    private void getGoodsPage() {
        ApiHttpClient.getGoodsPage(new Gson().toJson(new PageRequestBean(mCurrentPage, ConstantsHelper.indexShowPages)), this, stringCallback = new StringCallback() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(SignInActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    ResultBean<SignInActivityBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s,
                            new TypeToken<ResultBean<SignInActivityBean>>() {
                            }.getType());
                    if (resultBean != null && resultBean.isSuccess() && resultBean.getData() != null) {
                        exChangeGoodsAdapter.addAll(resultBean.getData().getList());
                        exChangeGoodsAdapter.notifyDataSetChanged();
                        tvIntegral.setText("剩余积分：" + NumberUtils.decimalFormatInteger(resultBean.getData().getIntegral()));
                        signInLineAdapter.setList(resultBean.getData().getSignLogList());
                        signInLineAdapter.notifyDataSetChanged();
                        if (resultBean.getData().isDayIsSign()) {
                            signInLineAdapter.setSignIn(true);
                            buttonSignIn.setSelected(false);
                            buttonSignIn.setTextColor(ContextCompat.getColor(SignInActivity.this, R.color.white));
                            buttonSignIn.setClickable(false);
                            buttonSignIn.setBackground(ContextCompat.getDrawable(SignInActivity.this, R.drawable.round_nv_color_30));
                        } else {
                            buttonSignIn.setTextColor(ContextCompat.getColor(SignInActivity.this, R.color.orange));
                            buttonSignIn.setSelected(true);
                            buttonSignIn.setClickable(true);
                            buttonSignIn.setBackground(ContextCompat.getDrawable(SignInActivity.this, R.drawable.round_yellow_30));
                        }
                    } else {
                        if (resultBean == null)
                            errorMsgShow("-1", "加载失败", ConstantsHelper.REQUEST_ERROR_LOGIN);
                        else
                            errorMsgShow(resultBean.getCode() + "", resultBean.getMessage(), ConstantsHelper.REQUEST_ERROR_LOGIN);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(SignInActivity.this, R.string.error);
                }

            }
        });

    }

    /**
     * 兑换商品
     *
     * @param postJson
     */
    private void exchange(String postJson) {
        ApiHttpClient.getGoodsExchange(postJson, this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(SignInActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    ToastUtils.showShort(SignInActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "兑换成功" : resultBean.getMessage());
                    mCurrentPage = 1;
                    getGoodsPage();//刷新
                    Bundle bd = new Bundle();
                    bd.putInt("type", 2);
                    bd.putString("title", "兑换成功");
                    bd.putString("result", "恭喜你，兑换成功！");
                    bd.putString("resultTips", "恭喜你，兑换成功！");
                    bd.putBoolean("isShowShareButton", false);
                    PlaceOrderResultActivity.show(SignInActivity.this, bd);
                } else {
                    ToastUtils.showShort(SignInActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "兑换失败" : resultBean.getMessage());
                }
            }
        });

    }

    /**
     * 签到获取今天是否签到
     */
    private void getTodayIsSignIn() {
        ApiHttpClient.getTodayIsSignIn(this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(SignInActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在加载，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(SignInActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
                if (mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(String response, int id) {
                //true:已签到;false:未签到
                ResultBean<Boolean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<Boolean>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    if (resultBean.getData()) {
                        signInLineAdapter.setSignIn(true);
                        buttonSignIn.setSelected(false);
                        buttonSignIn.setTextColor(ContextCompat.getColor(SignInActivity.this, R.color.white));
                        buttonSignIn.setClickable(false);
                        buttonSignIn.setBackground(ContextCompat.getDrawable(SignInActivity.this, R.drawable.round_nv_color_30));
                    } else {
                        buttonSignIn.setTextColor(ContextCompat.getColor(SignInActivity.this, R.color.orange));
                        buttonSignIn.setSelected(true);
                        buttonSignIn.setClickable(true);
                        buttonSignIn.setBackground(ContextCompat.getDrawable(SignInActivity.this, R.drawable.round_yellow_30));
                    }
                } else {
                    ToastUtils.showShort(SignInActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "操作失败" : resultBean.getMessage());
                }

            }
        });
    }

    /**
     * 签到
     */
    private void postSignIn() {
        ApiHttpClient.postSignIn(this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(SignInActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    ToastUtils.showShort(SignInActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "签到成功" : resultBean.getMessage());
                    getGoodsPage();
                } else {
                    ToastUtils.showShort(SignInActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "签到失败" : resultBean.getMessage());
                }

            }
        });
    }

    @OnClick({R.id.button_sign_in, R.id.custom_integral, R.id.custom_activity, R.id.custom_article, R.id.custom_lpt})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                if (buttonSignIn.isSelected() && (customProgressDialog == null || !customProgressDialog.isShowing())) {
                    customProgressDialog = new CustomProgressDialog(this, R.style.loading_dialog);
                    customProgressDialog.setMessage("签到中，请稍后...");
                    customProgressDialog.show();
                    postSignIn();
                }
                break;
            case R.id.custom_activity:
                HotActivityListActivity.show(this, new Bundle());
                break;
            case R.id.custom_article:
                ArticleListActivity.show(this, new Bundle());
                break;
            case R.id.custom_lpt:
                DiscountListActivity.show(this, new Bundle());
                break;
            case R.id.custom_integral:
                GoodExchangeActivity.show(this, new Bundle());
                break;
        }
    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, SignInActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void onExchangeClick(View v, int pos) {

        if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
            showLoginDialog(this, this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
            return;
        }

        if (UserAccountHelper.getLoginType() == LoginTypeEnum.consultant.getIndex()) {
            ToastUtils.showShort(this, "这是莱瘦会员服务，管家请注意");
            return;
        }

        DialogHelper.getConfirmDialog(this, "确定兑换此商品吗？", (dialogInterface, i) -> {

            if (customProgressDialog == null || !customProgressDialog.isShowing())
                customProgressDialog = new CustomProgressDialog(SignInActivity.this, R.style.loading_dialog);
            customProgressDialog.setMessage("正在兑换，请稍后...");
            customProgressDialog.show();
            GoodExchangeRequestBean goodExchangeRequestBean = new GoodExchangeRequestBean();
            goodExchangeRequestBean.setId(exChangeGoodsAdapter.getList().get(pos).getId());
            goodExchangeRequestBean.setNumber(1);
            exchange(new Gson().toJson(goodExchangeRequestBean));
        }).show();
    }

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        mCurrentPage = 1;
        exChangeGoodsAdapter.getList().clear();
        exChangeGoodsAdapter.notifyDataSetChanged();
        new Handler().postDelayed(() -> getGoodsPage(), 500);
    }

    @Override
    public void onScrollToStart() {
        LogUtil.show("滑动到顶部了");
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onScrolling() {
        LogUtil.show("onScrolling");
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onScrollToEnd() {
        LogUtil.show("滑动到底部了");
        mCurrentPage++;
        getGoodsPage();
    }
}
