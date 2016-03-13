package com.imxiaomai.convenience.store.scan.rest;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.google.gson.Gson;
import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.imxiaomai.convenience.store.scan.http.AsyncHttpResponseHandlerBase;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.model.AppVersionRequest;
import com.imxiaomai.convenience.store.scan.model.AppVersionResponse;
import com.imxiaomai.convenience.store.scan.util.GsonUtil;
import com.imxiaomai.convenience.store.scan.util.RestServiceUtil;

public class CheckUpdateRest {
	
	/**
	 * 获取app升级信息
	 * 
	 * @param context
	 * @param appVersion
	 * @param serviceResult
	 * @throws UnsupportedEncodingException
	 */
	public void createCheckUpdate(Context context, AppVersionRequest appVersion, ServiceResult serviceResult) 
			throws UnsupportedEncodingException{
		StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(appVersion));
		RestServiceUtil.post(context, HttpConstant.GET_CHECK_UPDATE, entity, new AsyncHttpResponseHandlerBase(serviceResult));
	}
	
	/**
	 * 解析json数据
	 * 
	 * @param json
	 * @return
	 */
	public AppVersionResponse parseCheckUpdateInfo(String json) {
		try {
			Gson gson = GsonUtil.getInstance();
	        return gson.fromJson(json, AppVersionResponse.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
}
