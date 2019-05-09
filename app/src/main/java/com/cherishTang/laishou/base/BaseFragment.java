package com.cherishTang.laishou.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cherishTang.laishou.api.OkhttpsHelper;
import com.cherishTang.laishou.laishou.user.activity.LoginActivity;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.bean.Code.ResponseCode;
import com.cherishTang.laishou.custom.dialog.LoginDialog;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.permission.PermissionsChecker;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 方舟 on 2017/11/22.
 * BaseFragment封装
 */

public abstract class BaseFragment extends Fragment implements LoginDialog.OnLoginClickListener {
    protected Activity mActivity;
    public LoginDialog loginDialog = null;

    // 所需的全部权限
    static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器

    protected Unbinder unbinder;

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public void onLoginClick(View v, int code) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, code);
    }

    public void requestPermission(int reqCode, Activity context, String[] PERMISSIONS) {
        if (mPermissionsChecker == null) mPermissionsChecker = new PermissionsChecker(mActivity);
        BaseFragment.PERMISSIONS = PERMISSIONS;
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity(reqCode, context);
        }
    }

    //权限检测
    private void startPermissionsActivity(int reqCode, Activity context) {
        PermissionsActivity.startActivityForResult(context, reqCode, PERMISSIONS);
    }

    public boolean checkPermission(String[] PERMISSIONS) {
        if (mPermissionsChecker == null)
            mPermissionsChecker = new PermissionsChecker(getActivity());
        BaseFragment.PERMISSIONS = PERMISSIONS;
        return mPermissionsChecker.lacksPermissions(PERMISSIONS);
    }

    public void errorMsgShow(String code, String msg, int requestCode) {
        switch (code) {
            case ResponseCode.LOGIN_PAST:
                showLoginDialog(getActivity(), this, "登录已过期，请重新登录", requestCode);
                break;
            case ResponseCode.UNLOGIN:
                showLoginDialog(getActivity(), this, "您还没有登录，请前往登录", requestCode);
                break;
            case ResponseCode.TOKEN_ERROR:
                showLoginDialog(getActivity(), this, "登录信息异常，请重新登录", requestCode);
                break;
            default:
                ToastUtils.showShort(getActivity(), msg == null ? "加载失败，请稍后再试！" : msg);
                break;
        }
    }

    /**
     * 弹出登录dialog
     *
     * @param context
     * @param message
     * @param requestCode
     */
    public void showLoginDialog(Context context, LoginDialog.OnLoginClickListener listener, String message, int requestCode) {
        BaseActivity baseActivity = (BaseActivity) getActivity();
        baseActivity.showLoginDialog(context, listener, message, requestCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        View view = LayoutInflater.from(mActivity)
                .inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkhttpsHelper.cancelRequestTag(this);
        if (unbinder != null)
            unbinder.unbind();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(getArguments() == null ? new Bundle() : getArguments());
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 该抽象方法就是 初始化view
     *
     * @param view
     * @param savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 执行数据的加载
     */
    protected abstract void initData(Bundle bundle);


    public long lastClick = 0;

    /**
     * [防止快速点击]
     *
     * @return
     */
    public boolean fastClick(long intervalTime) {
        if (System.currentTimeMillis() - lastClick <= intervalTime) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

}
