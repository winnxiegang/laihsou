package com.cherishTang.laishou.laishou.user.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.custom.picDialog.Compat;
import com.cherishTang.laishou.laishou.main.bean.SignInActivityBean;
import com.cherishTang.laishou.laishou.main.bean.SignInDayBean;
import com.cherishTang.laishou.util.apiUtil.DateUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 方舟 on 2019/2/28.
 * 签到
 */
public class SignInLineAdapter extends BaseRecyclerViewAdapter<SignInDayBean> {

    private boolean isSignIn = false;

    public SignInLineAdapter(Context context) {
        super(context);
        mList = (mList == null) ? new ArrayList<>() : mList;
        setList(mList);
    }

    public void setSignIn(boolean isSignIn) {
        this.isSignIn = isSignIn;
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEAD && getHeaderView() != null) {
            view = getHeaderView();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.time_line_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvDate.setText(mList.get(pos).getStrTime());
        if (mList.get(pos).isSign()) {
            holder.tvDate.setTextColor(ContextCompat.getColor(mContext, R.color.yellow));
            holder.vLine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.yellow));
            holder.vCycle.setBackground(ContextCompat.getDrawable(mContext, R.drawable.cycle_yellow));
        } else {
            holder.tvDate.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.vLine.setBackground(ContextCompat.getDrawable(mContext, R.drawable.cycle_white));
            holder.vCycle.setBackground(ContextCompat.getDrawable(mContext, R.drawable.cycle_white));
        }

        if (pos == mList.size()-1) {
            holder.vLine.setVisibility(View.GONE);
        } else {
            holder.vLine.setVisibility(View.VISIBLE);
        }
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.v_cycle)
        View vCycle;
        @BindView(R.id.v_line)
        View vLine;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }
}
