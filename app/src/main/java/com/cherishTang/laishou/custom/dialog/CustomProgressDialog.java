package com.cherishTang.laishou.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cherishTang.laishou.R;

/**
 * Created by 方舟 on 2017/11/2.
 * 自定义加载dialog
 */

public class CustomProgressDialog extends Dialog {
    private TextView tipTextView = null;
    private ImageView spaceshipImage = null;
    public CustomProgressDialog(Context context) {
        super(context,R.style.loading_dialog);
        createLoadingDialog(context,false);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        createLoadingDialog(context,true);
    }
    /*
     * 得到自定义的progressDialog
     * @param context 视图
     */
//    CustomProgressDialog  customProgressDialog = new CustomProgressDialog(this,R.style.loading_dialog);
//    customProgressDialog.setMessage("正在退出登录...");
//    customProgressDialog.show();
//    if(customProgressDialog.isShowing()) customProgressDialog.dismiss();

    private void createLoadingDialog(Context context,boolean loading) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        spaceshipImage = (ImageView) v.findViewById(R.id.img);
        tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
       if(loading) {
           Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                   context, R.anim.load_animation);
           spaceshipImage.startAnimation(hyperspaceJumpAnimation);
       }

        tipTextView.setText("正在加载中...");// 设置加载信息
        setCancelable(false);// 不可以用“返回键”取消
        setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void setMessage(String message) {
        if (tipTextView != null)
            tipTextView.setText(message);
    }
    public void setImgResource(int imgId) {
        if (spaceshipImage != null)
            spaceshipImage.setImageResource(imgId);
    }

}
