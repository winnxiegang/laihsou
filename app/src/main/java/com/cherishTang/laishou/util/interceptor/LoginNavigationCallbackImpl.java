package com.cherishTang.laishou.util.interceptor;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.util.log.LogUtil;


/**
 * Created by 方舟 on 2018/11/14.
 * 描述: 登录拦截器
 */

public class LoginNavigationCallbackImpl implements NavigationCallback {
    @Override
    public void onFound(Postcard postcard) {
        //找到
    }

    @Override
    public void onLost(Postcard postcard) {
        //丢失
    }

    @Override
    public void onArrival(Postcard postcard) {
        //跳转成功
    }

    @Override
    public void onInterrupt(Postcard postcard) {
        String path = postcard.getPath();
        LogUtil.show("路由："+path);
        Bundle bundle = postcard.getExtras();
        // 拦截了
        ARouter.getInstance().build(ConstantsHelper.Login)
                .with(bundle)
                .withString(ConstantsHelper.INTENT_PATH, path)
                .navigation();
    }
}
