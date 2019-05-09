package com.cherishTang.laishou.laishou.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.bean.ImagePathBean;
import com.cherishTang.laishou.custom.picDialog.bean.ImageInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 方舟 on 2019/2/26.
 * 展示圖片
 */
public class ImageShowAdapter extends BaseRecyclerViewAdapter<ImageInfo> {


    public ImageShowAdapter(Context context) {
        super(context);
        mList = (mList == null) ? new ArrayList<>() : mList;
        setList(mList);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEAD && getHeaderView() != null) {
            view = getHeaderView();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.img_show_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(holder);
        ViewHolder viewHolder = (ViewHolder) holder;
        Glide.with(mContext)
                .load(mList.get(pos).getUrl())
                .asBitmap()
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(viewHolder.ivShow);
    }

    class ViewHolder extends BaseViewHolder {
        private ImageView ivShow;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ivShow =  itemView.findViewById(R.id.iv_show);
        }
    }

}
