package com.cherishTang.laishou.laishou.main.adapter;

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
import com.cherishTang.laishou.custom.customlayout.CornersImageView;
import com.cherishTang.laishou.laishou.user.bean.ArticleBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2019/2/25.
 * 莱瘦圈
 */
public class StoryAdapter extends BaseRecyclerViewAdapter<ArticleBean> {

    public StoryAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.story_list_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvArticleContent.setText(mList.get(pos).getSimple());
        holder.tvArticleTitle.setText(mList.get(pos).getArticleTitle());
        Glide.with(mContext)
                .load(mList.get(pos).getImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(holder.imageArticle);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_article_title)
        TextView tvArticleTitle;
        @BindView(R.id.tv_article_content)
        TextView tvArticleContent;
        @BindView(R.id.image_article)
        ImageView imageArticle;
        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);

        }
    }
}
