package com.cherishTang.laishou.laishou.club.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.custom.customlayout.AutoNextLineLinearlayout;
import com.cherishTang.laishou.custom.customlayout.CornersImageView;
import com.cherishTang.laishou.custom.customlayout.StarBar;
import com.cherishTang.laishou.laishou.club.bean.ClubBean;
import com.cherishTang.laishou.util.apiUtil.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/2/24.
 * 推荐俱乐部
 */
public class ClubAdapter extends BaseRecyclerViewAdapter<ClubBean> {

    public ClubAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.index_club_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvClubName.setText(mList.get(pos).getClubName());
        holder.tvAddress.setText(mList.get(pos).getClubAddress());
        holder.tvScore.setText(mList.get(pos).getScore() + "分");
        holder.starBar.setStarClickable(false);
        holder.starBar.setStarMark(mList.get(pos).getScore());
        holder.tvUserNumber.setText("已有会员" + mList.get(pos).getUserNumber() + "名");

        Glide.with(mContext)
                .load(mList.get(pos).getLogo())
                .asBitmap()
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(holder.showPicture);
        holder.ll_mark.removeAllViews();
        if (!StringUtil.isEmpty(mList.get(pos).getLabel())) {
            final String[] marks = mList.get(pos).getLabel().replace("，", ",").split(",");
            for (String mark : marks) {
                addMarkView(holder.ll_mark, mark);
            }
        }
        if (!StringUtil.isEmpty(mList.get(pos).getIconType())) {
            final String[] iconTypes = mList.get(pos).getIconType().replace("，", ",").split(",");
            List<String> iconTypesList = Arrays.asList(iconTypes);

            if(iconTypesList.contains("1")){
                holder.tvKeyword1.setVisibility(View.VISIBLE);
            }else{
                holder.tvKeyword1.setVisibility(View.GONE);
            }

            if(iconTypesList.contains("2")){
                holder.tvKeyword2.setVisibility(View.VISIBLE);
            }else{
                holder.tvKeyword2.setVisibility(View.GONE);
            }

            if(iconTypesList.contains("3")){
                holder.tvKeyword3.setVisibility(View.VISIBLE);
            }else{
                holder.tvKeyword3.setVisibility(View.GONE);
            }
            if(iconTypesList.contains("4")){
                holder.tvKeyword4.setVisibility(View.VISIBLE);
            }else{
                holder.tvKeyword4.setVisibility(View.GONE);
            }
        }

    }

    private void addMarkView(AutoNextLineLinearlayout llMark, String marks) {
        TextView textView = new TextView(mContext);
        textView.setTextAppearance(mContext, R.style.club_marks);
        textView.setText(marks);
        textView.setPadding((int) mContext.getResources().getDimension(R.dimen.x8), 0,
                (int) mContext.getResources().getDimension(R.dimen.x8), 0);
        textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_green));
        llMark.addView(textView);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.showPicture)
        CornersImageView showPicture;
        @BindView(R.id.tv_club_name)
        TextView tvClubName;
        @BindView(R.id.tv_score)
        TextView tvScore;
        @BindView(R.id.starBar)
        StarBar starBar;
        @BindView(R.id.ll_mark)
        AutoNextLineLinearlayout ll_mark;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.tv_user_number)
        TextView tvUserNumber;
        @BindView(R.id.tv_keyword_1)
        TextView tvKeyword1;
        @BindView(R.id.tv_keyword_2)
        TextView tvKeyword2;
        @BindView(R.id.tv_keyword_3)
        TextView tvKeyword3;
        @BindView(R.id.tv_keyword_4)
        TextView tvKeyword4;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }
}
