package com.cherishTang.laishou.util.apiUtil;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ConstantsHelper;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by CherishTang on 2018/7/19.
 * 通知栏工具类
 */

public class NotificationUtils {
    private static NotificationManager mManager;
    private static NotificationCompat.Builder notification;
    private String contentText, contentTitle, channelId;
    private boolean autoCancel = true;
    private static Application application;

    public static void init(Application application) {
        NotificationUtils.application = application;
        mManager = (NotificationManager) application.getSystemService(NOTIFICATION_SERVICE);
        new NotificationUtils().Builder(ConstantsHelper.stepChannelId);

    }

    public NotificationUtils Builder(String channelId) {
        if (notification == null) {
            notification = new NotificationCompat.Builder(application, channelId == null ? "chat" : channelId);
        } else {
            notification.setChannelId(channelId);
        }
        this.channelId = channelId;
        return this;
    }

    public static NotificationManager getManager() {
        return mManager;
    }

    public static NotificationCompat.Builder getNotification() {
        return notification;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void createNotificationChannel(boolean isVibrate,
                                                 boolean hasSound,
                                                 String channelId,
                                                 String channelName,
                                                 int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) application.getSystemService(
                NOTIFICATION_SERVICE);
        channel.enableVibration(isVibrate);
        channel.enableLights(true);
        if (!hasSound)
            channel.setSound(null, null);
        if (notificationManager != null)
            notificationManager.createNotificationChannel(channel);
    }

    public NotificationUtils setChannelId(String channelId) {
        notification.setChannelId(channelId);
        this.channelId = channelId;
        return this;
    }

    public NotificationUtils setContentText(String contentText) {
        notification.setContentText(contentText);
        this.contentText = contentText;
        return this;
    }

    public NotificationUtils setContentTitle(String contentTitle) {
        notification.setContentTitle(contentTitle);
        this.contentTitle = contentTitle;
        return this;
    }

    public NotificationUtils setAutoCancel(boolean autoCancel) {
        notification.setAutoCancel(autoCancel);
        this.autoCancel = autoCancel;
        return this;
    }

    public NotificationUtils notifyMessage(int id) {
        if (mManager != null) {
            notification.setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(application.getResources(), R.mipmap.ic_launcher))
                    .setAutoCancel(autoCancel)
                    .build();
            mManager.notify(id, notification.build());
        }
        return this;
    }

}
