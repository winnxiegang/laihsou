package com.cherishTang.laishou.broadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by ndh on 16/11/8.
 *
 */

public class ScreenStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
            ScreenStateManager.screenState=1;
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
            ScreenStateManager.screenState=-1;
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
            ScreenStateManager.screenState = 0;
        }
    }
}
