package com.cherishTang.laishou.laishou.user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.custom.dialog.InputWeightDialog;
import com.cherishTang.laishou.laishou.club.bean.UpWeightRecodeImageBean;
import com.cherishTang.laishou.laishou.user.bean.ElegantUploadImageBean;
import com.cherishTang.laishou.laishou.user.bean.RecentlyWeightBean;
import com.cherishTang.laishou.laishou.user.bean.RecordWeightBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.BitmapUtil;
import com.cherishTang.laishou.util.apiUtil.FileUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/1/23.
 * 我的成绩
 */

public class MyGradeActivity extends BaseActivity {
    @BindView(R.id.tv_cycle_weight)
    TextView tvCycleWeight;
    @BindView(R.id.tv_record_weight)
    TextView tvRecord;
    @BindView(R.id.barChart)
    LineChart mChart;
    private Bundle bundle;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private CustomProgressDialog customProgressDialog;
    private List<Entry> entries;

    @Override
    public String setTitleBar() {
        return "我的成绩";
    }

    @Override
    public int initLayout() {
        return R.layout.my_grade_fragment_container;
    }

    @Override
    public void initView() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        mToolbar.inflateMenu(R.menu.toolbar_menu_weight_record);
        mToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.toolbar_weight_record:
                    MyWeightRecordActivity.show(this, new Bundle());
                    break;
                case R.id.toolbar_share:
                    Bitmap bitmap = ScreenUtils.screenShot(MyGradeActivity.this, true);
                    new Handler().postDelayed(() -> {
                        if (bitmap != null) {
                            uploadWeightecordImage(new File(BitmapUtil.saveBitmap(MyGradeActivity.this, bitmap)));
                        } else {
                            ToastUtils.showLong(MyGradeActivity.this, "图片分享失败");
                        }
                    }, 200);
                    break;
            }
            return false;
        });
    }

    @Override
    public void initData() {
        entries = new ArrayList<Entry>();
        weightRecently();
    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MyGradeActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 上传图片
     *
     * @param file
     */
    private void uploadWeightecordImage(File file) {
        ApiHttpClient.uploadWeightecordImage(file, FileUtils.getFileName(file.getAbsolutePath()), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.setMessage("正在上传，请稍后...");
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
                ToastUtils.showShort(MyGradeActivity.this, R.string.requestError);
            }


            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
            }

            @Override
            public void onResponse(String response, int id) {
                UpWeightRecodeImageBean resultBean = new Gson().fromJson(response, UpWeightRecodeImageBean.class);
                if (resultBean.getCode()==200) {
                    share(resultBean.getData().getFileName());
                } else {
                    ToastUtils.showLong(MyGradeActivity.this, resultBean.getMessage());
                }
            }
        });
    }

    /**
     * //     * 调用友盟分享
     * //     * 分享平台：QQ,QQ空间，微信好友，微信朋友圈
     * //     * 分享方式：web链接
     * //
     */
    private void share(String sharelink) {
        UMWeb web = new UMWeb(sharelink);//链接
        web.setTitle("我的体重");//标题
        web.setThumb(new UMImage(this, R.mipmap.ic_launcher));  //缩略图
        web.setDescription("点击跳转" + getResources().getString(R.string.app_name) +
                "app应用程序查看我的体重记录");//描述

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
            ToastUtils.showShort(MyGradeActivity.this, "分享成功");
            finish();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort(MyGradeActivity.this, "分享失败");
            finish();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort(MyGradeActivity.this, "你取消了分享");
        }
    };

    public void updateChart(RecentlyWeightBean recentlyWeightBean) {
        if (recentlyWeightBean == null)
            return;
        if (recentlyWeightBean.getWeightDiff() >= 0) {
            setResultWeight("比昨天胖\n" + Math.abs(recentlyWeightBean.getWeightDiff()) + "公斤");
        } else {
            setResultWeight("比昨天瘦\n" + Math.abs(recentlyWeightBean.getWeightDiff()) + "公斤");
        }
        if (recentlyWeightBean.getList() == null)
            return;
        entries = new ArrayList<>();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < recentlyWeightBean.getList().size(); i++) {
            entries.add(new Entry(i, recentlyWeightBean.getList().get(i).getWeight()));
            list.add(recentlyWeightBean.getList().get(i).getStrCreateTime());
        }
        LineDataSet dataSet = new LineDataSet(entries, "体重记录表（单位：kg）"); // add entries to dataset
        LineData lineData = new LineData(dataSet);
        mChart.setData(lineData);
        mChart.invalidate();
        dataSet.setColor(Color.parseColor("#7d7d7d"));//线条颜色
        dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
        dataSet.setLineWidth(1f);//线条宽度
        //设置样式
        YAxis rightAxis = mChart.getAxisRight();

        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
//        YAxis leftAxis = mChart.getAxisLeft();
//        //设置图表左边的y轴禁用
//        leftAxis.setEnabled(false);
        //设置x轴
        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘
        //透明化图例
        Legend legend = mChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(ContextCompat.getColor(this, R.color.dark_black));
        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        mChart.setDescription(description);
    }

    public void setResultWeight(String weight) {
        tvCycleWeight.setText(weight);
    }

    @OnClick({R.id.tv_record_weight})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_record_weight:
                new InputWeightDialog(this)
                        .setOnSureClickListener((dialog, inputText) -> {
                            DialogHelper.getConfirmDialog(MyGradeActivity.this, "确定记录当前体重吗？", (dialogInterface, i) -> {
                                if (customProgressDialog == null || !customProgressDialog.isShowing())
                                    customProgressDialog = new CustomProgressDialog(MyGradeActivity.this, R.style.loading_dialog);
                                customProgressDialog.setMessage("正在记录体重，请稍后...");
                                customProgressDialog.show();
                                recordWeight(new Gson().toJson(new RecordWeightBean(inputText.getText().toString())));
                            }).show();
                        })
                        .builder()
                        .show();
                break;
        }
    }

    private void recordWeight(String postJson) {
        ApiHttpClient.recordMyGradeList(postJson, this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(MyGradeActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                            fromJson(response, new TypeToken<ResultBean<Object>>() {
                            }.getType());
                    if (resultBean.isSuccess()) {
                        weightRecently();
                    } else {
                        ToastUtils.showShort(MyGradeActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "删除失败" : resultBean.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * 加载图表数据
     */
    private void weightRecently() {
        ApiHttpClient.weightRecently(this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(MyGradeActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在加载，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(MyGradeActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    ResultBean<RecentlyWeightBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                            fromJson(response, new TypeToken<ResultBean<RecentlyWeightBean>>() {
                            }.getType());
                    if (resultBean.isSuccess() && resultBean.getData() != null) {
                        updateChart(resultBean.getData());
                    } else {
                        ToastUtils.showShort(MyGradeActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "删除失败" : resultBean.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
