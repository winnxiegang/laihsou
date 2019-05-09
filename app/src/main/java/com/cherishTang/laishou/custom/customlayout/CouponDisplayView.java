package com.cherishTang.laishou.custom.customlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.cherishTang.laishou.R;

/**
 * Created by CherishTang on 2019/3/1.
 * describe
 */
public class CouponDisplayView extends View {
    private Paint mPaint;
    private Paint mPaintDotted;
    private Paint mPaintRect;

    /**
     * 半径
     */
    private float radius = 10;
    /**
     * 圆数量
     */
    private int circleNum;

    private float remain;
    private int width;
    private int height;

    public CouponDisplayView(Context context) {
        super(context);
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);

        mPaintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintRect.setDither(true);
        mPaintRect.setColor(ContextCompat.getColor(context,R.color.themeColor));
        mPaintRect.setStyle(Paint.Style.FILL);

        mPaintDotted = new Paint();
        mPaintDotted.setStrokeWidth(2);
        mPaintDotted.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
        mPaintDotted.setColor(Color.WHITE);
        mPaintDotted.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain == 0) {
            remain = h % (2 * radius);
        }
        circleNum = (int) (h / (2 * radius));
    }

    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0, 0, width, height);// 设置个新的长方形
        canvas.drawRoundRect(rectF, 10, 10, mPaintRect);//第二个参数是x半径，第三个参数是y半径

        canvas.drawCircle(width / 5 * 4, 0, radius * 2, mPaint);
        canvas.drawCircle(width / 5 * 4, height, radius * 2, mPaint);
        canvas.drawLine(width / 5 * 4, 0, width / 5 * 4, height, mPaintDotted);

        for (int i = 0; i < circleNum; i++) {
            float y = radius + remain / 2 + (radius * 2 * i);
            canvas.drawCircle(getWidth(), y, radius, mPaint);
        }

    }
}