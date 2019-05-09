package com.cherishTang.laishou.laishou.user.adapter;

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
import com.cherishTang.laishou.custom.customlayout.AutoNextLineLinearlayout;
import com.cherishTang.laishou.custom.customlayout.RoundImageView;
import com.cherishTang.laishou.laishou.consultant.bean.UserListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/3/7.
 * 我的会员
 */
public class NutritionConsultantMemberListAdapter extends BaseRecyclerViewAdapter<UserListBean> {

    public NutritionConsultantMemberListAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.consultant_member_list_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvName.setText(mList.get(pos).getName());
        holder.tvTel.setText(mList.get(pos).getMobile());
        holder.tvWeight.setText(mList.get(pos).getWeight() + "kg");
        holder.tvBmi.setText(mList.get(pos).getBmi() + "");

        Glide.with(mContext)
                .load(mList.get(pos).getHeadImg())
                .asBitmap()
                .error(R.mipmap.icon_head_default)
                .dontAnimate()
                .into(holder.imageHead);
    }


    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.image_head)
        RoundImageView imageHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_tel)
        TextView tvTel;
        @BindView(R.id.tv_weight)
        TextView tvWeight;
        @BindView(R.id.tv_bmi)
        TextView tvBmi;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }
}
