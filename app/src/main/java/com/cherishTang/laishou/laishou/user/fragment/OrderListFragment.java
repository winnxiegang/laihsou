package com.cherishTang.laishou.laishou.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
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
import com.cherishTang.laishou.custom.recyclerview.RecycleViewDivider;
import com.cherishTang.laishou.laishou.user.adapter.OrderListAdapter;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.laishou.user.bean.OrderListBean;
import com.cherishTang.laishou.laishou.user.bean.PageRequestBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Created by CherishTang on 2019/3/5.
 * 订单
 */

public class OrderListFragment extends BaseRecyclerViewFragment<OrderListBean> implements OrderListAdapter.OnBottomOptionClick{
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
    private CustomProgressDialog customProgressDialog;
    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<OrderListBean>>>() {
        }.getType();
    }
    @Override
    public boolean hideRecycleViewDivider() {
        return true;
    }
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        setCusTomDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,
                (int)getResources().getDimension(R.dimen.y10),
                ContextCompat.getColor(getActivity(), R.color.h_line_color)));
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getOrderIntegralList(new Gson().toJson(new PageRequestBean(mCurrentPage, ConstantsHelper.indexShowPages)),this,stringCallback);
    }

    @Override
    protected BaseRecyclerViewAdapter<OrderListBean> getRecyclerAdapter() {
        OrderListAdapter orderListAdapter = new OrderListAdapter(getActivity());
        orderListAdapter.setOnBottomOptionClick(this);
        return orderListAdapter;
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new OrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDelete(View v, int position) {
        DialogHelper.getConfirmDialog(getActivity(), "确定删除此订单吗？", (dialogInterface, i) -> {
            if (customProgressDialog == null || !customProgressDialog.isShowing())
                customProgressDialog = new CustomProgressDialog(getActivity(), R.style.loading_dialog);
            customProgressDialog.setMessage("正在删除订单，请稍后...");
            customProgressDialog.show();
            delete(new Gson().toJson(new IdBean(adapter.getList().get(position).getId())));
        }).show();
    }

    private void delete(String postJson) {
        ApiHttpClient.deleteIndentOrderList(postJson, this, new StringCallback() {
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
                    onRefresh();
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "删除成功" : resultBean.getMessage());
                } else {
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "删除失败" : resultBean.getMessage());
                }
            }
        });

    }

}
