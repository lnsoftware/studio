package com.imxiaomai.convenience.store.scan.http;

import org.apache.http.Header;

public interface ServiceResult {
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody);
	public void onFailure(int statusCode, Header[] headers,
						  byte[] responseBody, Throwable error);
}
