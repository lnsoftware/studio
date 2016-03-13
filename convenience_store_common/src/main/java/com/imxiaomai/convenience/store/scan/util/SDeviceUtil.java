package com.imxiaomai.convenience.store.scan.util;

import java.util.UUID;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.imxiaomai.convenience.store.scan.StoreApplication;

/**
 * Created with IntelliJ IDEA. 
 * User: lixiaoming 
 * Date: 13-5-28 Time: 下午3:54
 */
public class SDeviceUtil {
	
	public static final int SCREEN_240P = 240;
	public static final int SCREEN_360P = 360;
	public static final int SCREEN_480P = 480;
	public static final int SCREEN_720P = 720;
	public static final int SCREEN_1080P = 1080;
	public static final int SCREEN_1280P = 1280;

	/**
	 * 获取屏幕分辨率
	 */
	public static int getDeviceScreen(Context context){
		DisplayMetrics dm = null;
		dm = context.getResources().getDisplayMetrics();
		if (dm == null) {
			return -1;
		}
		int screenWidth = dm.widthPixels;
		if (screenWidth <= SCREEN_240P) {
			return SCREEN_240P;
		}else if (screenWidth > SCREEN_240P && screenWidth <= SCREEN_360P) {
			return SCREEN_360P;
		}else if (screenWidth > SCREEN_360P && screenWidth <= SCREEN_480P) {
			return SCREEN_480P;
		}else if (screenWidth > SCREEN_480P && screenWidth <= SCREEN_720P) {
			return SCREEN_720P;
		}else if (screenWidth > SCREEN_720P && screenWidth <= SCREEN_1080P) {
			return SCREEN_1080P;
		}else {
			return SCREEN_1280P;
		}
	}
	
	/**
	 * 获取手机型号
	 * 
	 * @return 手机型号串
	 */
	public static String getDeviceModel() {
		Build build = new Build();
		return build.MODEL;
	}

	/**
	 * 获取手机的IMEI串号
	 */
	public static String getImei(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		String imei = tm.getDeviceId();
		if (imei == null) {
			imei = "unknown_imei";
		}

		return imei;
	}

	/**
	 * 检查可用内存大小,返回数据单位为字节
	 */
	public long getAvailableMemorySize(Context context) {
		return 0;
	}

	/**
	 * 查看sdcard是否被挂载
	 */
	public boolean isMountSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 检查网络状态是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}

		return false;
	}

	/**
	 * 获取分配的内存大小
	 * 
	 * @param context
	 * @return
	 */
	public static int getMemoryClass(Context context) {
		return ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
	}

	/**
	 * 获取UUID
	 */
	public static String getUUID() {
		final UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	/**
	 * 获取屏幕宽度
	 *
	 * @param activity
	 * @return
	 */
	public static int getScreenWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	/**
	 * 获取屏幕宽度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int screenWidth = wm.getDefaultDisplay().getWidth();
		return screenWidth;
	}

	/**
	 * 获取屏幕宽度
	 * @return
	 */
	public static int getScreenWidth() {
		return getScreenWidth(StoreApplication.getContext());
	}

	/**
	 * 获取屏幕高度
	 *
	 * @param activity
	 * @return
	 */
	public static int getScreenHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenHeight = dm.heightPixels;
		return screenHeight;
	}

	/**
	 * 获取屏幕高度
	 *
	 * @param activity
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int screenHeight = wm.getDefaultDisplay().getHeight();
		return screenHeight;
	}

	/**
	 * 获取屏幕高度
	 *
	 * @param activity
	 * @return
	 */
	public static int getScreenHeight() {
		return getScreenHeight(StoreApplication.getContext());
	}

	/**
     * 获取屏幕显示像素密度比例
     *
     * @return 屏幕显示像素密度比例
     */
    public static float getDisplayDensity(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.density;
    }

    /**
     * 获取屏幕显示像素密度比例
     *
     * @return 屏幕显示像素密度比例
     */
    public static float getDisplayDensity() {
    	return getDisplayDensity(StoreApplication.getContext());
    }
	
}
