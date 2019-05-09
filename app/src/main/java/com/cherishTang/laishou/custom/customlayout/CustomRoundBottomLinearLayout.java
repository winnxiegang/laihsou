package com.cherishTang.laishou.custom.customlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cherishTang.laishou.R;

/**
 * Created by CherishTang on 2019/3/7.
 * describe
 */
public class CustomRoundBottomLinearLayout extends FrameLayout {
    private Paint mPaint;
    private int height;
    private int width;
    private RectF rectF;

    public CustomRoundBottomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomRoundBottomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        rectF = new RectF(0 - width / 2, 0 - 2 * width + height, width + width / 2, height);
    }

    private void init(Context mContext) {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(0x43cf7c);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF re1 = new RectF(0, 0, width, height- getResources().getDimension(R.dimen.y40));
        canvas.drawRect(re1, mPaint);
        //画椭圆
        RectF re2 = new RectF(0, height - getResources().getDimension(R.dimen.y80), width, height);
        canvas.drawOval(re2, mPaint);
    }
}
