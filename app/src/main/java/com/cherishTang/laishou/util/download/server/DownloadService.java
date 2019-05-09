package com.cherishTang.laishou.util.download.server;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;


import com.cherishTang.laishou.api.ConstantsHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 方舟 on 2017/10/17.
 * 下载服务
 */

public class DownloadService extends Service {

    public static final String ACTION_START = "download_start";
    public static final String ACTION_UPDATE = "download_update";
    public static final String ACTION_FINISHED = "download_finished";
    public static final String ACTION_CANCEL = "download_cancel";
    public static final String ACTION_ERROR = "download_error";
    public static final String ACTION_NOT_FOUND= "download_not_found";

    // 文件的保存路径
    public static final String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + ConstantsHelper.ROOTFILEPATH + File.separator + "download" + File.separator;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (ACTION_START.equals(intent.getAction())) {
                new DownLoadThread(intent.getIntExtra("id", 0)
                        , intent.getStringExtra("url"),
                        intent.getStringExtra("fileName")).start();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public class DownLoadThread extends Thread {

        private int id;
        private String downloadUrl;
        private String fileName;

        public DownLoadThread(int id, String downloadUrl, String fileName) {
            this.id = id;
            this.downloadUrl = downloadUrl;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            HttpURLConnection connLength = null;
            HttpURLConnection connFile = null;
            RandomAccessFile randomAccessFile = null;
            InputStream inputStream = null;
            URL url = null;
            int length = -1;

            try {
                url = new URL(downloadUrl);
                connLength = (HttpURLConnection) url.openConnection();
                connLength.setConnectTimeout(5000);
                connLength.setRequestMethod("GET");
                int code = connLength.getResponseCode();
                if (code == HttpURLConnection.HTTP_OK) {
                    length = connLength.getContentLength();
                }else if(code == HttpURLConnection.HTTP_NOT_FOUND){
                    sendBroadcast(new Intent().setAction(ACTION_NOT_FOUND));
                    return;
                } else {
                    sendBroadcast(new Intent().setAction(ACTION_ERROR));
                    return;
                }
                //判断文件是否存在，不存在则创建
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, fileName);
                randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.setLength(length);

                //下载文件
                connFile = (HttpURLConnection) url.openConnection();
                connFile.setConnectTimeout(5000);
                connFile.setRequestMethod("GET");
                connFile.setRequestProperty("Range", "bytes=" + 0 + "-" + length);
                code = connFile.getResponseCode();

                //显示通知栏进度条
                Intent intent = new Intent();
                intent.setAction(ACTION_START);
                intent.putExtra("id", id);
                sendBroadcast(intent);

                if (code == HttpURLConnection.HTTP_PARTIAL) {
                    inputStream = connFile.getInputStream();
                    int finished = 0;
                    byte[] bytes = new byte[1024 * 1024];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = inputStream.read(bytes)) != -1) {
                        //文件写入
                        randomAccessFile.write(bytes, 0, len);
                        //更新通知栏进度条
                        finished += len;
                        if (System.currentTimeMillis() - time > 1000) {
                            time = System.currentTimeMillis();
                            intent.setAction(ACTION_UPDATE);
                            int pro = (int) (((float) finished / length) * 100);
                            intent.putExtra("finished", pro);
                            intent.putExtra("fileName", fileName);
                            sendBroadcast(intent);
                        }
                    }
                }
                //关闭通知栏
                intent.setAction(ACTION_FINISHED);
                intent.putExtra("downloadPath",path);
                sendBroadcast(intent);

            } catch (MalformedURLException e) {
                sendBroadcast(new Intent().setAction(ACTION_ERROR));
                e.printStackTrace();
            } catch (IOException e) {
                sendBroadcast(new Intent().setAction(ACTION_ERROR));
                e.printStackTrace();
            } finally {
                if (connLength != null) {
                    connLength.disconnect();
                }
                if (connFile != null) {
                    connFile.disconnect();
                }
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            super.run();
        }
    }
}
