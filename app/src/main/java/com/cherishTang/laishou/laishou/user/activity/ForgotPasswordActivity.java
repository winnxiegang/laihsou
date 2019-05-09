package com.cherishTang.laishou.laishou.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.RegexUtils;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.laishou.user.bean.RegisterBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.jiami.MD5Util;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by 方舟 on 2019/2/22.
 * 修改密码
 */

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.modify_submit)
    Button modifySubmit;
    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.phone_number_button)
    TextView phoneNumberButton;
    @BindView(R.id.edit_password)
    EditText editPassword;
    private TimeCount time;
    private CustomProgressDialog customProgressDialog;

    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.modify_password;
    }

    @Override
    public void initView() {
        phoneNumberButton.setClickable(false);
        phoneNumberButton.setTextColor(getResources().getColor(R.color.btn_textcolor_unselected));
        editTel.addTextChangedListener(this);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }

    @Override
    public void initData() {

    }

    private void timeStrart() {
        time.start();
//        phoneNumberButton.setBackgroundResource(R.drawable.rounded_num_button_clicked);
    }

    //开启单利模式
    public static void show(Activity context) {
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        context.startActivityForResult(intent,ConstantsHelper.MODIFY_PASSWORD_SUCCESS);
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

    @OnClick({R.id.modify_submit, R.id.phone_number_button})
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.modify_submit:
                if (StringUtil.isEmpty(editTel.getText().toString())) {
                    ToastUtils.showShort(this, "您还没有输入手机号码");
                    return;
                }
                if (!RegexUtils.isMobile(editTel.getText().toString().trim())) {
                    ToastUtils.showShort(this, "您输入的手机号码不正确");
                    return;
                }

                if (StringUtil.isEmpty(editCode.getText().toString())) {
                    ToastUtils.showShort(this, "您还没有输入验证码");
                    return;
                }
                if (editPassword.getText().toString().trim().toCharArray().length < 6) {
                    ToastUtils.showShort(this, "您输入的密码过短");
                    return;
                }
                try {
                    forgetPassword(editTel.getText().toString(),
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
     * 修改密码
     *
     * @param tel
     */
    private void forgetPassword(String tel, String password, String code) {

        ApiHttpClient.forgetPassword(new Gson().toJson(new RegisterBean(tel, password, code)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(ForgotPasswordActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在修改密码，请稍后...");
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
                ToastUtils.showShort(ForgotPasswordActivity.this, R.string.requestError);
            }

            @Override
            public void onResponse(String s, int id) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(s, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    ToastUtils.showShort(ForgotPasswordActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "修改成功" : resultBean.getMessage());
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("result",true);
                    setResult(ConstantsHelper.MODIFY_PASSWORD_SUCCESS,getIntent().putExtras(bundle));
                    ForgotPasswordActivity.this.finish();
                }else{
                    ToastUtils.showShort(ForgotPasswordActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "修改失败" : resultBean.getMessage());
                }
            }
        });

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
                    customProgressDialog = new CustomProgressDialog(ForgotPasswordActivity.this, R.style.loading_dialog);
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
                ToastUtils.showShort(ForgotPasswordActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                ToastUtils.showShort(ForgotPasswordActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "发送成功" : resultBean.getMessage());
                if (resultBean.isSuccess()) {
                    time.start();
                }
            }
        });

    }
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
