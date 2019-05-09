package com.cherishTang.laishou.laishou.club.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseFragment;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.laishou.club.adapter.HeadFourAdapter;
import com.cherishTang.laishou.laishou.club.bean.CircleThreeLeiBean;
import com.cherishTang.laishou.laishou.main.bean.MainLaiShowListBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.CustomLoadMoreView;
import com.cherishTang.laishou.util.RecyclerViewListEmptyUtils;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class HeadFourFragment extends BaseFragment {
    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerViewList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    Unbinder unbinder;
    private int pageNum = 1;
    boolean isRefresh = true;
    private int total = 0;
    private int currentSize = 0;
    private HeadFourAdapter headFourAdapter;
    private int headFourFragmentId = 8;

    @Override
    protected int getLayoutId() {
        return R.layout.head_one_fragment;
    }

    public static Fragment newInstance(int headFourFragmentId) {
        HeadFourFragment fragment = new HeadFourFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("headFourFragmentId", headFourFragmentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            headFourFragmentId = bundle.getInt("headFourFragmentId", 8);
           // Log.d("postJson", headFourFragmentId + "HeadFourFragment");
        }
        swipeLayout.setColorSchemeColors(Color.parseColor("#43cf7c"));
        recyclerViewList.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false
        ));
        headFourAdapter = new HeadFourAdapter(R.layout.exclusive_interview_item);
        headFourAdapter.setLoadMoreView(new CustomLoadMoreView());
        recyclerViewList.setAdapter(headFourAdapter);
        swipeLayout.setRefreshing(true);  getCirlceThreeLei(pageNum, headFourFragmentId);
        swipeLayout.setOnRefreshListener(() -> {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pageNum = 1;
                    currentSize = 0;
                    isRefresh = true;
                    getCirlceThreeLei(pageNum, headFourFragmentId);
                }
            }, 300);
        });
        headFourAdapter.setOnLoadMoreListener(() -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pageNum++;
                    isRefresh = false;
                    getCirlceThreeLei(pageNum, headFourFragmentId);
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
    private void getCirlceThreeLei(int pageNum, int headTwoFragmentId) {
        ApiHttpClient.getCirlceThreeLei(new Gson().toJson(new MainLaiShowListBean(pageNum, 10,
                UserAccountHelper.getLocalSubstation().getId(), headTwoFragmentId)), this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id2) {
                CircleThreeLeiBean circleThreeLeiBean = new Gson().fromJson(response, CircleThreeLeiBean.class);
                if (circleThreeLeiBean.getCode() == 200) {
                    total = circleThreeLeiBean.getData().getRows();
                    setData(isRefresh, circleThreeLeiBean);
                    if (isRefresh) {
                        headFourAdapter.setEnableLoadMore(true);
                        swipeLayout.setRefreshing(false);
                    }
                } else {
                    ToastUtils.showLong(getActivity(), circleThreeLeiBean.getMessage());
                    if (isRefresh) {
                        headFourAdapter.setEnableLoadMore(true);
                        swipeLayout.setRefreshing(false);
                    } else {
                        headFourAdapter.loadMoreFail();
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
    private void setData(boolean isRefresh, CircleThreeLeiBean invoiceRecordEntity) {
        int size = invoiceRecordEntity.getData().getList().size();
        currentSize = currentSize + size;
        if (isRefresh) {
            if (invoiceRecordEntity.getData().getList().size() == 0) {
                headFourAdapter.setNewData(null);
                headFourAdapter.setEmptyView(notDataView());
            } else {
                headFourAdapter.setNewData(invoiceRecordEntity.getData().getList());
            }

        } else {
            if (size > 0) {
                headFourAdapter.addData(invoiceRecordEntity.getData().getList());
            }
        }
        if (currentSize >= total) {
            //第一页如果不够一页就不显示没有更多数据布局
            headFourAdapter.loadMoreEnd(isRefresh);
        } else {
            headFourAdapter.loadMoreComplete();
        }
    }

    private View notDataView() {
        View view = RecyclerViewListEmptyUtils.notDataView(getActivity(), recyclerViewList, R.mipmap.ic_launcher, "暂无数据");
        return view;
    }

}
