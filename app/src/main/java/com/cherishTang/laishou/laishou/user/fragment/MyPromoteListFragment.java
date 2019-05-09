package com.cherishTang.laishou.laishou.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.enumbean.LoginTypeEnum;
import com.cherishTang.laishou.laishou.consultant.bean.UserListBean;
import com.cherishTang.laishou.laishou.user.adapter.MyCircleListAdapter;
import com.cherishTang.laishou.laishou.user.adapter.NutritionConsultantMemberListAdapter;
import com.cherishTang.laishou.laishou.user.bean.MyCircleBean;
import com.cherishTang.laishou.laishou.user.bean.PageRequestBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by CherishTang on 2019/3/10.
 * 我的推广
 */

public class MyPromoteListFragment extends BaseRecyclerViewFragment<UserListBean> {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<UserListBean>>>() {
        }.getType();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.promote_swipe_recyclerview;
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
        if(UserAccountHelper.getUser()!=null&&UserAccountHelper.getLoginType()== LoginTypeEnum.consultant.getIndex()){
            ApiHttpClient.postCounselorPromote(new Gson().toJson(new PageRequestBean(mCurrentPage, ConstantsHelper.indexShowPages)), this, stringCallback);
        }else{
            ApiHttpClient.postUserPromote(new Gson().toJson(new PageRequestBean(mCurrentPage, ConstantsHelper.indexShowPages)), this, stringCallback);
        }
    }

    @Override
    protected BaseRecyclerViewAdapter<UserListBean> getRecyclerAdapter() {
        return new NutritionConsultantMemberListAdapter(getActivity());
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new MyPromoteListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
