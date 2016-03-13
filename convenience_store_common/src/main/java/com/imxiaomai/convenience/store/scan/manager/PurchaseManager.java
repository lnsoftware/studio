package com.imxiaomai.convenience.store.scan.manager;

import java.util.List;

import com.imxiaomai.convenience.store.scan.constants.AppConstants;
import com.imxiaomai.convenience.store.scan.db.DBManager;
import com.imxiaomai.convenience.store.scan.db.SQLiteTemplate;
import com.imxiaomai.convenience.store.scan.db.SQLiteTemplate.RowMapper;
import com.imxiaomai.convenience.store.scan.model.PrePurchaseProductResponse;
import com.imxiaomai.convenience.store.scan.util.DbUtil;
import com.imxiaomai.convenience.store.scan.util.StringUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class PurchaseManager {
	private static PurchaseManager messageManager = null;
	private static DBManager manager = null;

	private PurchaseManager(Context context) {
		manager = DBManager.getInstance(context, AppConstants.DATABASE_NAME);
	}

	public static PurchaseManager getInstance(Context context) {

		if (messageManager == null) {
			messageManager = new PurchaseManager(context);
		}

		return messageManager;
	}

	public static void setInstance(PurchaseManager messageManager) {
		PurchaseManager.messageManager = messageManager;
	}

	public void saveList(List<PrePurchaseProductResponse> productList) {
		delAll();
		
		for (PrePurchaseProductResponse product : productList) {
			save(product);
		}
	}

	/**
	 * 保存.
	 */
	public long save(PrePurchaseProductResponse product) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues contentValues = new ContentValues();
		contentValues.put("purchase_order_no", product.getPurchaseOrderNo());
		contentValues.put("sku", product.getSku());
		contentValues.put("num", product.getNum() + product.getGiftNum());
		contentValues.put("on_shelfed_num", product.getInboundedNum());
		contentValues.put("gift_num", product.getGiftNum());
		contentValues.put("owner_code", product.getOwnerCode());
		contentValues.put("owner_name", "");
		contentValues.put("unit", product.getUnit());
		contentValues.put("name", product.getName());
		contentValues.put("specification", product.getSpecification());
		contentValues.put("period", product.getPeriod());
		contentValues.put("self_life", product.getSelfLife());
		return st.insert("pre_purchase_product", contentValues);
	}

	public void updateStatus(String id, Integer status) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues contentValues = new ContentValues();
		contentValues.put("status", status);
		st.updateById("pre_purchase_product", id, contentValues);
	}

	public void updateNum(String purchaseOrderNo, String sku, int onShelfedNum) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues contentValues = new ContentValues();
		contentValues.put("on_shelfed_num", onShelfedNum);
		st.update("pre_purchase_product", contentValues, "purchase_order_no=? and sku= ?",
				new String[] { purchaseOrderNo, sku });
	}

	public List<PrePurchaseProductResponse> getList(String purchaseOrderNo, String sku) {
		if (StringUtil.empty(purchaseOrderNo) || StringUtil.empty(sku)) {
			return null;
		}
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		List<PrePurchaseProductResponse> list = st.queryForList(new RowMapper<PrePurchaseProductResponse>() {
			@Override
			public PrePurchaseProductResponse mapRow(Cursor cursor, int index) {
				PrePurchaseProductResponse product = new PrePurchaseProductResponse();
				product.setId(cursor.getLong(cursor.getColumnIndex("id")));
				product.setPurchaseOrderNo(cursor.getString(cursor.getColumnIndex("purchase_order_no")));
				product.setSku(cursor.getString(cursor.getColumnIndex("sku")));
				product.setNum(DbUtil.getInt("num", cursor));
				product.setInboundedNum(DbUtil.getInt("on_shelfed_num", cursor));
				product.setGiftNum(DbUtil.getInt("gift_num", cursor));
				product.setOwnerCode(DbUtil.getString("owner_code", cursor));
				product.setUnit(DbUtil.getString("unit", cursor));
				product.setName(DbUtil.getString("name", cursor));
				product.setSelfLife(DbUtil.getInt("self_life", cursor));
				product.setSpecification(DbUtil.getString("specification", cursor));
				product.setPeriod(DbUtil.getString("period", cursor));
				return product;

			}
		}, "select id,purchase_order_no, sku,num,on_shelfed_num,self_life,gift_num,unit,name,owner_code,owner_name,specification,period from pre_purchase_product where purchase_order_no=? and sku=?",
				new String[] { purchaseOrderNo, sku });
		return list;

	}

	public List<PrePurchaseProductResponse> getList(String purchaseOrderNo) {
		if (StringUtil.empty(purchaseOrderNo)) {
			return null;
		}
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		List<PrePurchaseProductResponse> list = st.queryForList(new RowMapper<PrePurchaseProductResponse>() {
			@Override
			public PrePurchaseProductResponse mapRow(Cursor cursor, int index) {
				PrePurchaseProductResponse product = new PrePurchaseProductResponse();
				product.setId(cursor.getLong(cursor.getColumnIndex("id")));
				product.setPurchaseOrderNo(cursor.getString(cursor.getColumnIndex("purchase_order_no")));
				product.setSku(cursor.getString(cursor.getColumnIndex("sku")));
				product.setNum(DbUtil.getInt("num", cursor));
				product.setInboundedNum(DbUtil.getInt("on_shelfed_num", cursor));
				product.setGiftNum(DbUtil.getInt("gift_num", cursor));
				product.setOwnerCode(DbUtil.getString("owner_code", cursor));
				product.setUnit(DbUtil.getString("unit", cursor));
				product.setName(DbUtil.getString("name", cursor));
				product.setSelfLife(DbUtil.getInt("self_life", cursor));
				product.setSpecification(DbUtil.getString("specification", cursor));
				product.setPeriod(DbUtil.getString("period", cursor));
				return product;

			}
		}, "select id,purchase_order_no, sku,num,on_shelfed_num,self_life,gift_num,unit,name,owner_code,owner_name,specification,period from pre_purchase_product where purchase_order_no=?",
				new String[] { purchaseOrderNo });
		return list;

	}
	
	public PrePurchaseProductResponse getProduct(String purchaseOrderNo, String sku) {
		if (StringUtil.empty(purchaseOrderNo) || StringUtil.empty(sku)) {
			return null;
		}
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		PrePurchaseProductResponse prd = st.queryForObject(new RowMapper<PrePurchaseProductResponse>() {
			@Override
			public PrePurchaseProductResponse mapRow(Cursor cursor, int index) {
				PrePurchaseProductResponse product = new PrePurchaseProductResponse();
				product.setId(cursor.getLong(cursor.getColumnIndex("id")));
				product.setPurchaseOrderNo(cursor.getString(cursor.getColumnIndex("purchase_order_no")));
				product.setSku(cursor.getString(cursor.getColumnIndex("sku")));
				product.setNum(DbUtil.getInt("num", cursor));
				product.setInboundedNum(DbUtil.getInt("on_shelfed_num", cursor));
				product.setGiftNum(DbUtil.getInt("gift_num", cursor));
				product.setOwnerCode(DbUtil.getString("owner_code", cursor));
				product.setUnit(DbUtil.getString("unit", cursor));
				product.setName(DbUtil.getString("name", cursor));
				product.setSelfLife(DbUtil.getInt("self_life", cursor));
				product.setSpecification(DbUtil.getString("specification", cursor));
				product.setPeriod(DbUtil.getString("period", cursor));
				return product;
			}
		}, "select id,purchase_order_no, sku,num,on_shelfed_num,self_life,gift_num,unit,name,owner_code,owner_name,specification,period from pre_purchase_product where purchase_order_no=? and sku=?",
				new String[] { purchaseOrderNo, sku });
		return prd;
	}

	public int delByCondtion(String purchaseOrderNo, String sku) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.deleteByCondition("pre_purchase_product", "purchase_order_no=? and sku= ?",
				new String[] { purchaseOrderNo, sku });
	}

	public int delAll() {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.deleteByCondition("pre_purchase_product", null, null);
	}
}
