package com.cherishTang.laishou.laishou.club.adapter;

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
import com.cherishTang.laishou.custom.customlayout.RoundImageView;
import com.cherishTang.laishou.custom.customlayout.StarBar;
import com.cherishTang.laishou.laishou.consultant.bean.ConsultantBean;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/2/24.
 * 顾问列表
 */
public class GoldMedalConsultantListAdapter extends BaseRecyclerViewAdapter<ConsultantBean> {

    @BindView(R.id.imageView_gold)
    ImageView imageViewGold;
    @BindView(R.id.consultant_head)
    RoundImageView consultantHead;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_consultant_name)
    TextView tvConsultantName;
    @BindView(R.id.starBar)
    StarBar starBar;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_consultant_detail)
    TextView tvConsultantDetail;

    public GoldMedalConsultantListAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.gold_medal_consultant_list_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.starBar.setIntegerMark(true);
        holder.starBar.setStarMark(4);
        switch (pos) {
            case 0:
                holder.imageViewGold.setImageResource(R.mipmap.cup_first);
                break;
            case 1:
                holder.imageViewGold.setImageResource(R.mipmap.cup_second);
                break;
            case 2:
                holder.imageViewGold.setImageResource(R.mipmap.cup_third);
                break;
        }
        holder.tvConsultantName.setText(mList.get(pos).getName());
        holder.tvConsultantDetail.setText(mList.get(pos).getMobile() + "  " +
                NumberUtils.formatInteger(mList.get(pos).getUserNumber()) + "学员  "
                +(mList.get(pos).getClubName()==null?"":mList.get(pos).getClubName()));
        holder.starBar.setStarMark(mList.get(pos).getScore());
        holder.starBar.setStarClickable(false);
        holder.tvScore.setText(mList.get(pos).getScore() + "分");
        holder.tvScore.setText(pos + 1 + "");
        Glide.with(mContext)
                .load(mList.get(pos).getHeadImg())
                .asBitmap()
                .error(R.mipmap.icon_head_default)
                .dontAnimate()
                .into(holder.consultantHead);

    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.imageView_gold)
        ImageView imageViewGold;
        @BindView(R.id.consultant_head)
        RoundImageView consultantHead;
        @BindView(R.id.tv_rank)
        TextView tvRank;
        @BindView(R.id.tv_consultant_name)
        TextView tvConsultantName;
        @BindView(R.id.starBar)
        StarBar starBar;
        @BindView(R.id.tv_score)
        TextView tvScore;
        @BindView(R.id.tv_consultant_detail)
        TextView tvConsultantDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);
        }
    }
}
