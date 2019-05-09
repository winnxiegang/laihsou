package com.cherishTang.laishou.custom.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by 方舟 on 2017/12/1.
 *
 */

public class MyGridLayoutManager  extends GridLayoutManager {
    private boolean isScrollEnabled = true;
    public MyGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public MyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }
    /**
     * 是否支持滑动
     * @param flag
     */
    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //isScrollEnabled：recyclerview是否支持滑动
        //super.canScrollVertically()：是否为竖直方向滚动
        return isScrollEnabled && super.canScrollVertically();
    }

}
