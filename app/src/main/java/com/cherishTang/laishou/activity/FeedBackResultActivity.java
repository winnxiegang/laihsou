package com.cherishTang.laishou.activity;

import android.content.Context;
import android.content.Intent;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseActivity;

/**
 * Created by CherishTang on 2019/3/10.
 * describe
 */
public class FeedBackResultActivity extends BaseActivity {
    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.feedback_result_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
    public static void show(Context context) {
        Intent intent = new Intent(context, FeedBackResultActivity.class);
        context.startActivity(intent);
    }
}
