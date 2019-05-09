package com.cherishTang.laishou.api;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.cherishTang.laishou.activity.FeedBackActivity;
import com.cherishTang.laishou.activity.LockViewActivity;
import com.cherishTang.laishou.activity.WebViewMessageActivity;
import com.cherishTang.laishou.activity.PrivacyPolicyActivity;
import com.cherishTang.laishou.laishou.main.activity.MainActivity;
import com.cherishTang.laishou.activity.MessageActivity;
import com.cherishTang.laishou.user.myhouse.MyHouseActivity;
import com.cherishTang.laishou.activity.WebViewActivity;
import com.cherishTang.laishou.welcome.WelcomeActivity;

/**
 * Created by 方舟 on 2017/5/23
 * 页面跳转等.
 */

public class AppHelper {
    public static void showWelcomeActivity(Activity context){
        WelcomeActivity.show(context);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void showMainActivity(Context context){
        MainActivity.show(context);
    }
    public static void showWebViewActivity(Context context,String loadUrl,String title_text){
        WebViewActivity.show(context,loadUrl,title_text);
    }
    public static void showWebViewMessageActivity(Context context,String loadUrl,String title_text){
        WebViewMessageActivity.show(context,loadUrl,title_text);
    }
    public static void showMessageActivity(Context context,Bundle bundle){
        MessageActivity.show(context,bundle);
    }
    public static void showMyHouseActivity(Context context){
        MyHouseActivity.show(context);
    }
    public static void showPrivacyPolicyActivity(Context context){
        PrivacyPolicyActivity.show(context);
    }
    public static void showLockViewActivity(Activity context){
        LockViewActivity.show(context);
    }

    public static void showFeedbackActivity(Context context){
        FeedBackActivity.show(context);
    }

}
