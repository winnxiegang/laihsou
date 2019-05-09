package com.cherishTang.laishou.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.bean.JpushBean;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite数据库操作类，执行一些简单的增删改查
 * 调用如下：
 * 		mUserSQLiteOpenHelper = UserSQLiteOpenHelper.getInstance(this);
 *		//mUserSQLiteOpenHelper.getWritableDatabase()以读写方式打开数据库
 *		//mUserSQLiteOpenHelper.getReadableDatabase();读取模式打开数据库
 *		mUserDataBaseOperate = new UserDataBaseOperate(mUserSQLiteOpenHelper.getWritableDatabase());
 */

public class JpushDataBaseOperate {

	private static final String TAG = ConstantsHelper.TAG;
	private static final boolean DEBUG = true;

	protected SQLiteDatabase mDB = null;

	public JpushDataBaseOperate(SQLiteDatabase db) {
		if (null == db) {
			throw new NullPointerException("The db cannot be null.");
		}
		mDB = db;
	}

	public long insert(JpushBean rows) {
		ContentValues values = new ContentValues();
		values.put(JpushSQLiteDataHelper.CONTENTS, rows.getContents());
        values.put(JpushSQLiteDataHelper.TITLE, rows.getTitle());
        values.put(JpushSQLiteDataHelper.EXTRA, rows.getExtra());
		values.put(JpushSQLiteDataHelper.NOTIFACTIONID, rows.getNotifactionId());
		values.put(JpushSQLiteDataHelper.ACCEPTDATE, rows.getAcceptDate());
		values.put(JpushSQLiteDataHelper.READEDDATE, rows.getReadedDate());
		values.put(JpushSQLiteDataHelper.ISREADED, rows.getIsReaded());

		return mDB.insert(JpushSQLiteDataHelper.DATABASE_TABLE_RECIRD, null,
				values);
	}

//	public long updateUser(SpellUserBean user) {
//		ContentValues values = new ContentValues();
//		values.put(ParamsSQLiteDataHelper.COL_TIME, user.getModifyTime());
//		return mDB.update(ParamsSQLiteDataHelper.DATABASE_PIC_PATH, values,
//				"_id=?", new String[] { ""+user.get_id() });
//	}

	// clear databases
	public long deleteAll() {
		return mDB.delete(JpushSQLiteDataHelper.DATABASE_TABLE_RECIRD, null, null);
	}

	public long delete(long id){
		return mDB.delete(JpushSQLiteDataHelper.DATABASE_TABLE_RECIRD, " notifactionId = ? ", new String[]{id+""});
	}

	public long getCount(String conditions, String[] args) {
        return 0;
	}

	public List<JpushBean> findAll() {
		List<JpushBean> rows = new ArrayList<JpushBean>();
		Cursor cursor = mDB.query(JpushSQLiteDataHelper.DATABASE_TABLE_RECIRD,
				null, null, null, null, null, null);

		if (cursor != null ) {
			while (cursor.moveToNext()) {
				JpushBean jpushBean = new JpushBean();
				jpushBean.setTitle(cursor.getString(cursor
						.getColumnIndex(JpushSQLiteDataHelper.TITLE)));
				jpushBean.setContents(cursor.getString(cursor
						.getColumnIndex(JpushSQLiteDataHelper.CONTENTS)));
				jpushBean.setExtra(cursor.getString(cursor
						.getColumnIndex(JpushSQLiteDataHelper.EXTRA)));
                jpushBean.setAcceptDate(cursor.getLong(cursor
						.getColumnIndex(JpushSQLiteDataHelper.ACCEPTDATE)));
                jpushBean.setReadedDate(cursor.getLong(cursor
                        .getColumnIndex(JpushSQLiteDataHelper.READEDDATE)));

				jpushBean.setIsReaded(cursor.getInt(cursor
						.getColumnIndex(JpushSQLiteDataHelper.ISREADED)));
				jpushBean.setNotifactionId(cursor.getLong(cursor
						.getColumnIndex(JpushSQLiteDataHelper.NOTIFACTIONID)));

                rows.add(jpushBean);
			}
			cursor.close();
		}
		return rows;
	}

	private static SQLiteDatabase mTestDb;

}
