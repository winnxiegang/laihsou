package com.cherishTang.laishou.laishou.club.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.laishou.club.adapter.ClubAdapter;
import com.cherishTang.laishou.laishou.club.bean.ClubBean;
import com.cherishTang.laishou.laishou.club.bean.GetClubListBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by CherishTang on 2019/2/25.
 * 俱乐部列表
 */

public class ClubListFragment extends BaseRecyclerViewFragment<ClubBean> {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<ClubBean>>>() {
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
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getClubList(new Gson().toJson(new GetClubListBean(mCurrentPage, ConstantsHelper.indexShowPages,"9784e38e-2b9f-4b04-8326-1f1eadb00837",null)),this,stringCallback);
    }

    @Override
    protected BaseRecyclerViewAdapter<ClubBean> getRecyclerAdapter() {
        return new ClubAdapter(getActivity());
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new ClubListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
