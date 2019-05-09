package com.cherishTang.laishou.util.glideImageToLocal;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by 方舟 on 2017/6/14.
 * Glide缓存照片到本地
 * new Thread(service).start();千万不要忘了开启线程。很重要，很重要，很重要！！！
 */

public class DownLoadBytsImageService implements Runnable{
    private byte[] bys;
    private Context context;
    private ImageDownBytsLoadCallBack callBack;
    private File currentFile;
    private String name;
    private String fileName;
    private String currentTime ;
    private int random;
    public DownLoadBytsImageService(Context context, byte[] bys, String fileName, ImageDownBytsLoadCallBack callBack) {
        this.bys = bys;
        this.callBack = callBack;
        this.context = context;
        random = (int) (Math.random() * 100);
        currentTime = Calendar.getInstance().getTimeInMillis()+""+random;
        name = currentTime+".png";
        this.fileName = fileName;
    }

    @Override
    public void run() {
        File file = null;
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .load(bys)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap != null){
                // 在这里执行图片保存方法
                saveImageToGallery(context,bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null && currentFile.exists()) {
                callBack.onDownLoadSuccess(currentFile,currentTime);
                callBack.onDownLoadSuccess(bitmap);
            } else {
                callBack.onDownLoadFailed();
            }
        }
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File file = Environment.getExternalStorageDirectory();
//        String fileName = "sanshi/bannerPic/";
        File appDir = new File(file ,fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        currentFile = new File(appDir, name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    currentFile.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                Uri.fromFile(new File(currentFile.getPath()))));
    }
}
