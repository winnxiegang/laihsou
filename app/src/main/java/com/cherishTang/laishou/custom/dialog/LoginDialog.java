package com.cherishTang.laishou.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.cherishTang.laishou.R;


/**
 * Created by 方舟 on 2018/3/29.
 * 登录弹框
 */

public class LoginDialog{
    private TextView dialog_textView, dialog_sure, dialog_cancel;
    private static Dialog dialog;
    private static LoginDialog instance = null;
    private Context mContext;

    /**
     * 加锁实现单利dialog
     * @return
     */
    public static LoginDialog getInstance(Context mContext) {
        if (dialog == null || !dialog.isShowing()) {
            instance = new LoginDialog(mContext).builder();
        }
        return instance;
    }

    private LoginDialog(Context context) {
        this.mContext = context;
    }

    public static Dialog getDialog() {
        return dialog;
    }

    private LoginDialog builder() {
        dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(mContext).inflate(
                R.layout.sure_cancel_dialog, null);
        dialog_textView = inflate.findViewById(R.id.dialog_textView);

        dialog_sure = inflate.findViewById(R.id.dialog_sure);
        dialog_cancel = inflate.findViewById(R.id.dialog_cancel);
        dialog.setCanceledOnTouchOutside(false);
        dialog_sure.setText("登录");
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
        });
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.CENTER);
        return this;
    }

    public LoginDialog setMessage(String message) {
        dialog_textView.setText(message);
        return this;
    }

    public LoginDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public LoginDialog setPositiveButton(final OnLoginClickListener listener, final int code) {
        if (listener != null)
            dialog_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                    listener.onLoginClick(v, code);
                }
            });
        return this;
    }

    public interface OnLoginClickListener {
        void onLoginClick(View v, int code);
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) dialog.show();
    }

}
