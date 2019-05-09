package com.cherishTang.laishou.laishou.main.adapter;

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
import com.cherishTang.laishou.laishou.main.bean.SpellUserBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/2/26.
 * 打折商品详情
 */
public class DiscountDetailMemberAdapter extends BaseRecyclerViewAdapter<SpellUserBean> {

    public DiscountDetailMemberAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.member_list_item, parent, false);
        }
        return new ViewHolder(view);
    }


    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvSpellName.setText(mList.get(pos).getName());
        Glide.with(mContext)
                .load(mList.get(pos).getHeadImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_head_default)
                .error(R.mipmap.icon_head_default)
                .dontAnimate()
                .into(holder.discountHeadImage);
    }


    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.discount_head_image)
        RoundImageView discountHeadImage;
        @BindView(R.id.tv_spell_name)
        TextView tvSpellName;
        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }
}
