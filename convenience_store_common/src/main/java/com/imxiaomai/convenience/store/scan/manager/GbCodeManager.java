package com.imxiaomai.convenience.store.scan.manager;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.imxiaomai.convenience.store.scan.constants.AppConstants;
import com.imxiaomai.convenience.store.scan.db.DBManager;
import com.imxiaomai.convenience.store.scan.db.SQLiteTemplate;
import com.imxiaomai.convenience.store.scan.db.SQLiteTemplate.RowMapper;
import com.imxiaomai.convenience.store.scan.model.ProductResponse;
import com.imxiaomai.convenience.store.scan.util.DbUtil;
import com.imxiaomai.convenience.store.scan.util.StringUtil;

public class GbCodeManager {
	private static GbCodeManager gbCodeManager = null;
	private static DBManager manager = null;

	private GbCodeManager(Context context) {
		manager = DBManager.getInstance(context, AppConstants.DATABASE_NAME);
	}

	public static GbCodeManager getInstance(Context context) {

		if (gbCodeManager == null) {
			gbCodeManager = new GbCodeManager(context);
		}

		return gbCodeManager;
	}
	
	public static void setInstance(GbCodeManager messageManager) {
		GbCodeManager.gbCodeManager = messageManager;
	}

	/**
	 * 保存.
	 */
	public long save(String sku,String gbCode) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues contentValues = new ContentValues();
		contentValues.put("sku", sku);
		contentValues.put("gb_code", gbCode);
		return st.insert("sku_gb", contentValues);
	}
	
	/**
	 * 保存.
	 */
	public void save(List<ProductResponse> beanList) {
		
		SQLiteDatabase db = manager.openDatabase();
		
	    db.beginTransaction();//开启事务  
	    try{  
	    	  for (ProductResponse prd : beanList) {
		            if (prd.getGbCodeList() != null) {
		                for (String gbCode : prd.getGbCodeList()) {
		                	db.execSQL("insert into sku_gb (sku, gb_code) values('" + prd.getSku() + "','" + gbCode + "');");
		                }
		            }
		        }
	    	  
	        db.setTransactionSuccessful();//设置事务的标志为True  
	    }finally{  
	        db.endTransaction();
	    //结束事务,有两种情况：commit,rollback,  
	    //事务的提交或回滚是由事务的标志决定的,如果事务的标志为True，事务就会提交，否侧回滚,默认情况下事务的标志为False  
	    }  
		  
	}


	public String getSku(String gbCode) {
		if (StringUtil.empty(gbCode)) {
			return null;
		}
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		String sku = st.queryForObject(
				new RowMapper<String>() {
					@Override
					public String mapRow(Cursor cursor, int index) {
						return DbUtil.getString("sku", cursor);
					}
				},
				"select sku from sku_gb where gb_code=?",
				new String[] {gbCode }); 
		return sku;
	}
	public boolean isExistSku(String sku) {
		if (StringUtil.empty(sku)) {
			return false;
		}
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		String countStr = st.queryForObject(
				new RowMapper<String>() {
					@Override
					public String mapRow(Cursor cursor, int index) {
						return DbUtil.getString("count", cursor);
					}
				},
				"select count(*) count from sku_gb where sku=?",
				new String[] {sku }); 
		if (!TextUtils.isEmpty(countStr)) {
			return Integer.parseInt(countStr) > 0 ? true:false;
		} else {
			return false;
		}
	}
	
	public int delAll() {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.deleteByCondition("sku_gb", "",null);
	}
}
