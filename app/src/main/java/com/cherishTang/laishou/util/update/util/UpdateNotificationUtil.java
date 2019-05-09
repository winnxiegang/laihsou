package com.cherishTang.laishou.util.update.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.laishou.main.activity.MainActivity;
import com.cherishTang.laishou.util.apiUtil.NotificationUtils;

/**
 * Created by 方舟 on 2017/10/13.
 * 更新通知
 */
public class UpdateNotificationUtil {

    private Context mContext;
    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;

    public UpdateNotificationUtil(Context context) {
        this.mContext = context;
        mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationUtils.createNotificationChannel(
                    false,false,
                    ConstantsHelper.updateVerisonChannelId,
                    ConstantsHelper.updateVerisonChannelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
        }
        mBuilder = new NotificationCompat.Builder(context, ConstantsHelper.updateVerisonChannelId);
    }

    /**
     * 显示通知栏
     *
     * @param id
     */
    public void showNotification(final int id) {

        mBuilder.setTicker("正在下载");//Ticker是状态栏显示的提示
        mBuilder.setOngoing(true);
        mBuilder.setContentTitle("正在下载最新版本");
        mBuilder.setProgress(100, 0, false);
        mBuilder.setContentText(0 + "%");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));//通知栏的大图标
        Intent msgIntent = new Intent();
        msgIntent.setClass(mContext, MainActivity.class);
        msgIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 100, msgIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);//点击跳转
        mManager.notify(id, mBuilder.build());
    }

    /**
     * 取消通知栏通知
     */
    public void cancelNotification(int id) {
        mManager.cancel(id);
    }

    /**
     * 更新通知栏进度条
     *
     * @param id       获取Notification的id
     * @param progress 获取的进度
     */
    public void updateNotification(int id, int progress) {
        if (mBuilder != null) {
            mBuilder.setTicker("开始下载");//Ticker是状态栏显示的提示
            mBuilder.setOngoing(true);
            mBuilder.setContentTitle("莱瘦");
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));//通知栏的大图标
            mBuilder.setProgress(100, progress, false);
            mBuilder.setContentText(progress + "%");
            mManager.notify(id, mBuilder.build());
        }
    }
}
