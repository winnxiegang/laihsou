package com.cherishTang.laishou.laishou.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseActivity;

/**
 * Created by CherishTang on 2019/3/8.
 * describe：支付结果
 */
public class PayResultActivity extends BaseActivity {

    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.pay_result_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PayResultActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
