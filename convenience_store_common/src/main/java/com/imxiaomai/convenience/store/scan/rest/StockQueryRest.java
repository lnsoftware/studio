package com.imxiaomai.convenience.store.scan.rest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.imxiaomai.convenience.store.scan.http.AsyncHttpResponseHandlerBase;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.model.BaseRequest;
import com.imxiaomai.convenience.store.scan.model.StockLocationRequest;
import com.imxiaomai.convenience.store.scan.model.StockLocationResponse;
import com.imxiaomai.convenience.store.scan.util.GsonUtil;
import com.imxiaomai.convenience.store.scan.util.RestServiceUtil;

public class StockQueryRest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
	 * 库存查询
	 * 
	 * @param context
	 * @param req
	 * @param serviceResult
	 * @throws UnsupportedEncodingException
	 */
	public void getStockQueryList(Context context, StockLocationRequest req, ServiceResult serviceResult)
			throws UnsupportedEncodingException {
		StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(req));
		RestServiceUtil.post(context, HttpConstant.STOCK_LOCATION_QUERY, entity, new AsyncHttpResponseHandlerBase(serviceResult));
	}

	public List<StockLocationResponse> parseStockQueryList(String json) {
		try {
			Gson gson = GsonUtil.getInstance();
			List<StockLocationResponse> beanList = gson.fromJson(json, new TypeToken<List<StockLocationResponse>>() {}.getType());
			return beanList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
