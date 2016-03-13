package com.imxiaomai.convenience.store.scan.rest;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.imxiaomai.convenience.store.scan.http.AsyncHttpResponseHandlerBase;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.model.StockInventoryResult;
import com.imxiaomai.convenience.store.scan.model.StockInventoryResultRequest;
import com.imxiaomai.convenience.store.scan.util.GsonUtil;
import com.imxiaomai.convenience.store.scan.util.RestServiceUtil;
import com.imxiaomai.convenience.store.scan.util.StringUtil;
/***
 * 盘点
 * @author li dajun
 *
 */
public class InventoryRest {

    public void getPrdList(String purchaseOrderNo, ServiceResult serviceResult) {
         String url = StringUtil.urlParse(HttpConstant.GET_PURCHASE_PRD_LIST, purchaseOrderNo);
         RestServiceUtil.get(url, null, new AsyncHttpResponseHandlerBase(serviceResult));
    }

    public void onShelf(Context context, StockInventoryResultRequest request, ServiceResult serviceResult)
              throws UnsupportedEncodingException {
         StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(request));
         RestServiceUtil.post(context, HttpConstant.INVENTORY_PRD_INFO, entity,
                   new AsyncHttpResponseHandlerBase(serviceResult));
    }

//    public List<PrePurchaseProductResponse> parsePrdList(String json) {
//         try {
//              Gson gson = GsonUtil.getInstance();
//              List<PrePurchaseProductResponse> beanList = gson.fromJson(json,
//                        new TypeToken<List<PrePurchaseProductResponse>>() {
//                        }.getType());
//              return beanList;
//         } catch (Exception e) {
//              e.printStackTrace();
//         }
//         return null;
//    }
}
