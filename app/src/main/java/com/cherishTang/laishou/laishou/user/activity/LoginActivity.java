package com.cherishTang.laishou.laishou.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.RegexUtils;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.enumbean.LoginTypeEnum;
import com.cherishTang.laishou.laishou.user.bean.LoginResultBean;
import com.cherishTang.laishou.laishou.user.bean.RegisterBean;
import com.cherishTang.laishou.laishou.user.bean.UserInfo;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.jiami.MD5Util;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by 方舟 on 2017/5/23.
 * 登录
 */
@Route(path = ConstantsHelper.Login)
public class LoginActivity extends BaseActivity implements TextWatcher, View.OnClickListener {

    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.phone_number_button)
    TextView phoneNumberButton;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    private TimeCount time;
    CustomProgressDialog customProgressDialog = null;
    @Autowired
    public String path;
    @Override
    public String setTitleBar() {
        return null;
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    @Override
    public int initLayout() {
        return R.layout.login;
    }


    @Override
    public void initView() {
        editTel.addTextChangedListener(this);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        phoneNumberButton.setOnClickListener(this);
        phoneNumberButton.setClickable(false);
        if (UserAccountHelper.getAccount() != null)
            editTel.setText(UserAccountHelper.getAccount());
    }

    @Override
    public void initData() {
    }


    /**
     * show the login activity
     *
     * @param context context
     */
    public static void show(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivityForResult(intent, ConstantsHelper.REQUEST_ERROR_LOGIN);
    }

    public static void show(Fragment context) {
        Intent intent = new Intent(context.getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivityForResult(intent, ConstantsHelper.REQUEST_ERROR_LOGIN);
    }

    @OnClick({R.id.tv_forgot_password, R.id.login_submit, R.id.phone_number_button, R.id.tv_register})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forgot_password:
                ForgotPasswordActivity.show(this);
                break;
            case R.id.tv_register:
                RegisterActivity.show(this);
                break;
            case R.id.login_submit:
                String username = editTel.getText().toString();
                String password = editCode.getText().toString();
                if (StringUtil.isEmpty(username)) {
                    ToastUtils.showShort(LoginActivity.this, "手机号码为空");
                    return;
                }
                if (!RegexUtils.isMobile(editTel.getText().toString())) {
                    ToastUtils.showShort(this, "您输入的手机号码不正确");
                    return;
                }
                if (StringUtil.isEmpty(password)) {
                    ToastUtils.showShort(LoginActivity.this, "密码为空");
                    return;
                }
                try {
                    InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    login(editTel.getText().toString(), MD5Util.md5Encode(editCode.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(this, R.string.error);
                }

                break;
            case R.id.phone_number_button:
                //判断未填写
                if (StringUtil.isEmpty(editTel.getText().toString())) {
                    ToastUtils.showShort(this, "您还没有输入内容");
                    return;
                }
                //判断号段存在不存在
                if (!RegexUtils.isMobile(editTel.getText().toString().trim())) {
                    ToastUtils.showShort(this, "您输入的手机号段不存在或位数不正确");
                    return;
                }
                break;
        }
    }

    /**
     * 登录
     *
     * @param tel
     * @param password
     */
    private void login(String tel, String password) {

        ApiHttpClient.login(new Gson().toJson(new RegisterBean(tel, password)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(LoginActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在登录，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
                ToastUtils.showShort(LoginActivity.this, R.string.requestError);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    ResultBean<LoginResultBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                            fromJson(response, new TypeToken<ResultBean<LoginResultBean>>() {
                            }.getType());
                    if (resultBean.isSuccess() && resultBean.getData() != null) {
                        ToastUtils.showShort(LoginActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "登录成功" : resultBean.getMessage());
                        UserAccountHelper.saveUserMessage(tel, password);
                        UserAccountHelper.saveLoginType(resultBean.getData().getLoginType() == null ? LoginTypeEnum.members.getIndex() : resultBean.getData().getLoginType().getIndex());
                        ApiHttpClient.setToken(resultBean.getData().getToken());
                        if (resultBean.getData().getLoginType() != null && resultBean.getData().getLoginType().getIndex() == LoginTypeEnum.consultant.getIndex()) {
                            ApiHttpClient.getConsultantUserInfo(null, this, stringCallback);
                        } else {
                            ApiHttpClient.getMemberUserInfo(null, this, stringCallback);
                        }
                    } else {
                        if (customProgressDialog != null && customProgressDialog.isShowing())
                            customProgressDialog.dismiss();
                        ToastUtils.showShort(LoginActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "登录失败" : resultBean.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (customProgressDialog != null && customProgressDialog.isShowing())
                        customProgressDialog.dismiss();
                    ToastUtils.showShort(LoginActivity.this, R.string.error);
                }

            }
        });

    }


    /**
     * 获取用户信息
     */

    StringCallback stringCallback = new StringCallback() {
        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
            if (customProgressDialog != null || customProgressDialog.isShowing())
                customProgressDialog.setMessage("正在加载，请稍后...");
        }

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
            if (customProgressDialog != null && customProgressDialog.isShowing())
                customProgressDialog.dismiss();
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtils.showShort(LoginActivity.this, R.string.requestError);
        }

        @Override
        public void onResponse(String response, int id) {
            ResultBean<UserInfo> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                    fromJson(response, new TypeToken<ResultBean<UserInfo>>() {
                    }.getType());
            if (resultBean.isSuccess()) {
                ToastUtils.showShort(LoginActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "登录成功" : resultBean.getMessage());
                UserAccountHelper.saveLoginState(resultBean.getData(), true);
                setBackResult(true);
            } else {
                ToastUtils.showShort(LoginActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "登录失败" : resultBean.getMessage());
            }
        }
    };


    private void timeStart() {
        time.start();
        phoneNumberButton.setTextColor(getResources().getColor(R.color.btn_textcolor_unselected));
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


    private void setBackResult(boolean resultFlag) {
        Bundle bundle = new Bundle();
        LogUtil.show("path:" + path);
        bundle.putBoolean("result", resultFlag);
        if (!StringUtil.isEmpty(path)) {
            ARouter.getInstance().build(path)
                    .with(bundle)
                    .navigation();
        } else {
            setResult(ConstantsHelper.LOGIN_SUCCESS, this.getIntent().putExtras(bundle));
        }
        finish();
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

    //点击返回键提示是否退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            CommonUtils.Exit(this);
//            return true;
            setBackResult(false);
        }
        return super.onKeyDown(keyCode, event);
    }

}
