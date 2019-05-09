package com.cherishTang.laishou.util.update;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ProgressBar;


import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.custom.dialog.UpdateMessageDialog;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.update.server.DownloadApkService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;


/**
 * Created by 方舟 on 2016/11/23.
 * 软件版本更新
 */
public class UpdateManger implements UpdateMessageDialog.OnUpdateListener {
    // 应用程序Context
    private static Activity mContext;
    // 提示消息
    private String updateMsg = "有最新的软件包，请下载！";
    // 下载安装包的网络路径
    private String apkUrl;
    private Dialog noticeDialog;// 提示有软件更新的对话框
    private Dialog downloadDialog;// 下载对话框
    private static final String savePath = "/sdcard/hefeifangchan/file/";// 保存apk的文件夹
    private static final String saveFileName = savePath + "hf" + Calendar.getInstance().getTimeInMillis() + ".apk";
    // 进度条与通知UI刷新的handler和msg常量
    private ProgressBar mProgress;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private int progress;// 当前进度
    private Thread downLoadThread; // 下载线程
    private boolean interceptFlag = false;// 用户取消下载
    private String mCurrentVersionName;
    private static UpdateManger updateManger;

    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISHED = "ACTION_FINISHED";
    public static final String ACTION_CANCEL = "ACTION_CANCEL";
    public static final String ACTION_ERROR = "ACTION_ERROR";
    //显示通知栏进度条
    public Intent intent = new Intent();
    long time;
    // 通知处理刷新界面的handler
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    time = System.currentTimeMillis();
                    intent.setAction(ACTION_UPDATE);
                    intent.putExtra("finished", progress);
                    mContext.sendBroadcast(intent);
//                    mProgress.setProgress(progress);
                    break;
                case DOWN_OVER:
                    //关闭通知栏
                    intent.setAction(ACTION_FINISHED);
                    mContext.sendBroadcast(intent);
//                    installApk();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public UpdateManger() {

    }

    public static UpdateManger getInstance() {
        if (updateManger == null) {
            updateManger = new UpdateManger();
        }
        return updateManger;
    }

    // 显示更新程序对话框，供主程序调用
    public void checkUpdateInfo(Activity mContext, String apkUrl, String updateMsg,
                                String mCurrentVersionName) {
        this.apkUrl = apkUrl;
        UpdateManger.mContext = mContext;
        if (updateMsg != null && !updateMsg.equals("")) {
            this.updateMsg = updateMsg;
        }
        this.mCurrentVersionName = mCurrentVersionName;
        UpdateMessageDialog updateMessageDialog =
                new UpdateMessageDialog(mContext, mCurrentVersionName, updateMsg);
        updateMessageDialog.create();
        updateMessageDialog.setOnUpdateListener(this);
    }

    public void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    public static void installApk(Context mContext,File apkFile) {
        if (!apkFile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) { //适配安卓7.0
            i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_ACTIVITY_NEW_TASK); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            Uri apkFileUri = FileProvider.getUriForFile(mContext.getApplicationContext(),
                    mContext.getPackageName() + ".fileprovider", apkFile);
            i.setDataAndType(apkFileUri, "application/vnd.android.package-archive");
        } else {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + apkFile.toString()),
                    "application/vnd.android.package-archive");// File.toString()会返回路径信息
        }
        mContext.startActivity(i);
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            URL url;
            try {
                url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream ins = conn.getInputStream();
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream outStream = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = ins.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 下载进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        if (downloadDialog != null && downloadDialog.isShowing())
                            downloadDialog.dismiss();
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    outStream.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消停止下载
                outStream.close();
                ins.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onUpdate(View v) {
        if(ConstantsHelper.isDownLoadApk){
            ToastUtils.showShort(mContext,"已存在下载任务，请勿重复下载");
            return;
        }

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0x01);
        } else {
            ToastUtils.showShort(mContext,"任务已转至后台下载");
            Intent intent = new Intent(mContext, DownloadApkService.class);
            intent.setAction(DownloadApkService.ACTION_START);
            intent.putExtra("id", 0);
            intent.putExtra("url", apkUrl);
            intent.putExtra("name", ConstantsHelper.apkName);
            mContext.startService(intent);
        }
    }
}
