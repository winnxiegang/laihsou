package com.cherishTang.laishou.laishou.consultant.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseViewPagerFragment;
import com.cherishTang.laishou.bean.base.ResultListBean;
import com.cherishTang.laishou.laishou.main.activity.AdvertisingWebViewActivity;
import com.cherishTang.laishou.laishou.main.bean.AdvertisingBean;
import com.cherishTang.laishou.laishou.main.bean.RequestSubstationBean;
import com.cherishTang.laishou.laishou.main.fragment.LaiShowFragment;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by CherishTang on 2019/2/25.
 * 莱瘦顾问
 */

public class ConsultantListSimpleFragment extends BaseViewPagerFragment {

    @BindView(R.id.image_top)
    ImageView imageTop;

    @Override
    protected int getLayoutId() {
        return R.layout.consultant_tablayout_viewpager;
    }


    @Override
    protected PagerInfo[] getPagers() {
        PagerInfo[] infoList = new PagerInfo[4];
        infoList[0] = new PagerInfo("综合", ConsultantListFragment.class, getBundle(1));
        infoList[1] = new PagerInfo("按人气", ConsultantListFragment.class, getBundle(2));
        infoList[2] = new PagerInfo("按会员数", ConsultantListFragment.class, getBundle(3));
        infoList[3] = new PagerInfo("按距离来", ConsultantListFragment.class, getBundle(4));
        return infoList;
    }


    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        getAdvertisingList();
    }

    @OnClick({R.id.image_top})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_top:
                if (imageTop.getTag(R.id.imageUrl) != null && !StringUtil.isEmpty(imageTop.getTag(R.id.imageUrl).toString())) {
//                    Uri uri = Uri.parse(imageTop.getTag(R.id.imageUrl).toString());
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);
                    Bundle bundle = new Bundle();
                    bundle.putString("loadUrl",imageTop.getTag(R.id.imageUrl).toString());
                    AdvertisingWebViewActivity.show(getActivity(),bundle);

                }
                break;
        }
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
                        for (AdvertisingBean bean : resultBean.getData()) {
                            if (bean.getAdvType() == 6) {
                                Glide.with(getActivity())
                                        .load(bean.getAdvUrl())
                                        .asBitmap()
                                        .error(R.mipmap.icon_zwf_default)
                                        .dontAnimate()
                                        .into(imageTop);
                                imageTop.setTag(R.id.imageUrl, bean.getUrl());
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new ConsultantListSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public Bundle getBundle(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(LaiShowFragment.ARG_PAGE, page);
        return bundle;
    }
}
