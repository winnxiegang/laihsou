package com.cherishTang.laishou.util.update.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.util.update.UpdateManger;
import com.cherishTang.laishou.util.update.util.UpdateNotificationUtil;

import java.io.File;

/**
 * Created by 方舟 on 2017/10/17.
 *  更新下载广播接收器
 */

public class DownloadApkReceiver extends BroadcastReceiver {

    private UpdateNotificationUtil mNotificationUtil;

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationUtil = new UpdateNotificationUtil(context);

        if (DownloadApkService.ACTION_START.equals(intent.getAction())) {
            ConstantsHelper.isDownLoadApk = true;//单例下载，防止多任务进行
            // 下载开始的时候启动通知栏
            mNotificationUtil.showNotification(intent.getIntExtra("id", 0));
        } else if (DownloadApkService.ACTION_UPDATE.equals(intent.getAction())) {
            // 更新进度条
            mNotificationUtil.updateNotification(intent.getIntExtra("id",0), intent.getIntExtra("finished", 0));
        } else if (DownloadApkService.ACTION_FINISHED.equals(intent.getAction())) {
            ConstantsHelper.isDownLoadApk = false;//变更未任务未下载

            mNotificationUtil.cancelNotification(intent.getIntExtra("id", 0));// 下载结束后取消通知
            UpdateManger.installApk(context, new File(DownloadApkService.path + ConstantsHelper.apkName));
        } else if (DownloadApkService.ACTION_CANCEL.equals(intent.getAction())) {

            mNotificationUtil.cancelNotification(intent.getIntExtra("id", 0));// 下载结束后取消通知
        }
    }
}
