package com.cherishTang.laishou.laishou.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.empty.EmptyLayout;
import com.cherishTang.laishou.laishou.user.adapter.MyCircleListAdapter;
import com.cherishTang.laishou.laishou.user.bean.MessageEvent;
import com.cherishTang.laishou.laishou.user.bean.MyCircleBean;
import com.cherishTang.laishou.laishou.user.bean.PageRequestBean;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;

/**
 * Created by CherishTang on 2019/3/10.
 * 我的圈子
 */

public class MyCircleListFragment extends BaseRecyclerViewFragment<MyCircleBean> {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<MyCircleBean>>>() {
        }.getType();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getPhoto(new Gson().toJson(new PageRequestBean(mCurrentPage, ConstantsHelper.indexShowPages)), this, stringCallback);
    }

    @Override
    protected BaseRecyclerViewAdapter<MyCircleBean> getRecyclerAdapter() {
        return new MyCircleListAdapter(getActivity());
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new MyCircleListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if ("1".equals(messageEvent.getType())) {
            onRefresh();

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
