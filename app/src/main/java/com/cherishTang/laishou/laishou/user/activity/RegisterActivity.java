package com.cherishTang.laishou.laishou.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.RegexUtils;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.laishou.user.bean.RegisterBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.jiami.MD5Util;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by 方舟 on 2017/10/9.
 * d
 */

public class RegisterActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.phone_number_button)
    TextView phoneNumberButton;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private TimeCount time;
    CustomProgressDialog customProgressDialog = null;

    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.register;
    }


    @Override
    public void initView() {
        editTel.addTextChangedListener(this);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        phoneNumberButton.setClickable(false);
        phoneNumberButton.setTextColor(getResources().getColor(R.color.btn_textcolor_unselected));

    }

    @Override
    public void initData() {
    }

    public static void show(Activity context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivityForResult(intent,ConstantsHelper.REGISTER_SUCCESS);
    }

    //获取验证码倒计时
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            phoneNumberButton.setTextColor(getResources().getColor(R.color.themeColor));
            phoneNumberButton.setText("重新获取");
            phoneNumberButton.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            phoneNumberButton.setClickable(false);
            phoneNumberButton.setText("重新发送(" + millisUntilFinished / 1000 + ")秒");
            phoneNumberButton.setTextColor(getResources().getColor(R.color.btn_textcolor_unselected));
        }
    }

    @OnClick({R.id.phone_number_button, R.id.tv_login, R.id.register_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone_number_button:
                if (StringUtil.isEmpty(editTel.getText().toString())) {
                    ToastUtils.showShort(this, "您还没有输入手机号码");
                    return;
                }

                if (!RegexUtils.isMobile(editTel.getText().toString())) {
                    ToastUtils.showShort(this, "您输入的手机号码不正确");
                    return;
                }
                sendCode(editTel.getText().toString());
                break;
            case R.id.tv_login:
                LoginActivity.show(this);
                break;
            case R.id.register_submit:
                if (StringUtil.isEmpty(editTel.getText().toString())) {
                    ToastUtils.showShort(this, "您还没有输入手机号码");
                    return;
                }
                if (!RegexUtils.isMobile(editTel.getText().toString())) {
                    ToastUtils.showShort(this, "您输入的手机号码不正确");
                    return;
                }
                if (StringUtil.isEmpty(editCode.getText().toString())) {
                    ToastUtils.showShort(this, "您还没有输入验证码");
                    return;
                }
                if (StringUtil.isEmpty(editPassword.getText().toString())) {
                    ToastUtils.showShort(this, "请填写密码");
                    return;
                }
                if (editPassword.getText().toString().trim().toCharArray().length < 6) {
                    ToastUtils.showShort(this, "您输入的密码过短");
                    return;
                }

                try {
                    register(editTel.getText().toString(),
                            MD5Util.md5Encode(editPassword.getText().toString()),
                            editCode.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(this, R.string.error);
                }
                break;
        }
    }

    /**
     * 发送验证码
     *
     * @param tel
     */
    private void sendCode(String tel) {
        ApiHttpClient.getPhoneCode(new Gson().toJson(new RegisterBean(tel)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(RegisterActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在发送验证码，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(RegisterActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                ToastUtils.showShort(RegisterActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "发送成功" : resultBean.getMessage());
                if (resultBean.isSuccess()) {
                    time.start();
                }
            }
        });

    }

    /**
     * 注册
     *
     * @param tel
     */
    private void register(String tel, String password, String code) {

        ApiHttpClient.registerUser(new Gson().toJson(new RegisterBean(tel, password, code)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(RegisterActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在注册，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(RegisterActivity.this, R.string.requestError);
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    ToastUtils.showShort(RegisterActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "注册成功" : resultBean.getMessage());
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("result",true);
                    setResult(ConstantsHelper.REGISTER_SUCCESS,getIntent().putExtras(bundle));
                    finish();
                }else{
                    ToastUtils.showShort(RegisterActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "注册失败" : resultBean.getMessage());
                }
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (RegexUtils.isMobile(s.toString())) {
            phoneNumberButton.setTextColor(getResources().getColor(R.color.themeColor));
            phoneNumberButton.setClickable(true);
        } else {
            phoneNumberButton.setTextColor(getResources().getColor(R.color.register_edit_color));
            phoneNumberButton.setClickable(false);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}
