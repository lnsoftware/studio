package com.imxiaomai.convenience.store.scan.util;

import android.database.Cursor;

public class DbUtil {
	public static int getInt(String columnName, Cursor cursor) {
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}

	public static String getString(String columnName, Cursor cursor) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}

	public static long getLong(String columnName, Cursor cursor) {
		return cursor.getLong(cursor.getColumnIndex(columnName));
	}

	public static double getDouble(String columnName, Cursor cursor) {
		return cursor.getDouble(cursor.getColumnIndex(columnName));
	}

}
