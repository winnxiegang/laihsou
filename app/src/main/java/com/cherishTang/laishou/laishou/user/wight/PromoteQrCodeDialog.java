package com.cherishTang.laishou.laishou.user.wight;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.zxing.encode.QRCodeUtil;


/**
 * Created by 方舟 on 2017/1/14.
 * 二维码弹框
 */

public class PromoteQrCodeDialog extends Dialog {
    private Context context;
    private String content;
    private String title;
    private ImageView imageQrCode,imageBg;
    private TextView tvQrTip;
    private View inflate;
    private boolean outSide = true;

    public PromoteQrCodeDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public PromoteQrCodeDialog setCanOutSide(boolean outSide) {
        this.outSide = outSide;
        return this;
    }
    public PromoteQrCodeDialog setMessage(String message) {
        this.content = message;
        return this;
    }
    public PromoteQrCodeDialog builder() {
        initView();
        return this;
    }
    public void initView() {
        inflate = LayoutInflater.from(context).inflate(
                R.layout.promote_qr_code_dialog, null);
        imageQrCode = inflate.findViewById(R.id.image_qr_code);
        imageBg = inflate.findViewById(R.id.image_bg);
        Glide.with(context)
                .load(R.mipmap.download_bg_image)
                .into(imageBg);

        imageQrCode.setImageBitmap(QRCodeUtil.createQRCodeBitmap(content,
                (int) context.getResources().getDimension(R.dimen.x268),
                (int) context.getResources().getDimension(R.dimen.x268)));
        setCanceledOnTouchOutside(outSide);
        setContentView(inflate);
        Window dialogWindow = getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        // 设置Dialog从窗体中间弹出
        dialogWindow.setGravity(Gravity.CENTER);
    }

}
