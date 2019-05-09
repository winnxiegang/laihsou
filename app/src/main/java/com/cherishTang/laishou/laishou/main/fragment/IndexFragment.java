package com.cherishTang.laishou.laishou.main.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.activity.WebViewActivity;
import com.cherishTang.laishou.activity.WebViewMessageActivity;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseFragment;
import com.cherishTang.laishou.bean.ComListBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.bean.base.ResultListBean;
import com.cherishTang.laishou.custom.customlayout.CustomBannerPicture;
import com.cherishTang.laishou.custom.customlayout.CustomTextView;
import com.cherishTang.laishou.custom.customlayout.ObservableScrollView;
import com.cherishTang.laishou.custom.dialog.ConfirmDialog;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.custom.recyclerview.FullyLinearLayoutManager;
import com.cherishTang.laishou.custom.recyclerview.GridSpacingItemDecoration;
import com.cherishTang.laishou.enumbean.LoginTypeEnum;
import com.cherishTang.laishou.laishou.activity.activity.HotActivity;
import com.cherishTang.laishou.laishou.activity.activity.HotDetailActivity;
import com.cherishTang.laishou.laishou.activity.activity.VedioListActivity;
import com.cherishTang.laishou.laishou.activity.activity.WenZhanListActivity;
import com.cherishTang.laishou.laishou.club.activity.LaiShowClubDetailActivity;
import com.cherishTang.laishou.laishou.club.activity.LaiShowEdenListActivity;
import com.cherishTang.laishou.laishou.club.adapter.ClubAdapter;
import com.cherishTang.laishou.laishou.consultant.activity.ConsultantDetailActivity;
import com.cherishTang.laishou.laishou.consultant.activity.ConsultantListActivity;
import com.cherishTang.laishou.laishou.consultant.adapter.RecommendedConsultantAdapter;
import com.cherishTang.laishou.laishou.main.activity.AdvertisingWebViewActivity;
import com.cherishTang.laishou.laishou.main.activity.DiscountDetailActivity;
import com.cherishTang.laishou.laishou.main.activity.DiscountListActivity;
import com.cherishTang.laishou.laishou.main.activity.LaiShowWebViewActivity;
import com.cherishTang.laishou.laishou.main.activity.LaiShowWebViewForMainActivity;
import com.cherishTang.laishou.laishou.main.activity.SignInActivity;
import com.cherishTang.laishou.laishou.main.activity.VideoViewActivity;
import com.cherishTang.laishou.laishou.main.adapter.RemenRecommendAdapter;
import com.cherishTang.laishou.laishou.main.adapter.ChangPinRecommendAdapter;
import com.cherishTang.laishou.laishou.main.adapter.HuoDongRecommendAdapter;
import com.cherishTang.laishou.laishou.main.adapter.ShipinRecommendAdapter;
import com.cherishTang.laishou.laishou.main.bean.AdvertisingBean;
import com.cherishTang.laishou.laishou.main.bean.LocationBean;
import com.cherishTang.laishou.laishou.main.bean.MainLaiShowListBean;
import com.cherishTang.laishou.laishou.main.bean.RecommendBean;
import com.cherishTang.laishou.laishou.main.bean.RequestSubstationBean;
import com.cherishTang.laishou.laishou.main.bean.SubstationBean;
import com.cherishTang.laishou.laishou.user.bean.UserInfo;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.sqlite.BannerBean;
import com.cherishTang.laishou.util.GPSUtils;
import com.cherishTang.laishou.util.QRCodeUtil;
import com.cherishTang.laishou.util.apiUtil.KeyBoardUtils;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
import com.cherishTang.laishou.util.apiUtil.SoftKeyBoardListener;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.params.DataHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Request;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 方舟 on 2017/5/24.
 * todo 首页
 */

