package com.cherishTang.laishou.laishou.main.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.cherishTang.laishou.util.log.LogUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.LockViewActivity;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.api.AppHelper;
import com.cherishTang.laishou.api.AppSettingHelper;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.OkhttpsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseFragment;
import com.cherishTang.laishou.bean.Code.VersionMessage;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.GetVersion;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.update.UpdateManger;
import com.cherishTang.laishou.util.cache.GlideCacheUtil;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.util.permission.PermissionsChecker;
import com.cherishTang.laishou.util.update.server.DownloadApkReceiver;
import com.cherishTang.laishou.util.update.server.DownloadApkService;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 方舟 on 2017/5/24.
 * <p>
 * 设置页面
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.button_backward)
    TextView buttonBackward;
    @BindView(R.id.button_forward)
    TextView buttonForward;
    @BindView(R.id.iv_logo_setting)
    ImageView ivLogoSetting;
    @BindView(R.id.setting_exit)
    Button settingExit;
    @BindView(R.id.cleanUp)
    TextView cleanUp;
    @BindView(R.id.titleLayout)
    RelativeLayout titleLayout;
    @BindView(R.id.checkUpdate)
    LinearLayout checkUpdate;
    @BindView(R.id.tipsAlert)
    TextView tipsAlert;
    @BindView(R.id.aboutUs)
    TextView aboutUs;
    @BindView(R.id.tglSound)
    ToggleButton tglSound;
    @BindView(R.id.gesturesPassword)
    ToggleButton gesturesPassword;
    @BindView(R.id.versionName)
    TextView versionName;
    private View view;

    private final int SCANER_CODE = 1;

    private boolean tipFlag = false;

    // 所需的全部权限
    static String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA
    };
    private static final int REQUEST_CODE = 0; // 请求码//
    private static final int LOCKVIEW = 3; // 请求码//LOCKVIEW
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private static final int LOGIN_FLAG = 1000;
    private CustomProgressDialog customProgressDialog;
    private DownloadApkReceiver  downloadApkReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.setting;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        this.view = view;
        unbinder = ButterKnife.bind(this, view);
        initView();
    }

    /**
     * 初始化布局
     */
    @SuppressLint("SetTextI18n")
    private void initView() {
        textTitle.setText(R.string.nv_setting);
        titleLayout.setBackgroundResource(R.drawable.title_layout);
        textTitle.setTextColor(getResources().getColor(R.color.black));
        versionName.setText("v"+GetVersion.getVersion(getActivity()));
    }

    @Override
    protected void initData(Bundle bundle) {
        try {
            cleanUp.setText(getCacheSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!UserAccountHelper.isLogin())
            settingExit.setVisibility(View.GONE);

        tglSound.setChecked(AppSettingHelper.getNotification());

        gesturesPassword.setChecked(AppSettingHelper.getGesturesIsOpen());
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    private void showLoginDialog(String message) {
       DialogHelper.getConfirmDialog(getActivity(), message,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }

    //点击清理
    private void onClickCleanUp() {
        DialogHelper.getConfirmDialog(getActivity(), "是否确定清理缓存?",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            clearCache();
                            cleanUp.setText(getCacheSize());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }

    /**
     * 计算缓存大小
     */
    private String getCacheSize() {
        String size = null;
        try {
            long cacheSize = GlideCacheUtil.getInstance().getFolderSize(
                    new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + File.separator + ConstantsHelper.ROOTFILEPATH));
            cacheSize += GlideCacheUtil.getInstance().getFolderSize(
                    new File(getActivity().getCacheDir() + "/" +
                            InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR));
            cacheSize += GlideCacheUtil.getInstance().getFolderSize(getActivity().getCacheDir());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheSize += GlideCacheUtil.getInstance().getFolderSize(getActivity().getExternalCacheDir());
            }

            size = GlideCacheUtil.getFormatSize(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 清理换攒
     */
    private void clearCache() {
        GlideCacheUtil.getInstance().clearImageAllCache(getActivity());
        GlideCacheUtil.getInstance().deleteFolderFile(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + ConstantsHelper.ROOTFILEPATH, false);
        GlideCacheUtil.getInstance().deleteFolderFile(getActivity().getCacheDir().getAbsolutePath(), false);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            GlideCacheUtil.getInstance().deleteFolderFile(getActivity().getExternalCacheDir().getAbsolutePath(), false);
        }
    }

    /**
     * @param view 当前视图
     */
    @OnClick({R.id.setting_exit, R.id.cleanUp, R.id.checkUpdate, R.id.feedBack,
            R.id.tglSound, R.id.aboutUs, R.id.gesturesPassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_exit:

                break;
            case R.id.feedBack:
                AppHelper.showFeedbackActivity(getActivity());
                break;
            case R.id.gesturesPassword:
                ToggleButton toggleButton = (ToggleButton) view;
                toggleButton.setChecked(AppSettingHelper.getGesturesIsOpen());
                if (!toggleButton.isChecked()) showSureDialog("是否开启手势密码？");
                else showSureDialog("是否关闭手势密码？");
                break;
            case R.id.checkUpdate:
                customProgressDialog = new CustomProgressDialog(getActivity(), R.style.loading_dialog);
                customProgressDialog.setMessage("正在检测新版本...");
                customProgressDialog.show();
                checkSoftInfo();
                break;
            case R.id.cleanUp:
                onClickCleanUp();
                break;
            case R.id.tglSound:
                customProgressDialog = new CustomProgressDialog(getActivity(), R.style.loading_dialog);
                customProgressDialog.setMessage("正在更改设置,请稍后...");
                customProgressDialog.show();
                long countDown = new Random().nextInt(4000);//这个操作主要给用户一定的延时效果，因为本地的效果，太快会尴尬
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (AppSettingHelper.getNotification()) {
                            AppSettingHelper.setNotification(false);
                            tglSound.setChecked(false);
                            JPushInterface.stopPush(getActivity());
                            if (customProgressDialog != null && customProgressDialog.isShowing())
                                customProgressDialog.dismiss();
                        } else {
                            AppSettingHelper.setNotification(true);
                            tglSound.setChecked(true);
                            JPushInterface.resumePush(getActivity());
                            if (customProgressDialog != null && customProgressDialog.isShowing())
                                customProgressDialog.dismiss();
                        }
                    }
                }, countDown);
                break;
            case R.id.aboutUs:
                ToastUtils.showShort(getActivity(), "请登录官方网站进行查看");
                break;
        }
    }

    /**
     * 检查版本更新
     */
    private void checkSoftInfo() {
        OkhttpsHelper.get("SysGlobal/DetectionAppVersion", new HashMap<String, String>(), this, new StringCallback() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(getActivity(), "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<VersionMessage> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(s, new TypeToken<ResultBean<VersionMessage>>() {
                        }.getType());
                if (resultBean.isSuccess() && resultBean.getData() != null &&
                        resultBean.getData().getVersionCode() > GetVersion.getVersionCode(getActivity())) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(DownloadApkService.ACTION_START);
                    intentFilter.addAction(DownloadApkService.ACTION_UPDATE);
                    intentFilter.addAction(DownloadApkService.ACTION_FINISHED);
                    intentFilter.addAction(DownloadApkService.ACTION_CANCEL);
                    intentFilter.addAction(DownloadApkService.ACTION_ERROR);
                    intentFilter.addAction(DownloadApkService.ACTION_REDIRECT_ERROR);

                    downloadApkReceiver = new DownloadApkReceiver();
                    getActivity().registerReceiver(downloadApkReceiver, intentFilter);
                    UpdateManger.getInstance().checkUpdateInfo(getActivity(),
                            resultBean.getData().getDownloadUrl(),
                            resultBean.getData().getLogContent(),
                            resultBean.getData().getVersionNumber());
                }else {
                    ToastUtils.showMessageDialog(getActivity(), "已是最新版本！");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (downloadApkReceiver != null) {
            getActivity().unregisterReceiver(downloadApkReceiver);
        }
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 二维码扫描，处理扫描结果（在界面上显示）
        switch (requestCode) {
            case SCANER_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString("result");
                    LogUtil.show("扫描结果为：" + scanResult);
                    AppHelper.showMessageActivity(getActivity(), bundle);
                }
                break;
            case LOGIN_FLAG:
                if (resultCode == 0 && data != null) {
                    if (data.getExtras().getBoolean("result"))
                        settingExit.setVisibility(View.VISIBLE);
                }
                break;
            case REQUEST_CODE:
                if (resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
                    ToastUtils.showMessageDialog(getActivity(), "权限拒绝可能会导致部分功能无法正常使用");
                }
                break;
            case LOCKVIEW:
                if (resultCode == RESULT_OK && data.getExtras() != null && data.getExtras().getBoolean("result", false)) {
                    if (AppSettingHelper.getGesturesIsOpen()) {
                        AppSettingHelper.setGesturesIsOpen(false);
                        AppSettingHelper.setGestures(null);
                    } else {
                        AppSettingHelper.setGesturesIsOpen(true);
                    }
                    gesturesPassword.setChecked(AppSettingHelper.getGesturesIsOpen());
                }
                break;
        }
    }

    private void showSureDialog(String message) {
        DialogHelper.getConfirmDialog(getActivity(), message,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), LockViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isSet", true);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, LOCKVIEW);
                    }
                }).show();
    }

}
