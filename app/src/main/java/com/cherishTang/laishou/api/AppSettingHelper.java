package com.cherishTang.laishou.api;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by 方舟 on 2017/11/21.
 * app相关设置
 */

public class AppSettingHelper {
    public static Application application;
    private static AppSettingHelper appSettingHelper;
    private static SharedPreferences sp;
    private static final String APPPSETTING = "AppSetting";
    private static final String ISOPEN = "isOpen";

    private static final String GESTURESPASSWORD = "gesturesPassword";//手势密码
    private static final String GESTURESPASSWORD_ISOPEN = "gesturesPasswordIsOpen";//手势密码是否开启

    public AppSettingHelper(Application application) {
        AppSettingHelper.application = application;
    }

    public static void init(Application application) {
        appSettingHelper = new AppSettingHelper(application);
        sp = application.getSharedPreferences(application.getPackageName() + APPPSETTING, Activity.MODE_PRIVATE);
    }

    public static void setNotification(boolean isOpen) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(ISOPEN, isOpen);
        editor.apply();
    }

    public static boolean getNotification() {
        return sp == null || sp.getBoolean(ISOPEN, true);
    }

    public static String getGestures(){
        return sp==null?null:sp.getString(GESTURESPASSWORD,null);
    }

    public static boolean getGesturesIsOpen(){
        return sp!=null&&sp.getBoolean(GESTURESPASSWORD_ISOPEN,false)&&sp.getString(GESTURESPASSWORD,null)!=null;
    }

    public static void setGestures(String gestures){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(GESTURESPASSWORD,gestures);
        editor.apply();
    }

    public static void setGesturesIsOpen(boolean gestures){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(GESTURESPASSWORD_ISOPEN,gestures);
        editor.apply();
    }
}
