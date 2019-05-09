package com.cherishTang.laishou.custom.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.networkTools.NetworkStateUtil;

/**
 * Created by 方舟 on 2017/12/13.
 * 更新弹框
 */

public class UpdateMessageDialog implements View.OnClickListener {
    private Context mContext;
    private String updateMsgString, versionName;
    public OnUpdateListener onUpdateListener;
    private Button updateBtn;
    private TextView updateTitle, updateMsg;
    private Dialog customDialog;

    public UpdateMessageDialog(Context context, String versionName, String updateMsgString) {
        this.mContext = context;
        this.versionName = versionName;
        this.updateMsgString = updateMsgString;
    }

    public void create() {
        customDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(
                R.layout.update_dialog, null);
        Button updateBtn = view.findViewById(R.id.updateBtn);
        TextView updateTitle = view.findViewById(R.id.updateTitle);
        TextView updateMsg = view.findViewById(R.id.updateMsg);
        updateMsg.setText(StringUtil.isEmpty(updateMsgString) ? "暂无更新内容" : updateMsgString);
        updateTitle.setText("是否更新到" + versionName + "版本");
        updateBtn.setOnClickListener(this);
        customDialog.setContentView(view);
        customDialog.setCancelable(false);//不可取消
        Window dialogWindow = customDialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.CENTER);
        customDialog.show();
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    private void showMessageDialog(String message, final View v) {
        DialogHelper.getConfirmDialog(mContext, message,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onUpdateListener != null) onUpdateListener.onUpdate(v);
                    }
                }).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateBtn:
                if (customDialog != null && customDialog.isShowing()) customDialog.dismiss();
                if (NetworkStateUtil.isMobileConnected(mContext)) {
                    showMessageDialog("您正在使用数据流量，确定继续下载吗？", v);
                } else {
                    if (onUpdateListener != null) onUpdateListener.onUpdate(v);
                }
                break;
        }
    }

    public interface OnUpdateListener {
        void onUpdate(View v);
    }
}
