package com.cherishTang.laishou.laishou.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.laishou.user.bean.MyWeightRecordBean;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/3/9.
 * 我的成绩
 */
public class MyGradeListAdapter extends BaseRecyclerViewAdapter<MyWeightRecordBean> {

    public MyGradeListAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.my_grade_list_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvWeight.setText("体重"+ NumberUtils.decimalFormatInteger(mList.get(pos).getWeight()+"") +"KG");
        holder.tvDate.setText(mList.get(pos).getStrCreateTime());
    }


    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_weight)
        TextView tvWeight;
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
