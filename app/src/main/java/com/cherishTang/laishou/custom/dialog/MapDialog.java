package com.cherishTang.laishou.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.cherishTang.laishou.R;
import com.cherishTang.laishou.util.apiUtil.OtherUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by fangzhou on 2017/1/14.
 * 地图导航dialog
 */

public class MapDialog implements View.OnClickListener {
    @BindView(R.id.baiduMap)
    Button baiduMap;
    @BindView(R.id.gaodeMap)
    Button gaodeMap;
    @BindView(R.id.tencentMap)
    Button tencentMap;
    @BindView(R.id.googleMap)
    Button googleMap;
    @BindView(R.id.cancelBtn)
    Button cancelBtn;
    private Context context;
    private Dialog dialog;
    private static MapDialog mapDialog;

    private View dialogView;
    private OnNavigationMapListener onNavigationMapListener;

    public void setOnNavigationMapListener(OnNavigationMapListener onNavigationMapListener) {
        this.onNavigationMapListener = onNavigationMapListener;
    }

    public interface OnNavigationMapListener {
        void onNavigationClick(View v, int position);
    }

    public static MapDialog getInstance() {
        if (mapDialog == null)
            mapDialog = new MapDialog();
        return mapDialog;
    }

    public Dialog create(Context context) {
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialogView = LayoutInflater.from(context).inflate(R.layout.map_dialog, null);
        ButterKnife.bind(this, dialogView);

        if (mapIsAvilible(context) == 4) {
            cancelBtn.setText("没有找到可用导航软件");
        }
        dialog.setContentView(dialogView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 30;// 设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        return dialog;
    }

    private int mapIsAvilible(Context context) {
        int count = 0;
        if (!OtherUtil.isAvilible(context, "com.baidu.BaiduMap")) {
            baiduMap.setVisibility(View.GONE);
            count++;
        }
        if (!OtherUtil.isAvilible(context, "com.autonavi.minimap")) {
            gaodeMap.setVisibility(View.GONE);
            count++;
        }
        if (!OtherUtil.isAvilible(context, "com.google.android.apps.maps")) {
            googleMap.setVisibility(View.GONE);
            count++;
        }
        if (!isHaveTencentMap()) {
            tencentMap.setVisibility(View.GONE);
            count++;
        }
        return count;
    }

    /***
     * 是否安装腾讯地图
     * @return
     */
    public boolean isHaveTencentMap() {
        try {
            if (!new File("/data/data/" + "com.tencent.map").exists()) {
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @OnClick({R.id.tencentMap, R.id.baiduMap, R.id.googleMap, R.id.gaodeMap, R.id.cancelBtn})
    public void onClick(View view) {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        switch (view.getId()) {
            case R.id.baiduMap:
                if (onNavigationMapListener != null)
                    onNavigationMapListener.onNavigationClick(view, 0);
                break;
            case R.id.googleMap:
                if (onNavigationMapListener != null)
                    onNavigationMapListener.onNavigationClick(view, 1);
                break;
            case R.id.tencentMap:
                if (onNavigationMapListener != null)
                    onNavigationMapListener.onNavigationClick(view, 2);
                break;
            case R.id.gaodeMap:
                if (onNavigationMapListener != null)
                    onNavigationMapListener.onNavigationClick(view, 3);
                break;

        }
    }
}
