package com.cherishTang.laishou.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.AppHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.util.jiami.Base64;
import com.cherishTang.laishou.util.log.ToastUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 方舟 on 2017/10/17.
 * 二维码解析
 */

public class MessageActivity extends BaseActivity {
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.frameWebView)
    FrameLayout frameWebView;
    private Bundle bundleData;
    private CustomProgressDialog customProgressDialog;
    private String result;

    @Override
    public String setTitleBar() {
        return "扫描信息";
    }

    @Override
    public int initLayout() {
        return R.layout.message;
    }

    private void decodeData() {
        if (result == null) {
            ToastUtils.showShort(this, "二维码解析失败");
            finish();
            return;
        }
        try {
            if (Patterns.WEB_URL.matcher(result).matches() || URLUtil.isValidUrl(result)) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(result);
                intent.setData(content_url);
                startActivity(intent);
                return;
            }
            JSONObject jsonObject = new JSONObject(new String(Base64.decode(result), "utf-8"));
            String jsonRes = jsonObject.getString("type");
            if (jsonObject.has("type")) {
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                AppHelper.showMessageActivity(this, bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,
                    "图片解析失败", Toast.LENGTH_SHORT).show();
        }finally {
            if(customProgressDialog!=null&&customProgressDialog.isShowing())customProgressDialog.dismiss();
            finish();
        }


    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void initData() {
        bundleData = getIntent().getExtras();
        result = bundleData.getString("result", null);
        decodeData();
    }

    public void initView() {
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //设置Web视图
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        customProgressDialog = new CustomProgressDialog(this, R.style.loading_dialog);
        customProgressDialog.setMessage("正在解析二维码，请稍后...");
        customProgressDialog.show();
    }

    @OnClick()
    public void OnClick(View view) {
        finish();
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

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO 自动生成的方法存根
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                webView.goBack();
                return true;
            } else {//当webview处于第一页面时,直接退出程序
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
