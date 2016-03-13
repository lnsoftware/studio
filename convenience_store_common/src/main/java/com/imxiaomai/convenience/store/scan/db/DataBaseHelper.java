package com.imxiaomai.convenience.store.scan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite数据库的帮助类
 * 
 * 该类属于扩展类,主要承担数据库初始化和版本升级使用,其他核心全由核心父类完成
 */
public class DataBaseHelper extends SQLiteOpenHelper {


	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS [sku_gb] ([id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,[sku] NVARCHAR,[gb_code] VARCHAR);");
		db.execSQL("CREATE TABLE IF NOT EXISTS [product] ([id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,[sku] NVARCHAR,[unit] VARCHAR, [name] NVARCHAR,[specification] NVARCHAR,[period] NVARCHAR,[sale_size] NVARCHAR);");
		db.execSQL("CREATE TABLE IF NOT EXISTS [pre_purchase_product] ([id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,[purchase_order_no] NVARCHAR, [sku] NVARCHAR, [num] INTEGER,[on_shelfed_num] INTEGER, [gift_num] INTEGER,[self_life] INTEGER, [unit] VARCHAR, [name] NVARCHAR,[owner_code] NVARCHAR,[owner_name] NVARCHAR,[specification] NVARCHAR,[period] NVARCHAR,[status] INTEGER default 0);");
        //销退待返架商品
        db.execSQL("CREATE TABLE IF NOT EXISTS [revoke_product] ([id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,[eshop_no] NVARCHAR, [revoke_no] NVARCHAR, [sku] NVARCHAR,[num] NVARCHAR,[sum_num] NVARCHAR,[name] NVARCHAR,[inbounded_num] NVARCHAR,[operate_num] NVARCHAR,[data1] NVARCHAR);");
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}
}
