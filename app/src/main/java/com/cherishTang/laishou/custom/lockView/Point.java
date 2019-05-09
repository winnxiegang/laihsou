package com.cherishTang.laishou.custom.lockView;

/**
 * 点的bean
 * Created by Administrator on 2015/9/21.
 * 九宫格手势密码中点的绘制
 */
public class Point {

    public static final int STATE_NORMAL = 1;
    public static final int STATE_PRESS = 2;
    public static final int STATE_ERROR = 3;

    float x;
    float y;
    int state = STATE_NORMAL;

    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * 计算两点间的距离
     * @param a 另外一个点
     * @return
     */
    public float getInstance(Point a){
       return (float) Math.sqrt((x-a.x)*(x-a.x) + (y-a.y)*(y-a.y));
    }
}
