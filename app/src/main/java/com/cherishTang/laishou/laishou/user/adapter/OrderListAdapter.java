package com.cherishTang.laishou.laishou.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.custom.customlayout.AutoNextLineLinearlayout;
import com.cherishTang.laishou.enumbean.GoodStateEnum;
import com.cherishTang.laishou.laishou.user.bean.OrderListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/3/5.
 * 订单
 */
public class OrderListAdapter extends BaseRecyclerViewAdapter<OrderListBean> {

    public OrderListAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.order_list_item, parent, false);
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
                .load(mList.get(pos).getGoodsImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(holder.imageShow);
        holder.tvTitle.setText(mList.get(pos).getGoodsName());
        holder.tvPrice.setText("单价：" + mList.get(pos).getIntegral() + "莱币");
        holder.tvNumber.setText("数量：" + mList.get(pos).getNumber() + "个");
        if (mList.get(pos).getStatus() != null)
            holder.tvState.setText(mList.get(pos).getStatus().getName());
        holder.tvTotalPrice.setText("合计：" + mList.get(pos).getIntegral() * mList.get(pos).getNumber());
        if (mList.get(pos).getStatus() != null && mList.get(pos).getStatus() == GoodStateEnum.received) {
            holder.tvDelete.setVisibility(View.VISIBLE);
        } else {
            holder.tvDelete.setVisibility(View.GONE);
        }
        holder.tvDelete.setOnClickListener(v -> {
            if (onBottomOptionClick != null)
                onBottomOptionClick.onDelete(v, pos);
        });

    }

    public interface OnBottomOptionClick {
        void onDelete(View v, int position);
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
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_total_price)
        TextView tvTotalPrice;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }
}
