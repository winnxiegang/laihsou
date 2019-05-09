package com.cherishTang.laishou.util.download;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.util.download.server.DownloadService;


/**
 * Created by 方舟 on 2016/11/23.
 * 下载文件
 */
public class DownLoadManger {

    public static void downLoad(final Context mContext, final String fileUrl,
                                final int id, final String fileName) {
        DialogHelper.getConfirmDialog(mContext, "是否下载此文件",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(mContext, DownloadService.class);
                        intent.setAction(DownloadService.ACTION_START);
                        intent.putExtra("id", id);
                        intent.putExtra("url", fileUrl);
                        intent.putExtra("fileName", fileName);
                        mContext.startService(intent);
                    }
                }).show();

    }

}
