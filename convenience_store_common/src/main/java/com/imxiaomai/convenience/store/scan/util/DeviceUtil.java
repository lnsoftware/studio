package com.imxiaomai.convenience.store.scan.util;

import java.util.UUID;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class DeviceUtil {
	/**
	 * 获取mac地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getMac(Context context) {
		if (context == null) {
			return "";
		}

		String mac = null;
		try {
			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			if (null != info) {
				mac = info.getMacAddress();
			}
		} catch (Exception e) {}

		return null != mac ? Base64.encodeToString(mac.getBytes(), Base64.DEFAULT) : "";
	}

	/**
	 * 获取设备唯一标识
	 * 
	 * @param context
	 * @return
	 */
	public synchronized static String getUUID(Context context) {
		if (context == null) {
			return "";
		}

		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
		UUID uuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		return uuid.toString();
	}

	/**
	 * 获取设备ID,友盟统计分析使用
	 * 
	 * @param context
	 * @return
	 */
	public static String getDevId(Context context) {
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice = tm.getDeviceId();
		return tmDevice;
	}

	/**
	 * 渠道号
	 * 
	 * @return
	 */
	public static String getChannel(Context context) {
		String metaData = null;
		try {
			ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);
			if (info != null && info.metaData != null) {
				metaData = info.metaData.getString("UMENG_CHANNEL");
			} else {
				metaData = "unknow";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return metaData;
	}

	/**
	 * Return true if sdcard is available.
	 * 
	 * @return
	 */
	public static boolean isSdCardExist() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * get application version name defined in manifest
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		PackageManager pm = context.getPackageManager();
		String verName = "";
		try {
			PackageInfo pInfo = pm.getPackageInfo(context.getPackageName(), 0);
			verName = pInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}

	/**
	 * get application version code defined in manifest
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		PackageManager pm = context.getPackageManager();
		int verCode = 0;
		try {
			PackageInfo pInfo = pm.getPackageInfo(context.getPackageName(), 0);
			verCode = pInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;
	}
	
	/** * 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/** * 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理） 
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象 
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接 
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * public static void addShortCut(Context context, String name, int ic) { context = context.getApplicationContext();
	 * Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT"); // 是否允许重复创建
	 * shortcut.putExtra("duplicate", false);
	 * 
	 * // 设置名称 shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
	 * 
	 * //设置桌面快捷方式的图标 Parcelable icon = Intent.ShortcutIconResource.fromContext(context, ic);
	 * shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
	 * 
	 * //点击快捷方式的操作 Intent intent = new Intent(Intent.ACTION_MAIN); intent.setClass(context, LoginActivity.class);
	 * 
	 * // 设置启动程序 shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
	 * 
	 * //广播通知桌面去创建 context.sendBroadcast(shortcut); }
	 */

//	public static void addShortCut(Context context) {
//		Intent addShortCut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//		// 不能重复创建快捷方式
//		addShortCut.putExtra("duplicate", false);
//		String title = context.getString(R.string.app_name);// 名称
//		Parcelable icon = Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher);// 图标
//		// 点击快捷方式后操作Intent,快捷方式建立后，再次启动该程序
//		Intent intent = new Intent(context, LoginActivity.class);
//		// 设置快捷方式的标题
//		addShortCut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
//		// 设置快捷方式的图标
//		addShortCut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
//		// 设置快捷方式对应的Intent
//		addShortCut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
//		// 发送广播添加快捷方式
//		context.sendBroadcast(addShortCut);
//	}

	public static void showInputMethod(Context context, View forceView) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(forceView, InputMethodManager.SHOW_FORCED);
	}

	public static void hideInputMethod(Context context, View forceView) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(forceView.getWindowToken(), 0); //强制隐藏键盘  
	}
	
	public static void hideInputMethod(Context context, IBinder windowToken) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//		if(imm.isActive())toggleInputMethod(context);
		imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS); //强制隐藏键盘  
	}

	public static void toggleInputMethod(Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
