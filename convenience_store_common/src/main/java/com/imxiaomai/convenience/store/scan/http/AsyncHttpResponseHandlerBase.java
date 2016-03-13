package com.imxiaomai.convenience.store.scan.http;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;


public class AsyncHttpResponseHandlerBase extends AsyncHttpResponseHandler {
	ServiceResult serviceResult;
	public AsyncHttpResponseHandlerBase(ServiceResult serviceResult) {
		this.serviceResult = serviceResult;
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
		serviceResult.onSuccess(statusCode, headers, responseBody);
	}
	
	@Override
	public void onFailure(int statusCode, Header[] headers,
			byte[] responseBody, Throwable error) {
		serviceResult.onFailure(statusCode, headers, responseBody, error);
	}
}