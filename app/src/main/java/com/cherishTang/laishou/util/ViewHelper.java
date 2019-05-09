package com.cherishTang.laishou.util;

import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;


import java.lang.reflect.Field;
import java.util.HashMap;


/**
 * @author po.
 * @date 2016/6/27.
 */
public class ViewHelper {

    private static final int CLICK_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    private static HashMap<Integer, Long> mClickTimeMap = new HashMap<>(4);

    /**
     * 主要业务按钮判断重复点击
     */
    public static boolean isDoubleClick(View view) {
        int hashCode = view.hashCode();
        if (mClickTimeMap.containsKey(hashCode)) {
            long lastTime = mClickTimeMap.get(hashCode);
            long now = System.currentTimeMillis();
            if (now - lastTime > CLICK_TIMEOUT) {
                mClickTimeMap.put(hashCode, now);
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

//    /**
//     * infowindow 根据上下行宽度 设置属性
//     * 上行宽时 保证导航按钮右对齐
//     * 下行宽时 保证下行信息显示完全
//     */
//    public static void setMarkView(Context appContext, View view, Marker marker){
//        //位置
//        TextView txt_Location = (TextView)view.findViewById(R.id.txt_Location);
////        txt_Location.setText(marker.getTitle());
//        //距离
//        TextView txt_Distance = (TextView)view.findViewById(R.id.txt_Distance);
////        txt_Distance.setText(marker.getSnippet());
//
//        txt_Location.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        txt_Distance.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//
//        // 如果上行宽度 小于 下行宽度 重设属性
//        if (txt_Location.getMeasuredWidth() < txt_Distance.getMeasuredWidth() + DensityUtil.dip2px(appContext, 44)) {
//            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams)txt_Location.getLayoutParams();
//            layoutParams1.width = LinearLayout.LayoutParams.MATCH_PARENT;
//            txt_Location.setLayoutParams(layoutParams1);
//
//            LinearLayout addrLayout = (LinearLayout)view.findViewById(R.id.ll_address);
//            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams)addrLayout.getLayoutParams();
//            layoutParams2.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//            addrLayout.setLayoutParams(layoutParams2);
//        }
//
//    }

    /**
     * 设置WebView 属性
     */
    public static void setWebViewAttribute(WebView view) {
        WebSettings webSettings = view.getSettings();

        webSettings.setJavaScriptEnabled(true);  //支持js

        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小

        webSettings.setSupportZoom(true);  //支持缩放

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        webSettings.supportMultipleWindows();  //多窗口

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存

        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);// 打开本地缓存提供JS调用,至关重要
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);// 实现8倍缓存
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
    }

    public static void setConfigCallback(WindowManager windowManager) {
        try {
            Field field = WebView.class.getDeclaredField("mWebViewCore");
            field = field.getType().getDeclaredField("mBrowserFrame");
            field = field.getType().getDeclaredField("sConfigCallback");
            field.setAccessible(true);
            Object configCallback = field.get(null);

            if (null == configCallback) {
                return;
            }

            field = field.getType().getDeclaredField("mWindowManager");
            field.setAccessible(true);
            field.set(configCallback, windowManager);
        } catch (Exception e) {
        }
    }

}
