package com.cherishTang.laishou.laishou.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseViewHolder;
import com.cherishTang.laishou.bean.ImagePathBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.customlayout.RoundImageView;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.picDialog.PicShowDialog;
import com.cherishTang.laishou.custom.picDialog.bean.ImageInfo;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.laishou.user.activity.MyCircleActivity;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.laishou.user.bean.MessageEvent;
import com.cherishTang.laishou.laishou.user.bean.MyCircleBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by 方舟 on 2019/2/25.
 * 我的圈子
 */
public class MyCircleListAdapter extends BaseRecyclerViewAdapter<MyCircleBean> {
    private CustomProgressDialog customProgressDialog;

    public MyCircleListAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.my_circle_list_item, parent, false);
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final BaseViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);
        if (getItemViewType(position) == TYPE_HEAD)
            return;
        int pos = getRealPosition(viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvSendDate.setText(mList.get(pos).getCreateTime());
        holder.tvCircleName.setText(mList.get(pos).getName());
        holder.tvCircleContent.setText(mList.get(pos).getIntroduce());
        final CircleImageShowAdapter circleImageShowAdapter = new CircleImageShowAdapter(mContext);
        holder.mRecyclerViewCircleShow.setLayoutManager(new FullyGridLayoutManager(mContext, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        final List<ImageInfo> imageInfosList = new ArrayList<>();
        if (mList.get(pos).getPhotoList() != null) {
            for (int i = 0; i < mList.get(pos).getPhotoList().size(); i++) {
                imageInfosList.add(new ImageInfo(mList.get(pos).getPhotoList().get(i), 200, 200));
            }
        }
        circleImageShowAdapter.setList(imageInfosList);
        holder.mRecyclerViewCircleShow.setAdapter(circleImageShowAdapter);
        circleImageShowAdapter.setOnItemClickListener((view, position1) -> {
            PicShowDialog dialog = new PicShowDialog(mContext, imageInfosList, position1);
            dialog.show();
        });

        Glide.with(mContext)
                .load(mList.get(pos).getHeadImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_head_default)
                .error(R.mipmap.icon_head_default)
                .dontAnimate()
                .into(holder.headImageCircle);
        holder.bt_detlect_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeletePhoto(mList.get(pos).getId());
            }
        });
    }

    /**
     * 积分参加活动
     */
    private void getDeletePhoto(String id) {
        ApiHttpClient.deletePhoto(new Gson().toJson(new IdBean(id)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(mContext, R.style.loading_dialog);
                customProgressDialog.setMessage("正在删除，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(mContext, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean resultBean = new Gson().fromJson(s, ResultBean.class);
                if (resultBean.getCode() == 200) {
                    ToastUtils.showLong(mContext, "删除成功");
                    EventBus.getDefault().post(new MessageEvent("1"));
                } else {
                    ToastUtils.showLong(mContext, resultBean.getMessage());
                }
            }
        });
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.head_image_circle)
        RoundImageView headImageCircle;
        @BindView(R.id.tv_circle_name)
        TextView tvCircleName;
        @BindView(R.id.tv_circle_content)
        TextView tvCircleContent;
        @BindView(R.id.tv_send_date)
        TextView tvSendDate;
        @BindView(R.id.mRecyclerViewCircle_Show)
        RecyclerView mRecyclerViewCircleShow;
        @BindView(R.id.bt_detlect_date)
        Button bt_detlect_date;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == getHeaderView())
                return;
            ButterKnife.bind(this, itemView);
        }
    }
}
