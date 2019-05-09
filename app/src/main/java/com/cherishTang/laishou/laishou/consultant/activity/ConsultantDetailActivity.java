package com.cherishTang.laishou.laishou.consultant.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.customlayout.StarBar;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.picDialog.PicShowDialog;
import com.cherishTang.laishou.custom.picDialog.bean.ImageInfo;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.custom.recyclerview.FullyLinearLayoutManager;
import com.cherishTang.laishou.custom.recyclerview.RecycleViewDivider;
import com.cherishTang.laishou.laishou.consultant.bean.ConsultantBean;
import com.cherishTang.laishou.laishou.consultant.bean.ElegantBean;
import com.cherishTang.laishou.laishou.main.activity.DiscountDetailActivity;
import com.cherishTang.laishou.laishou.main.activity.LaiShowWebViewActivity;
import com.cherishTang.laishou.laishou.main.activity.VideoViewActivity;
import com.cherishTang.laishou.laishou.main.adapter.ImageShowAdapter;
import com.cherishTang.laishou.laishou.main.adapter.LaiShowAdapter;
import com.cherishTang.laishou.laishou.user.adapter.NutritionConsultantMemberListAdapter;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
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
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/2/26.
 * 莱瘦顾问详情
 */
public class ConsultantDetailActivity extends BaseActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.starBar)
    StarBar starBar;
    @BindView(R.id.ll_star)
    LinearLayout llStar;
    @BindView(R.id.ll_mark)
    LinearLayout llMark;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_club_name)
    TextView tvClubName;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_user_number)
    TextView tvUserNumber;
    @BindView(R.id.mRecyclerView_show)
    RecyclerView mRecyclerViewShow;
    @BindView(R.id.mRecyclerView_article)
    RecyclerView mRecyclerViewArticle;
    @BindView(R.id.mRecyclerView_member)
    RecyclerView mRecyclerViewMember;
    @BindView(R.id.rl_member)
    LinearLayout rlMember;
    private LaiShowAdapter laiShowAdapter;
    private ImageShowAdapter imageShowAdapter;
    private NutritionConsultantMemberListAdapter nutritionConsultantMemberListAdapter;
    private CustomProgressDialog customProgressDialog;
    private Bundle bundle;
    private String id;
    private List<ImageInfo> imageInfosList = new ArrayList<>();

    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.consultant_detail_activity;
    }

    @Override
    public void initView() {

        imageShowAdapter = new ImageShowAdapter(this);
        mRecyclerViewShow.setAdapter(imageShowAdapter);
        mRecyclerViewShow.setLayoutManager(new FullyGridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rlMember.setVisibility(View.VISIBLE);
        nutritionConsultantMemberListAdapter = new NutritionConsultantMemberListAdapter(this);
        nutritionConsultantMemberListAdapter.setHeaderView(R.layout.consultant_member_list_item_title);
        mRecyclerViewMember.setAdapter(nutritionConsultantMemberListAdapter);
        mRecyclerViewMember.setLayoutManager(new FullyLinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewMember.addItemDecoration(new RecycleViewDivider(this,
                LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.default_background)));

        laiShowAdapter = new LaiShowAdapter(this);
        mRecyclerViewArticle.setAdapter(laiShowAdapter);
        mRecyclerViewArticle.setLayoutManager(new FullyLinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        laiShowAdapter.setOnItemClickListener((view, position) -> {
            try {
                LogUtil.show("地址：" + laiShowAdapter.getList().get(position).getArticleContent());
                if (laiShowAdapter.getList().get(position).getIsVideo() != 1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("contentId", LaiShowWebViewActivity.content_id);
                    bundle.putString("id", laiShowAdapter.getList().get(position).getId());
                    bundle.putString("title", laiShowAdapter.getList().get(position).getArticleTitle());
                    bundle.putString("image", laiShowAdapter.getList().get(position).getImg());
                    DataHolder.getInstance().save(LaiShowWebViewActivity.content_id + "", laiShowAdapter.getList().get(position).getArticleContent());
                    LaiShowWebViewActivity.show(ConsultantDetailActivity.this, bundle);
                    return;
                }
                if (StringUtil.isEmpty(laiShowAdapter.getList().get(position).getArticleContent())) {
                    ToastUtils.showShort(ConsultantDetailActivity.this, "视频地址为空");
                    return;
                }
                Intent intent = new Intent(ConsultantDetailActivity.this, VideoViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", laiShowAdapter.getList().get(position).getArticleTitle());
                bundle.putString("videoCoverImage", laiShowAdapter.getList().get(position).getImg());
                bundle.putString("path", laiShowAdapter.getList().get(position).getArticleContent());
                intent.putExtras(bundle);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.show("e:" + e);
                ToastUtils.showShort(ConsultantDetailActivity.this, "播放失败");
            }
        });
    }

    @Override
    public void initData() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        id = bundle.getString("id");
        if (!StringUtil.isEmpty(id))
            consultantDetail(new Gson().toJson(new IdBean(id)));
    }

    /**
     * 加载顾问详情
     *
     * @param postJson
     */
    private void consultantDetail(String postJson) {
        ApiHttpClient.getConsultantDetail(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(ConsultantDetailActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在加载，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(ConsultantDetailActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {

                ResultBean<ConsultantBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<ConsultantBean>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    updateView(resultBean.getData());
                } else {
                    ToastUtils.showShort(ConsultantDetailActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "操作失败" : resultBean.getMessage());
                }
            }
        });

    }

    private void updateView(ConsultantBean consultantBean) {
        if (consultantBean == null) return;
        tvTel.setText(consultantBean.getMobile());
        tvClubName.setText(consultantBean.getClubName());
        tvName.setText(consultantBean.getName());
        starBar.setIntegerMark(true);
        starBar.setStarMark(consultantBean.getScore());
        starBar.setStarClickable(false);
        tvDescribe.setText(StringUtil.isEmpty(consultantBean.getIntroduce()) ? "未填写" : consultantBean.getIntroduce());
        tvUserNumber.setText("会员：" + NumberUtils.formatInteger(consultantBean.getUserNumber()) + "人");
        tvScore.setText(NumberUtils.decimalFormatInteger(consultantBean.getScore() + "") + "分");
        Glide.with(this)
                .load(consultantBean.getHeadImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(imageView);
        ImageInfo imageInfo = null;
        imageInfo = new ImageInfo(consultantBean.getHeadImg(), 200, 200);
        imageInfosList.add(imageInfo);
        laiShowAdapter.setList(consultantBean.getArticleList());
        laiShowAdapter.notifyDataSetChanged();
        nutritionConsultantMemberListAdapter.setList(consultantBean.getUserList());
        nutritionConsultantMemberListAdapter.notifyDataSetChanged();

        if (consultantBean.getMienList() != null) {
            List<ImageInfo> imageInfosList = new ArrayList<>();
            for (ElegantBean elegantBean : consultantBean.getMienList()) {
                imageInfosList.add(new ImageInfo(elegantBean.getPicture(), 200, 200));
            }
            imageShowAdapter.setList(imageInfosList);
            imageShowAdapter.notifyDataSetChanged();
        }
        imageShowAdapter.setOnItemClickListener((view, position1) -> {
            try {
                PicShowDialog dialog = new PicShowDialog(ConsultantDetailActivity.this, imageShowAdapter.getList(), position1);
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ConsultantDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imageView)
    public void onClick() {
        if (imageInfosList == null || imageInfosList.isEmpty()) return;
        PicShowDialog dialog = new PicShowDialog(ConsultantDetailActivity.this, imageInfosList, 0);
        dialog.show();
    }
}
