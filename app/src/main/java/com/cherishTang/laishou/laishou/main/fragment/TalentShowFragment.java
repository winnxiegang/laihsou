package com.cherishTang.laishou.laishou.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.empty.EmptyLayout;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.custom.recyclerview.GridSpacingItemDecoration;
import com.cherishTang.laishou.laishou.main.adapter.LaiShowAdapter;
import com.cherishTang.laishou.laishou.main.adapter.TalentShowAdapter;
import com.cherishTang.laishou.laishou.main.bean.MainLaiShowListBean;
import com.cherishTang.laishou.laishou.main.bean.SevereUserBean;
import com.cherishTang.laishou.laishou.user.bean.PageRequestBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by CherishTang on 2019/2/25.
 * 莱瘦圈
 */

public class TalentShowFragment extends BaseRecyclerViewFragment<SevereUserBean> {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<SevereUserBean>>>() {
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
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        refreshLayout.setPadding((int) getResources().getDimension(R.dimen.x30),
                0,
                (int) getResources().getDimension(R.dimen.x30),
                0);
        setCusTomDecoration(new GridSpacingItemDecoration((int) getResources().getDimension(R.dimen.x30),
                ContextCompat.getColor(getActivity(),R.color.white)));
        mPage = bundle.getInt(ARG_PAGE,4);
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getSevereUserPage(new Gson().toJson(new PageRequestBean(mCurrentPage, ConstantsHelper.indexShowPages)),this,stringCallback);
    }

    @Override
    protected BaseRecyclerViewAdapter<SevereUserBean> getRecyclerAdapter() {
        return new TalentShowAdapter(getActivity());
    }

    @Override
    public RecyclerView.LayoutManager initLayoutManager() {
        return new FullyGridLayoutManager(getActivity(),2);
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new TalentShowFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
