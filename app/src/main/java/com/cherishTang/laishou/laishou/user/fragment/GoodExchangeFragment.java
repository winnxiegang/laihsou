package com.cherishTang.laishou.laishou.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.laishou.main.activity.PlaceOrderResultActivity;
import com.cherishTang.laishou.laishou.main.activity.SignInActivity;
import com.cherishTang.laishou.laishou.user.adapter.ExChangeGoodsAdapter;
import com.cherishTang.laishou.laishou.user.bean.GoodExchangeBean;
import com.cherishTang.laishou.laishou.user.bean.GoodExchangeRequestBean;
import com.cherishTang.laishou.laishou.user.bean.PageRequestBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/3/5.
 * 积分兑换
 */

public class GoodExchangeFragment extends BaseRecyclerViewFragment<GoodExchangeBean> implements ExChangeGoodsAdapter.OnExchangeClickListener {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
    private CustomProgressDialog customProgressDialog;
    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<GoodExchangeBean>>>() {
        }.getType();
    }
    @Override
    public boolean hideRecycleViewDivider() {
        return true;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
    }

    @Override
    public RecyclerView.LayoutManager initLayoutManager() {
        return new FullyGridLayoutManager(getActivity(), 2);
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getGoodsPage(new Gson().toJson(new PageRequestBean(mCurrentPage, ConstantsHelper.indexShowPages)),this,stringCallback);
    }
    private void exchange(String postJson) {
        ApiHttpClient.getGoodsExchange(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(getActivity(), R.style.loading_dialog);
                customProgressDialog.setMessage("正在兑换，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(getActivity(), R.string.requestError);
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
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "兑换成功" : resultBean.getMessage());
                    onRefresh();
                    Bundle bd = new Bundle();
                    bd.putInt("type", 2);
                    bd.putString("title", "兑换成功");
                    bd.putString("result", "恭喜你，兑换成功！");
                    bd.putString("resultTips", "恭喜你，兑换成功！");
                    bd.putBoolean("isShowShareButton", false);
                    PlaceOrderResultActivity.show(getActivity(), bd);
                } else {
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "兑换失败" : resultBean.getMessage());
                }
            }
        });

    }
    @Override
    protected BaseRecyclerViewAdapter<GoodExchangeBean> getRecyclerAdapter() {
        ExChangeGoodsAdapter exChangeGoodsAdapter = new ExChangeGoodsAdapter(getActivity());
        exChangeGoodsAdapter.setOnExchangeClickListener(this);
        return exChangeGoodsAdapter;
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new GoodExchangeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onExchangeClick(View v, int pos) {
        DialogHelper.getConfirmDialog(getActivity(), "确定兑换此商品吗？", (dialogInterface, i) -> {
            GoodExchangeRequestBean goodExchangeRequestBean = new GoodExchangeRequestBean();
            goodExchangeRequestBean.setId(adapter.getList().get(pos).getId());
            goodExchangeRequestBean.setNumber(1);
            exchange(new Gson().toJson(goodExchangeRequestBean));
        }).show();

    }
}
