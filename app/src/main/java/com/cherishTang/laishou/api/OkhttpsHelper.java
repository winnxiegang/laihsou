package com.cherishTang.laishou.api;

import android.app.Application;
import android.content.Context;

import com.cherishTang.laishou.okhttp.OkHttpUtils;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.okhttp.https.HttpsUtils;
import com.cherishTang.laishou.okhttp.log.LoggerInterceptor;
import com.cherishTang.laishou.util.apiUtil.DateUtil;
import com.cherishTang.laishou.util.apiUtil.RSAUtils;
import com.cherishTang.laishou.util.jiami.MD5Util;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by 方舟 on 2017/11/14.
 * OkHttp帮助类
 */

public class OkhttpsHelper {
    private static OkhttpsHelper okhttpsHelper;
    private static Application application;
    private static OkHttpClient client;

    public OkhttpsHelper(Application application) {
        this.application = application;
    }

    public static void init(Application application) {
        okhttpsHelper = new OkhttpsHelper(application);
        client = OkHttpUtils.getInstance().getOkHttpClient();
        HttpsUtils.SSLParams sslParams = null;
        try {
            sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sslParams == null) {
            client = OkHttpUtils.getInstance().getOkHttpClient();
        } else {
            client = new OkHttpClient.Builder()
                    .connectTimeout(15000L, TimeUnit.MILLISECONDS)
                    .readTimeout(15000L, TimeUnit.MILLISECONDS)
                    .addInterceptor(new LoggerInterceptor("TAG"))
                    .hostnameVerifier((hostname, session) -> true)
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .build();
        }
    }

    public static void get(String url, Map<String, String> map, Object TAG, StringCallback stringCallback) {
        try {

            Iterator entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                LogUtil.d("body----" + entry.getKey() + "：" + entry.getValue());
            }
            Map<String, String> headMap = setRequestTitleMap(application);

            OkHttpUtils
                    .get()//
                    .url(ApiHttpClient.getAbsoluteBaseUrl(url))//
                    .headers(headMap)
                    .params(map)
                    .tag(TAG)
                    .build()
                    .connTimeOut(15000)
                    .readTimeOut(15000)
                    .writeTimeOut(15000)
                    .execute(stringCallback);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(application, "服务器繁忙，请稍后再试！");
        }

    }

    public static void post(String url, String postJson, Object TAG, StringCallback stringCallback) {
        try {
            Map<String, String> headMap = setRequestTitleMap(application);
            LogUtil.show("postJson:" + postJson);
            OkHttpUtils
                    .postString()
                    .url(ApiHttpClient.getAbsoluteBaseUrl(url))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .headers(headMap)
                    .tag(TAG)
                    .content(postJson == null ? "" : postJson)
                    .build()
                    .connTimeOut(15000)
                    .readTimeOut(15000)
                    .writeTimeOut(15000)
                    .execute(stringCallback);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(application, "服务器繁忙，请稍后再试！");
        }
    }

    public static void postFile(String url, File file, Object TAG, StringCallback stringCallback) {
        try {
            Map<String, String> headMap = setRequestTitleMap(application);
            OkHttpUtils
                    .postFile()
                    .url(ApiHttpClient.getAbsoluteBaseUrl(url))
                    .headers(headMap)
                    .tag(TAG)
                    .build()
                    .connTimeOut(60000L)
                    .readTimeOut(60000L)
                    .writeTimeOut(60000L)
                    .execute(stringCallback);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(application, "服务器繁忙，请稍后再试！");
        }
    }
    public static void postFileWebForm(String url,String fileName, File file, Object TAG, StringCallback stringCallback) {
        try {
            Map<String, String> headMap = setRequestTitleMap(application);
            OkHttpUtils
                    .post()
                    .addFile("fileName", fileName, file)//
                    .url(ApiHttpClient.getAbsoluteBaseUrl(url))
                    .headers(headMap)
                    .tag(TAG)
                    .build()
                    .connTimeOut(60000L)
                    .readTimeOut(60000L)
                    .writeTimeOut(60000L)
                    .execute(stringCallback);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(application, "服务器繁忙，请稍后再试！");
        }
    }
    public static void cancelRequestTag(Object TAG) {
        OkHttpUtils.getInstance().cancelTag(TAG);
    }


    public static Map<String, String> setRequestTitleMap(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String stamp = System.currentTimeMillis() + "";
            String random = (long) (Math.random() * 10000000) + "";
            map.put("stamp", stamp);
            map.put("random", random);
            map.put("token", RSAUtils.enCode(context, stamp + random));
            map.put("auth", ApiHttpClient.getToken() == null ? "" : ApiHttpClient.getToken());

            Iterator entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                LogUtil.d("header----" + entry.getKey() + "：" + entry.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
