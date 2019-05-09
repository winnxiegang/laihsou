package com.cherishTang.laishou.okhttp.builder;

import com.cherishTang.laishou.okhttp.OkHttpUtils;
import com.cherishTang.laishou.okhttp.request.OtherRequest;
import com.cherishTang.laishou.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
