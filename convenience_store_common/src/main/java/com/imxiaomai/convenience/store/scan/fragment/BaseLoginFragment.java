package com.imxiaomai.convenience.store.scan.fragment;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.imxiaomai.convenience.store.scan.constants.HttpStatus;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.model.EshopEmpRequest;
import com.imxiaomai.convenience.store.scan.model.EshopEmpResponse;
import com.imxiaomai.convenience.store.scan.rest.LoginRest;
import com.imxiaomai.convenience.store.scan.rest.UploadCrashFileRest;
import com.imxiaomai.convenience.store.scan.service.DownloadService;

public abstract class BaseLoginFragment extends BaseFragment {
	
	protected EshopEmpResponse user;
	protected LoginRest loginRest;
	
	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LOADDATA_SUCCESS:
			    loginSuccess();
				break;
			case LOADDATA_FAILED:
				loginFailed();
				break;
			}
		}
	};
	
	protected abstract void loginSuccess();
	
	protected abstract void loginFailed();
	
	protected void startService() {
		if(getActivity() != null) {
			Intent downloadService = new Intent(getActivity(), DownloadService.class);
			getActivity().startService(downloadService);
		}
	}

	protected void login(String userName,String password) {
		if(loginRest == null) {
			loginRest = new LoginRest();
		}
		serviceResult = new LoginServiceResult();
		
		EshopEmpRequest request = new EshopEmpRequest();
		request.setEmpAccount(userName);
		request.setEmpPwd(password);
		
		try {
			loginRest.createLogin(getActivity(), request, serviceResult);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(LOADDATA_FAILED);
		}
	}
	
	protected void checkCrashFileUpload() {
		UploadCrashFileRest uploadCrashFileRest = new UploadCrashFileRest();
		uploadCrashFileRest.checkCrashFileUpload();
	}
	
	protected class LoginServiceResult implements ServiceResult {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			try {
				JSONObject json = new JSONObject(new String(responseBody));
				if (json != null && json.getInt("code") == HttpStatus.OK.getCode()) {
					user = loginRest.parseLoginInfo(json.getString("result"));
					handler.sendEmptyMessage(LOADDATA_SUCCESS);
				} else {
					handler.sendEmptyMessage(LOADDATA_FAILED);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(LOADDATA_FAILED);
			}
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			handler.sendEmptyMessage(LOADDATA_FAILED);
			getLogger().i("lxm-login:%s", "fail");
		}
		
	}
}
