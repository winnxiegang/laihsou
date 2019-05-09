package com.cherishTang.laishou.laishou.club.fragment;

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
import com.cherishTang.laishou.laishou.club.activity.LaiShowClubDetailActivity;
import com.cherishTang.laishou.laishou.club.adapter.ClubAdapter;
import com.cherishTang.laishou.laishou.club.bean.ClubBean;
import com.cherishTang.laishou.laishou.club.bean.GetClubListBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by CherishTang on 2019/2/26.
 * 莱瘦乐园
 */

public class LaiShowEdenFragment extends BaseRecyclerViewFragment<ClubBean> {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 1;
    private String clubName;
    private double longitude = 0.0f, latitude = 0.0f;

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<ClubBean>>>() {
        }.getType();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        Bundle bundle = getArguments() == null ? new Bundle() : getArguments();
        clubName = bundle.getString("clubName", null);
        mPage = bundle.getInt(ARG_PAGE, 1);
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        longitude = bundle.getDouble("longitude");
        latitude = bundle.getDouble("latitude");
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        if (longitude == 0.0 || latitude == 0.0) {
            ApiHttpClient.getClubList(new Gson().toJson(new GetClubListBean(mCurrentPage, ConstantsHelper.indexShowPages,
                    UserAccountHelper.getLocalSubstation().getId(), clubName, mPage)), this, stringCallback);
        } else {
            ApiHttpClient.getClubList(new Gson().toJson(new GetClubListBean(mCurrentPage, ConstantsHelper.indexShowPages,
                    UserAccountHelper.getLocalSubstation().getId(), clubName, mPage, longitude, latitude)), this, stringCallback);
        }
    }

    @Override
    protected BaseRecyclerViewAdapter<ClubBean> getRecyclerAdapter() {
        return new ClubAdapter(getActivity());
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        Bundle bundle = new Bundle();
        bundle.putString("id", adapter.getList().get(position).getId());
        LaiShowClubDetailActivity.show(getActivity(), bundle);
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new LaiShowEdenFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
