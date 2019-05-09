package com.cherishTang.laishou.user.myhouse;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.custom.swiperefreshlayout.wight.SwipeRecyclerView;
import com.cherishTang.laishou.base.BaseFragment;

import butterknife.Unbinder;

/**
 * Created by 方舟 on 2017/10/20.
 * 我的房源
 */

public class MyHousePageFragment extends BaseFragment implements
        SwipeRecyclerView.OnSwipeRecyclerViewListener {

    public static final String ARG_PAGE = "ARG_PAGE";
    SwipeRecyclerView refreshLayout;
    Unbinder unbinder;
    private int mPage;
    private View view;

    RecyclerView recyclerView;

    public static MyHousePageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MyHousePageFragment pageFragment = new MyHousePageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        switch (mPage) {
            case 0:
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.myhouse_fragment_page;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadNext() {

    }
}
