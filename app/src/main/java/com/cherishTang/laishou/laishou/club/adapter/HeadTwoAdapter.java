package com.cherishTang.laishou.laishou.club.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.laishou.club.bean.CircleThreeLeiBean;
import com.cherishTang.laishou.laishou.main.activity.LaiShowWebViewActivity;
import com.cherishTang.laishou.util.params.DataHolder;

public class HeadTwoAdapter extends BaseQuickAdapter<CircleThreeLeiBean.DataBean.ListBean, BaseViewHolder> {
    public HeadTwoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleThreeLeiBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_article_title, item.getArticleTitle());
        helper.setText(R.id.tv_article_content, item.getSimple());
        ImageView imageView = helper.getView(R.id.image_article);
        Glide.with(mContext)
                .load(item.getImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(imageView);
        helper.getView(R.id.head_two_detal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"1".equals(item.getIsVideo())) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("contentId", LaiShowWebViewActivity.content_id);
                    bundle.putString("id", item.getId());
                    bundle.putString("title", item.getArticleTitle());
                    bundle.putString("image", item.getImg());
                    DataHolder.getInstance().save(LaiShowWebViewActivity.content_id + "", item.getArticleContent());
                    LaiShowWebViewActivity.show(mContext, bundle);
                }

            }
        });

    }
}
