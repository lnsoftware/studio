package com.imxiaomai.convenience.store.scan.manager;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imxiaomai.convenience.store.scan.constants.AppConstants;
import com.imxiaomai.convenience.store.scan.db.DBManager;
import com.imxiaomai.convenience.store.scan.db.SQLiteTemplate;
import com.imxiaomai.convenience.store.scan.db.SQLiteTemplate.RowMapper;
import com.imxiaomai.convenience.store.scan.model.ProductResponse;
import com.imxiaomai.convenience.store.scan.util.DbUtil;
import com.imxiaomai.convenience.store.scan.util.StringUtil;

public class ProductManager {
	private static ProductManager messageManager = null;
	private static DBManager manager = null;

	private ProductManager(Context context) {
		manager = DBManager.getInstance(context, AppConstants.DATABASE_NAME);
	}

	public static ProductManager getInstance(Context context) {

		if (messageManager == null) {
			messageManager = new ProductManager(context);
		}

		return messageManager;
	}
	
	public static void setInstance(ProductManager messageManager) {
		ProductManager.messageManager = messageManager;
	}

	/**
	 * 保存.
	 */
	public long save(ProductResponse prd) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues contentValues = new ContentValues();
		contentValues.put("sku", prd.getSku());
		contentValues.put("unit", prd.getUnit());
		contentValues.put("name", prd.getName());
		contentValues.put("specification", prd.getSpecification());
		contentValues.put("period", prd.getPeriod());
		return st.insert("product", contentValues);
	}
	
	/**
	 * 保存.
	 */
	public void save(List<ProductResponse> beanList) {
		
		SQLiteDatabase db = manager.openDatabase();
		
	    db.beginTransaction();//开启事务  
	    try{  
	        for (ProductResponse prd : beanList) {
	        	db.execSQL("insert into product (sku, unit, name, specification, period, sale_size) values('" + prd.getSku() + "','" + prd.getUnit() + "','"+ prd.getName() + "','" + prd.getSpecification() + "','"+ prd.getPeriod() + "','"+ prd.getSaleSize() +"');"); 
	        }
	        db.setTransactionSuccessful();//设置事务的标志为True  
	    }finally{  
	        db.endTransaction();
	    //结束事务,有两种情况：commit,rollback,  
	    //事务的提交或回滚是由事务的标志决定的,如果事务的标志为True，事务就会提交，否侧回滚,默认情况下事务的标志为False  
	    }  
	    
	}


	public ProductResponse getProduct(String sku) {
		if (StringUtil.empty(sku)) {
			return null;
		}
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ProductResponse prd = st.queryForObject(
				new RowMapper<ProductResponse>() {
					@Override
					public ProductResponse mapRow(Cursor cursor, int index) {
						ProductResponse product = new ProductResponse();
						product.setSku(cursor.getString(cursor.getColumnIndex("sku")));
						product.setName(DbUtil.getString("name", cursor));
						product.setUnit(DbUtil.getString("unit", cursor));
						product.setPeriod(DbUtil.getString("period", cursor));
						product.setSpecification(DbUtil.getString("specification", cursor));
                        product.setSaleSize(DbUtil.getString("sale_size",cursor));
						return product;
					}
				},
				"select id,sku,name,unit,period,specification,sale_size from product where sku=?",
				new String[] {sku }); 
		return prd;
	}
	
	public int delAll() {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.deleteByCondition("product", "",null);
	}
}
