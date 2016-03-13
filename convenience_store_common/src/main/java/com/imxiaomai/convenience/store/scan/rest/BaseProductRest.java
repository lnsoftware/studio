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
import com.imxiaomai.convenience.store.scan.model.ProductRequest;
import com.imxiaomai.convenience.store.scan.model.ProductResponse;
import com.imxiaomai.convenience.store.scan.util.GsonUtil;
import com.imxiaomai.convenience.store.scan.util.RestServiceUtil;

public class BaseProductRest {

	public void getPrdList(Context context, ProductRequest product, ServiceResult serviceResult) 
			throws UnsupportedEncodingException {
		StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(product));
		RestServiceUtil.post(context, HttpConstant.LOAD_BASE_PRODUCT, entity, new AsyncHttpResponseHandlerBase(serviceResult));
	}

	public List<ProductResponse> parseProductList(String json) {
		try {
			Gson gson = GsonUtil.getInstance();
			List<ProductResponse> beanList = gson.fromJson(json,
					new TypeToken<List<ProductResponse>>() {
					}.getType());
			return beanList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
