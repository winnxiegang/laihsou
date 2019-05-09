package com.cherishTang.laishou.custom.customlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.cherishTang.laishou.R;

/**
 * Created by CherishTang on 2019/3/1.
 * describe
 */
public class CouponsView extends View {
    private Paint paint;

    public CouponsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CouponsView(Context context,@Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                new float[]{5.0f,5.0f,5.0f,5.0f}, Path.Direction.CW);
        canvas.clipPath(path);
    }


    private void init(Context mContext){
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(mContext, R.color.themeColor));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        RectF rectF = new RectF();


    }

}
