package com.cherishTang.laishou.laishou.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.laishou.activity.bean.HotActivityBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/2/25.
 * 热门活动
 */
public class HotActivityAdapter extends BaseRecyclerViewAdapter<HotActivityBean> {

    public HotActivityAdapter(Context mContext) {
        super(mContext);
        mList = (mList == null) ? new ArrayList<>() : mList;
        setList(mList);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEAD && getHeaderView() != null) {
            view = getHeaderView();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.hot_activity_menu_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        final StringBuilder sb = new StringBuilder();
        if (mList.get(pos).getAppActivityStatus() != null) {
            sb.append(mList.get(pos).getAppActivityStatus().getName());
            sb.append("  ");
        }
        sb.append(mList.get(pos).getClubAddress());

        holder.tvAddress.setText(sb.toString());
        holder.tvActivityTitle.setText(mList.get(pos).getActivityTitle());
        Glide.with(mContext)
                .load(mList.get(pos).getImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(holder.imageViewActivity);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.imageView_activity)
        ImageView imageViewActivity;
        @BindView(R.id.tv_activity_title)
        TextView tvActivityTitle;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }
}
