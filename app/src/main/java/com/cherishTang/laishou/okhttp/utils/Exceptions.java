package com.cherishTang.laishou.okhttp.utils;

/**
 * Created by zhy on 15/12/14.
 * 跑出异常
 */
public class Exceptions
{
    public static void illegalArgument(String msg, Object... params)
    {
        throw new IllegalArgumentException(String.format(msg, params));
    }


}
