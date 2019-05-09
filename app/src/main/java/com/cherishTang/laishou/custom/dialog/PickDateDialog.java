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
import com.cherishTang.laishou.custom.pickertime.PickTimeView;
import com.cherishTang.laishou.util.apiUtil.DateUtil;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.listener.OnDialogInterfaceClickListener;
import com.cherishTang.laishou.util.listener.OnPickDateDialogInterfaceClickListener;

import java.text.SimpleDateFormat;


/**
 * Created by 方舟 on 2017/1/14.
 * 确认弹框
 */

public class PickDateDialog extends Dialog implements PickTimeView.onSelectedChangeListener {
    private Context context;
    private PickTimeView pickDate;
    private View pickerView;
    private String sdfDate = "yyyy-MM-dd";
    private String tipMessage;
    private TextView submitBtn, tvSelected;
    private OnPickDateDialogInterfaceClickListener sureClickListener;
    private String defaultData;
    private int dateType = PickTimeView.TYPE_PICK_DATE;
    private long timeMillis = System.currentTimeMillis();

    public PickDateDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public PickDateDialog setOnSureClickListener(OnPickDateDialogInterfaceClickListener sureClickListener) {
        this.sureClickListener = sureClickListener;
        return this;
    }

    public PickDateDialog setDefaultData(String defaultData) {
        this.defaultData = defaultData;
        try {
            if (!StringUtil.isEmpty(defaultData)) {
                timeMillis = DateUtil.getDateByFormat(defaultData, sdfDate).getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public PickDateDialog setDateType(int dateType) {
        this.dateType = dateType;
        return this;
    }

    public PickDateDialog setTipMessage(String tipMessage) {
        this.tipMessage = tipMessage;
        return this;
    }

    public PickDateDialog setSimpleDateFormat(String sdfDate) {
        this.sdfDate = sdfDate;
        return this;
    }

    public PickDateDialog builder() {
        initView();
        return this;
    }

    private void initView() {
        pickerView = LayoutInflater.from(context).inflate(
                R.layout.picktime_view, null);
        pickDate = pickerView.findViewById(R.id.pickDate);
        submitBtn = pickerView.findViewById(R.id.submitBtn);
        tvSelected = pickerView.findViewById(R.id.Main_tvSelected);
        tvSelected.setText(StringUtil.isEmpty(tipMessage) ? "请选择时间" : tipMessage);
        submitBtn.setOnClickListener(view -> {
            if (sureClickListener != null)
                sureClickListener.onDialogClick(PickDateDialog.this, timeMillis);
        });
        pickDate.setViewType(PickTimeView.TYPE_PICK_DATE);
        if (!StringUtil.isEmpty(defaultData)) {
            pickDate.setTimeMillis(DateUtil.getDateByFormat(defaultData, sdfDate).getTime());
        }
        pickDate.setOnSelectedChangeListener(this);
        pickDate.setViewType(dateType);
        setContentView(pickerView); // 将布局设置给Dialog
        Window dialogWindow = getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);// 设置dialog宽度
        dialogWindow.setGravity(Gravity.BOTTOM);

    }

    @Override
    public void onSelected(PickTimeView view, long timeMillis) {
//        tvSelected.setText(DateUtil.getDateFromMillisNew(timeMillis));
        this.timeMillis = timeMillis;
    }
}
