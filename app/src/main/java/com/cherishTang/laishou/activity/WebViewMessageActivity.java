package com.cherishTang.laishou.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cherishTang.laishou.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方舟 on 2017/6/23
 */

public class WebViewMessageActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.button_backward)
    TextView buttonBackward;
    @BindView(R.id.button_forward)
    TextView buttonForward;
    @BindView(R.id.iv_logo_setting)
    ImageView ivLogoSetting;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_message);
        ButterKnife.bind(this);
        initView();
    }

    //布局初始化
    private void initView() {
        buttonBackward.setVisibility(View.VISIBLE);
        buttonBackward.setOnClickListener(this);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        Intent intent = getIntent();
        url = intent.getStringExtra("loadUrl");
        textTitle.setText(intent.getStringExtra("title_text"));
        webView.loadUrl(url);
        //设置Web视图
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    /**
     * show the MainActivity
     *
     * @param context context
     */
    public static void show(Context context, String loadUrl, String title_text) {
        Intent intent = new Intent(context, WebViewMessageActivity.class);
        intent.putExtra("loadUrl", loadUrl);
        intent.putExtra("title_text", title_text);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_backward:
                finish();
                break;
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
            if(newProgress==100){
                progressBar.setVisibility(View.GONE);//加载完网页进度条消失
            }
            else{
                progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                progressBar.setProgress(newProgress);//设置进度值
            }
        }
    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO 自动生成的方法存根
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            if(webView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                webView.goBack();
                return true;
            }
            else {//当webview处于第一页面时,直接退出程序
               finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
