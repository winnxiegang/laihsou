package com.cherishTang.laishou.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

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

public class DataBaseOperate {

//	private static final String TAG = "DBRemoteLive";
	private static final String TAG = "DBRemoteLive";

	private static final boolean DEBUG = true;

	protected SQLiteDatabase mDB = null;

	public DataBaseOperate(SQLiteDatabase db) {
		if (null == db) {
			throw new NullPointerException("The db cannot be null.");
		}
		mDB = db;
	}

	public long insertToBanner(BannerBean bannerBean) {
		ContentValues values = new ContentValues();
		values.put(SQLiteDataHelper.COL_TIME, bannerBean.getModifyTime());
        values.put(SQLiteDataHelper.DATABASE_PIC_PATH, bannerBean.getPath());

        return mDB.insert(SQLiteDataHelper.DATABASE_TABLE_BANNER, null,
				values);
	}

	public long updateUser(UserBean user) {
		ContentValues values = new ContentValues();
		values.put(SQLiteDataHelper.COL_TIME, user.getModifyTime());
		return mDB.update(SQLiteDataHelper.DATABASE_PIC_PATH, values,
				"_id=?", new String[] { ""+user.get_id() });
	}

	// clear databases
	public long deleteAll() {
		return mDB.delete(SQLiteDataHelper.DATABASE_TABLE_BANNER, null, null);
	}

	public long deleteUserByname(String name){
		
		return mDB.delete("user_info", "name=?", new String[]{name});
	}

	public long getCount(String conditions, String[] args) {
		long count = 0;
		if (TextUtils.isEmpty(conditions)) {
			conditions = " 1 = 1 ";
		}
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(1) AS count FROM ");
		builder.append(SQLiteDataHelper.DATABASE_PIC_PATH).append(" ");
		builder.append("WHERE ");
		builder.append(conditions);
		if (DEBUG)
			Log.d(TAG, "SQL: " + builder.toString());
		Cursor cursor = mDB.rawQuery(builder.toString(), args);
		if (null != cursor) {
			if (cursor.moveToNext()) {
				count = cursor.getLong(cursor.getColumnIndex("count"));
			}
			cursor.close();
		}
		return count;
	}
	public List<BannerBean> findAll() {
		List<BannerBean> bannerBeen = new ArrayList<BannerBean>();
		//order by modifytime desc
		Cursor cursor = mDB.query(SQLiteDataHelper.DATABASE_TABLE_BANNER,
				null, null, null, null, null, null);
		if (cursor != null ) {
			while (cursor.moveToNext()) {
				BannerBean banner = new BannerBean();
				banner.setPath(cursor.getString(cursor
						.getColumnIndex(SQLiteDataHelper.DATABASE_PIC_PATH)));

				banner.setModifyTime(cursor.getLong(cursor
						.getColumnIndex(SQLiteDataHelper.COL_TIME)));

				bannerBeen.add(banner);
			}
			cursor.close();
		}
		return bannerBeen;
	}

	public List<UserBean> findUserByName(String name) {

		List<UserBean> userList = new ArrayList<UserBean>();
		//模糊查询			
		Cursor cursor = mDB.query(SQLiteDataHelper.DATABASE_PIC_PATH,
				null, null,
				new String[] {"%"+name+"%"}, null, null, SQLiteDataHelper.COUNT
				+ " desc");
		
//		Cursor cursor = mDB.query(ParamsSQLiteDataHelper.DATABASE_TABLE_USER,
//			null, ParamsSQLiteDataHelper.COL_NAME + " =?",
//			new String[] {name}, null, null, ParamsSQLiteDataHelper.COL_ID
//			+ " desc");
		
		//多个条件查询
//		Cursor cursor = mDB.query(ParamsSQLiteDataHelper.DATABASE_TABLE_USER,
//				null, ParamsSQLiteDataHelper.COL_NAME + " like?"+" and "+ParamsSQLiteDataHelper.COL_ID+" >?",
//				new String[] {"%"+name+"%",2+""}, null, null, ParamsSQLiteDataHelper.COL_ID
//				+ " desc");
		if (null != cursor) {
			while (cursor.moveToNext()) {
				UserBean user = new UserBean();
				user.set_id(cursor.getLong(cursor
						.getColumnIndex(SQLiteDataHelper.COUNT)));
				user.setModifyTime(cursor.getLong(cursor
						.getColumnIndex(SQLiteDataHelper.COL_TIME)));
				userList.add(user);
			}
			cursor.close();
		}
		return userList;
	}
	private static SQLiteDatabase mTestDb;

}
