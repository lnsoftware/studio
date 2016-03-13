package com.imxiaomai.convenience.store.scan.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	public static boolean isAvaliable(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(
				Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		
		if (netInfo == null || !netInfo.isAvailable()) {
			return false;
		} 
		return true;
	}
}
