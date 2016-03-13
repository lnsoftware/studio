package com.imxiaomai.convenience.store.scan.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.imxiaomai.convenience.store.scan.http.AsyncHttpResponseHandlerBase;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.model.StockInventoryTaskResponse;
import com.imxiaomai.convenience.store.scan.model.StockInventoryTaskResquest;
import com.imxiaomai.convenience.store.scan.util.GsonUtil;
import com.imxiaomai.convenience.store.scan.util.RestServiceUtil;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
/**
 * 盘点网络帮助类
 * @author li dajun
 */
public class InventoryTaskRest  {

//    public void getPrdList(String purchaseOrderNo, ServiceResult serviceResult) {
//         String url = StringUtil.urlParse(HttpConstant.POST_INVENTORY_TASK, purchaseOrderNo);
//         RestServiceUtil.get(url, null, new AsyncHttpResponseHandlerBase(serviceResult));
//    }

    public void getInventoryTask(Context context, StockInventoryTaskResquest request, ServiceResult serviceResult)
              throws UnsupportedEncodingException {
         StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(request));
         RestServiceUtil.post(context, HttpConstant.POST_INVENTORY_TASK, entity,
                   new AsyncHttpResponseHandlerBase(serviceResult));
    }

    public StockInventoryTaskResponse parseInventoryTask(String json) {
         try {
              Gson gson = GsonUtil.getInstance();
              StockInventoryTaskResponse beanList = gson.fromJson(json,
                        new TypeToken<StockInventoryTaskResponse>() {
                        }.getType());
              return beanList;
         } catch (Exception e) {
              e.printStackTrace();
         }
         return null;
    }
}
