package com.cherishTang.laishou.laishou.user.adapter;

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
 * Created by 方舟 on 2017/10/20.
 * 展示
 */
public class CircleImageShowAdapter extends BaseRecyclerViewAdapter<ImageInfo> {

    public ImageViewClearListener imageViewClearListener;
    public ImageViewAddListener imageViewAddListener;

    public CircleImageShowAdapter(Context context) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.img_add_item, parent, false);
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
        viewHolder.ivClear.setVisibility(View.GONE);
        Glide.with(mContext)
                .load(mList.get(pos).getUrl())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(((ViewHolder) holder).ivAdd);
    }

    class ViewHolder extends BaseViewHolder {
        private ImageView ivAdd, ivClear;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ivAdd = (ImageView) itemView.findViewById(R.id.iv_add);
            ivClear = (ImageView) itemView.findViewById(R.id.iv_clear_img);
        }
    }

    public void setImageViewClearListener(ImageViewClearListener imageViewClearListener) {
        this.imageViewClearListener = imageViewClearListener;
    }

    public interface ImageViewClearListener {
        void imgClear(View view, String imgUrl);
    }

    public void setImageViewAddListener(ImageViewAddListener imageViewAddListener) {
        this.imageViewAddListener = imageViewAddListener;
    }

    public interface ImageViewAddListener {
        void imgAdd(View view);
    }
}
