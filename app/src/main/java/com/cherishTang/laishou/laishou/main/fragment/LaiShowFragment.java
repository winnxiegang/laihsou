package com.cherishTang.laishou.laishou.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.laishou.main.activity.LaiShowWebViewActivity;
import com.cherishTang.laishou.laishou.main.activity.VideoViewActivity;
import com.cherishTang.laishou.laishou.main.adapter.LaiShowAdapter;
import com.cherishTang.laishou.laishou.main.bean.MainLaiShowListBean;
import com.cherishTang.laishou.laishou.user.bean.ArticleBean;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.params.DataHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by CherishTang on 2019/2/25.
 * 莱瘦圈
 */

public class LaiShowFragment extends BaseRecyclerViewFragment<ArticleBean> {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<ArticleBean>>>() {
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
        Bundle bd = bundle == null?new Bundle():bundle;
        mPage = bd.getInt(ARG_PAGE,0);
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getArticlePage(new Gson().toJson(new MainLaiShowListBean(mCurrentPage, ConstantsHelper.indexShowPages, UserAccountHelper.getLocalSubstation().getId(),mPage)),this,stringCallback);
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        try {
            LogUtil.show("地址：" + adapter.getList().get(position).getArticleContent());
            if (adapter.getList().get(position).getIsVideo() != 1) {
                Bundle bundle = new Bundle();
                bundle.putInt("contentId", LaiShowWebViewActivity.content_id);
                bundle.putString("id",adapter.getList().get(position).getId());
                bundle.putString("title",adapter.getList().get(position).getArticleTitle());
                bundle.putString("image",adapter.getList().get(position).getImg());
                DataHolder.getInstance().save(LaiShowWebViewActivity.content_id + "", adapter.getList().get(position).getArticleContent());
                LaiShowWebViewActivity.show(getActivity(), bundle);
                return;
            }
            if (StringUtil.isEmpty(adapter.getList().get(position).getArticleContent())) {
                ToastUtils.showShort(getActivity(), "视频地址为空");
                return;
            }
            Intent intent = new Intent(getActivity(), VideoViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("videoCoverImage",adapter.getList().get(position).getImg());
            bundle.putString("title",adapter.getList().get(position).getArticleTitle());
            bundle.putString("path", adapter.getList().get(position).getArticleContent());
            intent.putExtras(bundle);
            startActivity(intent);
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_VIEW);
//            Uri uri = Uri.parse(adapter.getList().get(position).getArticleContent());
//            intent.setDataAndType(uri,"video/*");
//            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.show("e:" + e);
            ToastUtils.showShort(getActivity(), "播放失败");
        }

//        Bundle bundle = new Bundle();
//        bundle.putInt("contentId", LaiShowWebViewActivity.content_id);
//        DataHolder.getInstance().save(LaiShowWebViewActivity.content_id + "", adapter.getList().get(position).getArticleContent());
//        LaiShowWebViewActivity.show(getActivity(), bundle);
    }

    @Override
    protected BaseRecyclerViewAdapter<ArticleBean> getRecyclerAdapter() {
        return new LaiShowAdapter(getActivity());
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new LaiShowFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
