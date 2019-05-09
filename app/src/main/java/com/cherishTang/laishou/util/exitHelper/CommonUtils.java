package com.cherishTang.laishou.util.exitHelper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.cherishTang.laishou.api.AppManager;

/**
 * Created by 方舟 on 2017/6/15.
 *  登录页面点击返回键退出应用，弹框
 */

public class CommonUtils {

    /**
     * 退出程序
     * @param cont
     */

    public static void Exit(final Context cont)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(cont);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("确定退出维修资金吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //退出
                AppManager.getAppManager().AppExit(cont);
            }
        });
        builder.setNegativeButton("再逛逛", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
