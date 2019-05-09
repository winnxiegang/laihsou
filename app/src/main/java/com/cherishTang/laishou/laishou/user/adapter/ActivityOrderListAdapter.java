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
import com.cherishTang.laishou.enumbean.ActivityStateEnum;
import com.cherishTang.laishou.laishou.activity.bean.HotActivityBean;
import com.cherishTang.laishou.laishou.user.bean.ActivityListBean;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/3/5.
 * 活动订单
 */
public class ActivityOrderListAdapter extends BaseRecyclerViewAdapter<HotActivityBean> {

    public ActivityOrderListAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_order_list_item, parent, false);
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
                .load(mList.get(pos).getImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(holder.imageShow);
        holder.tvTitle.setText(mList.get(pos).getActivityTitle());
        holder.tvAddress.setText("地址：" + mList.get(pos).getClubAddress());
        holder.tvStDate.setText("开始时间：" + mList.get(pos).getStartTime());
        holder.tvEnDate.setText("结束时间：" + mList.get(pos).getEndTime());
        if (mList.get(pos).getAppActivityStatus() != null)
            holder.tvState.setText(mList.get(pos).getAppActivityStatus().getName());
        StringBuffer sb = new StringBuffer();
        sb.append("合计：");
        sb.append(NumberUtils.decimalFormatInteger(mList.get(pos).getPrice()));
        if (mList.get(pos).getSignType() != null) {
            sb.append(mList.get(pos).getSignType().getUnit());
        }
        if (StringUtil.isEmpty(mList.get(pos).getPrice()) ||
                NumberUtils.decimalFormatInteger(mList.get(pos).getPrice()).equals("0") ||
                mList.get(pos).getPrice().equals("免费")) {
            holder.tvDiscount.setText("免费");
        } else {
            holder.tvDiscount.setText(sb.toString());
        }

        if (mList.get(pos).getAppActivityStatus() != null && mList.get(pos).getAppActivityStatus() == ActivityStateEnum.end) {
            holder.tvDelete.setVisibility(View.GONE);
        } else {
            holder.tvDelete.setVisibility(View.VISIBLE);
        }

        holder.tvDelete.setOnClickListener(v -> {
            if (onBottomOptionClick != null)
                onBottomOptionClick.onDelete(v, pos);
        });

        holder.tvLookDetail.setOnClickListener(v -> {
            if (onBottomOptionClick != null)
                onBottomOptionClick.onLookDetail(v, pos);
        });
    }

    public interface OnBottomOptionClick {
        void onDelete(View v, int position);

        void onLookDetail(View v, int position);
    }

    private OnBottomOptionClick onBottomOptionClick;

    public void setOnBottomOptionClick(OnBottomOptionClick onBottomOptionClick) {
        this.onBottomOptionClick = onBottomOptionClick;
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.image_show)
        ImageView imageShow;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_stDate)
        TextView tvStDate;
        @BindView(R.id.tv_enDate)
        TextView tvEnDate;
        @BindView(R.id.tv_discount)
        TextView tvDiscount;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.tv_look_detail)
        TextView tvLookDetail;
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
