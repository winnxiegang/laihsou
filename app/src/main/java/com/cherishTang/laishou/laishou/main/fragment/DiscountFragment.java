package com.cherishTang.laishou.laishou.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseFragment;
import com.cherishTang.laishou.bean.base.ResultListBean;
import com.cherishTang.laishou.custom.customlayout.CustomBannerPicture;
import com.cherishTang.laishou.custom.customlayout.CustomTextView;
import com.cherishTang.laishou.laishou.activity.fragment.ApplyUserDetailListFragment;
import com.cherishTang.laishou.laishou.main.activity.DiscountListActivity;
import com.cherishTang.laishou.laishou.main.adapter.DiscountListAdapter;
import com.cherishTang.laishou.laishou.main.bean.AdvertisingBean;
import com.cherishTang.laishou.laishou.main.bean.RequestSubstationBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.sqlite.BannerBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by CherishTang on 2019/2/24.
 * 优惠券
 */

public class DiscountFragment extends BaseFragment implements DiscountListAdapter.OnItemClickListener {

    @BindView(R.id.custom_hot)
    CustomTextView customHot;
    @BindView(R.id.custom_discount)
    CustomTextView customDiscount;
    @BindView(R.id.custom_jmhf)
    CustomTextView customJmhf;
    @BindView(R.id.custom_sjsm)
    CustomTextView customSjsm;
    @BindView(R.id.custom_cloth)
    CustomTextView customCloth;
    @BindView(R.id.customBannerPicture_discount)
    CustomBannerPicture customBannerPictureDiscount;
    @BindView(R.id.imgLayout)
    FrameLayout imgLayout;
    //    @BindView(R.id.mRecyclerView_discount)
//    RecyclerView mRecyclerViewDiscount;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private DiscountListAdapter discountListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.discount_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        customHot.init(R.mipmap.ic_discount_menu_hot, R.string.discount_menu_rx, "1");
        customDiscount.init(R.mipmap.ic_discount_menu_fw, R.string.discount_menu_fw, "2");
        customJmhf.init(R.mipmap.ic_discount_menu_js, R.string.discount_menu_js, "3");
        customSjsm.init(R.mipmap.ic_discount_menu_cy, R.string.discount_menu_cy, "4");
        customCloth.init(R.mipmap.ic_discount_menu_fz, R.string.discount_menu_fz, "5");

        mFragmentManager = getChildFragmentManager();
        setFragment(SpellGoodsListFragment.instantiate(new Bundle()));
    }

    public void setFragment(Fragment fragment) {
        if (fragment == null) return;
        if (mCurrentFragment != fragment) {
            if (fragment.isAdded()) {
                mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commit();
            } else {
                mFragmentManager.beginTransaction().replace(R.id.discount_fragment_container, fragment).commit();
            }
            mCurrentFragment = fragment;
        }
    }

    @OnClick({R.id.custom_hot, R.id.custom_discount, R.id.custom_jmhf, R.id.custom_sjsm,R.id.custom_cloth})
    public void onClick(View v) {
        CustomTextView ctv = (CustomTextView) v;
        int spellType = ctv.getTagString();
        Bundle bundle = new Bundle();
        bundle.putInt("spellType", spellType);
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

    @Override
    protected void initData(Bundle bundle) {
        getAdvertisingList();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new DiscountFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
