package com.cherishTang.laishou.laishou.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.custom.customlayout.RoundImageView;
import com.cherishTang.laishou.laishou.activity.bean.ActivityApplyPeopleBean;
import com.cherishTang.laishou.util.apiUtil.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/2/25.
 * 报名人数
 */
public class ApplyMessageAdapter extends BaseRecyclerViewAdapter<ActivityApplyPeopleBean> {

    public ApplyMessageAdapter(Context mContext) {
        super(mContext);
        if (mList != null) setList(mList);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEAD && getHeaderView() != null) {
            view = getHeaderView();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.apply_list_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        if (StringUtil.isEmpty(mList.get(pos).getName())){
            holder.tvApplyName.setText(mList.get(pos).getMobile());
        }else{
            holder.tvApplyName.setText(mList.get(pos).getName());
        }
        Glide.with(mContext)
                .load(mList.get(pos).getHeadImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(holder.applyHeadImage);
    }


    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.apply_head_image)
        RoundImageView applyHeadImage;
        @BindView(R.id.tv_apply_name)
        TextView tvApplyName;
        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }
}
