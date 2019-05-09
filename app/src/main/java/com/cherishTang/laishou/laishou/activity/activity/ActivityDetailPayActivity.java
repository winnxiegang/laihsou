package com.cherishTang.laishou.laishou.activity.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.OkhttpsHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.laishou.activity.bean.AuthResult;
import com.cherishTang.laishou.laishou.activity.bean.HotActivityBean;
import com.cherishTang.laishou.laishou.activity.bean.PayRequestBean;
import com.cherishTang.laishou.laishou.activity.bean.PayResult;
import com.cherishTang.laishou.laishou.activity.util.IsInstallWeChatOrAliPay;
import com.cherishTang.laishou.laishou.activity.wight.PayMethodChoiceDialog;
import com.cherishTang.laishou.laishou.user.activity.ActivityDetailActivity;
import com.cherishTang.laishou.laishou.user.activity.PayResultActivity;
import com.cherishTang.laishou.laishou.user.activity.SwitchFriendActivity;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/3/8.
 *  todo describe：活动详情--->支付
 */
public class ActivityDetailPayActivity extends BaseActivity {

    @BindView(R.id.tv_pay_method)
    TextView tvPayMethod;
    @BindView(R.id.image_show)
    ImageView imageShow;
    @BindView(R.id.tv_activity_pay_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_stDate)
    TextView tvStDate;
    @BindView(R.id.tv_enDate)
    TextView tvEnDate;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.pay_submit)
    Button paySubmit;
    private Bundle bundle;
    private HotActivityBean activityListBean;
    private int payMethodChoice;
    private boolean isActivityOrder = false;
    private String orderId = null;
    private CustomProgressDialog customProgressDialog;

    private final static int PAY_REQUEST_CODE = 60001;//展示支付结果

    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.activity_detail_pay_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        activityListBean = (HotActivityBean) bundle.getSerializable("itemData");
        isActivityOrder = bundle.getBoolean("isActivityOrder", false);
        orderId = bundle.getString("orderId");
        if (activityListBean != null) {
            Glide.with(this)
                    .load(activityListBean.getImg())
                    .asBitmap()
                    .placeholder(R.mipmap.icon_zwf_default)
                    .error(R.mipmap.icon_zwf_default)
                    .dontAnimate()
                    .into(imageShow);
            tvTitle.setText(activityListBean.getActivityTitle());
            tvAddress.setText("地址：" + activityListBean.getClubAddress());
            tvStDate.setText("开始时间：" + activityListBean.getStartTime());
            tvEnDate.setText("结束时间：" + activityListBean.getEndTime());

            StringBuffer sb = new StringBuffer();
            sb.append("合计：");
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

        }
    }

    /**
     * 出现弹框
     */
    private void showPayMethodChoice() {
        new PayMethodChoiceDialog(this)
                .setDefaultPayMethod(PayMethodChoiceDialog.PAY_METHOD_WECHAT)
                .setOnSureClickListener((dialog, payMethod) -> {
                    payMethodChoice = payMethod;
                    if (payMethodChoice == PayMethodChoiceDialog.PAY_METHOD_WECHAT) {
                        tvPayMethod.setText("微信支付");
                    } else if (payMethodChoice == PayMethodChoiceDialog.PAY_METHOD_ALIPAY) {
                        tvPayMethod.setText("支付宝支付");
                    }
                })
                .builder()
                .show();
    }

    @OnClick({R.id.pay_submit, R.id.tv_pay_method})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay_method:
                showPayMethodChoice();
                break;
            case R.id.pay_submit:
                if (!fastClick(1000)) return;
                if (payMethodChoice != PayMethodChoiceDialog.PAY_METHOD_WECHAT && payMethodChoice != PayMethodChoiceDialog.PAY_METHOD_ALIPAY) {
                    showPayMethodChoice();
                    return;
                }
                if (activityListBean == null) {
                    ToastUtils.showShort(this, "数据加载异常，请稍后再试！");
                    return;
                }
                if (payMethodChoice == PayMethodChoiceDialog.PAY_METHOD_ALIPAY) {
                    if (!IsInstallWeChatOrAliPay.checkAliPayInstalled(this)) {
                        ToastUtils.showShort(this, "请先安装支付宝app");
                        return;
                    }
                    if (isActivityOrder) {
                        if (StringUtil.isEmpty(orderId)) {
                            ToastUtils.showShort(this, "未获取到订单信息");
                            return;
                        }
                        ApiHttpClient.postActivityOrderPay(new Gson().toJson(new IdBean(orderId)), this, stringCallback);
                    } else {
                        ApiHttpClient.postAlipayOrder(new Gson().toJson(new PayRequestBean(activityListBean.getId(), payMethodChoice)), this, stringCallback);
                    }
                } else {
                    ToastUtils.showShort(this, "暂时不支持此支付方式");
                }

//                final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
//
//                boolean result = msgApi.registerApp("wxd930ea5d5a258f4f");
//                LogUtil.show("result:"+result);
//                PayReq request = new PayReq();
//                request.appId = "wxd930ea5d5a258f4f";
//                request.partnerId = "1900000109";
//                request.prepayId= "1101000000140415649af9fc314aa427";
//                request.packageValue = "Sign=WXPay";
//                request.nonceStr= "1101000000140429eb40476f8896f4c9";
//                request.timeStamp= "1398746574";
//                request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
//                boolean sendReqResult = msgApi.sendReq(request);
//                LogUtil.show("sendReqResult:"+sendReqResult);

//                PayResultActivity.show(this,new Bundle());
                break;
        }
    }

    StringCallback stringCallback = new StringCallback() {
        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
            if (customProgressDialog == null || !customProgressDialog.isShowing())
                customProgressDialog = new CustomProgressDialog(ActivityDetailPayActivity.this, R.style.loading_dialog);
            customProgressDialog.setMessage("正在获取支付信息，请稍后...");
            customProgressDialog.show();
        }

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
            if (customProgressDialog != null && customProgressDialog.isShowing())
                customProgressDialog.dismiss();
        }
        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtils.showShort(ActivityDetailPayActivity.this, R.string.requestError);
        }

        @Override
        public void onResponse(String response, int id) {
            try {
                ResultBean<String> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(response,
                        new TypeToken<ResultBean<String>>() {
                        }.getType());
                if (resultBean != null && !StringUtil.isEmpty(resultBean.getData())) {
                    Runnable payRunnable = () -> {
                        PayTask payTask = new PayTask(ActivityDetailPayActivity.this);
                        Map<String, String> result = payTask.payV2(resultBean.getData(), true);
                        LogUtil.show("result:" + new Gson().toJson(result));
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    };
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } else {
                    ToastUtils.showShort(ActivityDetailPayActivity.this, resultBean.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.show("e:" + e);
                ToastUtils.showShort(ActivityDetailPayActivity.this, "支付调用失败");
            }

        }
    };

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        PayResultActivity.show(ActivityDetailPayActivity.this,new Bundle());
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.showShort(ActivityDetailPayActivity.this, "支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PAY_REQUEST_CODE:
//                if (resultCode == RESULT_OK)
//                    finish();
                break;
        }
    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ActivityDetailPayActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
