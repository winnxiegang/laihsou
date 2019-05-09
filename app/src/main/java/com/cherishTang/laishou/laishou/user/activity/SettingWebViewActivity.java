package com.cherishTang.laishou.laishou.user.activity;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.WebViewActivity;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.params.DataHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by 方舟 on 2017/11/28.
 */

public class SettingWebViewActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Bundle bundle;
    private String contents;

    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.base_webview;
    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, SettingWebViewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void initWebView() {
        //设置Web视图
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        contents = bundle.getString("loadUrl");
        tvTitle.setText(bundle.getString("title",""));
        if(!StringUtil.isEmpty(contents)){
            webView.loadUrl(contents);
        }
    }

    //Web视图
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    //Web视图
    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO 自动生成的方法存根
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);//加载完网页进度条消失
            } else {
                progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                progressBar.setProgress(newProgress);//设置进度值
            }
        }
    }
    @Override
    public void initView() {
        initWebView();
    }

    @Override
    public void initData() {

    }


    @OnClick({})
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
