package com.cherishTang.laishou.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.LockViewActivity;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.laishou.user.activity.LoginActivity;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.api.AppManager;
import com.cherishTang.laishou.api.OkhttpsHelper;
import com.cherishTang.laishou.bean.Code.ResponseCode;
import com.cherishTang.laishou.custom.dialog.LoginDialog;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.permission.PermissionsChecker;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2017/7/31.
 * BaseActivity
 */

public abstract class BaseActivity extends AppCompatActivity implements
        LoginDialog.OnLoginClickListener{
    public boolean wasBackground = false;    //声明一个布尔变量,记录当前的活动背景

    // 所需的全部权限
    static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private static final int REQUEST_CODE = 0; // 请求码
    private static final int LOCKVIEW = 3; // 请求码
    public TextView tvTitle;
    public Toolbar mToolbar;
    FrameLayout container;
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private Integer netFlag = 1;

    private LayoutInflater inflater;
    public static boolean hasToolBar = true;
    private LoginDialog loginDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        AppManager.getAppManager().addActivity(this);//把activity添加到栈中
        inflater = LayoutInflater.from(this);
        hasToolBar = hasToolBar();
        setContentView(initLayout());//设置布局
        ButterKnife.bind(this);
        initView();//初始化控件
        initData(); //初始化数据
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        if (hasToolBar) {
            super.setContentView(R.layout.baseactivity_toolbar);
            tvTitle =  findViewById(R.id.tv_title);
            mToolbar =  findViewById(R.id.main_bar);
            container =  findViewById(R.id.container);
            tvTitle.setText(setTitleBar());
            mToolbar.setNavigationIcon(R.mipmap.icon_fh);
            mToolbar.setNavigationOnClickListener(v -> {
                setResult(ConstantsHelper.REQUEST_BACK);
                AppManager.getAppManager().finishActivity(this);
            });
            inflater.inflate(layoutResID, container, true);
        } else {
            super.setContentView(layoutResID);
        }

    }

    protected boolean hasToolBar() {
        return true;
    }

    public abstract String setTitleBar();

    /**
     * 设置布局
     *
     * @return activity布局
     */
    public abstract int initLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();


    public void netWorkChange(int state) {
    }
    /**
     * 设置透明状态栏
     * <p>
     * 可在Activity的onCreate()中调用
     * <p>
     * 注意:需在顶部控件布局中加入以下属性让内容出现在状态栏之下:
     * android:clipToPadding="true"   // true 会贴近上层布局 ; false 与上层布局有一定间隙
     * android:fitsSystemWindows="true"   //true 会保留actionBar,title,虚拟键的空间 ; false 不保留
     */
    public void setTransparentStatusBar(@ColorRes int colorRes) {
        //5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, colorRes));
        }
    }
    //权限请求
    public void requestPermission(Activity context, String[] PERMISSIONS) {
        if (mPermissionsChecker == null) mPermissionsChecker = new PermissionsChecker(this);
        this.PERMISSIONS = PERMISSIONS;
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity(context);
        }
    }

    //权限请求
    public void requestPermission(int reqCode, Activity context, String[] PERMISSIONS) {
        if (mPermissionsChecker == null) mPermissionsChecker = new PermissionsChecker(this);
        this.PERMISSIONS = PERMISSIONS;
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity(reqCode, context);
        }
    }


    public long lastClick = 0;
    /**
     * [防止快速点击]
     *
     * @return
     */
    public boolean fastClick(long intervalTime) {
        if (System.currentTimeMillis() - lastClick <= intervalTime) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    //权限检测
    private void startPermissionsActivity(int reqCode, Activity context) {
        PermissionsActivity.startActivityForResult(context, reqCode, PERMISSIONS);
    }
    public void errorMsgShow(String code, String msg, int requestCode) {
        switch (code) {
            case ResponseCode.LOGIN_PAST:
                showLoginDialog(this,this,"登录已过期，请重新登录",requestCode);
                break;
            case ResponseCode.UNLOGIN:
                showLoginDialog(this,this,"您还没有登录，请前往登录",requestCode);
                break;
            case ResponseCode.TOKEN_ERROR:
                showLoginDialog(this,this,"登录信息异常，请重新登录",requestCode);
                break;
            default:
                ToastUtils.showShort(this, StringUtil.isEmpty(msg) ? "加载失败" : msg);
                break;
        }
    }


    /**
     * 弹出登录dialog
     * @param context
     * @param message
     * @param requestCode
     */
    public void showLoginDialog(Context context,LoginDialog.OnLoginClickListener listener,String message,int requestCode){
        LoginDialog.getInstance(context)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(listener,requestCode)
                .show();
    }
    @Override
    public void onLoginClick(View v, int code) {
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        startActivityForResult(intent, code);
    }
    //检查是否有权限
    public boolean checkPermission(String[] PERMISSIONS) {
        if (mPermissionsChecker == null) mPermissionsChecker = new PermissionsChecker(this);
        this.PERMISSIONS = PERMISSIONS;
        return mPermissionsChecker.lacksPermissions(PERMISSIONS);
    }

    private void startLockView() {
        Intent intent = new Intent(this, LockViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, LOCKVIEW);
    }

    //权限检测
    private void startPermissionsActivity(Activity context) {
        PermissionsActivity.startActivityForResult(context, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(ConstantsHelper.REQUEST_BACK);
        OkhttpsHelper.cancelRequestTag(this);
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
        if (AppManager.getAppManager().isAppIsInBackground(this))
            wasBackground = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
       // MobclickAgent.onResume(this);
        if (wasBackground) {
//            if (AppSettingHelper.getGesturesIsOpen()&&AppSettingHelper.getGestures()!=null&&!AppManager.isActivityTop(LockView.class,this)){
//                startLockView();
//            }
        }
        wasBackground = false;
    }
}
