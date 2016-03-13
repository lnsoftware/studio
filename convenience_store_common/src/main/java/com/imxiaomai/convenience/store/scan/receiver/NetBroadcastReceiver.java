package com.imxiaomai.convenience.store.scan.receiver;

import com.imxiaomai.convenience.store.scan.util.NetworkUtil;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class NetBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			if (!NetworkUtil.isAvaliable(context)) {
				ToastUtils.showShort(context, "网络已经断开,请检查网络设置!");
			} 
		}
	}

}
