package com.cherishTang.laishou.laishou.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.custom.customlayout.AutoNextLineLinearlayout;
import com.cherishTang.laishou.custom.dialog.CallDialog;
import com.cherishTang.laishou.enumbean.AppStateEnum;
import com.cherishTang.laishou.laishou.activity.activity.ActivityDetailPayActivity;
import com.cherishTang.laishou.laishou.activity.bean.HotActivityBean;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by CherishTang on 2019/3/8.
 * describe：活动详情
 */
public class ActivityDetailActivity extends BaseActivity {

    @BindView(R.id.top_image)
    ImageView topImage;
    @BindView(R.id.bottom_menu)
    AutoNextLineLinearlayout bottomMenu;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_organization)
    TextView tvOrganization;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_activity_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.pay_submit)
    Button paySubmit;
    private Bundle bundle;
    private HotActivityBean activityListBean;

    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.activity_detail_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        activityListBean = (HotActivityBean) bundle.getSerializable("itemData");
        if (activityListBean != null) {
            Glide.with(this)
                    .load(activityListBean.getImg())
                    .asBitmap()
                    .placeholder(R.mipmap.icon_zwf_default)
                    .error(R.mipmap.icon_zwf_default)
                    .dontAnimate()
                    .into(topImage);
            tvTitle.setText(activityListBean.getActivityTitle());
            tvAddress.setText("地址：" + activityListBean.getClubAddress());
            tvDate.setText("活动时间：" + activityListBean.getStartTime() + "~" + activityListBean.getEndTime());
            StringBuffer sb = new StringBuffer();
            sb.append(NumberUtils.decimalFormatInteger(activityListBean.getPrice()));
            if (activityListBean.getSignType() != null) {
                sb.append(activityListBean.getSignType().getUnit());
            }
            if (StringUtil.isEmpty(activityListBean.getPrice()) ||
                    NumberUtils.decimalFormatInteger(activityListBean.getPrice()).equals("0") ||
                    activityListBean.getPrice().equals("免费")) {
                tvPrice.setText("免费");
            } else {
                tvPrice.setText(sb.toString());
            }
            tvContent.setText(activityListBean.getActivityContent());
            tvOrganization.setText("组织者：" + activityListBean.getClubName());
            tvTel.setText("联系电话：" + activityListBean.getClubPhone());
            if (activityListBean.getAppStatus() != null && activityListBean.getAppStatus() == AppStateEnum.unPay) {
                paySubmit.setText("立即支付");
            } else {
                paySubmit.setText("联系电话");
            }
        }
    }

    @OnClick({R.id.pay_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_submit:
                if (activityListBean == null) return;
                if (paySubmit.getText().toString().equals("立即支付")) {
                    ActivityDetailPayActivity.show(this, bundle);
                } else {
                    new CallDialog(this, activityListBean.getClubPhone()).call();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SwitchFriendActivity.PK_REQUEST_CODE:
//                if (resultCode == RESULT_OK)
//                    requestData();
                break;
        }
    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ActivityDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
