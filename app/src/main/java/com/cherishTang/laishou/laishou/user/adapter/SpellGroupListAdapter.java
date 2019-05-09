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
import com.cherishTang.laishou.enumbean.SpellStateEnum;
import com.cherishTang.laishou.laishou.user.bean.SpellBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/3/5.
 * 拼团
 */
public class SpellGroupListAdapter extends BaseRecyclerViewAdapter<SpellBean> {

    public SpellGroupListAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.spell_group_list_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvOrder.setText("订单号：" + mList.get(pos).getIndentCode());
        Glide.with(mContext)
                .load(mList.get(pos).getGoodsUrl())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(holder.imageShow);
        holder.tvTitle.setText(mList.get(pos).getGoodsName());
        holder.tvNumber.setText("数量：" + mList.get(pos).getNumber() + "个");
        if (mList.get(pos).getSpellStatus() != null)
            holder.tvState.setText(mList.get(pos).getSpellStatus().getName());
        holder.tvTotalPrice.setText("合计：" + mList.get(pos).getTotalPrice());
        if (mList.get(pos).getSpellStatus() != null && mList.get(pos).getSpellStatus() == SpellStateEnum.spelling) {
            holder.bottomMenu.setVisibility(View.VISIBLE);
            holder.tvInvite.setVisibility(View.VISIBLE);
            holder.tvCancel.setVisibility(View.GONE);
        }else if (mList.get(pos).getSpellStatus() != null && mList.get(pos).getSpellStatus() == SpellStateEnum.spelled) {
            holder.bottomMenu.setVisibility(View.VISIBLE);
            holder.tvInvite.setVisibility(View.GONE);
            holder.tvCancel.setVisibility(View.VISIBLE);
        }else{
            holder.bottomMenu.setVisibility(View.GONE);
        }
        holder.tvCancel.setOnClickListener( v ->{
            if(onBottomOptionClick!=null)
                onBottomOptionClick.onDelete(v,pos);
        });

        holder.tvInvite.setOnClickListener( v ->{
            if(onBottomOptionClick!=null)
                onBottomOptionClick.onInvite(v,pos);
        });

    }

    public interface OnBottomOptionClick {
        void onDelete(View v, int position);
        void onInvite(View v, int position);
    }

    private OnBottomOptionClick onBottomOptionClick;

    public void setOnBottomOptionClick(OnBottomOptionClick onBottomOptionClick) {
        this.onBottomOptionClick = onBottomOptionClick;
    }
    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_order)
        TextView tvOrder;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.image_show)
        ImageView imageShow;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_total_price)
        TextView tvTotalPrice;
        @BindView(R.id.tv_cancel)
        TextView tvCancel;
        @BindView(R.id.tv_invite)
        TextView tvInvite;
        @BindView(R.id.bottom_menu)
        AutoNextLineLinearlayout bottomMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }
}
