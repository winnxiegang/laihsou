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
import com.cherishTang.laishou.laishou.user.bean.GoodExchangeBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/2/28.
 * 兑换商品列表
 */
public class ExChangeGoodsAdapter extends BaseRecyclerViewAdapter<GoodExchangeBean> {

    public interface OnExchangeClickListener {
        void onExchangeClick(View v, int pos);
    }

    private OnExchangeClickListener onExchangeClickListener;

    public void setOnExchangeClickListener(OnExchangeClickListener onExchangeClickListener) {
        this.onExchangeClickListener = onExchangeClickListener;
    }

    public ExChangeGoodsAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.exchange_goods_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        Glide.with(mContext)
                .load(mList.get(pos).getGoodsImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(holder.imageGood);
        holder.tvGoodName.setText(mList.get(pos).getGoodsName());
        holder.tvMoney.setText(mList.get(pos).getIntegral()+"莱币");
        holder.tvExchange.setOnClickListener(view -> {
            if (onExchangeClickListener != null)
                onExchangeClickListener.onExchangeClick(view, pos);
        });
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.image_good)
        ImageView imageGood;
        @BindView(R.id.tv_good_name)
        TextView tvGoodName;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_exchange)
        TextView tvExchange;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }
}
