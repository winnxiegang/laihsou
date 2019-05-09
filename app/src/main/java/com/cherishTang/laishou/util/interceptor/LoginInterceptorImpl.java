package com.cherishTang.laishou.util.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.util.log.LogUtil;

/**
 * Created by 方舟 on 2018/11/14.
 * 描述: 登录拦截器
 */
@Interceptor(name = "login", priority = 4)
public class LoginInterceptorImpl implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();

        if (UserAccountHelper.isLogin()) { // 如果已经登录不拦截
            callback.onContinue(postcard);
        } else {  // 如果没有登录
            switch (path) {
                // 不需要登录的直接进入这个页面
                case ConstantsHelper.Login:
//                case ConfigConstants.FIRST_PATH:
                    callback.onContinue(postcard);
                    break;
                default:
                    callback.onInterrupt(null);
                    // 需要登录的直接拦截下来
                    break;
            }
        }

    }

    @Override
    public void init(Context context) {
        LogUtil.d("路由登录拦截器初始化成功"); //只会走一次
    }

}
