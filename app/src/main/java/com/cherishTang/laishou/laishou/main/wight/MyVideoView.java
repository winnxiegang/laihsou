package com.cherishTang.laishou.laishou.main.wight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by CherishTang on 2019/3/19.
 * describe
 */
public class MyVideoView extends VideoView {
    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = getDefaultSize(0, widthMeasureSpec);
        int heightSize = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(widthSize,heightSize);
    }
}
