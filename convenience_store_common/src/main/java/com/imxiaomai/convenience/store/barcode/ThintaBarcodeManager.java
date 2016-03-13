package com.imxiaomai.convenience.store.barcode;

import com.thinta.device.Laser;

import android.os.AsyncTask;

public class ThintaBarcodeManager implements BaseBarcodeManager {
	private static final String TAG = "ThintaBarcodeManager";
	private ResultListener mListener;
	private boolean isScaning = false;
	private boolean isInit;

	private static ThintaBarcodeManager instance;

	public static ThintaBarcodeManager getInstance() {
		if (instance == null) {
			instance = new ThintaBarcodeManager();
		}
		return instance;
	}

	@Override
	public void init() {
		if (isInit) return;

		try {
			boolean r;
			r = Laser.open();
			if (r) {
				r = Laser.init();
//				L.d(TAG, "scan init");
				isInit = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
//		L.d(TAG, "scan destroy");
		isInit = false;
		try {
            Laser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		instance = null;
	}

	@Override
	public void scan() {
		if (!isInit) init();
		if (isScaning) return;

//		L.d(TAG, "scan start");

		new AsyncTask<Void, Void, String>() {
			@Override
			protected void onPreExecute() {
				isScaning = true;
			}

			@Override
			protected String doInBackground(Void... params) {
				return Laser.softScan();
			}

			@Override
			protected void onPostExecute(String result) {
				isScaning = false;
//				L.d(TAG, "scan end >> result=" + result);
				if (mListener != null) mListener.onResult(result);
			}
		}.execute();
	}

	@Override
	public void setResultListener(ResultListener listener) {
		this.mListener = listener;
	}
}
