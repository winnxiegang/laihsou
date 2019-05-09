package com.cherishTang.laishou.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cherishTang.laishou.api.OkhttpsHelper;
import com.google.gson.GsonBuilder;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.LoginDialog;
import com.cherishTang.laishou.custom.empty.EmptyLayout;
import com.cherishTang.laishou.custom.recyclerview.MyLayoutManager;
import com.cherishTang.laishou.custom.recyclerview.RecycleViewDivider;
import com.cherishTang.laishou.custom.swiperefreshlayout.wight.SwipeRecyclerView;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.networkTools.NetworkStateUtil;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by 方舟 on 2017/11/17.
 * BaseRecyclerViewFragment封装
 */

public abstract class BaseRecyclerViewFragment<T> extends BaseFragment implements BaseRecyclerViewAdapter.OnItemClickListener,
        BaseRecyclerViewAdapter.OnItemLongClickListener, SwipeRecyclerView.OnSwipeRecyclerViewListener {
    public SwipeRecyclerView refreshLayout;
    Unbinder unbinder;
    private View currentView;

    public BaseRecyclerViewAdapter<T> adapter;

    public int mCurrentPage = 1;
    public RecyclerView recyclerView;
    public StringCallback stringCallback;

    @Override
    protected int getLayoutId() {
        return R.layout.base_swipe_recyclerview;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        this.currentView = view;
        unbinder = ButterKnife.bind(this, currentView);
        initView();
    }

    @Override
    protected void initData(Bundle bundle) {
        stringCallback = new StringCallback() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                if (refreshLayout != null)
                    refreshLayout.setRecyclerViewVisibility(EmptyLayout.LOADING_ERROR);
                ToastUtils.showShort(getActivity(), "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<PageBean<T>> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s, getType());
                if (resultBean != null && resultBean.isSuccess() && resultBean.getData() != null) {
                    if (refreshLayout != null)
                        refreshLayout.setRecyclerViewVisibility(EmptyLayout.HIDE_LAYOUT);
                    setListData(resultBean);
                } else {
                    if (resultBean == null)
                        errorMsgShow("-1", "加载失败", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    else
                        errorMsgShow(resultBean.getCode() + "", resultBean.getMessage(), ConstantsHelper.REQUEST_ERROR_LOGIN);
                }
            }
        };
        initAdapter();
    }

    protected void requestData() {
    }

    public void setCanRefresh(boolean isCanRefresh) {
        refreshLayout.setCanRefresh(isCanRefresh);
    }

    public void setCanLoadMore(boolean isCanLoadMore) {
        refreshLayout.setCanLoadMore(isCanLoadMore);
    }

    protected void setListData(ResultBean<PageBean<T>> resultBean) {
        if (refreshLayout == null) return;
        refreshLayout.onLoadFinish();
        refreshLayout.onRefreshFinish();
        if (resultBean != null && resultBean.getData() != null) {
            adapter.addAll(resultBean.getData().getList());
            adapter.notifyDataSetChanged();
            if (adapter == null || adapter.getList() == null || adapter.getList().size() == 0) {
                refreshLayout.setRecyclerViewVisibility(EmptyLayout.NODATA);
            } else {
                refreshLayout.setRecyclerViewVisibility(EmptyLayout.HIDE_LAYOUT);
            }
        } else {
            refreshLayout.setRecyclerViewVisibility(EmptyLayout.LOADING_ERROR);
            ToastUtils.showShort(getActivity(), "已加载全部");
        }
    }

    public void setData(List<T> rows) {
        adapter.addAll(rows);
        adapter.notifyDataSetChanged();
    }

    protected abstract Type getType();

    @Override
    public void errorMsgShow(String code, String msg, int requestCode) {
        super.errorMsgShow(code, msg, requestCode);
        if (refreshLayout != null)
            refreshLayout.setRecyclerViewVisibility(EmptyLayout.LOADING_ERROR);
    }

    public void errorMsgShowNoEmptyLayout(String code, String msg, int requestCode) {
        super.errorMsgShow(code, msg, requestCode);
    }

    public RecyclerView.LayoutManager initLayoutManager() {
        return new MyLayoutManager(getActivity());
    }

    public boolean hideRecycleViewDivider() {
        return false;
    }

    public void setCusTomDecoration(RecyclerView.ItemDecoration recycleViewDivider) {
        recyclerView.addItemDecoration(recycleViewDivider);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(initLayoutManager());
        if (!hideRecycleViewDivider()) {
            recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1,
                    ContextCompat.getColor(getActivity(), R.color.h_line_color)));
        }

    }

    protected abstract BaseRecyclerViewAdapter<T> getRecyclerAdapter();

    private void initView() {
        refreshLayout = (SwipeRecyclerView) currentView.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshDateTag(this.getClass().getSimpleName());
        recyclerView = refreshLayout.getRecyclerView();
        adapter = getRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);

        refreshLayout.setOnSwipeRecyclerViewListener(this);

        if (NetworkStateUtil.isNetWorkConnected(getActivity()))
            refreshLayout.setRecyclerViewVisibility(EmptyLayout.NETWORK_LOADING);
        else
            refreshLayout.setRecyclerViewVisibility(EmptyLayout.LOADING_ERROR);
    }


    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        adapter.getList().clear();
        requestData();
    }

    @Override
    public void onLoadNext() {
        mCurrentPage++;
        requestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkhttpsHelper.cancelRequestTag(this);
        if (unbinder != null)
            unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstantsHelper.REQUEST_ERROR_LOGIN:
                if (resultCode == ConstantsHelper.LOGIN_SUCCESS) {
                    if (LoginDialog.getDialog() != null || LoginDialog.getDialog().isShowing())
                        LoginDialog.getDialog().dismiss();
                    refreshLayout.setRecyclerViewVisibility(EmptyLayout.NETWORK_LOADING);
                    onRefresh();
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
