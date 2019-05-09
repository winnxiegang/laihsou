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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.util.listener.OnDialogInterfaceClickListener;
import com.cherishTang.laishou.util.listener.OnInputDialogInterfaceClickListener;


/**
 * Created by 方舟 on 2017/1/14.
 * 确认弹框
 */

public class InputWeightDialog extends Dialog {
    private Context context;
    private View inflate;
    private Button buttonCancel, buttonSubmit;
    private EditText editInput;
    private OnInputDialogInterfaceClickListener sureClickListener;
    private boolean outSide = true;

    public InputWeightDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public InputWeightDialog setOnSureClickListener(OnInputDialogInterfaceClickListener sureClickListener) {
        this.sureClickListener = sureClickListener;
        return this;
    }

    public InputWeightDialog setCanOutSide(boolean outSide) {
        this.outSide = outSide;
        return this;
    }

    public InputWeightDialog builder() {
        initView();
        return this;
    }

    public void initView() {
        inflate = LayoutInflater.from(context).inflate(
                R.layout.input_weight_dialot, null);
        // 初始化控件
        buttonCancel = inflate.findViewById(R.id.button_cancel);
        buttonSubmit = inflate.findViewById(R.id.button_submit);
        editInput = inflate.findViewById(R.id.edit_input);

        buttonSubmit.setOnClickListener(v -> {
            if (sureClickListener != null) {
                sureClickListener.onDialogClick(this, editInput);
            }
            dismiss();
        });

        buttonCancel.setOnClickListener(v -> dismiss());
        setCanceledOnTouchOutside(outSide);

        setContentView(inflate);
        Window dialogWindow = getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置Dialog从窗体中间弹出
        dialogWindow.setGravity(Gravity.CENTER);
    }

}
