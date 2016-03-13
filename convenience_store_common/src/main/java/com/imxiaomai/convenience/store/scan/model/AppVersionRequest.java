package com.imxiaomai.convenience.store.scan.model;


/**
 * AppVersion：APP版本更新控制表实体类
 * 
 * @author zhc2054
 * @version 2015-8-10 11:32:54
 * 
 */
public class AppVersionRequest extends BaseRequest {

    /** 序列化标识 */
	private static final long serialVersionUID = 1L;
	
    /** app类型 */
    private String appType; 
    /** 仓库编号 */
    private String eshopNo;
    
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getEshopNo() {
		return eshopNo;
	}
	public void setEshopNo(String eshopNo) {
		this.eshopNo = eshopNo;
	} 
    
}
