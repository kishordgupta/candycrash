package com.siliconorchard.myowncandy.database;


import java.util.HashMap;

import com.siliconorchard.myowncandy.Level;
import com.siliconorchard.myowncandy.mylog.MyLog;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	private static final String NAME_DB = "jewels.db";
	private static final int VERSION = 1;
	public final String BESTTIME = "BESTTIME";
	public final String STAR = "STAR";
	public final String LEVEL = "level";
	public final String TIME = "time";
	private SQLiteDatabase mSQLiteDatabase = null;

	// ==============================================================================
	public Database(Context context) {
		super(context, NAME_DB, null, VERSION);
	}

	// ==============================================================================
	/**
	 * Đóng Database
	 */
	public void closeDatabase() {
		this.close();
	}

	// ==============================================================================
	/**
	 * Thực hiện 1 câu lệnh sql
	 * 
	 * @param sql
	 */
	public void execSQL(String sql) {
		this.execSQL(sql);
	}

	// ==============================================================================
	/**
	 * Get toàn bộ time
	 * 
	 * @return
	 */
	public HashMap<Integer, Integer> getAllTime() {
		HashMap<Integer, Integer> map_time = new HashMap<Integer, Integer>();

		if (mSQLiteDatabase.isOpen()) {
			Cursor mCursor = getCursorQuery(BESTTIME, null, null, null, null,
					null, LEVEL);
			if (mCursor.getCount() != 0) {
				while (mCursor.moveToNext()) {
					int time = mCursor.getInt(mCursor.getColumnIndex(TIME));
					int level = mCursor.getInt(mCursor.getColumnIndex(LEVEL));
					map_time.put(level, time);
				}
			}
			mCursor.close();
		}

		return map_time;
	}

	// ==============================================================================
	/**
	 * Thực hiện 1 câu lệnh query
	 * 
	 * @param query
	 * @return Cursor
	 */
	public Cursor getCursorQuery(String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		return mSQLiteDatabase.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy);
	}

	// ==============================================================================
	/**
	 * Get time theo level
	 * 
	 * @param level
	 * @return
	 */
	public int getTimeByLevel(int level) {
		if (mSQLiteDatabase.isOpen()) {
			Cursor mCursor = getCursorQuery(BESTTIME, null,
					LEVEL + "=" + level, null, null, null, null);
			if (mCursor.getCount() != 0) {
				mCursor.moveToFirst();
				int time = mCursor.getInt(mCursor.getColumnIndex(TIME));
				mCursor.close();
				return time;
			}
		}
		return -1;
	}

	// ==============================================================================
	/**
	 * Insert time theo level. Nếu insert được thì là thời gian tốt nhất của
	 * level return true: nếu time được thêm hoặc update return false: time nhỏ
	 * hơn time đã có
	 * 
	 * @param level
	 * @param time
	 * @return
	 */
	public boolean insertTime(int level, int time) {
		if (mSQLiteDatabase.isOpen()) {
			ContentValues CV = new ContentValues();
			CV.put(TIME, time);
			CV.put(LEVEL, level);
			CV.put(STAR, getStarByLevel(level, time));

			int tmp_time = getTimeByLevel(level);

			if (tmp_time == -1) {
				mSQLiteDatabase.insert(BESTTIME, null, CV);
				MyLog.println("Insert level = " + level + " time = " + time);
				return true;
			} else {
				if (time < tmp_time) {
					updateTime(level, time);
					MyLog.println("Update level = " + level + " time = " + time);
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	// ==============================================================================
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + BESTTIME + " " + "( " + LEVEL
				+ " INTEGER PRIMARY KEY," + TIME + " INTEGER NOT NULL," + STAR
				+ " INTEGER NOT NULL);");
		MyLog.println("onCreate DB");
	}

	// ==============================================================================
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + BESTTIME);
		onCreate(db);
		MyLog.println("onUpgrade DB");
	}

	// ==============================================================================
	/**
	 * Mở Database. Nếu csdl chưa có sẽ được tự tạo ra
	 */
	public void openDatabase() {
		mSQLiteDatabase = this.getWritableDatabase();
	}

	// ==============================================================================
	/**
	 * Xóa toàn bộ bảng best time.
	 */
	public void resetTableTime() {
		if (mSQLiteDatabase.isOpen()) {
			mSQLiteDatabase.delete(BESTTIME, null, null);
		}
	}

	// ==============================================================================
	/**
	 * Update time theo level
	 * 
	 * @param level
	 * @param time
	 */
	public void updateTime(int level, int time) {
		if (mSQLiteDatabase.isOpen()) {
			ContentValues CV = new ContentValues();
			CV.put(STAR, getStarByLevel(level, time));
			CV.put(TIME, time);

			mSQLiteDatabase.update(BESTTIME, CV, LEVEL + "=" + level, null);
		}
	}

	// ==============================================================================
	/**
	 * 
	 * @param level
	 * @param time
	 * @return
	 */
	public int getStarByLevel(int level, int time) {
		int star = 0;
		int timeLevel = Level.timeLevel[level - 1] / 5;
		if (time <= timeLevel * 2) {
			return 3;// 3 sao
		} else if (time <= timeLevel * 3) {
			return 2;// 2 sao
		} else if (time <= timeLevel * 4) {
			return 1;// 2 sao
		}
		return star;
	}
}