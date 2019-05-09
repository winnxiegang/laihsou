package com.cherishTang.laishou.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by 方舟 on 2018/1/16.
 * 把布局转为bitmap
 */

public class XmlToBitmap {
    //把布局转化为bitmap
    public static Bitmap getViewBitmap(Context context, String text, @LayoutRes int layout, @DrawableRes int drawableId) {
        try {
            View markerView = LayoutInflater.from(context).inflate(layout, null);
//            TextView textView = (TextView) markerView.findViewById(R.id.markerText);
//            textView.setBackgroundResource(drawableId);
//            textView.setText(text);
            markerView.setDrawingCacheEnabled(true);
            markerView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            markerView.layout(0, 0,
                    markerView.getMeasuredWidth(),
                    markerView.getMeasuredHeight());
            markerView.buildDrawingCache();
            Bitmap cacheBitmap = markerView.getDrawingCache();
            return Bitmap.createBitmap(cacheBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
