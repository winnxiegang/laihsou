package com.cherishTang.laishou.util.glideImageToLocal;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by 方舟 on 2017/6/14.
 * DownLoadImageService图片下载回调接口
 */

public interface ImageDownBytsLoadCallBack {
    void onDownLoadSuccess(File file,String mCurrentTime);
    void onDownLoadSuccess(Bitmap bitmap);
    void onDownLoadFailed();
}
