package com.cherishTang.laishou.laishou.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.custom.customlayout.RoundImageView;
import com.cherishTang.laishou.laishou.activity.bean.ActivityApplyPeopleBean;
import com.cherishTang.laishou.util.apiUtil.StringUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/2/25.
 * 报名
 */
public class ApplyUserDetailListAdapter extends BaseRecyclerViewAdapter<ActivityApplyPeopleBean> {

    public ApplyUserDetailListAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.apply_detail_list_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        if (StringUtil.isEmpty(mList.get(pos).getName())){
            holder.tvApplyDate.setText(mList.get(pos).getMobile());
        }else{
            holder.tvApplyDate.setText(mList.get(pos).getName());
        }

        holder.tvApplyName.setText(mList.get(pos).getMobile());
        Glide.with(mContext)
                .load(mList.get(pos).getHeadImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(holder.applyHeadImageView);
    }


    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.applyHeadImageView)
        RoundImageView applyHeadImageView;
        @BindView(R.id.tv_apply_name)
        TextView tvApplyName;
        @BindView(R.id.tv_apply_date)
        TextView tvApplyDate;
        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }
}
