package com.imxiaomai.convenience.store.scan.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.imxiaomai.convenience.store.scan.util.ToastUtils;

public class BroadcastManager  {

	public static final String PROMPT_LOCATION_SUCCESS = "location_info_success";
	public static final String PROMPT_LOCATION_FAIL = "location_info_fail";
	public static final String PROMPT_LOCATION_NETWORK_FAIL = "location_info_network_fail";
	public static final String PROMPT_PRODUCT_SUCCESS = "product_info_success";
	public static final String PROMPT_PRODUCT_FAIL = "product_info_fail";
	public static final String PROMPT_PRODUCT_NETWORK_FAIL = "product_info_network_fail";
	public static final String PROMPT_DOWNLOAD = "download_no_update_info";
	
	private static BroadcastManager instance;
	private BroadcastReceiver receiver;
	private Context mContext;
	
	private BroadcastManager() {
	}
	
	public static BroadcastManager getInstance() {
		if (instance == null) {
			instance = new BroadcastManager();
		}
		return instance;
	}
	
	public void init(Context context) {
		mContext = context;
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(PROMPT_LOCATION_SUCCESS);
		filter.addAction(PROMPT_LOCATION_FAIL);
		filter.addAction(PROMPT_LOCATION_NETWORK_FAIL);
		filter.addAction(PROMPT_PRODUCT_SUCCESS);
		filter.addAction(PROMPT_PRODUCT_FAIL);
		filter.addAction(PROMPT_PRODUCT_NETWORK_FAIL);
		filter.addAction(PROMPT_DOWNLOAD);
		
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(mContext);
		receiver  = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(PROMPT_LOCATION_SUCCESS)) {
					ToastUtils.showShort(context, "获取货位信息完成");
				} else if (action.equals(PROMPT_LOCATION_FAIL)) {
					ToastUtils.showShort(context, "获取货位信息失败");
				} else if (action.equals(PROMPT_LOCATION_NETWORK_FAIL)) {
					ToastUtils.showShort(context, "网络不佳或服务端无响应");
				} else if (action.equals(PROMPT_PRODUCT_SUCCESS)) {
					ToastUtils.showShort(context, "获取商品信息完成");
				} else if (action.equals(PROMPT_PRODUCT_FAIL)) {
					ToastUtils.showShort(context, "获取商品信息失败");
				} else if (action.equals(PROMPT_PRODUCT_NETWORK_FAIL)) {
					ToastUtils.showShort(context, "网络不佳或服务端无响应");
				} else if (action.equals(PROMPT_DOWNLOAD)) {
					ToastUtils.showShort(context, "当前已经是最新版本");
				}
			}
			
		};
		
		lbm.registerReceiver(receiver, filter);
	}	
	
	public void sendBroadcast(String action) {
		if (mContext == null) return;
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(mContext);
		lbm.sendBroadcast(new Intent(action));
	}
	
	public void unRegisterBroadcast() {
		if (mContext == null || receiver == null) return;
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(mContext);
		lbm.unregisterReceiver(receiver);
	}
}
