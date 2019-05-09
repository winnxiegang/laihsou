package com.cherishTang.laishou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.bean.PopupWindowBean;
import com.cherishTang.laishou.bean.rent.RentParamsBean;

import java.util.List;


/**
 * Created by 方舟 on 2017/10/20.
 *
 */
public class SingleRecyclerViewAdapter extends BaseRecyclerViewAdapter<PopupWindowBean> {

    public SingleRecyclerViewAdapter(Context context, List<PopupWindowBean> list) {
        super(context);
        if (list != null) setList(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEAD && getHeaderView() != null) {
            view = getHeaderView();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.singler_recyclerview_item, parent, false);
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
        viewHolder.itemView.setTag(position);
        holder.textView.setText(mList.get(position).getName());
        holder.textView.setTag(mList.get(position).getIndex());

    }


    class ViewHolder extends BaseViewHolder {
        private TextView textView;
        private LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            textView = (TextView) itemView.findViewById(R.id.textView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
