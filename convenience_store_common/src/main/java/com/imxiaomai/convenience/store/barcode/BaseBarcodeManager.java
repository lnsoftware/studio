package com.imxiaomai.convenience.store.barcode;

public interface BaseBarcodeManager {

	/**
	 * 打开激光头模块, 必须在onResume或之后调用.
	 */
	public void init();

	/**
	 * 激光头模块出光，并返回扫描到的条码结果
	 * 
	 * @param context
	 */
	public void scan();

	/**
	 * 
	 */
	public void destroy();

	public void setResultListener(ResultListener listener);

	public interface ResultListener {
		void onResult(String result);
	}
}
