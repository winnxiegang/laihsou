package com.cherishTang.laishou.util.log;

import android.util.Log;

import com.cherishTang.laishou.BuildConfig;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by CherishTang on 2019/2/22.
 * 日志打印
 */

public class LogUtil {
    public static void init() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // 是否打印线程号,默认true
                .methodCount(5)         // 展示几个方法数,默认2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) //是否更换打印输出,默认为logcat
                .tag(ConstantsHelper.TAG)   // 全局的tag
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static void show(String str) {
        if (BuildConfig.LOG_DEBUG)
            Log.d(ConstantsHelper.TAG, str);
    }

    public static void i(String str) {
        if (BuildConfig.LOG_DEBUG)
            Log.i(ConstantsHelper.TAG, str);
    }

    public static void d(String str) {
        if (BuildConfig.LOG_DEBUG)
            Log.d(ConstantsHelper.TAG, str);
    }

    public static void json(final String json) {
        if (BuildConfig.LOG_DEBUG)
            Logger.json(json);
    }

    public static void xml(final String xml) {
        if (BuildConfig.LOG_DEBUG)
            Logger.xml(xml);
    }

    public static void e(Throwable exception) {
        if (BuildConfig.LOG_DEBUG)
            Logger.e(exception, "message");
    }
}
