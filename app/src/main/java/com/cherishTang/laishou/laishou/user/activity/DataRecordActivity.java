package com.cherishTang.laishou.laishou.user.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.customlayout.CustomProgressBar;
import com.cherishTang.laishou.custom.customlayout.RoundImageView;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.custom.dialog.PickStringDialog;
import com.cherishTang.laishou.enumbean.PKResultEnum;
import com.cherishTang.laishou.laishou.main.bean.GetPkBean;
import com.cherishTang.laishou.laishou.user.bean.PKResultBean;
import com.cherishTang.laishou.laishou.user.bean.PkRequestBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.DateUtil;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.step.bean.StepData;
import com.cherishTang.laishou.util.step.service.StepService;
import com.cherishTang.laishou.util.step.utils.DbUtils;
import com.cherishTang.laishou.util.step.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/3/6.
 * describe
 */
public class DataRecordActivity extends BaseActivity {
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_bmi)
    TextView tvBmi;
    @BindView(R.id.tv_fat)
    TextView tvFat;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.tv_switch)
    TextView tvSwitch;
    @BindView(R.id.image_head_me)
    RoundImageView imageHeadMe;
    @BindView(R.id.image_me_win)
    ImageView imageMeWin;
    @BindView(R.id.image_head_firend)
    RoundImageView imageHeadFirend;
    @BindView(R.id.image_friend_win)
    ImageView imageFriendWin;
    @BindView(R.id.press_weight)
    CustomProgressBar pressWeight;
    @BindView(R.id.press_bmi)
    CustomProgressBar pressBmi;
    @BindView(R.id.press_fat)
    CustomProgressBar pressFat;
    @BindView(R.id.press_age)
    CustomProgressBar pressAge;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_step)
    TextView tvStep;
    @BindView(R.id.tv_pk_date)
    TextView tvPkDate;
    @BindView(R.id.tv_weight_number)
    TextView tvWeightNumber;
    @BindView(R.id.tv_xw)
    TextView tvXw;
    @BindView(R.id.tv_yw)
    TextView tvYw;
    @BindView(R.id.tv_tw)
    TextView tvTw;
    @BindView(R.id.tv_xw_bz)
    TextView tvXwBz;
    @BindView(R.id.tv_yw_bz)
    TextView tvYwBz;
    @BindView(R.id.tv_tw_bz)
    TextView tvTwBz;
    private SharedPreferencesUtils sp;
    private String startStr = "今天您走了<font color=\"#43cf7c\">";
    private String middleStr = "</font>步，比昨天多走了<font color=\"#43cf7c\">";
    private String middleStrLess = "</font>步，比昨天少走了<font color=\"#43cf7c\">";

    private String endStr = "</font>步，继续保持哦！";
    private String endStrLess = "</font>步，还需努力哦！";

    /**
     * 当前所走的步数
     */
    private int CURRENT_STEP;
    /**
     * 昨天所走的步数
     */
    private int YESTODAY_STEP;
    /**
     * 当前的日期
     */
    private static String CURRENT_DATE = "";
    /**
     * 昨天的日期
     */
    private static String CURRENT_DATE_YESTODAY = "";
    private CustomProgressDialog customProgressDialog;
    private String pkId;

    @Override
    public String setTitleBar() {
        return "数据记录";
    }

    @Override
    public int initLayout() {
        return R.layout.data_record_activity;
    }

    @Override
    public void initView() {
        pressAge.init(0, 0, "身体年龄", "", "");
        pressBmi.init(0, 0, "BMI", "", "");
        pressFat.init(0, 0, "体脂率", "", "");
        pressWeight.init(0, 0, "体重", "", "");
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            String schemeParams = uri.getScheme();
            pkId = uri.getQueryParameter("pkuser");
            LogUtil.show("参数：" + uri.getQuery());
            if (!StringUtil.isEmpty(schemeParams) && (StringUtil.isEmpty(pkId))) {
                ToastUtils.showShort(this, "分享已失效");
            }
            getStartPK();
        } else {
            getMyPK();
        }
    }

    private void updateView(PKResultBean pkResultBean) {
        try{
            if (pkResultBean == null) {
                return;
            }
            if (StringUtil.isEmpty(pkResultBean.getNormChestWai())) {
                tvXwBz.setText("未知");
            } else {
                tvXwBz.setText(NumberUtils.decimalFormat(pkResultBean.getNormChestWai()) + "cm");
            }
            if (StringUtil.isEmpty(pkResultBean.getNormWaistWai())) {
                tvYwBz.setText("未知");
            } else {
                tvYwBz.setText(NumberUtils.decimalFormat(pkResultBean.getNormWaistWai()) + "cm");
            }
            if (StringUtil.isEmpty(pkResultBean.getNormHipWai())) {
                tvTwBz.setText("未知");
            } else {
                tvTwBz.setText(NumberUtils.decimalFormat(pkResultBean.getNormHipWai()) + "cm");
            }
            tvPkDate.setText("PK日期：" + pkResultBean.getStrStartTime() + "至" + pkResultBean.getStrEndTime());

            if (pkResultBean.getMyInfo() != null) {
                if (StringUtil.isEmpty(pkResultBean.getMyInfo().getChestWai())) {
                    tvXw.setText("未知");
                } else {
                    tvXw.setText(NumberUtils.decimalFormat(pkResultBean.getMyInfo().getChestWai()) + "cm");
                }
                if (StringUtil.isEmpty(pkResultBean.getMyInfo().getWaistWai())) {
                    tvYw.setText("未知");
                } else {
                    tvYw.setText(NumberUtils.decimalFormat(pkResultBean.getMyInfo().getWaistWai()) + "cm");
                }
                if (StringUtil.isEmpty(pkResultBean.getMyInfo().getHipWai())) {
                    tvTw.setText("未知");
                } else {
                    tvTw.setText(NumberUtils.decimalFormat(pkResultBean.getMyInfo().getHipWai()) + "cm");
                }
                tvWeightNumber.setText(NumberUtils.decimalFormat(pkResultBean.getMyInfo().getWeight()) + "KG");
                tvWeight.setText("体重\n"+NumberUtils.decimalFormat(pkResultBean.getMyInfo().getWeight()) + "KG");
                tvBmi.setText("BMI\n"+NumberUtils.decimalFormatInteger(pkResultBean.getMyInfo().getBmi()));
                tvFat.setText("体脂率\n"+NumberUtils.decimalFormatInteger(pkResultBean.getMyInfo().getFatRate())+"%");

            }
            if (pkResultBean.getMyPk() != null && pkResultBean.getOtherPk() != null) {
                Glide.with(this)
                        .load(pkResultBean.getMyPk().getHeadImg())
                        .asBitmap()
                        .placeholder(R.mipmap.icon_head_default)
                        .error(R.mipmap.icon_head_default)
                        .dontAnimate()
                        .into(imageHeadMe);
                Glide.with(this)
                        .load(pkResultBean.getOtherPk().getHeadImg())
                        .asBitmap()
                        .placeholder(R.mipmap.icon_head_default)
                        .error(R.mipmap.icon_head_default)
                        .dontAnimate()
                        .into(imageHeadFirend);
            }

            if(pkResultBean.getMyPk().getResult()!=null&&pkResultBean.getMyPk().getResult()== PKResultEnum.win){
                imageMeWin.setVisibility(View.VISIBLE);
                tvResult.setText("恭喜你，你赢了！");
            }else{
                imageMeWin.setVisibility(View.GONE);
            }
            if(pkResultBean.getOtherPk().getResult()!=null&&pkResultBean.getOtherPk().getResult()== PKResultEnum.win){
                imageFriendWin.setVisibility(View.VISIBLE);
            }else{
                imageFriendWin.setVisibility(View.GONE);
            }
            pressAge.init(pkResultBean.getMyPk().getAge(), pkResultBean.getOtherPk().getAge(), "身体年龄",
                    pkResultBean.getMyPk().getAge() + "岁", pkResultBean.getOtherPk().getAge() + "岁");
            pressBmi.init((int) pkResultBean.getMyPk().getBmi(), (int) pkResultBean.getOtherPk().getBmi(), "BMI",
                    pkResultBean.getMyPk().getBmi() + "", pkResultBean.getOtherPk().getBmi() + "");
            pressFat.init((int) pkResultBean.getMyPk().getFatRate(), (int) pkResultBean.getOtherPk().getFatRate(), "体脂率",
                    pkResultBean.getMyPk().getFatRate() + "%", pkResultBean.getOtherPk().getFatRate() + "%");
            pressWeight.init((int) pkResultBean.getMyPk().getWeight(), (int) pkResultBean.getOtherPk().getWeight(), "体重",
                    pkResultBean.getMyPk().getWeight() + "kg", pkResultBean.getOtherPk().getWeight() + "kg");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private boolean isBind = false;

    @Override
    public void initData() {

        CURRENT_DATE = DateUtil.getToday();
        CURRENT_DATE_YESTODAY = DateUtil.getYesterday();
        sp = new SharedPreferencesUtils(this);
        try {
            //获取当天的数据，用于展示
            List<StepData> list = DbUtils.getQueryByWhere(StepData.class, "today", new String[]{CURRENT_DATE});
            if (list != null && list.size() > 0) {
                LogUtil.show("StepData=" + list.get(0).toString());
                CURRENT_STEP = Integer.parseInt(list.get(0).getStep());
            } else {
                CURRENT_STEP = 0;
            }

            //获取昨天的数据，用于展示
            List<StepData> listYestoday = DbUtils.getQueryByWhere(StepData.class, "today", new String[]{CURRENT_DATE_YESTODAY});
            if (listYestoday != null && listYestoday.size() > 0) {
                LogUtil.show("StepData=" + listYestoday.get(0).toString());
                YESTODAY_STEP = Integer.parseInt(listYestoday.get(0).getStep());
            } else {
                YESTODAY_STEP = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //判断昨天的步数和今天的步数差别，显示不同的问题
        if (YESTODAY_STEP > CURRENT_STEP) {
            tvStep.setText(Html.fromHtml(startStr + CURRENT_STEP + middleStrLess + Math.abs(CURRENT_STEP - YESTODAY_STEP) + endStrLess));
        } else {
            tvStep.setText(Html.fromHtml(startStr + CURRENT_STEP + middleStr + Math.abs(CURRENT_STEP - YESTODAY_STEP) + endStr));
        }
        Intent intent = new Intent(this, StepService.class);
        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            //设置初始化数据
            String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");

            //设置步数监听回调
            stepService.registerCallback(stepCount -> {
                String planWalk_QTY1 = (String) sp.getParam("planWalk_QTY", "7000");
                LogUtil.show("stepCount:" + stepCount);
                if (YESTODAY_STEP > stepCount) {
                    tvStep.setText(Html.fromHtml(startStr + stepCount + middleStrLess + Math.abs(stepCount - YESTODAY_STEP) + endStrLess));
                } else {
                    tvStep.setText(Html.fromHtml(startStr + stepCount + middleStr + Math.abs(stepCount - YESTODAY_STEP) + endStr));
                }
            });
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void getMyPK() {
        ApiHttpClient.getMyPK(this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(DataRecordActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在加载，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(DataRecordActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                try{
                    ResultBean<PKResultBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                            fromJson(response, new TypeToken<ResultBean<PKResultBean>>() {
                            }.getType());
                    if (resultBean.isSuccess()) {
                        ToastUtils.showShort(DataRecordActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "兑换成功" : resultBean.getMessage());
                        updateView(resultBean.getData());
                    } else {
                        ToastUtils.showShort(DataRecordActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "兑换失败" : resultBean.getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void getStartPK() {
        PkRequestBean pkRequestBean = new PkRequestBean();
        pkRequestBean.setPkId(pkId);
        ApiHttpClient.startPK(new Gson().toJson(pkRequestBean), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(DataRecordActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在绑定PK，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(DataRecordActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    getMyPK();
//                    DialogHelper.getConfirmDialog(DataRecordActivity.this, "PK关系绑定成功，是否立即查看PK结果", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            getMyPK();
//                        }
//                    }).show();
                } else {
                    ToastUtils.showShort(DataRecordActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "兑换失败" : resultBean.getMessage());
                }
            }
        });
    }

    @OnClick({R.id.tv_switch})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_switch:
                PickStringDialog pickStringDialog = new PickStringDialog(this, onPickSelectedListener);
                pickStringDialog.create().show();
                break;
        }
    }

    PickStringDialog.OnPickSelectedListener onPickSelectedListener = (mCurrentPick, selectStr) -> {
        getShareLink(selectStr);
    };

    /**
     * 邀请好友
     */
    private void getShareLink(String day) {
        ApiHttpClient.getPKShareLink(new Gson().toJson(new GetPkBean(day)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(DataRecordActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在生成分享链接，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(DataRecordActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<String> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s,
                        new TypeToken<ResultBean<String>>() {
                        }.getType());
                if (resultBean.isSuccess() && !StringUtil.isEmpty(resultBean.getData())) {
                    share(resultBean.getData());
                } else {
                    errorMsgShow(resultBean.getCode() + "", resultBean.getMessage(), ConstantsHelper.REQUEST_ERROR_LOGIN);
                }
            }
        });
    }

    /**
     * 调用友盟分享
     * 分享平台：QQ,QQ空间，微信好友，微信朋友圈
     * 分享方式：web链接
     */
    private void share(String shareLink) {
        //初始化web分享参数
        UMWeb web = new UMWeb(shareLink);//链接
        web.setTitle("邀请来pk" + getResources().getString(R.string.app_name));//标题
        web.setThumb(new UMImage(this, R.mipmap.ic_launcher));  //缩略图
        web.setDescription("点击前往" + getResources().getString(R.string.app_name) +
                "app应用程序PK");//描述

        new ShareAction(this)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ,
                        SHARE_MEDIA.QZONE,
                        SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener).open();

    }

    /**
     * 分享回调监听
     * 开始、分享成功、分享失败、取消分享
     */
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
//            if (shareDialog != null && shareDialog.isShowing())
//                shareDialog.dismiss();
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShort(DataRecordActivity.this, "分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            LogUtil.show("t:" + t);
            ToastUtils.showShort(DataRecordActivity.this, "分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort(DataRecordActivity.this, "你取消了分享");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            case SwitchFriendActivity.PK_REQUEST_CODE:
//                if (resultCode == RESULT_OK)
//                    requestData();
//                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            this.unbindService(conn);
        }
    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, DataRecordActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
