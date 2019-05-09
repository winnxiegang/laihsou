package com.cherishTang.laishou.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by 方舟 on 2017/11/23.
 *
 */

public class PrivacyPolicyActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.webView)
    WebView webView;

    @Override
    public String setTitleBar() {
        return "用户使用协议";
    }

    @Override
    public int initLayout() {
        return R.layout.privacypolicy_webview;
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, PrivacyPolicyActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        //不跳转到其他浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
        //允许webview对文件的操作
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        //支持JS
        settings.setJavaScriptEnabled(true);
        //加载本地html文件
        webView.loadUrl("file:///android_asset/protocol.html");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_backward:
                finish();
                break;
        }
    }
}
