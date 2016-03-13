package com.imxiaomai.convenience.store.scan;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.imxiaomai.convenience.store.barcode.BaseBarcodeManager;
import com.imxiaomai.convenience.store.scan.crash.CrashHandler;

public class StoreApplication extends Application {
	private List<WeakReference<Activity>> sGlobalActivityStack = new LinkedList<WeakReference<Activity>>();
	
	private BaseBarcodeManager mBarcodeMng;
	private static Context context;
	
	public BaseBarcodeManager getBarcodeMng() {
		return mBarcodeMng;
	}

	public void setBarcodeMng(BaseBarcodeManager mBarcodeMng) {
		this.mBarcodeMng = mBarcodeMng;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		CrashHandler.getInstance().init(this); 
	}
	
	public static Context getContext() {
		return context;
	}
	
	public void pushStack(Activity activity) {
		for (WeakReference<Activity> ref : sGlobalActivityStack) {
			if (ref.get() == activity) {
				sGlobalActivityStack.remove(activity);
				break;
			}
		}
		sGlobalActivityStack.add(0, new WeakReference<Activity>(activity));
	}

	public void popStack(Activity activity) {
		if (sGlobalActivityStack.size() > 0) {
			WeakReference<Activity> a = sGlobalActivityStack.get(0);
			if (a.get() == activity) {
				sGlobalActivityStack.remove(0);
			} 
		}
	}
	
	public void exit() {
		for (WeakReference<Activity> ref : sGlobalActivityStack) {
			if (ref.get() != null) {
				Activity activity = ref.get();
				activity.finish();
			}
		}
		sGlobalActivityStack.clear();
	}
}
