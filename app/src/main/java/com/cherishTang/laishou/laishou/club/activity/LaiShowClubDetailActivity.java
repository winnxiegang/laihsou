package com.cherishTang.laishou.laishou.club.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.customlayout.StarBar;
import com.cherishTang.laishou.custom.dialog.CallDialog;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.MapDialog;
import com.cherishTang.laishou.custom.empty.EmptyLayout;
import com.cherishTang.laishou.custom.picDialog.PicShowDialog;
import com.cherishTang.laishou.custom.picDialog.bean.ImageInfo;
import com.cherishTang.laishou.custom.recyclerview.FullyLinearLayoutManager;
import com.cherishTang.laishou.laishou.activity.activity.HotActivity;
import com.cherishTang.laishou.laishou.activity.activity.HotActivityListActivity;
import com.cherishTang.laishou.laishou.activity.activity.HotDetailActivity;
import com.cherishTang.laishou.laishou.activity.bean.HotActivityBean;
import com.cherishTang.laishou.laishou.activity.bean.HotActivityRequestBean;
import com.cherishTang.laishou.laishou.activity.fragment.HotActivityFragment;
import com.cherishTang.laishou.laishou.club.adapter.GoldMedalConsultantListAdapter;
import com.cherishTang.laishou.laishou.activity.adapter.HotActivityAdapter;
import com.cherishTang.laishou.laishou.club.bean.ClubBean;
import com.cherishTang.laishou.laishou.consultant.activity.ConsultantDetailActivity;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.NavigationMap;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/2/26.
 * 莱瘦俱乐部详情
 */
