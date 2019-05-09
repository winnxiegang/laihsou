package com.cherishTang.laishou.util.crash;

import android.content.Context;
import android.os.Build;

import com.google.gson.Gson;
import com.cherishTang.laishou.api.ApiAccountHelper;
import com.cherishTang.laishou.api.OkhttpsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.util.apiUtil.GetVersion;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 方舟 on 2017/7/26.
 * 日志上传
 */

public class AccessLog {

    /**
     * 上传访问log日志
     *
     * @param context    视图
     * @param accessType 访问类型
     */
    public static void upDataLog(Context context, String accessType) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AccessMsg accessMsg = new AccessMsg(UserAccountHelper.getUser() == null ? "" : "", ApiAccountHelper.getIp(context),
                accessType, GetVersion.getVersion(context), GetVersion.getVersionCode(context) + "", Build.BRAND, Build.MODEL, sDateFormat.format(new Date()),
                UserAccountHelper.getMacAddress(), UserAccountHelper.getPhoneInfo(),
                UserAccountHelper.isLogin() ? 1 : 0);
        OkhttpsHelper.post("SysGlobal/UploadMobileEvLog", new Gson().toJson(accessMsg), context,null);
    }

    /**
     * 上传错误log日志
     */
    public static void upLoadErrorFile(String ErrorMsg, Context context) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ErrorMsg errorMsgBean = new ErrorMsg(Build.BRAND, Build.MODEL, GetVersion.getVersionCode(context) + "", GetVersion.getVersion(context),
                ApiAccountHelper.getIp(context), UserAccountHelper.getUser() == null ? "" : "",
                sDateFormat.format(new Date()), Build.VERSION.RELEASE, ErrorMsg, Build.CPU_ABI, sDateFormat.format(new Date()));
        OkhttpsHelper.post("SysGlobal/UploadAppErrorLog", new Gson().toJson(errorMsgBean),context,  null);
    }
}
