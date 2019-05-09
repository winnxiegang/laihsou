package com.cherishTang.laishou.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.bean.Rows;

import java.util.ArrayList;


/**
 * Created by 方舟 on 2017/10/20.
 */
public class HouseRecycleViewAdapter extends BaseRecyclerViewAdapter<Rows> {

    public HouseRecycleViewAdapter(Context mContext) {
        super(mContext);
        mList = (mList == null) ? new ArrayList<Rows>() : mList;
        setList(mList);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEAD && getHeaderView() != null) {
            view = getHeaderView();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;

        if (mList.get(position).getLeaseMode() == null && mList.get(position).getLeaseMode().equals("")) {
            holder.LeaseMode.setVisibility(View.GONE);
        } else holder.LeaseMode.setText(mList.get(position).getLeaseMode());
        if (mList.get(position).getTitle() != null)
            holder.title.setText(mList.get(position).getTitle());
        holder.conditions.setText(mList.get(position).getDoorModel() + "  " +
                mList.get(position).getHouseArea() + "㎡" + "  " + mList.get(position).getPaymentMode());
        holder.houseSource.setText(mList.get(position).getReleaseType());

        if (mList.get(position).getReleaseType().contains("中介"))
            holder.houseSource.setTextColor(ContextCompat.getColor(mContext, R.color.zhongjie));
        else if (mList.get(position).getReleaseType().contains("企业"))
            holder.houseSource.setTextColor(ContextCompat.getColor(mContext, R.color.qiye));
        else
            holder.houseSource.setTextColor(ContextCompat.getColor(mContext, R.color.themeColor));

        holder.renovation.setText(mList.get(position).getRenovation());
        holder.payforMoney.setText(mList.get(position).getLeaseMoney().replace(".0", "") + "元/月");

        Glide.with(mContext).load(mList.get(position).getCoverImage()).error(R.mipmap.icon_zwf_default).into(holder.showPic);
        holder.itemView.setTag(position);
    }


    class ViewHolder extends BaseViewHolder {
        private TextView title, conditions, payforMoney, houseSource, renovation, LeaseMode, liveIn;
        private ImageView showPic;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            renovation = (TextView) itemView.findViewById(R.id.renovation);
            showPic = (ImageView) itemView.findViewById(R.id.showPic);
            title = (TextView) itemView.findViewById(R.id.title);
            conditions = (TextView) itemView.findViewById(R.id.conditions);
            payforMoney = (TextView) itemView.findViewById(R.id.payforMoney);
            houseSource = (TextView) itemView.findViewById(R.id.houseSource);
            LeaseMode = (TextView) itemView.findViewById(R.id.LeaseMode);
        }
    }
}
