package com.cherishTang.laishou.laishou.club.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.laishou.club.bean.DarenDetalBean;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.ViewHelper;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

public class OnlyShowWebViewActivity extends BaseActivity {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView mWebView;
    private String id;
    private String title;
    private Bundle bundle;
    private String image;
    private CustomProgressDialog customProgressDialog;
    private String contents;

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, OnlyShowWebViewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String setTitleBar() {
        return "达人详情";
    }

    @Override
    public int initLayout() {
        return R.layout.activity_only_show_web_view;
    }

    @Override
    public void initView() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        id = bundle.getString("id");
        title = bundle.getString("title");
    }

    @Override
    public void initData() {
        getPeople(id);
    }

    public void initWebView(String contents) {
        ViewHelper.setConfigCallback((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
        ViewHelper.setWebViewAttribute(mWebView);
        //数据加载进度提示
        mWebView.loadData(contents, "text/html; charset=UTF-8", null);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小
        webSettings.setSupportZoom(true);  //支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        //启用支持javascript
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                // 处理自定义scheme
                if (!url.startsWith("http")) {
                    try {
                        // 以下固定写法
                        final Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    } catch (Exception e) {
                        // 防止没有安装的情况
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

        });


    }

    /**
     * 邀请好友
     */
    private void getPeople(String id) {
        ApiHttpClient.postSevereUserDetails(new Gson().toJson(new IdBean(id)), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(OnlyShowWebViewActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("请求中，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(OnlyShowWebViewActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                DarenDetalBean darenDetalBean = new Gson().fromJson(s, DarenDetalBean.class);
                if (darenDetalBean.getCode() == 200) {
                    image = darenDetalBean.getData().getImgUrl();
                    contents = darenDetalBean.getData().getContent();
                    initWebView(contents);

                } else {
                    ToastUtils.showLong(OnlyShowWebViewActivity.this, darenDetalBean.getMessage());
                }
            }
        });
    }

}
