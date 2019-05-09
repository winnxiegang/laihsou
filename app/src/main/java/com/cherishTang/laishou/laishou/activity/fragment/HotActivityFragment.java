package com.cherishTang.laishou.laishou.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.bean.base.ResultListBean;
import com.cherishTang.laishou.custom.customlayout.CustomBannerPicture;
import com.cherishTang.laishou.custom.customlayout.CustomTextView;
import com.cherishTang.laishou.laishou.activity.activity.HotActivityListActivity;
import com.cherishTang.laishou.laishou.activity.activity.HotDetailActivity;
import com.cherishTang.laishou.laishou.activity.adapter.HotActivityAdapter;
import com.cherishTang.laishou.laishou.activity.bean.HotActivityRequestBean;
import com.cherishTang.laishou.laishou.activity.bean.HotActivityBean;
import com.cherishTang.laishou.laishou.main.bean.AdvertisingBean;
import com.cherishTang.laishou.laishou.main.bean.RequestSubstationBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.sqlite.BannerBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by CherishTang on 2019/2/25.
 * 莱瘦圈
 */

public class HotActivityFragment extends BaseRecyclerViewFragment<HotActivityBean> implements View.OnClickListener {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
    private boolean isShowHeadView = false;
    CustomTextView hotMenuYl;
    CustomTextView hotMenuGy;
    CustomTextView hotMenuJh;
    CustomTextView hotMenuPx;
    CustomTextView hotMenuLy;
    CustomTextView hotMenuYd;
    CustomTextView hotMenuMr;
    CustomTextView hotMenuCy;
    CustomTextView hotMenuDy;
    CustomTextView hotMenuQb;
    CustomBannerPicture customBannerPicture;

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<HotActivityBean>>>() {
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
        Bundle bd = bundle == null ? new Bundle() : bundle;
        mPage = bd.getInt(ARG_PAGE, 0);
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getActivityPage(new Gson().toJson(new HotActivityRequestBean(mCurrentPage, ConstantsHelper.indexShowPages, UserAccountHelper.getLocalSubstation().getId(), mPage)), this, stringCallback);
    }

    @Override
    protected BaseRecyclerViewAdapter<HotActivityBean> getRecyclerAdapter() {
        HotActivityAdapter hotActivityAdapter = new HotActivityAdapter(getActivity());
        Bundle bd = getArguments() == null ? new Bundle() : getArguments();
        isShowHeadView = bd.getBoolean("isShowHeadView", false);
        if (isShowHeadView) {
            hotActivityAdapter.setHeaderView(R.layout.hot_activity_head_view);
            hotMenuCy = hotActivityAdapter.getHeaderView().findViewById(R.id.hot_menu_cy);
            hotMenuDy = hotActivityAdapter.getHeaderView().findViewById(R.id.hot_menu_dy);
            hotMenuGy = hotActivityAdapter.getHeaderView().findViewById(R.id.hot_menu_gy);
            hotMenuJh = hotActivityAdapter.getHeaderView().findViewById(R.id.hot_menu_jh);
            hotMenuLy = hotActivityAdapter.getHeaderView().findViewById(R.id.hot_menu_ly);
            hotMenuMr = hotActivityAdapter.getHeaderView().findViewById(R.id.hot_menu_mr);
            hotMenuPx = hotActivityAdapter.getHeaderView().findViewById(R.id.hot_menu_px);
            hotMenuQb = hotActivityAdapter.getHeaderView().findViewById(R.id.hot_menu_qb);
            hotMenuYd = hotActivityAdapter.getHeaderView().findViewById(R.id.hot_menu_yd);
            hotMenuYl = hotActivityAdapter.getHeaderView().findViewById(R.id.hot_menu_yl);

            hotMenuCy.setOnClickListener(this);
            hotMenuDy.setOnClickListener(this);
            hotMenuGy.setOnClickListener(this);
            hotMenuJh.setOnClickListener(this);
            hotMenuLy.setOnClickListener(this);
            hotMenuMr.setOnClickListener(this);
            hotMenuPx.setOnClickListener(this);
            hotMenuQb.setOnClickListener(this);
            hotMenuYd.setOnClickListener(this);
            hotMenuYl.setOnClickListener(this);

            customBannerPicture = hotActivityAdapter.getHeaderView().findViewById(R.id.customBannerPicture_hot);
            hotMenuYl.init(R.mipmap.hot_yl, R.string.hot_menu_yl, "1");
            hotMenuGy.init(R.mipmap.hot_gy, R.string.hot_menu_gy, "2");
            hotMenuJh.init(R.mipmap.hot_jh, R.string.hot_menu_jh, "3");
            hotMenuPx.init(R.mipmap.hot_px, R.string.hot_menu_px, "4");
            hotMenuLy.init(R.mipmap.hot_ly, R.string.hot_menu_ly, "5");
            hotMenuYd.init(R.mipmap.hot_yd, R.string.hot_menu_yd, "6");
            hotMenuMr.init(R.mipmap.hot_mr, R.string.hot_menu_mr, "7");
            hotMenuCy.init(R.mipmap.hot_cy, R.string.hot_menu_cy, "8");
            hotMenuDy.init(R.mipmap.hot_dy, R.string.hot_menu_dy, "9");
            hotMenuQb.init(R.mipmap.hot_qb, R.string.hot_menu_qb, "0");

            getAdvertisingList();
        }
        return hotActivityAdapter;
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new HotActivityFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 获取广告列表
     */
    private void getAdvertisingList() {
        ApiHttpClient.getAdvertisingList(new Gson().toJson(new RequestSubstationBean(UserAccountHelper.getLocalSubstation().getId())), this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    ResultListBean<AdvertisingBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                            fromJson(response, new TypeToken<ResultListBean<AdvertisingBean>>() {
                            }.getType());
                    if (resultBean.isSuccess()) {
                        List<BannerBean> listPicture = new ArrayList<>();
                        for (AdvertisingBean bean : resultBean.getData()) {
                            if (bean.getAdvType() == 7)
                                listPicture.add(new BannerBean(bean.getAdvUrl(), bean.getUrl()));
                        }
                        customBannerPicture.initView(listPicture);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        CustomTextView ctv = (CustomTextView) v;
        Bundle bundle = new Bundle();
        bundle.putInt(HotActivityFragment.ARG_PAGE, ctv.getTagString());
        HotActivityListActivity.show(getActivity(), bundle);
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isActivityOrder", false);
        bundle.putString("id", adapter.getList().get(position).getId());
        Log.d("LaiShouLog",adapter.getList().get(position).getId());
        HotDetailActivity.show(getActivity(), bundle);
    }
}
