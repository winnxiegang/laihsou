package com.cherishTang.laishou.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.AppManager;
import com.cherishTang.laishou.api.AppSettingHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.custom.lockView.LockView;
import com.cherishTang.laishou.util.jiami.MD5Util;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 方舟 on 2017/6/29.
 * 九宫格手势密码
 */

public class LockViewActivity extends BaseActivity implements LockView.OnDrawFinishedListener, View.OnClickListener {

    //    @BindView(R.id.iv_logo_setting)
//    ImageView ivLogoSetting;
    @BindView(R.id.lockView)
    LockView lockView;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.lockViewTips)
    TextView lockViewTips;
    private String lockStr;
    private boolean isSet = false;

    @Override
    public String setTitleBar() {
        return "手势密码";
    }

    @Override
    public int initLayout() {
        return R.layout.lockview;
    }

    @Override
    public void initData() {
        if (getIntent().getExtras() != null)
            isSet = getIntent().getExtras().getBoolean("isSet", false);
    }

    public static void show(Activity context) {
        Intent intent = new Intent(context, LockViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivityForResult(intent, 3);
    }

    //初始化布局
    public void initView() {
        if (AppSettingHelper.getGestures() == null)
            tips.setText("您还未设置手势密码，请绘制手势密码");
        else tips.setText("请绘制手势密码");
        lockView.setOnDrawFinishedListener(this);
    }

    //当绘制完成后调用此方法，用来判断成功还是失败
    @Override
    public boolean onDrawFinished(List<Integer> passPositions) {
        StringBuilder sb = new StringBuilder();
        for (Integer i :
                passPositions) {
            sb.append(i.intValue());
        }
        // 把字符串md5加密后存入本地
        String md5Str = "";
        try {
            md5Str = MD5Util.md5Encode(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 比较路径是否一致
//                boolean valid = comparePath(sb.toString());
        boolean valid = false;

        if (AppSettingHelper.getGestures() == null) {
            if (isShort(sb.toString())) return false;
            if (lockStr == null) {
                lockStr = md5Str;
                valid = true;
                setTips("请再次绘制密码", Color.WHITE);
            } else {
                if (lockStr.equals(md5Str)) {
                    AppSettingHelper.setGestures(md5Str);
                    valid = true;
                    setTips("手势密码设置成功", Color.WHITE);
                    setResultBack(RESULT_OK, true);
                } else {
                    setTips("手势密码错误，请重新绘制", Color.RED);
                    lockStr = null;
                    valid = false;
                }
            }
        } else {
            if (AppSettingHelper.getGestures() != null && AppSettingHelper.getGestures().equals(md5Str)) {
                setResultBack(RESULT_OK, true);
                valid = true;
            } else {
                valid = false;
                setTips("手势密码错误，请重新绘制", Color.RED);
            }
        }
        lockView.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lockView.resetPoints();
                lockView.setClickable(true);
            }
        }, 300);
        return valid;
    }

    private void setTips(String tipsMsg, int color) {
        tips.setText(tipsMsg);
        tips.setTextColor(color);
    }

    private boolean isShort(String lock) {
        if (lock.length() < 4) {
            setTips("最少链接4个点，请重新设置", Color.RED);
            lockView.resetPoints();
            return true;
        }
        return false;
    }

    //设置返回结果，在启动此activity的OnActivityResult中接受结果
    private void setResultBack(int resultCode, boolean res) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("result", res);
        setResult(resultCode, getIntent().putExtras(bundle));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (AppSettingHelper.getGesturesIsOpen() && !isSet)
                AppManager.getAppManager().AppExit(this);
            setResultBack(RESULT_CANCELED, false);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @OnClick({})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_backward:
                if (AppSettingHelper.getGesturesIsOpen() && !isSet)
                    AppManager.getAppManager().AppExit(this);
                setResultBack(RESULT_CANCELED, false);
                break;
        }
    }
}
