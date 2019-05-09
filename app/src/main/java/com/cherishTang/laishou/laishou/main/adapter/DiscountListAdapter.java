package com.cherishTang.laishou.laishou.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.custom.customlayout.CornersImageView;
import com.cherishTang.laishou.laishou.main.bean.SpellGoodsBean;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/2/24.
 * 优惠券
 */
public class DiscountListAdapter extends BaseRecyclerViewAdapter<SpellGoodsBean> {

    public DiscountListAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.discount_list_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
        holder.tvTitle.setText(mList.get(pos).getGoodsName());
        holder.tvMessage.setText(mList.get(pos).getUnit());
        holder.tvSale.setText("已团"+ NumberUtils.formatInteger(mList.get(pos).getThenGroup())+"份");
        holder.tvRemaining.setText("剩余"+ NumberUtils.formatInteger(mList.get(pos).getInventory())+"份");
        Glide.with(mContext)
                .load(mList.get(pos).getGoodsUrl())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(holder.showPicture);
        holder.tvOldPrice.setText(NumberUtils.decimalFormat(mList.get(pos).getRawPrice() + ""));
        holder.tvDiscountPrice.setText(NumberUtils.decimalFormat(mList.get(pos).getPrice() + ""));

    }


    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.showPicture)
        CornersImageView showPicture;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.tv_sale)
        TextView tvSale;
        @BindView(R.id.tv_remaining)
        TextView tvRemaining;
        @BindView(R.id.tv_discount_price)
        TextView tvDiscountPrice;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }

}
