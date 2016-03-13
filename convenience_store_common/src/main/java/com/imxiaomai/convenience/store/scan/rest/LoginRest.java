package com.imxiaomai.convenience.store.scan.rest;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.google.gson.Gson;
import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.imxiaomai.convenience.store.scan.http.AsyncHttpResponseHandlerBase;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.model.EshopEmpRequest;
import com.imxiaomai.convenience.store.scan.model.EshopEmpResponse;
import com.imxiaomai.convenience.store.scan.util.GsonUtil;
import com.imxiaomai.convenience.store.scan.util.RestServiceUtil;

public class LoginRest {
	
	/**
	 * 登录接口
	 * 
	 * @param context
	 * @param shopUser
	 * @param serviceResult
	 * @throws UnsupportedEncodingException
	 */
	public void createLogin(Context context, EshopEmpRequest shopUser, ServiceResult serviceResult) 
			throws UnsupportedEncodingException {
		StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(shopUser));
		RestServiceUtil.post(context, HttpConstant.GET_URL_LOGIN, entity, new AsyncHttpResponseHandlerBase(serviceResult));
	}
	
	/**
	 * 解析json数据
	 * 
	 * @param json
	 * @return
	 */
	public EshopEmpResponse parseLoginInfo(String json) {
		try {
			Gson gson = GsonUtil.getInstance();
	        return gson.fromJson(json, EshopEmpResponse.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
}
