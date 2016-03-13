package com.imxiaomai.convenience.store.scan.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.imxiaomai.convenience.store.scan.constants.HttpStatus;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;
import com.imxiaomai.convenience.store.scan.manager.GbCodeManager;
import com.imxiaomai.convenience.store.scan.manager.ProductManager;
import com.imxiaomai.convenience.store.scan.model.ProductRequest;
import com.imxiaomai.convenience.store.scan.model.ProductResponse;
import com.imxiaomai.convenience.store.scan.rest.BaseProductRest;
import com.imxiaomai.convenience.store.scan.util.SystemDialog;

public class LoadDataService extends BaseLoadService {
    private BaseProductRest rest;
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        loadProduct();
        return super.onStartCommand(intent, flags, startId);
    }

    private void loadProduct() {
    	showDialog("加载商品数据...");
    	
        if (rest == null) {
            rest = new BaseProductRest();
        }
        ProductRequest request = new ProductRequest();
        request.setEshopNo(AccountManager.getInstance().getShopCode());
        try {
			rest.getPrdList(getApplicationContext(), request, new LoadProductServiceResult());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }

    private class LoadProductServiceResult implements ServiceResult {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                JSONObject json = new JSONObject(new String(responseBody));
                if (json.getInt("code") == HttpStatus.OK.getCode()) {
                    final List<ProductResponse> beanList = rest.parseProductList(json.getString("result"));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            save(beanList);
                        }
                    }).start();
                } else {
                    loadDataFailed(TextUtils.isEmpty(json.getString("message")) ? "获取商品信息失败" : json.getString("message"));
                    stopSelf();
                }
            } catch (JSONException e) {
                loadDataFailed("获取商品信息失败");
                stopSelf();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            mHandler.sendEmptyMessage(SystemDialog.NETWORK_FAIL);
            stopSelf();
        }
    }

    public void save(List<ProductResponse> beanList) {
        ProductManager prdm = ProductManager.getInstance(this);
        GbCodeManager gbm = GbCodeManager.getInstance(this);
        if (beanList != null && beanList.size() > 0) {
            prdm.delAll();
            gbm.delAll();
        }

        prdm.save(beanList);
        gbm.save(beanList);
        
        mHandler.sendEmptyMessage(SystemDialog.LOADDATA_SUCCESS);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
