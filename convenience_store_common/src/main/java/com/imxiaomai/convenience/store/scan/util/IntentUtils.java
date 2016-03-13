package com.imxiaomai.convenience.store.scan.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.imxiaomai.convenience.store.scan.activity.AccountInfoActivity;
import com.imxiaomai.convenience.store.scan.activity.LoginActivity;
import com.imxiaomai.convenience.store.scan.activity.MainActivity;
import com.imxiaomai.convenience.store.scan.zxing.ZxScanningActivity;

public class IntentUtils {

	public static void showLoginActivity(Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);
	}
	
	public static void showLoginParamsActivity(Context context, String userName, String password) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.putExtra("userName", userName);
		intent.putExtra("password", password);
		context.startActivity(intent);
	}
   
	public static void showMainActivity(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}
	
	/**
	 * 调转到摄像头扫描
	 * 		
	 * @param context
	 */
	public static void showZxScanningActivity(Activity context) {
		 Intent intent = new Intent(context, ZxScanningActivity.class);
		 context.startActivityForResult(intent, ZxScanningActivity.INTENT_CODE_SCAN);
	}
	
	/**
	 * 跳转到账户信息
	 * 
	 * @param context
	 */
	public static void showAccountInfoActivity(Context context) {
		Intent intent = new Intent(context, AccountInfoActivity.class);
		context.startActivity(intent);
	}
}
