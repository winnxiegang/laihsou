package com.cherishTang.laishou.laishou.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.empty.EmptyLayout;
import com.cherishTang.laishou.laishou.user.adapter.SwitchFriendListAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by CherishTang on 2019/3/6.
 * 切换好友
 */

public class SwithchFriendFragment extends BaseRecyclerViewFragment<Object> {
    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<Object>>>() {
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
        setCanLoadMore(false);
        setCanRefresh(false);
        refreshLayout.setRecyclerViewVisibility(EmptyLayout.HIDE_LAYOUT);
    }

    @Override
    protected void requestData() {
        super.requestData();
    }

    @Override
    protected BaseRecyclerViewAdapter<Object> getRecyclerAdapter() {
        return new SwitchFriendListAdapter(getActivity());
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new SwithchFriendFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
