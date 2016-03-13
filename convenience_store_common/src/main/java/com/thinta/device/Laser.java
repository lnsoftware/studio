package com.thinta.device;

public class Laser {
	static{
		System.loadLibrary("laser");
	}

	/* open 		打开激光头模块
	 * paramters:
	 * 				NONE
	 * return:
	 * 				true:	成功
	 * 				false:	失败
	 */
	public native static boolean open();

	/* close 		关闭激光头模块
	 * paramters:
	 * 				NONE
	 * return:
	 * 				true:	成功
	 * 				false:	失败
	 */
	public native static boolean close();
	
	/* init			初始激光头模块通讯
	 * 				必须调用init方法softScan函数才能返回扫描结果 
	 * paramters:
	 * 				NONE
	 * return:
	 * 				true:	success
	 * 				false:	faild
	 */
	public native static boolean init();
	
	/* softScan		激光头模块出光，并返回扫描到的条码结果
	 * paramters:
	 * 				NONE
	 * return:
	 * 				String:	barcode data
	 */
	public native static String softScan();
	
	/* paramSend	设置激光头参数
	 * paramters:
	 * 				paramNum	设置控制码（见激光头参数表）
	 * 				value		控制码对应的值（见激光头参数表）
	 * return:
	 * 				true:	成功
	 * 				false:	失败
	 */
	public native static boolean paramSend(int paramNum, byte[] values);
	
	/* paramRequest	获取激光头参数
	 * paramters:
	 * 				paramNum	设置控制码（见激光头参数表）
	 * return:
	 * 				byte[]:	控制码对应的值
	 */
	public native static byte[] paramRequest(int paramNum);
}
