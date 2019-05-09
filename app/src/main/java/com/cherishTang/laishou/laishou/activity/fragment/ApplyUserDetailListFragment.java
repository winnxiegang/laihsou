package com.cherishTang.laishou.laishou.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.empty.EmptyLayout;
import com.cherishTang.laishou.laishou.activity.adapter.ApplyUserDetailListAdapter;
import com.cherishTang.laishou.laishou.activity.bean.ActivityApplyPeopleBean;
import com.cherishTang.laishou.laishou.activity.bean.ActivityApplyPeopleRequestBean;
import com.cherishTang.laishou.laishou.main.bean.MainLaiShowListBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by CherishTang on 2019/2/25.
 * 已报名
 */

public class ApplyUserDetailListFragment extends BaseRecyclerViewFragment<ActivityApplyPeopleBean> {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
    private String id;

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<ActivityApplyPeopleBean>>>() {
        }.getType();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        Bundle bd = bundle == null ? new Bundle() : bundle;
        id = bd.getString("id");
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getHotActivityApplyPeopleDetail(new Gson().toJson(new ActivityApplyPeopleRequestBean(id, mCurrentPage, ConstantsHelper.indexShowPages)), this, stringCallback);
    }

    @Override
    protected BaseRecyclerViewAdapter<ActivityApplyPeopleBean> getRecyclerAdapter() {
        return new ApplyUserDetailListAdapter(getActivity());
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new ApplyUserDetailListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
