package com.cherishTang.laishou.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cherishTang.laishou.api.MyApplication;

/**
 * Created by 方舟 on 2017/6/12.、
 * 数据库的创建
 */

public class SQLiteDataHelper extends SQLiteOpenHelper {

    private static final String REMOTE_LIVE_DATABASE_NAME = MyApplication.getInstance().getPackageName()+".db";
    private static int version = 1;
    public static final String DATABASE_TABLE_BANNER = "bannerPic";
    public static final String DATABASE_PIC_PATH= "picPath";
    public static final String COUNT = "count";
    public static final String COL_TIME = "modifyTime";// modify time
//    integer primary key autoincrement,自增
    private final String REMOTE_LIVE_DATABASE_CREATE =
            "create table IF NOT EXISTS "+DATABASE_TABLE_BANNER+"("+
                    DATABASE_PIC_PATH +" text ,"+
            COL_TIME+" integer)";

    private static SQLiteDataHelper mInstance = null;
    private static Context mContext;

    //开启单例模式
    public static SQLiteDataHelper getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new SQLiteDataHelper(context);
            mContext = context;
        }
        return mInstance;
    }

    private SQLiteDataHelper(Context context) {
        super(context, REMOTE_LIVE_DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(REMOTE_LIVE_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

}
