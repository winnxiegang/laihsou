package com.cherishTang.laishou.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.cherishTang.laishou.R;

/**
 * Created by 方舟 on 2018/3/29.
 * 选择照片和拍照弹框
 */

public class OpenImageDialog implements View.OnClickListener {
    private View dialogView;
    private static OpenImageDialog openImageDialog;
    private Dialog dialog;
    private Button choosePhoto, takePhoto, she_cancel;
    private OnOpenImageClickListener onOpenImageClickListener;

    public static OpenImageDialog getInstance() {
        if (openImageDialog == null)
            openImageDialog = new OpenImageDialog();
        return openImageDialog;
    }

    public Dialog create(Context context) {
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialogView = LayoutInflater.from(context).inflate(R.layout.she_dialog, null);
        choosePhoto = (Button) dialogView.findViewById(R.id.choosePhoto);
        takePhoto = (Button) dialogView.findViewById(R.id.takePhoto);
        she_cancel = (Button) dialogView.findViewById(R.id.she_cancel);
        she_cancel.setOnClickListener(this);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        dialog.setContentView(dialogView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;// 设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        switch (v.getId()) {
            case R.id.choosePhoto:
                if(onOpenImageClickListener!=null) onOpenImageClickListener.openAlbum();
                break;
            case R.id.takePhoto:
                if(onOpenImageClickListener!=null) onOpenImageClickListener.openCamera();
                break;
        }
    }
    public void setOnOpenImageClickListener(OnOpenImageClickListener onOpenImageClickListener){
        this.onOpenImageClickListener = onOpenImageClickListener;
    }

    public interface OnOpenImageClickListener{
        void openCamera();
        void openAlbum();
    }

}
