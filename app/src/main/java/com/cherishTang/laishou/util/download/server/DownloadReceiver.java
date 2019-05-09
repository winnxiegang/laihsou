package com.cherishTang.laishou.util.download.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cherishTang.laishou.util.download.util.DownLoadNotificationUtil;
import com.cherishTang.laishou.util.log.ToastUtils;


/**
 * Created by 方舟 on 2017/10/17.
 * 更新下载广播接收器
 */

public class DownloadReceiver extends BroadcastReceiver {

    private DownLoadNotificationUtil mNotificationUtil;

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", 0);
        mNotificationUtil = new DownLoadNotificationUtil(context, "fileDownload");
        if (DownloadService.ACTION_START.equals(intent.getAction())) {
            // 下载开始的时候启动通知栏
            ToastUtils.showShort(context, "任务已开始下载");
            mNotificationUtil.showNotification(id,
                    intent.getStringExtra("fileName"));
        } else if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
            // 更新进度条
            mNotificationUtil.updateNotification(id,
                    intent.getIntExtra("finished", 0),
                    intent.getStringExtra("fileName"));
        } else if (DownloadService.ACTION_FINISHED.equals(intent.getAction())) {
            if (intent.getStringExtra("downloadPath") != null)
                ToastUtils.showShort(context, "文件已保存至" + intent.getStringExtra("downloadPath"));
            mNotificationUtil.cancelNotification(id);// 下载结束后取消通知
//            UpdateManger.installApk(context, new File(DownloadService.path + StaticUtil.apkName));
        } else if (DownloadService.ACTION_CANCEL.equals(intent.getAction())) {
            mNotificationUtil.cancelNotification(id);// 下载结束后取消通知
        } else if (DownloadService.ACTION_ERROR.equals(intent.getAction())) {
            ToastUtils.showShort(context, "下载出现错误，请稍后再试！");
        }else if (DownloadService.ACTION_NOT_FOUND.equals(intent.getAction())) {
            ToastUtils.showShort(context, "下载地址不正确或不支持此格式文件下载");
        }
    }
}
