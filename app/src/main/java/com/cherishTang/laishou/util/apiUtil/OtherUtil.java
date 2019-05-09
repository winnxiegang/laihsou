package com.cherishTang.laishou.util.apiUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.cherishTang.laishou.api.AppHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 方舟 on 2018/3/16.
 * 一些工具类
 */

public class OtherUtil {
    /**
     * 调用手机浏览器打开连接
     *
     * @param linkUrl 打开地址
     * @param isInsideBrowser 是否调用app内部webView还是自带浏览器
     */
    public static void reqBrowser(Context context,String linkUrl, boolean isInsideBrowser) {
        if (linkUrl == null) return;
        if(isInsideBrowser){
            AppHelper.showWebViewActivity(context,linkUrl,"详情");
        }else{
            Uri uri = Uri.parse(linkUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

    }


    /* 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}
