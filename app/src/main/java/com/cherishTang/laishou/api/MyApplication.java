package com.cherishTang.laishou.api;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.SDKInitializer;
import com.cherishTang.laishou.BuildConfig;
import com.cherishTang.laishou.activity.LockViewActivity;
import com.cherishTang.laishou.broadCastReceiver.NetworkStateReceiver;
import com.cherishTang.laishou.util.apiUtil.Density;
import com.cherishTang.laishou.util.apiUtil.FileUtils;
import com.cherishTang.laishou.util.apiUtil.NotificationUtils;
import com.cherishTang.laishou.util.crash.CrashHandler;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.LogcatHelper;
import com.cherishTang.laishou.welcome.LaunchActivity;
import com.cherishTang.laishou.welcome.WelcomeActivity;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zzhoujay.richtext.RichText;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 方舟 on 2017/6/20.
 * Application
 */

public class MyApplication extends Application {
    private boolean isBackGround;
    private static MyApplication myApplication;
    public final static String ACTION_INTENT_RECEIVER = "android.net.conn.CONNECTIVITY_CHANGE";
    public final static String ACTION_INTENT_RECEIVER_8 = "android.net.wifi.SCAN_RESULTS";

    NetworkStateReceiver networkChangeReceiver;

    IntentFilter intentFilter;

    {
        PlatformConfig.setWeixin("wxffe2dfea7444359e", "b8ca7dac0e8e5d26b3b5f4b1879b1060");
        PlatformConfig.setQQZone("101562226", "5406b4be30059480a989a710f45bf41e");
    }

    @Override
    public void onCreate() {
        Density.setDensity(this, 360);
        super.onCreate();
        init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (networkChangeReceiver != null)
            unregisterReceiver(networkChangeReceiver);
    }

    private void initNetWorkReceiver() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_INTENT_RECEIVER);
        intentFilter.addAction(ACTION_INTENT_RECEIVER_8);

        networkChangeReceiver = new NetworkStateReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    private void startLockView() {
        Intent intent = new Intent(this, LockViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public static MyApplication getInstance() {
        return myApplication;
    }


    private void initFile() {
        if (FileUtils.hasSdcard()) {
            FileUtils.makeDirs(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + ConstantsHelper.ROOTFILEPATH + File.separator + "crash" + File.separator);
            FileUtils.makeDirs(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + ConstantsHelper.ROOTFILEPATH + File.separator + "log" + File.separator);
        }
    }

    /**
     * 全局捕获crash并保存日志到本地
     */
    private void initLogCrash() {
        CrashHandler.getInstance().init(getApplicationContext());
    }

    private void init() {
        myApplication = this;
        CrashReport.initCrashReport(getApplicationContext(), "2c57dd3ee7", true);
        RichText.initCacheDir(this);
        RichText.debugMode = true;
        JPushInterface.init(this);
        UserAccountHelper.init(this);  //UserAccountHelper和ApiHttpClient初始化
        ApiHttpClient.init(this);//ApiHttpClient初始化
        AppSettingHelper.init(this);//app设置初始化
        SDKInitializer.initialize(getApplicationContext());//百度地图初始化
        OkhttpsHelper.init(this);//初始化网络框架
        initNetWorkReceiver();//初始化网络广播器
        initLogCrash(); //初始化异常记录类
        initBack();
        initFile();
        LogcatHelper.getInstance(this).start();//缓存日志至本地
        LogUtil.init();
        NotificationUtils.init(this);
        UMConfigure.setLogEnabled(BuildConfig.LOG_DEBUG);
        UMConfigure.init(this,"5b90889f8f4a9d38fb0002d9"//appKey5b90889f8f4a9d38fb0002d9
                ,getPackageName()+"umeng"//channel
                ,UMConfigure.DEVICE_TYPE_PHONE,//手机类型
                "");//如果不集成推送的话，设为空
        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         */
//        UMConfigure.init(this,"5cb40847570df3836d000174"//appKey
//                ,getPackageName()+"umeng"//channel
//                ,UMConfigure.DEVICE_TYPE_PHONE,//手机类型
//                "");//如果不集成推送的话，设为空
        if (BuildConfig.DEBUG) {// 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
//        // 打开统计SDK调试模式
//        UMConfigure.setLogEnabled(true);
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    public NetworkStateReceiver getNetworkChangeReceiver(){
        return networkChangeReceiver;
    }

    //前后台切换
    private void initBack() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (isBackGround) {
                    isBackGround = false;
//                    Integer userId = null;
//                    String phoneCode = null;
//                    if (UserAccountHelper.isLogin()) {
//                        userId = Integer.valueOf(UserAccountHelper.getUserId() + "");
//                        phoneCode = UserAccountHelper.getUser().getTelphone();
//                    }
//                    AccessLog.upDataLog(getApplicationContext(),"APP回到了前台");
                    LogUtil.show("APP回到了前台");
                    if (AppSettingHelper.getGesturesIsOpen() && !AppManager.isActivityTop(LockViewActivity.class, activity)&&
                            (activity.getClass()!= LaunchActivity.class)&&
                            (activity.getClass()!= WelcomeActivity.class)) {
                        startLockView();
                    }
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }

    @Override
    public void onTrimMemory(int level) {

        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isBackGround = true;
//            Integer userId = null;
//            String phoneCode = null;
//            if (UserAccountHelper.isLogin()) {
//                userId = Integer.valueOf(UserAccountHelper.getUserId() + "");
//                phoneCode = UserAccountHelper.getUser().getTelphone();
//            }
            LogUtil.show("APP遁入后台");
//            AccessLog.upDataLog(getApplicationContext(),"APP遁入后台");
        }
        super.onTrimMemory(level);
    }


}
