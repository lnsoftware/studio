package com.imxiaomai.convenience.store.scan.rest;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;

import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.imxiaomai.convenience.store.scan.constants.HttpStatus;
import com.imxiaomai.convenience.store.scan.http.AsyncHttpResponseHandlerBase;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;
import com.imxiaomai.convenience.store.scan.util.MD5Util;
import com.imxiaomai.convenience.store.scan.util.RestServiceUtil;
import com.loopj.android.http.RequestParams;

public class UploadCrashFileRest {
	public void uploadCrashFile(File crashFile) {
		try {
			String timeStamp = String.valueOf(System.currentTimeMillis());
			String cipherText = MD5Util.md5AsLowerHex(timeStamp + "cb");
			RequestParams params = new RequestParams();
			params.put("file", crashFile);
			params.put("eshopNo", AccountManager.getInstance().getShopCode());
			    
			String url = HttpConstant.UPLOAD_CRASH_FILE + "/" + timeStamp + "/" + cipherText;
			RestServiceUtil.post(url, params, 
					new AsyncHttpResponseHandlerBase(new UpLoadCrashFileServiceResult(crashFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public  void checkCrashFileUpload() {
		 File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator + "wms"+File.separator + "crash");
		 if(dir.isDirectory() && dir.exists()) {
			 File[] crashFiles = dir.listFiles();
			 for(File crashFile :crashFiles) {
				 this.uploadCrashFile(crashFile);
			 }
		 }
	}
	
	
    class UpLoadCrashFileServiceResult implements ServiceResult {
    	File crashFile;
    	UpLoadCrashFileServiceResult(File crashFile) {
    		this.crashFile = crashFile;
    	}
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			try {
				JSONObject json = new JSONObject(new String(responseBody));
				if (json.getInt("code") == HttpStatus.OK.getCode()) {
					if(this.crashFile.exists()) {
						this.crashFile.delete();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
		}
    	
    }
}
