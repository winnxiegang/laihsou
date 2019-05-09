package com.cherishTang.laishou.util.download.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.laishou.main.activity.MainActivity;


/**
 * Created by 方舟 on 2017/10/13.
 * 通知栏
 */
public class DownLoadNotificationUtil {

    private Context mContext;
    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;

    public DownLoadNotificationUtil(Context context,String channelId) {
        this.mContext = context;
        mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context,channelId);
    }

    /**
     * 显示通知栏
     * @param id
     */
    public void showNotification(final int id,String message) {

        mBuilder.setTicker("正在下载");//Ticker是状态栏显示的提示
        mBuilder.setContentTitle(message);
        mBuilder.setProgress(100, 0, false);
        mBuilder.setContentText(0 + "%");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));//通知栏的大图标

        Intent msgIntent = new Intent();
        msgIntent.setClass(mContext, MainActivity.class);
        msgIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
    public void updateNotification(int id, int progress,String fileName) {
        if (mBuilder != null) {
            mBuilder.setTicker("开始下载");//Ticker是状态栏显示的提示
            mBuilder.setContentTitle(fileName);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));//通知栏的大图标
            mBuilder.setProgress(100, progress, false);
            mBuilder.setContentText(progress + "%");
            mManager.notify(id, mBuilder.build());
        }
    }
}
