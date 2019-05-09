package com.cherishTang.laishou.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.util.listener.OnDialogInterfaceClickListener;


/**
 * Created by 方舟 on 2017/1/14.
 * 确认弹框
 */

public class ConfirmDialog extends Dialog {
    private Context context;
    private String content;
    private View sLine;
    private View inflate;
    private TextView dialog_textView, dialog_sure, dialog_cancel;
    private OnDialogInterfaceClickListener sureClickListener, cancelClickListener;
    private boolean outSide = true;
    private String strSureText = "确定",strCancelText = "取消";
    private boolean isShowSureView = true,isShowCancelView = true,isShowLineView = true;

    public ConfirmDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }
    public ConfirmDialog setOnSureClickListener(OnDialogInterfaceClickListener sureClickListener){
        this.sureClickListener = sureClickListener;
        return this;
    }
    public ConfirmDialog setCanOutSide(boolean outSide){
        this.outSide = outSide;
        return this;
    }
    public ConfirmDialog setOnCancelClickListener(OnDialogInterfaceClickListener cancelClickListener){
        this.cancelClickListener = cancelClickListener;
        return this;
    }
    public ConfirmDialog setMessage(String message){
        this.content = message;
        return this;
    }
    public ConfirmDialog setSureText(String strSureText){
        this.strSureText = strSureText;
        return this;
    }
    public ConfirmDialog setCancelText(String strCancelText){
        this.strCancelText = strCancelText;
        return this;
    }
    public ConfirmDialog setShowSureView(boolean isShowSureView){
        this.isShowSureView = isShowSureView;
        this.isShowLineView = this.isShowSureView;
        return this;
    }
    public ConfirmDialog setShowCancelView(boolean isShowCancelView){
        this.isShowCancelView = isShowCancelView;
        this.isShowLineView = this.isShowCancelView;
        return this;
    }

    public ConfirmDialog builder() {
        initView();
        return this;
    }

    public void initView() {
        inflate = LayoutInflater.from(context).inflate(
                R.layout.sure_cancel_dialog, null);
        // 初始化控件
        dialog_textView = inflate.findViewById(R.id.dialog_textView);
        dialog_sure = inflate.findViewById(R.id.dialog_sure);
        dialog_cancel = inflate.findViewById(R.id.dialog_cancel);
        sLine = inflate.findViewById(R.id.s_line);
        dialog_sure.setText(strSureText);
        dialog_cancel.setText(strCancelText);

        if(!isShowLineView)
            sLine.setVisibility(View.GONE);
        if(!isShowCancelView)
            dialog_cancel.setVisibility(View.GONE);
        if(!isShowSureView)
            dialog_sure.setVisibility(View.GONE);

        if (sureClickListener != null) {
            dialog_sure.setOnClickListener(v -> {
                dismiss();
                sureClickListener.onDialogClick(this);
            });
        } else {
            dialog_sure.setOnClickListener(v -> dismiss());
        }

        if (cancelClickListener != null) {
            dialog_cancel.setOnClickListener(v -> {
                dismiss();
                cancelClickListener.onDialogClick(this);
            });
        } else
            dialog_cancel.setOnClickListener(v -> dismiss());
        dialog_textView.setText(content);
        setCanceledOnTouchOutside(outSide);
        setContentView(inflate);
        Window dialogWindow = getWindow();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(dm);
            dialogWindow.setLayout(dm.widthPixels * 4 / 5,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        // 设置Dialog从窗体中间弹出
        dialogWindow.setGravity(Gravity.CENTER);
    }

}
