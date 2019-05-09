package com.cherishTang.laishou.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.custom.customlayout.CustomPersonalFragmentTextView;
import com.cherishTang.laishou.laishou.main.bean.PersonalCenterItemMenuBean;

import java.util.ArrayList;


/**
 * Created by 方舟 on 2018/7/12.
 * 个人中心网格布局
 */
public class PersonalCenterItemMenuAdapter extends BaseRecyclerViewAdapter<PersonalCenterItemMenuBean> {

    public PersonalCenterItemMenuAdapter(Context mContext) {
        super(mContext);
        mList = (mList == null) ? new ArrayList<PersonalCenterItemMenuBean>() : mList;
        setList(mList);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEAD && getHeaderView() != null) {
            view = getHeaderView();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.personal_center_item_menu,
                    parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        final int pos = getRealPosition(viewHolder);
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.customPersonalFragmentTextView.init(mList.get(pos).getImgRes()
                , mList.get(pos).getStrRes(), mList.get(pos).getTag(),
                mList.get(pos).getClx(), mList.get(pos).getRequestCode(),
                mList.get(pos).getFlag());

    }

    class ViewHolder extends BaseViewHolder {
        CustomPersonalFragmentTextView customPersonalFragmentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            customPersonalFragmentTextView = itemView.findViewById(R.id.customPersonalFragmentTextView);
        }
    }
}
