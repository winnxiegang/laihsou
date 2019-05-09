package com.cherishTang.laishou.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 方舟 on 2017/10/21.、
 * 数据库的创建
 */

public class JpushSQLiteDataHelper extends SQLiteOpenHelper {
    private  static final String dbName = "hffc_jpush.db";
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_TABLE_RECIRD= "jpushData";
    static final String TITLE= "title";
    static final String CONTENTS = "contents";
    static final String EXTRA = "extra";
    static final String ACCEPTDATE = "acceptDate";
    static final String NOTIFACTIONID = "notifactionId";
    static final String ISREADED= "isReaded";
    static final String READEDDATE= "readedDate";
    @SuppressLint("StaticFieldLeak")
    private static JpushSQLiteDataHelper mInstance = null;
    @SuppressLint("StaticFieldLeak")
    private static  Context context;
    //开启单例模式
    public static JpushSQLiteDataHelper getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new JpushSQLiteDataHelper(context);
            context = context;
        }
        return mInstance;
    }
    private JpushSQLiteDataHelper(Context context) {
        super(context, dbName, null, 1);
    }
    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        final int FIRST_DATABASE_VERSION = 1;
        onUpgrade(db, FIRST_DATABASE_VERSION, DATABASE_VERSION);
        String sql1 = "create table if not exists "+DATABASE_TABLE_RECIRD+" (id integer primary key AUTOINCREMENT , " +
                TITLE+" text ,"+CONTENTS+" text ,"+EXTRA+" text ,"+ACCEPTDATE+" text ,"+
                NOTIFACTIONID +" text ,"+ISREADED+" integer ,"+ READEDDATE+" text )";
        try {
            db.execSQL(sql1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //删除数据库
    public void deleteDb(){
        context.deleteDatabase(dbName);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
