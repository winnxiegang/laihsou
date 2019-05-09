package com.cherishTang.laishou.laishou.consultant.fragment;

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
import com.cherishTang.laishou.laishou.consultant.activity.ConsultantDetailActivity;
import com.cherishTang.laishou.laishou.consultant.adapter.ConsultantListAdapter;
import com.cherishTang.laishou.laishou.consultant.bean.ConsultantBean;
import com.cherishTang.laishou.laishou.consultant.bean.GetConsultantListBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by CherishTang on 2019/2/25.
 * 莱瘦顾问
 */

public class ConsultantListFragment extends BaseRecyclerViewFragment<ConsultantBean> {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 1;
    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<ConsultantBean>>>() {
        }.getType();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        Bundle bundle = getArguments()==null?new Bundle():getArguments();
        mPage = bundle.getInt(ARG_PAGE,1);
    }
    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getConsultantList(new Gson().toJson(new GetConsultantListBean(mCurrentPage, ConstantsHelper.indexShowPages, UserAccountHelper.getLocalSubstation().getId(),null,mPage)),this,stringCallback);
    }

    @Override
    protected BaseRecyclerViewAdapter<ConsultantBean> getRecyclerAdapter() {
        return new ConsultantListAdapter(getActivity());
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        Bundle bundle = new Bundle();
        bundle.putString("id",adapter.getList().get(position).getId());
        ConsultantDetailActivity.show(getActivity(),bundle);
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new ConsultantListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
