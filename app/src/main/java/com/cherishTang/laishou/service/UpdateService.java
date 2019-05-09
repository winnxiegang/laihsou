package com.cherishTang.laishou.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cherishTang.laishou.laishou.setting.bean.VersionCheckRequestBean;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.AppManager;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.MyApplication;
import com.cherishTang.laishou.bean.Code.VersionMessage;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.broadCastReceiver.NetworkStateReceiver;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.GetVersion;
import com.cherishTang.laishou.util.listener.NetworkChangeListener;
import com.cherishTang.laishou.util.update.UpdateManger;
import com.cherishTang.laishou.util.update.server.DownloadApkReceiver;
import com.cherishTang.laishou.util.update.server.DownloadApkService;

import okhttp3.Call;

/**
 * Created by 方舟 on 16/11/8.    
 * 更新
 */

public class UpdateService extends Service implements NetworkChangeListener {
    public static DownloadApkReceiver downloadApkReceiver;//设为静态的，防止多次注册

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{
            Bundle bundle = intent.getExtras();
            if(bundle!=null&&bundle.getSerializable("updateVersion")!=null){
                VersionMessage versionMessage = (VersionMessage) bundle.getSerializable("updateVersion");
                updateCheck(versionMessage);
            }else{
                checkVersionUpdate();
                NetworkStateReceiver networkStateReceiver = MyApplication.getInstance().getNetworkChangeReceiver();
                if (networkStateReceiver != null)
                    networkStateReceiver.setNetworkChangeListener(this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (downloadApkReceiver != null)
            unregisterReceiver(downloadApkReceiver);
    }

    /**
     * 更新
     */
    public void checkVersionUpdate() {
        ApiHttpClient.checkUpdateVersion(new Gson().toJson(
                new VersionCheckRequestBean(GetVersion.getVersionCode(this),"android",GetVersion.getVersion(this)))
                ,this,new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ConstantsHelper.isSuccessRequestUpdate = false;
            }

            @Override
            public void onResponse(String s, int i) {
                ConstantsHelper.isSuccessRequestUpdate = true;
                ResultBean<VersionMessage> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(s, new TypeToken<ResultBean<VersionMessage>>() {
                        }.getType());
                if (resultBean.isSuccess() && resultBean.getData() != null ){
                    updateCheck(resultBean.getData());
                }
            }
        });
    }

    /**
     * 注册更新BroadcastReceiver
     *
     * @param versionMessage 更新数据
     */
    public void updateCheck(VersionMessage versionMessage) {
        ConstantsHelper.hasNewAppVersion = true;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadApkService.ACTION_START);
        intentFilter.addAction(DownloadApkService.ACTION_UPDATE);
        intentFilter.addAction(DownloadApkService.ACTION_FINISHED);
        intentFilter.addAction(DownloadApkService.ACTION_CANCEL);
        intentFilter.addAction(DownloadApkService.ACTION_ERROR);
        intentFilter.addAction(DownloadApkService.ACTION_REDIRECT_ERROR);

        downloadApkReceiver = new DownloadApkReceiver();
        registerReceiver(downloadApkReceiver, intentFilter);
        Activity mContext = AppManager.getAppManager().currentActivity();
        if (AppManager.isForeground(this) && mContext != null) {
            UpdateManger.getInstance().checkUpdateInfo(mContext,
                    versionMessage.getDownloadUrl(), versionMessage.getLogContent(),
                    versionMessage.getVersionNumber());
        }
    }

    @Override
    public void networkChanger(int state) {
        if ((state == 1 || state == 2) && !ConstantsHelper.isSuccessRequestUpdate)
            checkVersionUpdate();
    }
}
