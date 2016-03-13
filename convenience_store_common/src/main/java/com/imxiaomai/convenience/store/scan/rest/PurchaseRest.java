package com.imxiaomai.convenience.store.scan.rest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.entity.StringEntity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.imxiaomai.convenience.store.scan.http.AsyncHttpResponseHandlerBase;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.model.InBoundAffirmRequest;
import com.imxiaomai.convenience.store.scan.model.InboundRequest;
import com.imxiaomai.convenience.store.scan.model.PrePurchaseProductResponse;
import com.imxiaomai.convenience.store.scan.util.GsonUtil;
import com.imxiaomai.convenience.store.scan.util.RestServiceUtil;
import com.imxiaomai.convenience.store.scan.util.StringUtil;

import android.content.Context;

public class PurchaseRest {

    public void getPrdList(Context context, InBoundAffirmRequest req, ServiceResult serviceResult)
            throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(req));
        RestServiceUtil.post(context, HttpConstant.GET_PURCHASE_PRD_LIST, entity, new AsyncHttpResponseHandlerBase(serviceResult));
    }

	public void onShelf(Context context, InboundRequest request, ServiceResult serviceResult)
			throws UnsupportedEncodingException {
		StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(request));
		RestServiceUtil.post(context, HttpConstant.PURCHASE_ON_SELF, entity,
				new AsyncHttpResponseHandlerBase(serviceResult));
	}

	public List<PrePurchaseProductResponse> parsePrdList(String json) {
		try {
			Gson gson = GsonUtil.getInstance();
			List<PrePurchaseProductResponse> beanList = gson.fromJson(json,
					new TypeToken<List<PrePurchaseProductResponse>>() {
					}.getType());
			return beanList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
