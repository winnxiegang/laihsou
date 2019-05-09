package com.cherishTang.laishou.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.bean.PopupWindowBean;
import com.cherishTang.laishou.custom.pickertime.PickValueView;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.LogUtil;

import java.util.List;

/**
 * Created by 方舟 on 2017/10/18.
 * 单选弹框
 */

public class PickStringDialog implements View.OnClickListener, PickValueView.onSelectedChangeListener {

    public Context context;
    private Dialog customDialog;
    private TextView submitBtn, tvSelected;
    private View pickerView;
    private LinearLayout pvLayout;
    private PickValueView pickString;
    private OnPickSelectedListener onPickSelectedListener;

    public PickStringDialog(Context context, OnPickSelectedListener onPickSelectedListener) {
        this.context = context;
        this.onPickSelectedListener = onPickSelectedListener;
    }

    public Dialog create() {
        customDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        pickerView = LayoutInflater.from(context).inflate(
                R.layout.numberpicker, null);
        pvLayout = pickerView.findViewById(R.id.Main_pvLayout);
        tvSelected = pickerView.findViewById(R.id.Main_tvSelected);
        pickString = pickerView.findViewById(R.id.pickString);
        pickString.setVisibility(View.VISIBLE);

        tvSelected.setHint("请选择pk天数");

        pickString.setOnSelectedChangeListener(this);

        submitBtn = pickerView.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);
        initPickData();
        customDialog.setContentView(pickerView); // 将布局设置给Dialog
        Window dialogWindow = customDialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);// 设置dialog宽度
        dialogWindow.setGravity(Gravity.BOTTOM);
        return customDialog;
    }

    private void initPickData() {
        String[] strs = new String[365];
        for (int i = 0; i < strs.length; i++) {
            strs[i] = (i + 1) + "";
        }
        pickString.setValueData(strs, strs[2]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                if (customDialog != null && customDialog.isShowing()) customDialog.dismiss();
                if (onPickSelectedListener != null) {
                    onPickSelectedListener.pickSelected(this, StringUtil.isEmpty(tvSelected.getText().toString()) ? "2" : tvSelected.getText().toString());
                }
                break;
        }
    }

    @Override
    public void onSelected(PickValueView view, Object leftValue, Object middleValue, Object rightValue) {
        if (leftValue != null) {
            tvSelected.setText(leftValue.toString());
        }
    }

    public interface OnPickSelectedListener {
        void pickSelected(PickStringDialog mCurrentPick, String selectStr);
    }
}
