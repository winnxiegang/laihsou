package com.cherishTang.laishou.broadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;

import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.util.listener.NetworkChangeListener;
import com.cherishTang.laishou.util.networkTools.NetworkStateUtil;

/**
 * Created by 方舟 on 2016/12/29.
 * 自定义广播接收器，监听网络状态是否发生改变
 */
public class NetworkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(NetworkStateUtil.isNetWorkConnected(context)&&NetworkStateUtil.isWifiConnected(context)){
            WifiInfo wifiInfo = NetworkStateUtil.getWifiInfo(context);
//            ToastUtils.showShort(context, "已连接到"+(wifiInfo.getSSID()==null?"wifi":wifiInfo.getSSID())+"网络");
            processCustomMessage(context,1);
        }else if(NetworkStateUtil.isNetWorkConnected(context)&&NetworkStateUtil.isMobileConnected(context)){
//            ToastUtils.showShort(context, "已连接到数据流量网络");
            processCustomMessage(context,2);
        }else{
            ConstantsHelper.isDownLoadApk = false;
            processCustomMessage(context,3);
//            ToastUtils.showShort(context, "已进入无网络次元，请检查网络设置！");
        }
    }

    //send msg to BaseActivity
    private void processCustomMessage(Context context, Integer message) {
//        Intent mIntent=new Intent(BaseActivity.ACTION_INTENT_RECEIVER_MESSAGE);
//        mIntent.putExtra("message", message);
//        context.sendBroadcast(mIntent);
        if (networkChangeListener != null) {
            networkChangeListener.networkChanger(message);
        }
    }
    private NetworkChangeListener networkChangeListener;

    public void setNetworkChangeListener(NetworkChangeListener networkChangeListener) {
        this.networkChangeListener = networkChangeListener;
    }
}
