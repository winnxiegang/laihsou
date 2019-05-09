package com.cherishTang.laishou.laishou.setting.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.laishou.activity.activity.HotDetailActivity;
import com.cherishTang.laishou.laishou.activity.bean.ActivityApplyPeopleRequestBean;
import com.cherishTang.laishou.laishou.setting.bean.ModifyPasswordBean;
import com.cherishTang.laishou.laishou.user.activity.DataRecordActivity;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.jiami.MD5Util;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/3/10.
 * describe
 */
public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.edit_old_password)
    EditText editOldPassword;
    @BindView(R.id.edit_new_password)
    EditText editNewPassword;
    @BindView(R.id.edit_new_password_again)
    EditText editNewPasswordAgain;
    private CustomProgressDialog customProgressDialog;
    @Override
    public String setTitleBar() {
        return "修改密码";
    }

    @Override
    public int initLayout() {
        return R.layout.modify_password_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.button_modify_password})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_modify_password:
                if (StringUtil.isEmpty(editOldPassword.getText().toString())) {
                    ToastUtils.showShort(this, "您还没有输入旧密码");
                    return;
                }
                if (StringUtil.isEmpty(editNewPassword.getText().toString())) {
                    ToastUtils.showShort(this, "您还没有输入新密码");
                    return;
                }
                if (editOldPassword.getText().toString().toCharArray().length < 6) {
                    ToastUtils.showShort(this, "新密码过短");
                    return;
                }
                if (StringUtil.isEmpty(editNewPasswordAgain.getText().toString())) {
                    ToastUtils.showShort(this, "请再次输入密码");
                    return;
                }
                if (!editNewPassword.getText().toString().equals(editNewPasswordAgain.getText().toString())) {
                    ToastUtils.showShort(this, "两次密码输入不一致");
                    return;
                }

                DialogHelper.getConfirmDialog(this, "确定修改密码吗？", (dialogInterface, i) -> {

                    try {
                        if (customProgressDialog == null || !customProgressDialog.isShowing())
                            customProgressDialog = new CustomProgressDialog(ModifyPasswordActivity.this, R.style.loading_dialog);
                        customProgressDialog.setMessage("正在加载，请稍后...");
                        customProgressDialog.show();
                        modifyPassword(MD5Util.md5Encode(editOldPassword.getText().toString()),
                                MD5Util.md5Encode(editNewPassword.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).show();
                break;
        }
    }

    private void modifyPassword(String oldPassword,String newPassword) {
        ApiHttpClient.modifyPassword(new Gson().toJson(new ModifyPasswordBean(oldPassword, newPassword)), this, new StringCallback() {

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(ModifyPasswordActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().fromJson(s,
                        new TypeToken<ResultBean<Object>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    UserAccountHelper.saveUserMessage(UserAccountHelper.getAccount(),newPassword);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    errorMsgShow(resultBean.getCode() + "", resultBean.getMessage(), ConstantsHelper.REQUEST_ERROR_LOGIN);
                }
            }
        });
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, ModifyPasswordActivity.class);
        context.startActivity(intent);
    }


}
