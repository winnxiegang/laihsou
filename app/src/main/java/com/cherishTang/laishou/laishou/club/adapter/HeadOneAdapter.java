package com.cherishTang.laishou.laishou.club.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.laishou.club.activity.OnlyShowWebViewActivity;
import com.cherishTang.laishou.laishou.club.bean.CircleHeadOneBean;
import com.cherishTang.laishou.laishou.main.activity.LaiShowWebViewForCircleActivity;

public class HeadOneAdapter extends BaseQuickAdapter<CircleHeadOneBean.DataBean.ListBean, BaseViewHolder> {
    public HeadOneAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleHeadOneBean.DataBean.ListBean item) {
        ImageView imageView = helper.getView(R.id.image_talent);
        Glide.with(mContext)
                .load(item.getHeadImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(imageView);
        helper.getView(R.id.image_talent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", item.getId());
                bundle.putString("title", item.getCreateTime());
                OnlyShowWebViewActivity.show(mContext, bundle);

            }
        });

    }
}
