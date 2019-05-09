package com.cherishTang.laishou.adapter.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;

import java.util.ArrayList;


/**
 * Created by 方舟 on 2017/10/20.
 * 工单详情
 */
public class SingleSelectedPopupWIndowAdapter extends BaseRecyclerViewAdapter<String> {
    private int selectedPosition = 0;

    public SingleSelectedPopupWIndowAdapter(Context mContext) {
        super(mContext);
        mList = (mList == null) ? new ArrayList<String>() : mList;
        setList(mList);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEAD && getHeaderView() != null) {
            view = getHeaderView();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_parent_category_item, parent, false);
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
        holder.tvParentCategoryName.setText(mList.get(pos));
        if(pos==selectedPosition){
            holder.tvParentCategoryName.setTextColor(mContext.getResources().getColor(R.color.themeColor));
        }else{
            holder.tvParentCategoryName.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }
    class ViewHolder extends BaseViewHolder {
        private LinearLayout lyHeight;
        private TextView tvParentCategoryName;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            tvParentCategoryName = (TextView) itemView.findViewById(R.id.tv_parent_category_name);
        }
    }
}
