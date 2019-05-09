package com.cherishTang.laishou.laishou.club.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseFragment;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.laishou.club.bean.CircleHeadOneBean;
import com.cherishTang.laishou.laishou.club.adapter.HeadOneAdapter;
import com.cherishTang.laishou.laishou.user.bean.PageRequestBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.CustomLoadMoreView;
import com.cherishTang.laishou.util.RecyclerViewListEmptyUtils;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class HeadOneFragment extends BaseFragment {
    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerViewList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    Unbinder unbinder;
    private int pageNum = 1;
    boolean isRefresh = true;
    private int total = 0;
    private int currentSize = 0;
    private HeadOneAdapter headOneAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.head_one_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        swipeLayout.setColorSchemeColors(Color.parseColor("#43cf7c"));
        recyclerViewList.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2));
        headOneAdapter = new HeadOneAdapter(R.layout.talent_show_item);
        headOneAdapter.setLoadMoreView(new CustomLoadMoreView());
        recyclerViewList.setAdapter(headOneAdapter);
        swipeLayout.setRefreshing(true); getSevereUserPage(pageNum);
         swipeLayout.setOnRefreshListener(() -> {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pageNum = 1;
                    currentSize = 0;
                    isRefresh = true;
                    getSevereUserPage(pageNum);
                }
            }, 300);
        });
        headOneAdapter.setOnLoadMoreListener(() -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pageNum++;
                    isRefresh = false;
                    getSevereUserPage(pageNum);
                }
            }, 300);
        }, recyclerViewList);
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 推荐顾问、推荐列表
     */
    private void getSevereUserPage(int pageNum) {
        ApiHttpClient.getSevereUserPage(new Gson().toJson(new PageRequestBean(pageNum, ConstantsHelper.indexShowPages)), this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id2) {
                CircleHeadOneBean circleHeadOneBean = new Gson().fromJson(response, CircleHeadOneBean.class);
                if (circleHeadOneBean.getCode() == 200) {
                    total = circleHeadOneBean.getData().getRows();
                    setData(isRefresh, circleHeadOneBean);
                    if (isRefresh) {
                        headOneAdapter.setEnableLoadMore(true);
                        swipeLayout.setRefreshing(false);
                    }
                } else {
                    ToastUtils.showLong(getActivity(), circleHeadOneBean.getMessage());
                    if (isRefresh) {
                        headOneAdapter.setEnableLoadMore(true);
                        swipeLayout.setRefreshing(false);
                    } else {
                        headOneAdapter.loadMoreFail();
                    }
                }

            }
        });
    }

    /**
     * 获取数据判断
     *
     * @param isRefresh
     * @param invoiceRecordEntity
     */
    private void setData(boolean isRefresh, CircleHeadOneBean invoiceRecordEntity) {
        int size = invoiceRecordEntity.getData().getList().size();
        currentSize = currentSize + size;
        if (isRefresh) {
            if (invoiceRecordEntity.getData().getList().size() == 0) {
                headOneAdapter.setNewData(null);
                headOneAdapter.setEmptyView(notDataView());
            } else {
                headOneAdapter.setNewData(invoiceRecordEntity.getData().getList());
            }

        } else {
            if (size > 0) {
                headOneAdapter.addData(invoiceRecordEntity.getData().getList());
            }
        }
        if (currentSize >= total) {
            //第一页如果不够一页就不显示没有更多数据布局
            headOneAdapter.loadMoreEnd(isRefresh);
        } else {
            headOneAdapter.loadMoreComplete();
        }
    }

    private View notDataView() {
        View view = RecyclerViewListEmptyUtils.notDataView(getActivity(), recyclerViewList, R.mipmap.ic_launcher, "暂无数据");
        return view;
    }

}