public class IndexFragment extends BaseFragment implements SearchView.OnQueryTextListener, View.OnClickListener {
    @BindView(R.id.searchEdit)
    EditText searchEdit;
    @BindView(R.id.fouce)
    LinearLayout fouce;
    @BindView(R.id.nav_scan)
    ImageView navScan;
    @BindView(R.id.searchBg)
    LinearLayout searchBg;
    @BindView(R.id.customBannerPicture)
    CustomBannerPicture customBannerPicture;
    @BindView(R.id.custom_activity)
    CustomTextView customActivity;
    @BindView(R.id.custom_consultant)
    CustomTextView customConsultant;
    @BindView(R.id.custom_sign_in)
    CustomTextView customSignIn;
    @BindView(R.id.custom_ly)
    CustomTextView customLy;
    @BindView(R.id.mRecyclerView_club)
    RecyclerView mRecyclerViewClub;
    @BindView(R.id.club_layout)
    LinearLayout clubLayout;
    @BindView(R.id.mRecyclerView_consultant)
    RecyclerView mRecyclerViewConsultant;
    @BindView(R.id.mRecyclerView_article)//热门推荐
            RecyclerView mRecyclerViewArticle;
    @BindView(R.id.consultant_layout)
    LinearLayout consultantLayout;
    @BindView(R.id.image_advertising)
    ImageView imageAdvertising;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;
    @BindView(R.id.tv_person_number)
    TextView tvPersonNumber;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    // 所需的全部权限
    static String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    static String[] PERMISSIONS_CAMERA = new String[]{
            Manifest.permission.CAMERA,
    };
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.imgLayout)
    FrameLayout imgLayout;
    @BindView(R.id.tv_consultant)
    TextView tvConsultant;
    @BindView(R.id.ll_filtrate_consultant)
    LinearLayout llFiltrateConsultant;
    @BindView(R.id.mRecyclerView_huodong)
    RecyclerView mRecyclerViewHuodong;
    @BindView(R.id.mRecyclerView_article_more)
    TextView mRecyclerViewArticleMore;
    @BindView(R.id.mRecyclerView_changpin_more)
    TextView mRecyclerViewChangpinMore;
    @BindView(R.id.mRecyclerView_changpin)
    RecyclerView mRecyclerViewChangpin;
    @BindView(R.id.mRecyclerView_shipin_more)
    TextView mRecyclerViewShipinMore;
    @BindView(R.id.mRecyclerView_shipin)
    RecyclerView mRecyclerViewShipin;
    @BindView(R.id.tv_more_club)
    TextView tvMoreClub;
    @BindView(R.id.consultant_more)
    TextView consultantMore;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    Unbinder unbinder;
    private ClubAdapter clubAdapter;
    private HuoDongRecommendAdapter huoDongRecommendAdapter;
    private RemenRecommendAdapter laiShowAdapter;
    private ChangPinRecommendAdapter changPinRecommendAdapter;
    private ShipinRecommendAdapter shipinRecommendAdapter;
    private RecommendedConsultantAdapter consultantAdapter;
    private LocationManager locationManager;
    private String locationProvider;
    private boolean isFirstLoc = true;
    private LatLng desLatLng;
    private CustomProgressDialog customProgressDialog;
    private boolean isRequestError = true;

    @Override
    protected int getLayoutId() {
        return R.layout.index;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        tvPlace.setText(UserAccountHelper.getLocalSubstation() == null ? "合肥" : UserAccountHelper.getLocalSubstation().getName());

        searchEdit.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        customActivity.init(R.mipmap.index_menu_hd, R.string.index_menu_hd, "1");
        customConsultant.init(R.mipmap.index_menu_gw, R.string.index_menu_gw, "2");
        customLy.init(R.mipmap.index_menu_qd, R.string.index_menu_ly, "3");
        customSignIn.init(R.mipmap.index_menu_ly, R.string.index_menu_sign_in, "4");
        searchEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("clubName", v.getText().toString());
                    LaiShowEdenListActivity.show(getActivity(), bundle);
                    // 当按了搜索之后关闭软键盘
                    ((InputMethodManager) searchEdit.getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        });

        clubAdapter = new ClubAdapter(getActivity());
        mRecyclerViewClub.setLayoutManager(new FullyLinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewClub.setAdapter(clubAdapter);
        clubAdapter.setOnItemClickListener(onClubItemClickListener);

        consultantAdapter = new RecommendedConsultantAdapter(getActivity());
        consultantAdapter.setOnItemClickListener(onRecommendedConsultantItemClickListener);
        mRecyclerViewConsultant.setLayoutManager(new FullyLinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewConsultant.setAdapter(consultantAdapter);
//---------------------------------------活动------------------------
        huoDongRecommendAdapter = new HuoDongRecommendAdapter(getActivity());
        huoDongRecommendAdapter.setOnItemClickListener(onHuoDongItemClickListener);
        mRecyclerViewHuodong.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewHuodong.addItemDecoration(new GridSpacingItemDecoration((int) getResources().getDimension(R.dimen.x20),
                ContextCompat.getColor(getActivity(), R.color.white)));
        mRecyclerViewHuodong.setAdapter(huoDongRecommendAdapter);
/**
 * 热门推荐
 */
        laiShowAdapter = new RemenRecommendAdapter(getActivity());
        laiShowAdapter.setOnItemClickListener(onArticleItemClickListener);
        mRecyclerViewArticle.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewArticle.addItemDecoration(new GridSpacingItemDecoration((int) getResources().getDimension(R.dimen.x20),
                ContextCompat.getColor(getActivity(), R.color.white)));
        mRecyclerViewArticle.setAdapter(laiShowAdapter);

        //----------------------------------------产品----------------------------------
        changPinRecommendAdapter = new ChangPinRecommendAdapter(getActivity());
        changPinRecommendAdapter.setOnItemClickListener(onChangpinItemClickListener);
        mRecyclerViewChangpin.setLayoutManager(new FullyLinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewChangpin.addItemDecoration(new GridSpacingItemDecoration((int) getResources().getDimension(R.dimen.x5),
                ContextCompat.getColor(getActivity(), R.color.white)));
        mRecyclerViewChangpin.setAdapter(changPinRecommendAdapter);
        // --------------------------------------视频----------------------------------
        shipinRecommendAdapter = new ShipinRecommendAdapter(getActivity());
        shipinRecommendAdapter.setOnItemClickListener(onShipinItemClickListener);
        mRecyclerViewShipin.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewShipin.addItemDecoration(new GridSpacingItemDecoration((int) getResources().getDimension(R.dimen.x20),
                ContextCompat.getColor(getActivity(), R.color.white)));
        mRecyclerViewShipin.setAdapter(shipinRecommendAdapter);

    }

    /**
     * 莱瘦俱乐部列表点击事件
     */
    ClubAdapter.OnItemClickListener onClubItemClickListener = (view, position) -> {
        Bundle bundle = new Bundle();
        bundle.putString("id", clubAdapter.getList().get(position).getId());
        LaiShowClubDetailActivity.show(getActivity(), bundle);
    };

    /**
     * 推荐顾问列表点击事件
     */
    RecommendedConsultantAdapter.OnItemClickListener onRecommendedConsultantItemClickListener = (view, position) -> {
        LogUtil.show("path:" + consultantAdapter.getList().get(position).getHeadImg());
        Bundle bundle = new Bundle();
        bundle.putString("id", consultantAdapter.getList().get(position).getId());
        ConsultantDetailActivity.show(getActivity(), bundle);
    };
    /**
     * todo 热门
     */
    RemenRecommendAdapter.OnItemClickListener onArticleItemClickListener = (view, position) -> {
        try {
            Intent intent = new Intent(getActivity(), LaiShowWebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("contentId", LaiShowWebViewActivity.content_id);
            bundle.putString("title", laiShowAdapter.getList().get(position).getArticleTitle());
            bundle.putString("id", laiShowAdapter.getList().get(position).getId());
            bundle.putString("image", laiShowAdapter.getList().get(position).getImg());
            DataHolder.getInstance().save(LaiShowWebViewActivity.content_id + "", laiShowAdapter.getList().get(position).getArticleContent());
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.show("e:" + e);
        }
    };
    /**
     * todo huodng点击事件
     */
    HuoDongRecommendAdapter.OnItemClickListener onHuoDongItemClickListener = (view, position) -> {
        try {
            Intent intent = new Intent(getActivity(), HotDetailActivity.class);
            Bundle bundle = new Bundle();
            //   bundle.putString("title_text", huoDongRecommendAdapter.getList().get(position).getActivityTitle());
            bundle.putString("id", huoDongRecommendAdapter.getList().get(position).getId());
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.show("e:" + e);
        }
    };

    /**
     * todo 产品点击事件
     */
    ChangPinRecommendAdapter.OnItemClickListener onChangpinItemClickListener = (view, position) -> {
        try {
            Intent intent = new Intent(getActivity(), DiscountDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", changPinRecommendAdapter.getList().get(position).getId());
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.show("e:" + e);
            ToastUtils.showShort(getActivity(), "播放失败");
        }
    };
    /**
     * todo 视频点击事件
     */
    ShipinRecommendAdapter.OnItemClickListener onShipinItemClickListener = (view, position) -> {
        try {
            Intent intent = new Intent(getActivity(), VideoViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", shipinRecommendAdapter.getList().get(position).getArticleTitle());
            bundle.putString("path", shipinRecommendAdapter.getList().get(position).getArticleContent());
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.show("e:" + e);
            ToastUtils.showShort(getActivity(), "播放失败");
        }
    };

    @Override
    protected void initData(Bundle bundle) {
        getLocation();
    }

    @Override
    public void onDestroy() {
        if (locationManager != null) {
            //移除监听器
            locationManager.removeUpdates(locationListener);
        }
        super.onDestroy();
    }


    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public void openGPS(Context context) {
        if (!GPSUtils.isGPSOpen(context)) {
            new ConfirmDialog(context)
                    .setMessage("检测到您的GPS可能未开启，前往设置？")
                    .setSureText("设置")
                    .setOnSureClickListener(dialog -> GPSUtils.openGPS(getActivity()))
                    .builder()
                    .show();
        }
    }

    private void getLocation() {

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);//获取地理位置管理器
        //获取所有可用的位置提供器
        List<String> providers = null;
        if (locationManager != null)
            providers = locationManager.getProviders(true);
        if (providers != null && providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;//如果是GPS
        } else if (providers != null && providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER; //如果是Network
        } else if (providers != null && providers.contains(LocationManager.PASSIVE_PROVIDER)) {
            //如果是PASSIVE定位
            locationProvider = LocationManager.PASSIVE_PROVIDER;
        } else {
            openGPS(getActivity());
            getSubstationList(null, null);
            return;
        }
        if (checkPermission(PERMISSIONS)) {
            requestPermission(ConstantsHelper.FIND_LOCATION_REQUEST_CODE, getActivity(), PERMISSIONS);
            return;
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);//低精度，如果设置为高精度，依然获取不了location。
        criteria.setAltitudeRequired(false);//不要求海拔
        criteria.setBearingRequired(false);//不要求方位
        criteria.setCostAllowed(true);//允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗
        locationProvider = locationManager.getBestProvider(criteria, true);
        //获取Location
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            //不为空,显示地理位置经纬度
            showLocation(location);
        } else {
//            openGPS(getActivity());
            getSubstationList(null, null);
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {
            LogUtil.show("------onStatusChanged-------");
        }

        @Override
        public void onProviderEnabled(String provider) {
            LogUtil.show("------onProviderEnabled-------");
        }

        @Override
        public void onProviderDisabled(String provider) {
            LogUtil.show("------onProviderDisabled-------");
        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            LogUtil.show("------onLocationChanged------");
            showLocation(location);
        }
    };

    /**
     * 显示地理位置经度和纬度信息
     *
     * @param location
     */
    private void showLocation(Location location) {
        // 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        // sourceLatLng待转换坐标
        LatLng oldLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        converter.coord(oldLatLng);
        desLatLng = converter.convert();
        if (isFirstLoc) {
            getSubstationList(desLatLng.longitude, desLatLng.latitude);
            isFirstLoc = false;
        }
    }

    /**
     * 加载分站列表
     */
    private void getSubstationList(Double ln, Double lat) {
        ApiHttpClient.getLocalSubstation(new Gson().toJson(new LocationBean(ln, lat)), this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                isRequestError = false;
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<SubstationBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<SubstationBean>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    if (tvPlace != null)
                        tvPlace.setText(resultBean.getData() != null ? "合肥" : resultBean.getData().getName());
                    UserAccountHelper.saveLocalSubstation(resultBean.getData());
                    if (tvPersonNumber != null)
                        tvPersonNumber.setText(resultBean.getData() == null ? "0" : resultBean.getData().getUserNumber());
                    getAdvertisingList();
                    getRecommendList();
                    getArticleList();
                }
            }
        });
    }


    /**
     * 获取广告列表
     */
    private void getAdvertisingList() {
        ApiHttpClient.getAdvertisingList(new Gson().toJson(new RequestSubstationBean(UserAccountHelper.getLocalSubstation().getId())), this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                isRequestError = false;
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    ResultListBean<AdvertisingBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                            fromJson(response, new TypeToken<ResultListBean<AdvertisingBean>>() {
                            }.getType());
                    if (resultBean.isSuccess()) {
                        List<BannerBean> bannerBeanList = new ArrayList<>();
                        for (AdvertisingBean bean : resultBean.getData()) {
                            if (bean.getAdvType() == 2) {
                                Glide.with(getActivity())
                                        .load(bean.getAdvUrl())
                                        .asBitmap()
                                        .dontAnimate()
                                        .into(imageAdvertising);
                                imageAdvertising.setTag(R.id.imageUrl, bean.getAdvUrl());
                                imageAdvertising.setTag(R.id.JumpUrl, bean.getUrl());
                                imageAdvertising.setTag(R.id.imageType, bean.getUrlType());
                                imageAdvertising.setTag(R.id.TypeId, bean.getId());
                            } else if (bean.getAdvType() == 1) {
                                bannerBeanList.add(new BannerBean(bean.getAdvUrl(), bean.getUrl(), bean.getUrlType(), bean.getId()));
                                /**
                                 * 2019-04-20 17:03:25.948 25119-25119/com.cherishTang.laishou D/LaiShouLog: xgimageUrlhttp://47.99.100.88:81/file/showimg?filename=20190417102418.jpg
                                 * 2019-04-20 17:03:25.948 25119-25119/com.cherishTang.laishou D/LaiShouLog: xgAddUrl18350fd8-35be-423a-b54f-72b5e86a64bf
                                 * 2019-04-20 17:03:25.948 25119-25119/com.cherishTang.laishou D/LaiShouLog: xgimageType2
                                 * UrlType：无(0)，文章(1)，活动(2)，视频(3)，产品(4)
                                 */
                            } else if (bean.getAdvType() == 3) {
                                Glide.with(getActivity())
                                        .load(bean.getAdvUrl())
                                        .asBitmap()
                                        .dontAnimate()
                                        .into(image3);
                                image3.setTag(R.id.imageUrl, bean.getAdvUrl());
                                image3.setTag(R.id.JumpUrl, bean.getUrl());
                                image3.setTag(R.id.imageType, bean.getUrlType());
                                image3.setTag(R.id.TypeId, bean.getId());
                                LogUtil.show("xgimageUrl" + bean.getAdvUrl());
                                LogUtil.show("xgJumpUrl" + bean.getUrl());
                                LogUtil.show("xgimageType" + bean.getUrlType());
                                LogUtil.show("xgTypeId" + bean.getId());
                            } else if (bean.getAdvType() == 4) {
                                Glide.with(getActivity())
                                        .load(bean.getAdvUrl())
                                        .asBitmap()
                                        .dontAnimate()
                                        .into(image4);
                                image4.setTag(R.id.imageUrl, bean.getAdvUrl());
                                image4.setTag(R.id.JumpUrl, bean.getUrl());
                                image4.setTag(R.id.imageType, bean.getUrlType());
                                image4.setTag(R.id.TypeId, bean.getId());
                                LogUtil.show("xgimageUrl" + bean.getAdvUrl());
                                LogUtil.show("xgJumpUrl" + bean.getUrl());
                                LogUtil.show("xgimageType" + bean.getUrlType());
                                LogUtil.show("xgTypeId" + bean.getId());
                            }
                        }
                        customBannerPicture.initView(bannerBeanList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 推荐顾问、推荐列表
     */
    private void getRecommendList() {
        ApiHttpClient.getRecommendList(new Gson().toJson(new RequestSubstationBean(UserAccountHelper.getLocalSubstation().getId())), this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                isRequestError = false;
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<RecommendBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<RecommendBean>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    if (resultBean.getData() != null) {
                        clubAdapter.setList(resultBean.getData().getClubList());
                        clubAdapter.notifyDataSetChanged();
                    }
                    if (resultBean.getData() != null) {
                        consultantAdapter.setList(resultBean.getData().getCounselorList());
                        consultantAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
    }


    /**
     * 获取精选文章列表
     */
    private void getArticleList() {
        ApiHttpClient.getHomeRecommendList(new Gson().toJson(new MainLaiShowListBean(1, 4, UserAccountHelper.getLocalSubstation().getId(), 0)), this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                isRequestError = false;
            }

            @Override
            public void onResponse(String response, int id) {
                ComListBean resultBean = new Gson().fromJson(response, ComListBean.class);
                if (resultBean.getCode() == 200) {
                    if (resultBean.getData() != null) {
                        laiShowAdapter.setList(resultBean.getData().getHotList());
                        laiShowAdapter.notifyDataSetChanged();
                        huoDongRecommendAdapter.setList(resultBean.getData().getActivityList());
                        huoDongRecommendAdapter.notifyDataSetChanged();
                        changPinRecommendAdapter.setList(resultBean.getData().getSpellGoodsList());
                        changPinRecommendAdapter.notifyDataSetChanged();
                        shipinRecommendAdapter.setList(resultBean.getData().getVideoList());
                        shipinRecommendAdapter.notifyDataSetChanged();


                    }
                } else {
                    ToastUtils.showLong(getActivity(), resultBean.getMessage());
                }
            }
        });
    }


    /**
     * 移除搜索框的焦点
     */
    private void clearFource() {
        try {
            fouce.setFocusable(true);
            fouce.setFocusableInTouchMode(true);
            fouce.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isRequestError)
            getLocation();
        clearFource();
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
                clearFource();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("clubName", query);
            LaiShowEdenListActivity.show(getActivity(), bundle);
            KeyBoardUtils.closeKeybord(searchEdit, getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @OnClick({R.id.nav_scan, R.id.customBannerPicture, R.id.custom_activity, R.id.custom_consultant, R.id.image3,
            R.id.image4, R.id.image_advertising,
            R.id.custom_ly, R.id.custom_sign_in, R.id.tv_consultant, R.id.tv_more_club, R.id.consultant_more
            , R.id.mRecyclerView_article_more, R.id.mRecyclerView_changpin_more, R.id.mRecyclerView_shipin_more
            , R.id.mRecyclerView_ctivity_more})
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.image3:
                imageOnClick(image3);
                break;
            case R.id.image4:
                imageOnClick(image4);
                break;
            case R.id.image_advertising:
                imageOnClick(imageAdvertising);
                break;
            case R.id.custom_sign_in:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.SIGN_IN_REQUEST_CODE);
                    return;
                }
                if (UserAccountHelper.isLogin() && UserAccountHelper.getUser() != null && UserAccountHelper.getLoginType() == LoginTypeEnum.consultant.getIndex()) {
                    ToastUtils.showShort(getActivity(), "您没有此项权限，请做好管家服务");
                    return;
                }
                SignInActivity.show(getActivity(), bundle);
                break;
            case R.id.tv_more_club:
            case R.id.custom_ly:
                if (desLatLng != null) {
                    bundle.putDouble("longitude", desLatLng.longitude);
                    bundle.putDouble("latitude", desLatLng.latitude);
                }
                LaiShowEdenListActivity.show(getActivity(), bundle);
                break;
            case R.id.consultant_more:
            case R.id.custom_consultant:
            case R.id.tv_consultant:
                ConsultantListActivity.show(getActivity(), bundle);
                break;
            case R.id.custom_activity:
                HotActivity.show(getActivity(), bundle);
                break;
            case R.id.customBannerPicture:
                ToastUtils.showLong(getActivity(), "当前位置" + customBannerPicture.getCurrentPosition());
                // HotDetailActivity.show(getActivity(), bundle);
                break;
            case R.id.nav_scan:
                if (checkPermission(PERMISSIONS_CAMERA)) {
                    requestPermission(ConstantsHelper.CAMERA_REQUEST_CODE, getActivity(), PERMISSIONS_CAMERA);
                    return;
                }
                QRCodeUtil.getInstance().decode(this);
                break;
            case R.id.mRecyclerView_article_more:
                Intent intent = new Intent(getActivity(), WenZhanListActivity.class);
                startActivity(intent);
                break;
            case R.id.mRecyclerView_changpin_more:
                Intent intent2 = new Intent(getActivity(), DiscountListActivity.class);
                startActivity(intent2);
                break;
            case R.id.mRecyclerView_shipin_more:
                startActivity(new Intent(getActivity(), VedioListActivity.class));
                break;
            case R.id.mRecyclerView_ctivity_more:
                startActivity(new Intent(getActivity(), HotActivity.class));
                break;
        }
    }

    public void imageOnClick(View v) {
/**
 * 行风:
 * @Android谢 @二老表 banner中的urlType需要重新定义，0:无;1:文章;2:活动;3:视频;4:产品
 * 二老表:
 * 你先看下你的文章详情怎么显示，是请求了一个详情接口还是直接从列表中将content带进去显示的
 *`
 * 二老表:
 * 0的话写的是无，我就给他跳webview，把加载url内容；后面的1、2、4的话url里面是ID，
 * 即文章、活动、产品的ID，将ID传到对应的详情页就行了，3 视频 url里面放的是视频播放地址，直接跳到视频播放页面就行了
 */
        try {
            /**
             *   image3.setTag(R.id.imageUrl, bean.getAdvUrl());
             *                                 image3.setTag(R.id.JumpUrl, bean.getUrl());
             *                                 image3.setTag(R.id.imageType, bean.getUrlType());
             *                                 image3.setTag(R.id.TypeId, bean.getId());
             */
            ImageView imageView = (ImageView) v;
            if (imageView.getTag(R.id.imageType) != null) {
                if ("0".equals(imageView.getTag(R.id.imageType).toString()) && !StringUtil.isEmpty(imageView.getTag(R.id.AddUrl).toString())) {

                } else if ("1".equals(imageView.getTag(R.id.imageType).toString())) {//1:文章
                    Bundle bundle = new Bundle();
                    bundle.putString("id", imageView.getTag(R.id.TypeId).toString());
                    bundle.putInt("contentId", LaiShowWebViewActivity.content_id);
                    bundle.putString("title", "");
                    bundle.putString("image", imageView.getTag(R.id.JumpUrl).toString());
                    LaiShowWebViewForMainActivity.show(getActivity(), bundle);

                } else if ("2".equals(imageView.getTag(R.id.imageType).toString())) {//2:活动
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isActivityOrder", false);
                    bundle.putString("id", imageView.getTag(R.id.JumpUrl).toString());
                    HotDetailActivity.show(getActivity(), bundle);
                } else if ("3".equals(imageView.getTag(R.id.imageType).toString())) {//3:视频
                    Bundle bundle = new Bundle();
                    bundle.putString("path", imageView.getTag(R.id.JumpUrl).toString());
                    VideoViewActivity.show(getActivity(), bundle);

                } else if ("4".equals(imageView.getTag(R.id.imageType).toString())) {//4:产品
                    Bundle bundle = new Bundle();
                    bundle.putString("id", imageView.getTag(R.id.JumpUrl).toString());
                    DiscountDetailActivity.show(getActivity(), bundle);

                }
            }
        } catch (Exception e) {
            LogUtil.show("e:" + e);
            e.printStackTrace();
        }
//        if (imageInfosList == null || imageInfosList.isEmpty()) return;
//        PicShowDialog dialog = new PicShowDialog(mContext, imageInfosList, mCurrentPosition);
//        dialog.show();
    }

    /**
     * 回调方法
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        返回数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstantsHelper.SIGN_IN_REQUEST_CODE:
                if (resultCode == ConstantsHelper.LOGIN_SUCCESS && data != null &&
                        data.getBooleanExtra("result", false)) {
                    SignInActivity.show(getActivity(), new Bundle());
                }
                break;
            case ConstantsHelper.CAMERA_REQUEST_CODE:
                if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
                    QRCodeUtil.getInstance().decode(this);
                }
                break;
            case ConstantsHelper.QR_CODE_REQUEST:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    try {
                        Bundle bundle = data.getExtras();
                        String scanResult = bundle.getString("result", null);
                        QRCodeUtil.decodeResult(getActivity(), scanResult, onDecodeListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.show("e:" + e);
                    }
                }
                break;
            case ConstantsHelper.FIND_LOCATION_REQUEST_CODE:
                if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
                    getLocation();
                }
                break;
        }
    }

    QRCodeUtil.OnDecodeListener onDecodeListener = isComplete -> {
        if (isComplete) {
            ToastUtils.showShort(getActivity(), "绑定成功");
            checkUserInfo();
        }

    };

    /**
     * 检查登录状态
     */
    private void checkUserInfo() {
        ApiHttpClient.getUserInfo(null, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing()) {
                    customProgressDialog = new CustomProgressDialog(getActivity(), R.style.loading_dialog);
                    customProgressDialog.setMessage("正在更新用户信息，请稍后...");
                    customProgressDialog.show();
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(getActivity(), "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<UserInfo> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(s, new TypeToken<ResultBean<UserInfo>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    UserAccountHelper.saveLoginState(resultBean.getData(), UserAccountHelper.isLogin());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