public class LaiShowClubDetailActivity extends BaseActivity implements MapDialog.OnNavigationMapListener {
    @BindView(R.id.starBar)
    StarBar starBar;
    @BindView(R.id.mRecyclerView_consultant)
    RecyclerView mRecyclerViewConsultant;
    @BindView(R.id.mRecyclerView_activity)
    RecyclerView mRecyclerViewActivity;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_club_name)
    TextView tvClubName;
    @BindView(R.id.tv_star_score)
    TextView tvStarScore;
    @BindView(R.id.tv_word_date)
    TextView tvWordDate;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.image_tel)
    ImageView imageTel;
    @BindView(R.id.tv_reservation)
    TextView tvReservation;
    private HotActivityAdapter hotActivityAdapter;
    private GoldMedalConsultantListAdapter goldMedalConsultantListAdapter;
    private Bundle bundle;
    private String id;
    private CustomProgressDialog customProgressDialog;
    private ClubBean clubBean;
    TextView richText;
    private List<ImageInfo> imageInfosList = new ArrayList<>();

    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.laishow_club_detail_activity;
    }

    @Override
    public void initView() {
        richText = findViewById(R.id.rich_text);
        goldMedalConsultantListAdapter = new GoldMedalConsultantListAdapter(this);
        mRecyclerViewConsultant.setAdapter(goldMedalConsultantListAdapter);
        mRecyclerViewConsultant.setLayoutManager(new FullyLinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        goldMedalConsultantListAdapter.setOnItemClickListener((view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", goldMedalConsultantListAdapter.getList().get(position).getId());
            ConsultantDetailActivity.show(LaiShowClubDetailActivity.this, bundle);
        });

        hotActivityAdapter = new HotActivityAdapter(this);
        mRecyclerViewActivity.setAdapter(hotActivityAdapter);
        mRecyclerViewActivity.setLayoutManager(new FullyLinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        hotActivityAdapter.setOnItemClickListener((view, position) -> {
            Bundle bd = new Bundle();
            bd.putString("id", hotActivityAdapter.getList().get(position).getId());
            HotDetailActivity.show(LaiShowClubDetailActivity.this, bd);
        });

    }

    @Override
    public void initData() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        id = bundle.getString("id");
        LogUtil.show("id:" + id);

        if (!StringUtil.isEmpty(id))
            clubDetail(new Gson().toJson(new IdBean(id)));
        getActivityPage();
    }

    /**
     * 获取俱乐部详情
     *
     * @param postJson
     */
    private void clubDetail(String postJson) {
        ApiHttpClient.getClubDetail(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(LaiShowClubDetailActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在加载，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(LaiShowClubDetailActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<ClubBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<ClubBean>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    updateView(resultBean.getData());
                } else {
                    ToastUtils.showShort(LaiShowClubDetailActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "操作失败" : resultBean.getMessage());
                }
            }
        });

    }

    private void updateView(ClubBean clubBean) {
        if (clubBean == null) return;
        this.clubBean = clubBean;

        starBar.setIntegerMark(true);
        starBar.setStarMark(clubBean.getScore());
        starBar.setStarClickable(false);
        tvWordDate.setText("营业至" + clubBean.getBusinessTime());
        tvStarScore.setText(NumberUtils.decimalFormatInteger(clubBean.getScore() + "") + "分");
        tvAddress.setText(clubBean.getClubAddress());
        tvClubName.setText(clubBean.getClubName());
        Glide.with(this)
                .load(clubBean.getLogo())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(imageView);
        ImageInfo imageInfo = null;
        imageInfo = new ImageInfo(clubBean.getLogo(), 200, 200);
        imageInfosList.add(imageInfo);
        RichText.fromHtml(clubBean.getClubConent()).into(richText);
        goldMedalConsultantListAdapter.setList(clubBean.getCounselorList());
        goldMedalConsultantListAdapter.notifyDataSetChanged();

    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, LaiShowClubDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 活动列表
     */
    private void getActivityPage() {
        ApiHttpClient.getActivityPage(new Gson().toJson(new HotActivityRequestBean(1, ConstantsHelper.indexShowPages, UserAccountHelper.getLocalSubstation().getId(), 0)), this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(LaiShowClubDetailActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    ResultBean<PageBean<HotActivityBean>> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s, new TypeToken<ResultBean<PageBean<HotActivityBean>>>() {
                    }.getType());
                    if (resultBean != null && resultBean.isSuccess()) {
                        if (resultBean.getData() != null && resultBean.getData().getList() != null) {
                            hotActivityAdapter.setList(resultBean.getData().getList());
                            hotActivityAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (resultBean == null)
                            errorMsgShow("-1", "加载失败", ConstantsHelper.REQUEST_ERROR_LOGIN);
                        else
                            errorMsgShow(resultBean.getCode() + "", resultBean.getMessage(), ConstantsHelper.REQUEST_ERROR_LOGIN);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @OnClick({R.id.image_tel, R.id.tv_consultant_more, R.id.tv_hot_activity_more, R.id.ll_location,R.id.imageView})
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.ll_location:
                MapDialog.getInstance().create(this).show();
                MapDialog.getInstance().setOnNavigationMapListener(this);
                break;
            case R.id.image_tel:
                if (clubBean == null) return;
                new CallDialog(this, clubBean.getClubPhone()).call();
                break;
            case R.id.tv_consultant_more:
                LaiShowEdenListActivity.show(this, bundle);
                break;
            case R.id.tv_hot_activity_more:
                bundle.putInt(HotActivityFragment.ARG_PAGE, 0);
                HotActivityListActivity.show(this, bundle);
                break;
            case R.id.imageView:
                if (imageInfosList == null || imageInfosList.isEmpty()) return;
                PicShowDialog dialog = new PicShowDialog(LaiShowClubDetailActivity.this, imageInfosList, 0);
                dialog.show();
                break;
        }
    }

    @Override
    public void onNavigationClick(View v, int position) {
        switch (position) {
            case 0:
                if (clubBean != null && clubBean.getLng() != null && clubBean.getLat() != null)
                    NavigationMap.baiduNav(this, new double[]{clubBean.getLat(), clubBean.getLng()});
                else ToastUtils.showShort(this, "未获取到当前位置信息");
                break;
            case 1:
                if (clubBean != null && clubBean.getLng() != null && clubBean.getLat() != null)
                    NavigationMap.googleNav(this, new double[]{clubBean.getLat(), clubBean.getLng()});
                else ToastUtils.showShort(this, "未获取到当前位置信息");
                break;
            case 2:
                if (clubBean != null && clubBean.getLng() != null && clubBean.getLat() != null)
                    NavigationMap.invokeNavi(this, "drive", null, null, null, "合肥",
                            new double[]{clubBean.getLat(), clubBean.getLng()}, null, "textApp");
                else ToastUtils.showShort(this, "未获取到当前位置信息");
                break;
            case 3:
                if (clubBean != null && clubBean.getLng() != null && clubBean.getLat() != null)
                    NavigationMap.gaodeNav(this, new double[]{clubBean.getLat(), clubBean.getLng()});
                else
                    ToastUtils.showShort(this, "未获取到当前位置信息");
                break;
            default:
                ToastUtils.showShort(this, "没有检测到导航软件");
                break;
        }
    }
}
