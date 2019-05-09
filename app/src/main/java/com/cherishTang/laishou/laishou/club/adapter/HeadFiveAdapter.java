package com.cherishTang.laishou.laishou.club.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.custom.picDialog.PicShowDialog;
import com.cherishTang.laishou.custom.picDialog.bean.ImageInfo;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.laishou.club.bean.CirclePageBean;
import com.cherishTang.laishou.laishou.user.adapter.CircleImageShowAdapter;

import java.util.ArrayList;
import java.util.List;

public class HeadFiveAdapter extends BaseQuickAdapter<CirclePageBean.DataBean.ListBean, BaseViewHolder> {
    public HeadFiveAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CirclePageBean.DataBean.ListBean item) {

        helper.setText(R.id.tv_send_date, item.getCreateTime());
        helper.setText(R.id.tv_circle_name, item.getName());
        helper.setText(R.id.tv_circle_content, item.getIntroduce());
        ImageView imageView = helper.getView(R.id.head_image_circle);
        RecyclerView mRecyclerViewCircleShow = helper.getView(R.id.mRecyclerViewCircle_Show);
        Glide.with(mContext)
                .load(item.getHeadImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(imageView);
        final CircleImageShowAdapter circleImageShowAdapter = new CircleImageShowAdapter(mContext);
        mRecyclerViewCircleShow.setLayoutManager(new FullyGridLayoutManager(mContext, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        final List<ImageInfo> imageInfosList = new ArrayList<>();
        if (item.getPhotoList() != null) {
            for (int i = 0; i < item.getPhotoList().size(); i++) {
                imageInfosList.add(new ImageInfo(item.getPhotoList().get(i), 200, 200));
            }
        }
        circleImageShowAdapter.setList(imageInfosList);
        mRecyclerViewCircleShow.setAdapter(circleImageShowAdapter);
        circleImageShowAdapter.setOnItemClickListener((view, position1) -> {
            PicShowDialog dialog = new PicShowDialog(mContext, imageInfosList, position1);
            dialog.show();
        });

    }
}
