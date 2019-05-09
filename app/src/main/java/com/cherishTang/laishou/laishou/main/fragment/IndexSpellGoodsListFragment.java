package com.cherishTang.laishou.laishou.main.fragment;

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
import com.cherishTang.laishou.bean.base.ResultListBean;
import com.cherishTang.laishou.custom.customlayout.CustomBannerPicture;
import com.cherishTang.laishou.custom.customlayout.CustomTextView;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.laishou.main.activity.DiscountDetailActivity;
import com.cherishTang.laishou.laishou.main.activity.DiscountListActivity;
import com.cherishTang.laishou.laishou.main.adapter.DiscountListAdapter;
import com.cherishTang.laishou.laishou.main.bean.AdvertisingBean;
import com.cherishTang.laishou.laishou.main.bean.RequestSubstationBean;
import com.cherishTang.laishou.laishou.main.bean.SpellGoodsBean;
import com.cherishTang.laishou.laishou.main.bean.SpellGoodsRequestBean;
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
 * Created by CherishTang on 2019/3/9.
 * 拼团商品列表--首页
 */

public class IndexSpellGoodsListFragment extends BaseRecyclerViewFragment<SpellGoodsBean> implements View.OnClickListener {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
    private CustomProgressDialog customProgressDialog;
    private int spellType;
    private boolean isHideHeadView = false;
    CustomTextView customHot;
    CustomTextView customDiscount;
    CustomTextView customJmhf;
    CustomTextView customSjsm;
    CustomTextView customCloth;
    CustomBannerPicture customBannerPictureDiscount;
    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<SpellGoodsBean>>>() {
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
        spellType = bd.getInt("spellType", 0);
        isHideHeadView = bd.getBoolean("isHideHeadView",false);
        getAdvertisingList();
        requestData();
    }


    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getSpellGoods(new Gson().toJson(new SpellGoodsRequestBean(mCurrentPage, ConstantsHelper.indexShowPages, UserAccountHelper.getLocalSubstation().getId(), spellType == 0 ? null : spellType)), this, stringCallback);
    }

    @Override
    protected BaseRecyclerViewAdapter<SpellGoodsBean> getRecyclerAdapter() {
        DiscountListAdapter discountListAdapter = new DiscountListAdapter(getActivity());
        if(!isHideHeadView){
            discountListAdapter.setHeaderView(R.layout.index_discount_fragment_head_view);
            customHot = discountListAdapter.getHeaderView().findViewById(R.id.custom_hot);
            customDiscount = discountListAdapter.getHeaderView().findViewById(R.id.custom_discount);
            customJmhf = discountListAdapter.getHeaderView().findViewById(R.id.custom_jmhf);
            customSjsm = discountListAdapter.getHeaderView().findViewById(R.id.custom_sjsm);
            customCloth = discountListAdapter.getHeaderView().findViewById(R.id.custom_cloth);
            customBannerPictureDiscount = discountListAdapter.getHeaderView().findViewById(R.id.customBannerPicture_discount);
            customHot.init(R.mipmap.ic_discount_menu_hot, R.string.discount_menu_rx, "1");
            customDiscount.init(R.mipmap.ic_discount_menu_fw, R.string.discount_menu_fw, "2");
            customJmhf.init(R.mipmap.ic_discount_menu_js, R.string.discount_menu_js, "3");
            customSjsm.init(R.mipmap.ic_discount_menu_cy, R.string.discount_menu_cy, "4");
            customCloth.init(R.mipmap.ic_discount_menu_fz, R.string.discount_menu_fz, "5");
        }
        return discountListAdapter;
    }


    @Override
    public void onClick(View v) {
        CustomTextView ctv = (CustomTextView) v;
        int spellType = ctv.getTagString();
        Bundle bundle = new Bundle();
        bundle.putInt("spellType", spellType);
        bundle.putBoolean("isHideHeadView",true);
        DiscountListActivity.show(getActivity(), bundle);
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
                            if (bean.getAdvType() == 8)
                                listPicture.add(new BannerBean(bean.getAdvUrl(),bean.getUrl()));
                        }
                        customBannerPictureDiscount.initView(listPicture);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new IndexSpellGoodsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        Bundle bundle = new Bundle();
        bundle.putString("id", adapter.getList().get(position).getId());
        DiscountDetailActivity.show(getActivity(), bundle);
    }
}
