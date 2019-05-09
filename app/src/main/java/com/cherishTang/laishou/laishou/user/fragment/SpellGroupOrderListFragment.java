package com.cherishTang.laishou.laishou.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.custom.recyclerview.RecycleViewDivider;
import com.cherishTang.laishou.laishou.main.activity.DiscountDetailActivity;
import com.cherishTang.laishou.laishou.user.adapter.SpellGroupListAdapter;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.laishou.user.bean.PageRequestBean;
import com.cherishTang.laishou.laishou.user.bean.SpellBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/3/5.
 * 拼团
 */

public class SpellGroupOrderListFragment extends BaseRecyclerViewFragment<SpellBean> implements SpellGroupListAdapter.OnBottomOptionClick{
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
    private CustomProgressDialog customProgressDialog;
    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<SpellBean>>>() {
        }.getType();
    }
    @Override
    public boolean hideRecycleViewDivider() {
        return true;
    }
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        setCusTomDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,
                (int)getResources().getDimension(R.dimen.y10),
                ContextCompat.getColor(getActivity(), R.color.h_line_color)));
    }
    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        requestData();
    }
    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getMySpellList(new Gson().toJson(new PageRequestBean(mCurrentPage, ConstantsHelper.indexShowPages)),this,stringCallback);
    }
    @Override
    protected BaseRecyclerViewAdapter<SpellBean> getRecyclerAdapter() {
        SpellGroupListAdapter spellGroupListAdapter = new SpellGroupListAdapter(getActivity());
        spellGroupListAdapter.setOnBottomOptionClick(this);
        return spellGroupListAdapter;
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new SpellGroupOrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDelete(View v, int position) {
        DialogHelper.getConfirmDialog(getActivity(), "确定删除此订单吗？", (dialogInterface, i) -> {
            if (customProgressDialog == null || !customProgressDialog.isShowing())
                customProgressDialog = new CustomProgressDialog(getActivity(), R.style.loading_dialog);
            customProgressDialog.setMessage("正在删除订单，请稍后...");
            customProgressDialog.show();
            delete(new Gson().toJson(new IdBean(adapter.getList().get(position).getId())));
        }).show();
    }

    @Override
    public void onInvite(View v, int position) {
        getShareLink(adapter.getList().get(position).getGoodsId());
    }
    /**
     * 邀请好友
     */
    private void getShareLink(String id) {
        ApiHttpClient.getSpellShareLink(new Gson().toJson(new IdBean(id)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(getActivity(), R.style.loading_dialog);
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
                ToastUtils.showShort(getActivity(), "网络状态不佳，请稍后再试！");
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

    /**
     * 调用友盟分享
     * 分享平台：QQ,QQ空间，微信好友，微信朋友圈
     * 分享方式：web链接
     */
    private void share(String shareLink) {
        //初始化web分享参数
        UMWeb web = new UMWeb(shareLink+"&auth="+( ApiHttpClient.getToken() == null ? "" : ApiHttpClient.getToken()));//链接
        web.setTitle("邀请你参与拼团");//标题
        web.setThumb(new UMImage(getActivity(), R.mipmap.ic_launcher));  //缩略图
        web.setDescription("点击跳转" + getResources().getString(R.string.app_name) +
                "app应用程序");//描述

        new ShareAction(getActivity())
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
            ToastUtils.showShort(getActivity(), "分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort(getActivity(), "分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort(getActivity(), "你取消了分享");
        }
    };
    
    
    /**
     * 删除订单
     * @param postJson
     */
    private void delete(String postJson) {
        ApiHttpClient.deleteSpellOrderList(postJson, this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(getActivity(), R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    onRefresh();
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "删除成功" : resultBean.getMessage());
                } else {
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "删除失败" : resultBean.getMessage());
                }
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        Bundle bundle = new Bundle();
        bundle.putString("id", adapter.getList().get(position).getGoodsId());
        DiscountDetailActivity.show(getActivity(), bundle);
    }

}
