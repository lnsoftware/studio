package com.imxiaomai.convenience.store.scan.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.imxiaomai.convenience.store.scan.StoreApplication;
import com.imxiaomai.convenience.store.scan.constants.AppConstants;
import com.imxiaomai.convenience.store.scan.db.DBManager;
import com.imxiaomai.convenience.store.scan.model.InBoundRevokeProdOnShelfResponse;

import java.util.ArrayList;

/**
 * 返架商品本地数据库管理类
 * Created by Li dajun
 * Date: 2015-10-31
 * Time: 09:39
 */
public class RevokeManager {
    private static RevokeManager sRevokeManager = null;
    private static DBManager sDBManager = null;
    /**
     * 数据库
     */
    private SQLiteDatabase mSqLiteDatabase;
    /**
     * 指针实例
     */
    private Cursor mCursor;

    private RevokeManager() {
        sDBManager = DBManager.getInstance(StoreApplication.getContext(), AppConstants.DATABASE_NAME);
        mSqLiteDatabase = sDBManager.getDatabaseHelper().getWritableDatabase();
    }

    public static RevokeManager getInstance() {
        if (sRevokeManager == null) {
            sRevokeManager = new RevokeManager();
        }
        return sRevokeManager;
    }


    /**
     * 表中是否有数据
     *
     * @return
     */
    public boolean isEmpty() {
        mCursor = mSqLiteDatabase.rawQuery("select * from revoke_product", new String[]{});
        return !mCursor.moveToNext();
    }

    /**
     * 得到表中所有的订单号
     *
     * @return
     */
    public ArrayList<String> getOrderNo() {
        ArrayList<String> orderNoList = new ArrayList<String>();
        mCursor = mSqLiteDatabase.rawQuery("select distinct order_no from revoke_product where eshop_no = ? ", 
        		new String[]{AccountManager.getInstance().getShopCode()});
        while (mCursor.moveToNext()) {
            String order_no = mCursor.getString(mCursor.getColumnIndex("order_no"));
            if (!TextUtils.isEmpty(order_no) && !orderNoList.contains(order_no)) {
                orderNoList.add(order_no);
            }
        }
        return orderNoList;
    }

    /**
     * 得到表中所有的销退单号
     *
     * @return
     */
    public ArrayList<String> getRevokeNo() {
        ArrayList<String> returnNoList = new ArrayList<String>();
        mCursor = mSqLiteDatabase.rawQuery("select revoke_no from revoke_product", new String[]{});
        while (mCursor.moveToNext()) {
            String revoke_no = mCursor.getString(mCursor.getColumnIndex("revoke_no"));
            if (!TextUtils.isEmpty(revoke_no) && !returnNoList.contains(revoke_no)) {
                returnNoList.add(revoke_no);
            }
        }
        return returnNoList;
    }



    /**
     * 通过销退单号得到商品的明细
     *
     * @param revokeNo
     * @return
     */
    public ArrayList<InBoundRevokeProdOnShelfResponse> getProductListForRevokeNo(String revokeNo) {
        ArrayList<InBoundRevokeProdOnShelfResponse> list = new ArrayList<InBoundRevokeProdOnShelfResponse>();
        InBoundRevokeProdOnShelfResponse bean;
        mCursor = mSqLiteDatabase.rawQuery("select * from revoke_product where revoke_no=?", new String[]{revokeNo});
        while (mCursor.moveToNext()) {
            bean = new InBoundRevokeProdOnShelfResponse();
            bean.setRevokeNo(revokeNo);
            String sku = mCursor.getString(mCursor.getColumnIndex("sku"));
            bean.setSku(sku);
            String num = mCursor.getString(mCursor.getColumnIndex("num"));
            bean.setNum(Integer.valueOf(num));
            String name = mCursor.getString(mCursor.getColumnIndex("name"));
            bean.setName(name);
            int inbounded_num = mCursor.getInt(mCursor.getColumnIndex("inbounded_num"));
            bean.setInboundedNum(inbounded_num);
            int sumNum = mCursor.getInt(mCursor.getColumnIndex("sum_num"));
            bean.setSumNum(sumNum);
            list.add(bean);
        }
        return list;
    }




    /**
     * 跟据销退单号,判断是不是可以删除,如果这个订单的商品有过改变就不允许删除
     * @param revokeNo
     * @return
     */
    public boolean mayDeleteForRevokeNo(String revokeNo) {
        mCursor = mSqLiteDatabase.rawQuery("select operate_num from revoke_product where revoke_no=?", new String[]{revokeNo});
        while (mCursor.moveToNext()) {
            String operate_num = mCursor.getString(mCursor.getColumnIndex("operate_num"));
            if (!TextUtils.isEmpty(operate_num)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 跟据销退号删除数据
     *
     * @param revokeNo
     * @return
     */
    public void deleteRevokeNo(String revokeNo) {
        mSqLiteDatabase.execSQL("delete from revoke_product where revoke_no=?", new String[]{revokeNo});
    }

    /**
     * 跟据销退单号和sku商品数据
     *
     * @param sku
     * @return
     */
    public void deleteProductForRevokeNoAndSku(String revokeNo, String sku) {
        mSqLiteDatabase.execSQL("delete from revoke_product where revoke_no=? and sku=?", new String[]{revokeNo, sku});
        if (getProductListForRevokeNo(revokeNo).size() > 0) {
            mSqLiteDatabase.execSQL("update revoke_product set operate_num="+"1"+" where revoke_no=?",new String[]{revokeNo});
        }
    }

    /**
     * 保存销退数据到本地
     *
     * @param revokeProdsList
     * @param revokeNo
     * @param mayDelete
     */
    public void saveReturnShelfByRevokeNo(ArrayList<InBoundRevokeProdOnShelfResponse> revokeProdsList, String revokeNo, String mayDelete) {
        mSqLiteDatabase.beginTransaction();//开启事务
        try {
            for (InBoundRevokeProdOnShelfResponse bean : revokeProdsList) {
                mSqLiteDatabase.execSQL("insert into revoke_product (eshop_no, revoke_no, sku, num, sum_num, name,  inbounded_num, operate_num) values('" + bean.getEshopNo() + "','" + revokeNo + "','" + bean.getSku() + "','" + bean.getNum() + "','" + bean.getNum() + "','" + bean.getName() + "','" + bean.getInboundedNum() + "','" + mayDelete  + "');");
            }
            mSqLiteDatabase.setTransactionSuccessful();//设置事务的标志为True
        } finally {
            mSqLiteDatabase.endTransaction();
            //结束事务,有两种情况：commit,rollback,
            //事务的提交或回滚是由事务的标志决定的,如果事务的标志为True，事务就会提交，否侧回滚,默认情况下事务的标志为False
        }
    }
}
