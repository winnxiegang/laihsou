package com.cherishTang.laishou.laishou.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.empty.EmptyLayout;
import com.cherishTang.laishou.laishou.main.adapter.CouponsItemAdapter;
import com.cherishTang.laishou.laishou.main.adapter.LaiShowAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by CherishTang on 2019/2/25.
 * 莱瘦圈
 */

public class CouponsFragment extends BaseRecyclerViewFragment<Object> {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
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
        setCanRefresh(false);
        setCanLoadMore(false);
        refreshLayout.setRecyclerViewVisibility(EmptyLayout.HIDE_LAYOUT);
    }

    @Override
    protected BaseRecyclerViewAdapter<Object> getRecyclerAdapter() {
        return new CouponsItemAdapter(getActivity());
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new CouponsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
