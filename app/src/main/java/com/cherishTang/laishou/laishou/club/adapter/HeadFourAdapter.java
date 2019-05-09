package com.cherishTang.laishou.laishou.club.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.laishou.club.bean.CircleThreeLeiBean;
import com.cherishTang.laishou.laishou.main.activity.VideoViewActivity;

public class HeadFourAdapter extends BaseQuickAdapter<CircleThreeLeiBean.DataBean.ListBean, BaseViewHolder> {
    public HeadFourAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleThreeLeiBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getArticleTitle());
        helper.setText(R.id.tv_date, item.getCreateTime());
        ImageView imageView = helper.getView(R.id.imageView);
        Glide.with(mContext)
                .load(item.getImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(imageView);
        helper.getView(R.id.head_four_detal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VideoViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("videoCoverImage", item.getImg());
                bundle.putString("title", item.getArticleTitle());
                bundle.putString("path", item.getArticleContent());
                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });

    }
}
