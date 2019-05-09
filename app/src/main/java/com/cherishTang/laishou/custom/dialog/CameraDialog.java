package com.cherishTang.laishou.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.cherishTang.laishou.R;

/**
 * Created by 方舟 on 2017/6/9.
 * 拍照还是相册选择dialog底部弹出一般
 */

public class CameraDialog implements View.OnClickListener{

    private static Dialog headDialog;
    private static Button choosePhoto;
    private static Button takePhoto;
    private static Button she_cancel;
    private static View dialogView;

    public static Dialog showDialog(Context context) {
        headDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        // 填充对话框的布局
        dialogView = LayoutInflater.from(context).inflate(R.layout.she_dialog, null);
        // 初始化控件
        choosePhoto = (Button) dialogView.findViewById(R.id.choosePhoto);
        takePhoto = (Button) dialogView.findViewById(R.id.takePhoto);
        she_cancel = (Button) dialogView.findViewById(R.id.she_cancel);
        // 将布局设置给Dialog
        headDialog.setContentView(dialogView);
        Window dialogWindow = headDialog.getWindow();
        dialogWindow.setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;// 设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        return headDialog;
    }

    @Override
    public void onClick(View v) {

    }
}
