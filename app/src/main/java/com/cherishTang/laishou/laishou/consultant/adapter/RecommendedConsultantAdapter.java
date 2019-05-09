package com.cherishTang.laishou.laishou.consultant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.custom.customlayout.CornersImageView;
import com.cherishTang.laishou.custom.customlayout.StarBar;
import com.cherishTang.laishou.laishou.consultant.bean.ConsultantBean;
import com.cherishTang.laishou.util.apiUtil.StringUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/2/24.
 * 推荐顾问
 */
public class RecommendedConsultantAdapter extends BaseRecyclerViewAdapter<ConsultantBean> {


    public RecommendedConsultantAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.index_consultant_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.starBar.setStarMark(mList.get(pos).getScore());
        holder.starBar.setStarClickable(false);
        holder.tvClubName.setText(mList.get(pos).getName());
        holder.tvUserNumber.setText("已有会员" + mList.get(pos).getUserNumber() + "名");
        holder.tvDetail.setText(mList.get(pos).getMobile() + (StringUtil.isEmpty(mList.get(pos).getClubName()) ? "" : (" | " + mList.get(pos).getClubName())));
        holder.tvScore.setText(mList.get(pos).getScore() + "分");

        Glide.with(mContext)
                .load(mList.get(pos).getHeadImg())
                .asBitmap()
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(holder.showPicture);
    }


    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.showPicture)
        CornersImageView showPicture;
        @BindView(R.id.tv_club_name)
        TextView tvClubName;
        @BindView(R.id.starBar)
        StarBar starBar;
        @BindView(R.id.tv_score)
        TextView tvScore;
        @BindView(R.id.tv_user_number)
        TextView tvUserNumber;
        @BindView(R.id.tv_detail)
        TextView tvDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);
        }
    }
}
