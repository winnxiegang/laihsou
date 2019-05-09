package com.cherishTang.laishou.custom.customlayout;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 方舟 on 2017/5/16.
 * 防今日头条的顶部提示框代替toast提示框
 */

public class Tips {
    private TextView tips_textView;
    Timer timerTipS = new Timer();
    private int tips_time = 1;
    private String strContent;

    public void tipsStart(TextView tips_textView, Timer timerTipS, String strContent) {
        this.timerTipS = timerTipS;
        this.tips_textView = tips_textView;

        tips_textView.setText(strContent);
        tips_textView.setVisibility(View.VISIBLE);

        tips_time = 2;

        if (timerTipS == null) {
            timerTipS = new Timer();
        }
        try {
            timerTipS.schedule(new RequestTimerTask2(), 1000, 1000);       // timeTask
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final Handler handlerTips = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (tips_time < 0) {
                        tips_textView.setVisibility(View.GONE);
                        timerTipS.cancel();
                    }
            }
        }
    };

    class RequestTimerTask2 extends TimerTask {
        public void run() {
            tips_time--;
            Message message = new Message();
            message.what = 1;
            handlerTips.sendMessage(message);
        }
    }
    public void endTimer(){
        if (timerTipS != null) {
            timerTipS.cancel();
        }
    }


}
