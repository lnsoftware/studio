package com.imxiaomai.convenience.store.scan.util;

import android.content.Context;

import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.HttpEntity;

/**
 * restful服务调用工具类
 * @author liumin
 *
 */
public class RestServiceUtil {
	 private static AsyncHttpClient client = new AsyncHttpClient();
	 
	 static{
		 client.setTimeout(HttpConstant.CONNECTION_TIME_OUT);
		 client.setMaxConnections(HttpConstant.CONNECTION_MAXIMUM);
//		 client.addHeader(AsyncHttpClient.HEADER_CONTENT_TYPE, "application/json");
	 }
	  
	  public static AsyncHttpClient getIntance() {
		  return client;
	  }

	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.get(getAbsoluteUrl(url), params, responseHandler);
	  }
	  
	  public static void getWeb(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.get(context, url, params, responseHandler);
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.post(getAbsoluteUrl(url), params, responseHandler);
	  }
	  
	  public static void post(Context context, String url, HttpEntity entity, ResponseHandlerInterface responseHandler) {
		  client.post(context, getAbsoluteUrl(url), entity, HttpConstant.CONTENT_TYPE, responseHandler);
	  }
	  public static void post(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
		  client.post(context, getAbsoluteUrl(url), entity, contentType, responseHandler);
	  }
	  
	  public static void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.post(context, getAbsoluteUrl(url), params, responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
		  System.out.println("lxm:" + HttpConstant.SERVER_URL + relativeUrl);
	      return HttpConstant.SERVER_URL + relativeUrl;
	  }
}
