package com.imxiaomai.convenience.store.scan.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.imxiaomai.convenience.store.scan.http.AsyncHttpResponseHandlerBase;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.model.OutBoundAffirmRequest;
import com.imxiaomai.convenience.store.scan.model.OutBoundAffirmStatisticsResponse;
import com.imxiaomai.convenience.store.scan.util.GsonUtil;
import com.imxiaomai.convenience.store.scan.util.RestServiceUtil;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class OutBoundRest {
	
	/**
	 * 获取出库的订单信息
	 * 
	 * @param serviceResult
	 */
	public void getOutBoundOrderInfo(Context context, OutBoundAffirmRequest req, ServiceResult serviceResult) throws UnsupportedEncodingException {
		StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(req));
		RestServiceUtil.post(context, HttpConstant.POST_ORDER_STATICS, entity, new AsyncHttpResponseHandlerBase(serviceResult));
	}

	/**
	 * 出库订单确认
	 *
	 * @param serviceResult
	 */
	public void onOutBoundConfirm(Context context, OutBoundAffirmRequest req, ServiceResult serviceResult)
			throws UnsupportedEncodingException{
		StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(req));
		RestServiceUtil.post(context, HttpConstant.ORDER_OFF_SHELF, entity, new AsyncHttpResponseHandlerBase(serviceResult));
	}
	
	/**
	 * 解析json数据
	 * 
	 * @param json
	 * @return
	 */
	public OutBoundAffirmStatisticsResponse parseOutBoundOrderInfo(String json) {
		try {
			Gson gson = GsonUtil.getInstance();
	        return gson.fromJson(json, OutBoundAffirmStatisticsResponse.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
}
