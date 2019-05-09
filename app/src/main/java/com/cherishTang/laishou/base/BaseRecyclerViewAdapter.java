package com.cherishTang.laishou.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cherishTang.laishou.custom.swiperefreshlayout.adapter.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 方舟 on 2017/12/28.
 *
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements
        SimpleItemTouchHelperCallback.ItemTouchHelperAdapter {
    protected Context mContext;
    protected List<T> mList;
    protected RecyclerView mRecyclerView;

    private View headerView;
    public static final int TYPE_HEAD = 1;
    public static final int TYPE_NOMAL = 0;

    public OnItemClickListener mOnItemClickListener;
    public OnItemLongClickListener mOnItemLongClickListener;

    public BaseRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
   abstract public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) ;

    public void setHeaderView(@LayoutRes int headerViewRes) {
        this.headerView = LayoutInflater.from(mContext).inflate(headerViewRes,null);
        headerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        notifyItemInserted(0);
    }

    public void addAll(List<T> items) {
        if (items != null) {
            this.mList.addAll(items);
            notifyItemRangeInserted(this.mList.size(), items.size());
        }
    }
    public View getHeaderView() {
        return headerView;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder baseViewHolder, final int i) {
        if (getItemViewType(i) == TYPE_HEAD)
            return;
        baseViewHolder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(baseViewHolder.itemView, getRealPosition(baseViewHolder));
        });
        baseViewHolder.itemView.setOnLongClickListener(v -> {
            if (mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onItemLongClick(baseViewHolder.itemView, getRealPosition(baseViewHolder));
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        int count = (mList == null ? 0 : mList.size());
        if (headerView != null) {
            count++;
        }
        return count;
    }
    @Override
    public int getItemViewType(int position) {
        if (headerView == null)
            return TYPE_NOMAL;
        if (position == 0)
            return TYPE_HEAD;
        return TYPE_NOMAL;
    }

    public int getItemViewHeight() {
        return 0;
    }

    public void setList(List<T> list) {
        this.mList = list;
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(T[] list) {
        ArrayList<T> arrayList = new ArrayList<T>(list.length);
        for (T t : list) {
            arrayList.add(t);
        }
        setList(arrayList);
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int from, int to) {
        Collections.swap(mList, from, to);
        notifyItemMoved(from, to);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener l) {
        mOnItemLongClickListener = l;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
