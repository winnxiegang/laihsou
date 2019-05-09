package com.cherishTang.laishou.laishou.activity.wight;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.util.listener.OnDialogInterfaceClickListener;
import com.cherishTang.laishou.util.listener.OnPayMethodDialogInterfaceClickListener;
import com.cherishTang.laishou.util.log.ToastUtils;


/**
 * Created by 方舟 on 2017/1/14.
 * 支付方式选择
 */

public class PayMethodChoiceDialog extends Dialog {
    private Context context;
    private String content;
    private View inflate;
    private ImageView imageClose, imageWeChatSelect, imageAlipaySelect;
    private Button buttonPay;
    private RelativeLayout rlAliPay, rlWeChat;
    private OnPayMethodDialogInterfaceClickListener sureClickListener;
    private boolean outSide = true;
    public static final int PAY_METHOD_WECHAT = 2;
    public static final int PAY_METHOD_ALIPAY = 1;

    private int payMethod = 1;//1-微信，2-支付宝

    public PayMethodChoiceDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public PayMethodChoiceDialog setOnSureClickListener(OnPayMethodDialogInterfaceClickListener sureClickListener) {
        this.sureClickListener = sureClickListener;
        return this;
    }

    public PayMethodChoiceDialog setCanOutSide(boolean outSide) {
        this.outSide = outSide;
        return this;
    }

    public PayMethodChoiceDialog setMessage(String message) {
        this.content = message;
        return this;
    }

    public PayMethodChoiceDialog setDefaultPayMethod(int payMethod) {
        this.payMethod = payMethod;
        return this;
    }

    public PayMethodChoiceDialog builder() {
        initView();
        return this;
    }

    public void initView() {
        inflate = LayoutInflater.from(context).inflate(
                R.layout.pay_method_choice_dialog, null);
        // 初始化控件
        imageClose = inflate.findViewById(R.id.image_close);
        imageWeChatSelect = inflate.findViewById(R.id.image_weChat_select);
        imageAlipaySelect = inflate.findViewById(R.id.image_alipay_select);
        buttonPay = inflate.findViewById(R.id.button_pay);
        rlAliPay = inflate.findViewById(R.id.rl_aliPay);
        rlWeChat = inflate.findViewById(R.id.rl_weChat);

        if (payMethod == PAY_METHOD_WECHAT) {
            imageWeChatSelect.setSelected(true);
            imageAlipaySelect.setSelected(false);
        } else if (payMethod == PAY_METHOD_ALIPAY) {
            imageWeChatSelect.setSelected(false);
            imageAlipaySelect.setSelected(true);
        }

        rlAliPay.setOnClickListener(v -> {
            payMethod = PAY_METHOD_ALIPAY;
            imageWeChatSelect.setSelected(false);
            imageAlipaySelect.setSelected(true);
        });
        rlWeChat.setOnClickListener(v -> {
            payMethod = PAY_METHOD_WECHAT;
            imageWeChatSelect.setSelected(true);
            imageAlipaySelect.setSelected(false);
        });
        buttonPay.setOnClickListener(v -> {
            if (payMethod != PAY_METHOD_WECHAT && payMethod != PAY_METHOD_ALIPAY) {
                ToastUtils.showShort(context, "请至少选择一种选择方式");
                return;
            }
            dismiss();
            if (sureClickListener != null) {
                sureClickListener.onDialogClick(this, payMethod);
            }
        });
        setCanceledOnTouchOutside(outSide);
        setContentView(inflate);
        Window dialogWindow = getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置Dialog从窗体中间弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
    }

}
