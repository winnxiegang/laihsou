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
import com.cherishTang.laishou.custom.pickertime.PickTimeView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 方舟 on 2017/10/18.
 * 单选弹框
 */

public class PickTimeViewDialog implements View.OnClickListener, PickTimeView.onSelectedChangeListener {

    public Context context;
    private Dialog customDialog;
    private long stTime = 0, enTime = 0, inTime = 0;
    private PickTimeView pickDate;
    SimpleDateFormat sdfDate;
    private TextView submitBtn, tvSelected;
    private View pickerView;
    private TextView textView;

    public PickTimeViewDialog(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    public void create() {
        customDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        pickerView = LayoutInflater.from(context).inflate(
                R.layout.picktime_view, null);
        pickDate = (PickTimeView) pickerView.findViewById(R.id.pickDate);//tvSelected
        submitBtn = (TextView) pickerView.findViewById(R.id.submitBtn);
        tvSelected = (TextView) pickerView.findViewById(R.id.Main_tvSelected);
        submitBtn.setOnClickListener(this);
        pickDate.setViewType(PickTimeView.TYPE_PICK_DATE);
        if (textView.getTag() != null && textView.getTag() instanceof Long) {
            pickDate.setTimeMillis((Long) textView.getTag());
        }
        sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        pickDate.setOnSelectedChangeListener(this);
        pickDate.setViewType(PickTimeView.TYPE_PICK_DATE);
        customDialog.setContentView(pickerView); // 将布局设置给Dialog
        Window dialogWindow = customDialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);// 设置dialog宽度
        dialogWindow.setGravity(Gravity.BOTTOM);
        customDialog.show();
    }


    @Override
    public void onSelected(PickTimeView view, long timeMillis) {
        stTime = timeMillis;
        String str = sdfDate.format(timeMillis);
        tvSelected.setText(str);
        tvSelected.setTag(timeMillis);
    }

    @Override
    public void onClick(View v) {
        if (customDialog.isShowing()) customDialog.dismiss();
        if (tvSelected.getText().toString() == null || tvSelected.getText().toString().equals("")) {
            textView.setText(sdfDate.format(Calendar.getInstance().getTimeInMillis()));
            textView.setTag(Calendar.getInstance().getTimeInMillis());
        } else {
            textView.setText(tvSelected.getText().toString());
            if (tvSelected.getTag() != null) textView.setTag(tvSelected.getTag().toString());
        }

    }
}
