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
import com.cherishTang.laishou.laishou.consultant.bean.ElegantBean;

import java.util.ArrayList;


/**
 * Created by 方舟 on 2017/10/20.
 * 我的相册
 */
public class PhotoImageAddAdapter extends BaseRecyclerViewAdapter<Object> implements View.OnClickListener {

    public ImageViewClearListener imageViewClearListener;
    public ImageViewAddListener imageViewAddListener;

    public PhotoImageAddAdapter(Context context) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.consultant_img_add_item, parent, false);
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
        viewHolder.itemView.setTag(position);
        viewHolder.ivAdd.setTag(R.id.imageUrl, position);
        viewHolder.ivAdd.setOnClickListener(this);
        if (position == mList.size()) {
            Glide.with(mContext).load(R.mipmap.ic_tweet_add).into(viewHolder.ivAdd);
            viewHolder.ivClear.setVisibility(View.GONE);
        } else {
            viewHolder.ivClear.setVisibility(View.VISIBLE);
            viewHolder.ivClear.setOnClickListener(v -> {
                if (imageViewClearListener != null)
                    imageViewClearListener.imgClear(v, pos, v.getTag(R.id.imageUrl).toString());
            });
//            viewHolder.ivClear.setTag(R.id.imageUrl, mList.get(position).getPicture());
//            Glide.with(mContext).load(mList.get(position).getPicture()).into(viewHolder.ivAdd);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                if (imageViewAddListener != null && Integer.parseInt(v.getTag(R.id.imageUrl).toString()) == mList.size())
                    imageViewAddListener.imgAdd(v);
                break;
        }
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
        void imgClear(View view, int pos, String imgUrl);
    }

    public void setImageViewAddListener(ImageViewAddListener imageViewAddListener) {
        this.imageViewAddListener = imageViewAddListener;
    }

    public interface ImageViewAddListener {
        void imgAdd(View view);
    }
}
