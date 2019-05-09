package com.cherishTang.laishou.util.log;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.cherishTang.laishou.custom.dialog.DialogHelper;

/**
 * Created by 方舟 on 2017/5/23.
 * Toast弹框提示
 */

public class ToastUtils {
    public static void showShort(Context context, String str) {
        try {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showShort(Context context, @StringRes int strRes) {
        try {
            Toast.makeText(context, context.getString(strRes), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLong(Context context, String str) {
        try {
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showMessageDialog(Context context, String message) {
        try {
            DialogHelper.getMessageDialog(context, message).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
