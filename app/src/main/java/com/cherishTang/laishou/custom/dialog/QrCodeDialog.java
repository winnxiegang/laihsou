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
import android.widget.ImageView;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.listener.OnDialogInterfaceClickListener;
import com.cherishTang.laishou.zxing.encode.QRCodeUtil;


/**
 * Created by 方舟 on 2017/1/14.
 * 二维码弹框
 */

public class QrCodeDialog extends Dialog {
    private Context context;
    private String content;
    private String title;
    private ImageView imageQrCode, imageClose;
    private TextView tvQrTip;
    private View inflate;
    private boolean outSide = true;

    public QrCodeDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public QrCodeDialog setCanOutSide(boolean outSide) {
        this.outSide = outSide;
        return this;
    }
    public QrCodeDialog setTitle(String title) {
        this.title = title;
        return this;
    }
    public QrCodeDialog setMessage(String message) {
        this.content = message;
        return this;
    }
    public QrCodeDialog builder() {
        initView();
        return this;
    }
    public void initView() {
        inflate = LayoutInflater.from(context).inflate(
                R.layout.create_qr_code_dialog, null);
        tvQrTip = inflate.findViewById(R.id.tv_qr_tip);
        imageQrCode = inflate.findViewById(R.id.image_qr_code);
        imageClose = inflate.findViewById(R.id.image_close);
        imageClose.setOnClickListener(v -> dismiss());

        if(!StringUtil.isEmpty(tvQrTip)){
            tvQrTip.setText(title);
        }
        imageQrCode.setImageBitmap(QRCodeUtil.createQRCodeBitmap(content,
                (int) context.getResources().getDimension(R.dimen.x268),
                (int) context.getResources().getDimension(R.dimen.x268)));
        setCanceledOnTouchOutside(outSide);
        setContentView(inflate);
        Window dialogWindow = getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置Dialog从窗体中间弹出
        dialogWindow.setGravity(Gravity.CENTER);
    }

}
