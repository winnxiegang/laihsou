package com.cherishTang.laishou.broadCastReceiver;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.laishou.main.activity.MainActivity;
import com.cherishTang.laishou.api.AppManager;
import com.cherishTang.laishou.bean.JpushBean;
import com.cherishTang.laishou.database.JpushDataBaseOperate;
import com.cherishTang.laishou.database.JpushSQLiteDataHelper;
import com.cherishTang.laishou.util.JPushDataUitl;
import com.cherishTang.laishou.util.log.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by fangzhou on 2016/10/22.
 * 自定义接收器
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushDataReceiver extends BroadcastReceiver {
    private JpushSQLiteDataHelper jpushSQLiteDataHelper;
    private JpushDataBaseOperate jpushDataBaseOperate;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        jpushSQLiteDataHelper = JpushSQLiteDataHelper.getInstance(context);
        jpushDataBaseOperate = new JpushDataBaseOperate(jpushSQLiteDataHelper.getWritableDatabase());
       LogUtil.show( "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
           LogUtil.show( "[MyReceiver]    接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
           LogUtil.show( "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            intent.putExtras(extraBundle);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.setClass(context, MainActivity.class);
//            context.startActivity(intent);
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
           LogUtil.show( "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            try {
                /*
                 * 点亮屏幕，并且在屏幕中显示弹框提示，
                 * 但是不是所有的机型都会适配，oppo大部分机型
                 */
                KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                if (km.inKeyguardRestrictedInputMode()) {
                    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                    if (!pm.isScreenOn()) {
                        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                                PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
                        wl.acquire();
                        wl.release();
                    }
                }
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String message = bundle.getString(JPushInterface.EXTRA_ALERT);
                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
                JpushBean jpushBean = new JpushBean(title,message,extra,null, Calendar.getInstance().getTimeInMillis(),notifactionId,0);
                jpushDataBaseOperate.insert(jpushBean);
                Bundle extraBundle = new Bundle();
                extraBundle.putString("title", title);
                extraBundle.putString("message", message);
                extraBundle.putString("extra", extra);
                extraBundle.putLong("notifactionId", notifactionId);
                processMessage(context, extraBundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
           LogUtil.show( "[MyReceiver] 用户点击打开了通知");
            /*
             * 打开自定义的Activity
             * 根据不同的推送信息，来选择跳转到哪里
             *
             * title   ：应用名称
             * content ：推送内容
             * type    ：推送数据，自定义的键值对，用来判断跳转到哪里去，需要json解析
             */
            if(!AppManager.isActivityTop(MainActivity.class,context)){
                Intent mainIntent = new Intent(context,MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(mainIntent);
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
           LogUtil.show( "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等...
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtil.show("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
           LogUtil.show( "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA) == null) {
                    LogUtil.show( "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtil.show(  "Get message extra JSON error!");
                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void processMessage(Context context, Bundle bundle) {
        Intent msgIntent = new Intent(ConstantsHelper.MESSAGE_INFO);
        msgIntent.putExtras(bundle);
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
    }

    //send msg to HomeActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(ConstantsHelper.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(ConstantsHelper.KEY_MESSAGE, message);
            if (!JPushDataUitl.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(ConstantsHelper.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }
}
