package com.cherishTang.laishou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.bean.rent.RentParamsBean;
import com.cherishTang.laishou.custom.swiperefreshlayout.adapter.SimpleItemTouchHelperCallback;

import java.util.Collections;
import java.util.List;

/**
 * Created by 方舟 on 2017/10/15.
 * 房源列表
 */
public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> implements
        SimpleItemTouchHelperCallback.ItemTouchHelperAdapter {

    private Context context;
    private View headerView;
    public static final int TYPE_HEAD = 1;
    public static final int TYPE_NOMAL = 0;
    List<RentParamsBean.Detail> list;

    public SearchRecyclerViewAdapter(Context context, List<RentParamsBean.Detail> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEAD && headerView != null) {
            view = headerView;
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.textview, parent, false);
        }
        return new MyViewHolder(view);
    }

    public void setDefaultString( List<RentParamsBean.Detail> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(holder);

        if(headerView==null){
            holder.textView.setText(list.get(position).getName());
            holder.textView.setTag(list.get(position).getId());
        }else{
            holder.textView.setText(list.get(position-1).getName());
            holder.textView.setTag(list.get(position-1).getId());
        }

        holder.itemView.setTag(position);
        if (mOnItemClickListener != null) {
            if(headerView!=null){
                headerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                });
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        int count = (list == null ? 0 : list.size());
        if (headerView != null) {
            count++;
        }
        return count;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView == headerView)
                return;
            textView = (TextView) itemView.findViewById(R.id.textView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int from, int to) {
        Collections.swap(list, from, to);
        notifyItemMoved(from, to);
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView == null)
            return TYPE_NOMAL;
        if (position == 0)
            return TYPE_HEAD;
        return TYPE_NOMAL;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        headerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return headerView;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
