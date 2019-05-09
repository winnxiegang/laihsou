package com.cherishTang.laishou.laishou.club.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseFragment;
import com.cherishTang.laishou.laishou.club.adapter.HeadTwoAdapter;
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

public class HeadTwoFragment extends BaseFragment {
    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerViewList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    Unbinder unbinder;
    private int pageNum = 1;
    boolean isRefresh = true;
    private int total = 0;
    private int currentSize = 0;
    private HeadTwoAdapter headTwoAdapter;
    private int headTwoFragmentId = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.head_one_fragment;
    }

    public static Fragment newInstance(int headTwoFragmentId) {
        HeadTwoFragment fragment = new HeadTwoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("headTwoFragmentId", headTwoFragmentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            headTwoFragmentId = bundle.getInt("headTwoFragmentId", 1);
        }
        swipeLayout.setColorSchemeColors(Color.parseColor("#43cf7c"));
        recyclerViewList.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false
        ));
        headTwoAdapter = new HeadTwoAdapter(R.layout.story_list_item);
        headTwoAdapter.setLoadMoreView(new CustomLoadMoreView());
        recyclerViewList.setAdapter(headTwoAdapter);
        swipeLayout.setRefreshing(true);   getCirlceThreeLei(pageNum, headTwoFragmentId);
        swipeLayout.setOnRefreshListener(() -> {
            pageNum = 1;
            currentSize = 0;
            isRefresh = true;
            getCirlceThreeLei(pageNum, headTwoFragmentId);
        });
        headTwoAdapter.setOnLoadMoreListener(() -> {
            pageNum++;
            isRefresh = false;
            getCirlceThreeLei(pageNum, headTwoFragmentId);
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
                        headTwoAdapter.setEnableLoadMore(true);
                        swipeLayout.setRefreshing(false);
                    }
                } else {
                    ToastUtils.showLong(getActivity(), circleThreeLeiBean.getMessage());
                    if (isRefresh) {
                        headTwoAdapter.setEnableLoadMore(true);
                        swipeLayout.setRefreshing(false);
                    } else {
                        headTwoAdapter.loadMoreFail();
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
                headTwoAdapter.setNewData(null);
                headTwoAdapter.setEmptyView(notDataView());
            } else {
                headTwoAdapter.setNewData(invoiceRecordEntity.getData().getList());
            }

        } else {
            if (size > 0) {
                headTwoAdapter.addData(invoiceRecordEntity.getData().getList());
            }
        }
        if (currentSize >= total) {
            //第一页如果不够一页就不显示没有更多数据布局
            headTwoAdapter.loadMoreEnd(isRefresh);
        } else {
            headTwoAdapter.loadMoreComplete();
        }
    }

    private View notDataView() {
        View view = RecyclerViewListEmptyUtils.notDataView(getActivity(), recyclerViewList, R.mipmap.ic_launcher, "暂无数据");
        return view;
    }

}
