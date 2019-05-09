package com.cherishTang.laishou.laishou.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.adapter.PictureViewPagerAdapter;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.customlayout.ObservableScrollView;
import com.cherishTang.laishou.custom.dialog.CallDialog;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.custom.picDialog.PicShowDialog;
import com.cherishTang.laishou.custom.picDialog.bean.ImageInfo;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.enumbean.LoginTypeEnum;
import com.cherishTang.laishou.laishou.main.adapter.DiscountDetailMemberAdapter;
import com.cherishTang.laishou.laishou.main.bean.SpellGoodsBean;
import com.cherishTang.laishou.laishou.user.bean.GoodExchangeRequestBean;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnUrlClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/2/26.
 * describe
 */
public class DiscountDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.picIndex)
    TextView picIndex;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.mRecyclerView_members)
    RecyclerView mRecyclerViewMembers;
    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.image_share)
    ImageView imageShare;
    @BindView(R.id.rl_title_bar)
    RelativeLayout rlTitleBar;
    @BindView(R.id.mScrollView)
    ObservableScrollView mScrollView;
    @BindView(R.id.tv_money_logo)
    TextView tvMoneyLogo;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_gg_tips)
    TextView tvGgTips;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_place_order)
    TextView tvPlaceOrder;
    @BindView(R.id.tv_join_in_place_order)
    TextView tvJoinInPlaceOrder;
    @BindView(R.id.tv_old_money)
    TextView tvOldMoney;
    @BindView(R.id.tv_spell_goods_number)
    TextView tvSpellGoodsNumber;
    @BindView(R.id.tv_share_number)
    TextView tvShareNumber;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.edit_number)
    EditText editNumber;
    private PictureViewPagerAdapter pictureAdapter;
    private DiscountDetailMemberAdapter discountDetailMemberAdapter;
    private List<ImageView> list;
    private List<ImageInfo> imageInfosList = new ArrayList<>();
    private int mCurrent = 0;
    private Bundle bundle;
    private String id;
    private String indentCode;
    private SpellGoodsBean spellGoodsBean;
    private CustomProgressDialog customProgressDialog;
    private TextView richText;

    @Override
    public String setTitleBar() {
        return null;
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    @Override
    public int initLayout() {
        return R.layout.discount_detail_activity;
    }

    @Override
    public void initView() {
        richText = findViewById(R.id.rich_text);
        discountDetailMemberAdapter = new DiscountDetailMemberAdapter(this);
        mRecyclerViewMembers.setLayoutManager(new FullyGridLayoutManager(this, 5) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewMembers.setAdapter(discountDetailMemberAdapter);
        list = new ArrayList<>();

//        // 获取顶部图片高度后，设置滚动监听,顶部搜索框渐变背景色
//        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(
//                        this);
//                final int imageHeight = frameLayout.getHeight();
//                mScrollView.setScrollViewListener((scrollView, x, y, oldx, oldy) -> {
//                    if (y <= 0) {
//                        rlTitleBar.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));//AGB由相关工具获得，或者美工提供
//                    } else if (y > 0 && y <= imageHeight) {
//                        float scale = (float) y / imageHeight;
//                        float alpha = (255 * scale);
//                        // 只是layout背景透明(仿知乎滑动效果)白色透明
//                        rlTitleBar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
//                    } else {
////							白色不透明
//                        rlTitleBar.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
//                    }
//                });
//            }
//        });
    }

    /**
     * 拼团详情
     */
    private void getSpellGoodDetail() {
        ApiHttpClient.getSpellGoodDetail(new Gson().toJson(new IdBean(id)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(DiscountDetailActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在加载，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(DiscountDetailActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<SpellGoodsBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s,
                        new TypeToken<ResultBean<SpellGoodsBean>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    updateView(resultBean.getData());
                } else {
                    errorMsgShow(resultBean.getCode() + "", resultBean.getMessage(), ConstantsHelper.REQUEST_ERROR_LOGIN);
                }
            }
        });
    }

    /**
     * 刷新布局
     *
     * @param spellGoodsBean
     */
    private void updateView(SpellGoodsBean spellGoodsBean) {
        if (spellGoodsBean == null) return;
        this.spellGoodsBean = spellGoodsBean;
        if (!StringUtil.isEmpty(spellGoodsBean.getGoodsUrl())) {
            setImg(spellGoodsBean.getGoodsUrl(), 0);
            viewPager.addOnPageChangeListener(this);
            picIndex.setText("1/" + list.size());
            if (pictureAdapter == null) {
                pictureAdapter = new PictureViewPagerAdapter(list);
                viewPager.setAdapter(pictureAdapter);
            } else {
                pictureAdapter.notifyDataSetChanged();
            }
        }
        RichText.fromHtml(spellGoodsBean.getGoodsNote()).into(richText);
        tvShareNumber.setText("浏览量：" + spellGoodsBean.getBrowseNumber() + "  分享量：" + spellGoodsBean.getLikeNumber());
        tvAddress.setText("地址：" + spellGoodsBean.getReceiveAddress());
        tvTel.setText("电话：" + spellGoodsBean.getLinkPhone());
        tvSpellGoodsNumber.setText("已拼团数（" + (spellGoodsBean.getUserList() == null ? 0 : spellGoodsBean.getUserList().size())
                + "），满" + (spellGoodsBean.getSpellNumber()) + "人拼团成功");
        tvTitle.setText(spellGoodsBean.getGoodsName());
        tvNumber.setText("已团" + NumberUtils.formatInteger(spellGoodsBean.getThenGroup()) + "份，剩余" + NumberUtils.formatInteger(spellGoodsBean.getInventory()) + "份");
        tvOldMoney.setText(NumberUtils.decimalFormat(spellGoodsBean.getRawPrice() + ""));
        tvOldMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvPrice.setText(NumberUtils.decimalFormat(spellGoodsBean.getPrice() + ""));
        tvUnit.setText(spellGoodsBean.getUnit());
        tvPlaceOrder.setText("￥" + NumberUtils.decimalFormat(spellGoodsBean.getPrice() + "") + "\n发起拼单");
        discountDetailMemberAdapter.setList(spellGoodsBean.getUserList());
        discountDetailMemberAdapter.notifyDataSetChanged();
    }

    /**
     * 邀请好友
     */
    private void getShareLink() {
        ApiHttpClient.getSpellShareLink(new Gson().toJson(new IdBean(id)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(DiscountDetailActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在生成分享链接，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(DiscountDetailActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<String> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s,
                        new TypeToken<ResultBean<String>>() {
                        }.getType());
                if (resultBean.isSuccess() && !StringUtil.isEmpty(resultBean.getData())) {
                    share(resultBean.getData());
                } else {
                    errorMsgShow(resultBean.getCode() + "", resultBean.getMessage(), ConstantsHelper.REQUEST_ERROR_LOGIN);
                }
            }
        });
    }

    @Override
    public void initData() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();

        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            String schemeParams = uri.getScheme();
            indentCode = uri.getQueryParameter("indentCode");
            id = uri.getQueryParameter("id");
            LogUtil.show("参数：" + uri.getQuery());
            if (!StringUtil.isEmpty(schemeParams) && (StringUtil.isEmpty(indentCode) || StringUtil.isEmpty(id))) {
                ToastUtils.showShort(this, "分享已失效");
                tvJoinInPlaceOrder.setVisibility(View.GONE);
            } else
                tvJoinInPlaceOrder.setVisibility(View.VISIBLE);
        } else {
            tvJoinInPlaceOrder.setVisibility(View.GONE);
            id = bundle.getString("id");
        }
        getSpellGoodDetail();
    }

    private void setImg(String loadUrl, @DrawableRes int resImg) {
        ImageView imageView = new ImageView(this);
        ImageInfo imageInfo = null;
        if (loadUrl != null) {
            Glide.with(this).load(loadUrl).asBitmap().placeholder(R.mipmap.icon_banner_default)
                    .error(R.mipmap.icon_banner_default).into(imageView);
            list.add(imageView);
            imageInfo = new ImageInfo(loadUrl, 200, 200);
        } else {
            Glide.with(this).load(resImg)
                    .asBitmap().placeholder(R.mipmap.icon_zwf_default)
                    .error(R.mipmap.icon_zwf_default).into(imageView);
            list.add(imageView);
            imageInfo = new ImageInfo(resImg, 200, 200);
        }
        imageInfosList.add(imageInfo);
        imageView.setOnClickListener(v -> {
            if (imageInfosList == null || imageInfosList.isEmpty()) return;
            PicShowDialog dialog = new PicShowDialog(DiscountDetailActivity.this, imageInfosList, mCurrent);
            dialog.show();
        });

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mCurrent = i;
        picIndex.setText(i + 1 + "/" + list.size());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    @OnClick({R.id.tv_place_order, R.id.tv_option_delete, R.id.tv_option_add, R.id.image_share,
            R.id.tv_join_in_place_order, R.id.image_back})
    public void onClick(View v) {
        int number = 0;
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.tv_join_in_place_order:
                if (spellGoodsBean == null) return;
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(this, this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }

                if (UserAccountHelper.getLoginType() == LoginTypeEnum.consultant.getIndex()) {
                    ToastUtils.showShort(this, "这是莱瘦会员服务，管家请注意");
                    return;
                }
                DialogHelper.getConfirmDialog(DiscountDetailActivity.this, "确定发起参与拼团吗？", (dialogInterface, i) -> {
                    if (customProgressDialog == null || !customProgressDialog.isShowing())
                        customProgressDialog = new CustomProgressDialog(DiscountDetailActivity.this, R.style.loading_dialog);
                    customProgressDialog.setMessage("正在参与拼团，请稍后...");
                    customProgressDialog.show();
                    GoodExchangeRequestBean goodExchangeRequestBean = new GoodExchangeRequestBean();
                    goodExchangeRequestBean.setId(spellGoodsBean.getId());
                    goodExchangeRequestBean.setIndentCode(indentCode);
                    goodExchangeRequestBean.setNumber(Integer.parseInt(editNumber.getText().toString()));
                    spell(new Gson().toJson(goodExchangeRequestBean), true);
                }).show();
                break;
            case R.id.image_share:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(this, this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                getShareLink();
                break;
            case R.id.tv_option_add:
                number = Integer.parseInt(editNumber.getText().toString());
                editNumber.setText((number + 1) + "");

                break;
            case R.id.tv_option_delete:
                number = Integer.parseInt(editNumber.getText().toString());
                if (number > 1)
                    editNumber.setText((number - 1) + "");
                else
                    ToastUtils.showShort(this, "数量不能小于1");
                break;
            case R.id.tv_place_order:
                if (spellGoodsBean == null) return;
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(this, this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                DialogHelper.getConfirmDialog(DiscountDetailActivity.this, "确定发起订单吗？", (dialogInterface, i) -> {
                    if (customProgressDialog == null || !customProgressDialog.isShowing())
                        customProgressDialog = new CustomProgressDialog(DiscountDetailActivity.this, R.style.loading_dialog);
                    customProgressDialog.setMessage("正在发起拼单，请稍后...");
                    customProgressDialog.show();
                    GoodExchangeRequestBean goodExchangeRequestBean = new GoodExchangeRequestBean();
                    goodExchangeRequestBean.setId(spellGoodsBean.getId());
                    goodExchangeRequestBean.setNumber(Integer.parseInt(editNumber.getText().toString()));
                    spell(new Gson().toJson(goodExchangeRequestBean), false);
                }).show();
                break;
        }
    }

    /**
     * 调用友盟分享
     * 分享平台：QQ,QQ空间，微信好友，微信朋友圈
     * 分享方式：web链接
     */
    private void share(String shareLink) {
        //初始化web分享参数
        UMWeb web = new UMWeb(shareLink + "&auth=" + (ApiHttpClient.getToken() == null ? "" : ApiHttpClient.getToken()));//链接
        web.setTitle("邀请你参与拼团");//标题
        web.setThumb(new UMImage(this, R.mipmap.ic_launcher));  //缩略图
        web.setDescription("点击跳转" + getResources().getString(R.string.app_name) +
                "app应用程序");//描述

        new ShareAction(this)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ,
                        SHARE_MEDIA.QZONE,
                        SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener).open();
    }

    /**
     * 分享回调监听
     * 开始、分享成功、分享失败、取消分享
     */
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShort(DiscountDetailActivity.this, "分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort(DiscountDetailActivity.this, "分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort(DiscountDetailActivity.this, "你取消了分享");
        }
    };

    /**
     * 拼团
     *
     * @param postJson
     */
    private void spell(String postJson, boolean isJoin) {
        ApiHttpClient.postSpellGoods(postJson, this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(DiscountDetailActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int code) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    ToastUtils.showShort(DiscountDetailActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "删除成功" : resultBean.getMessage());
                    Bundle bd = new Bundle();
                    bd.putInt("type", 1);
                    bd.putString("id", id);
                    bd.putString("title", "拼团成功");
                    bd.putString("result", "恭喜你，拼团成功！");
                    if (isJoin) {
                        bd.putString("resultTips", "恭喜你，拼团成功！");
                        bd.putBoolean("isShowShareButton", false);
                    } else {
                        bd.putString("resultTips", "恭喜你，拼团成功！你可以点击分享给好友参与拼团");
                        bd.putBoolean("isShowShareButton", true);
                    }
                    PlaceOrderResultActivity.show(DiscountDetailActivity.this, bd);
                } else {
                    ToastUtils.showShort(DiscountDetailActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "删除失败" : resultBean.getMessage());
                }
            }
        });

    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, DiscountDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
