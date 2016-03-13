package com.imxiaomai.convenience.store.scan.model;

import java.util.Date;

/**
 * AppVersion：APP版本更新控制表实体类
 * 
 * @author zhc2054
 * @version 2015-8-10 11:32:54
 * 
 */
public class AppVersionResponse implements java.io.Serializable  {

    /** 序列化标识 */
	private static final long serialVersionUID = 1L;
	
    /** 唯一ID */
    private Integer id; 
    /** app类型 */
    private String appType; 
    /** 版本号 */
    private Integer versionCode; 
    /** 版本名称 */
    private String versionName; 
    /** 更新提示内容 */
    private String updConent; 
    /** 是否必须下载0：非必须 1：必须 */
    private Integer isMust; 
    /** 下载次数 */
    private Integer downloadCount; 
    /** 操作时间 */
    private Date operateTime; 
    /** 操作人*/
    private String operator; 
    /** 下载url*/
    private String url; 
    /** 便利店编号*/
    private String eshopNo;
    
    public Integer getId(){
        return id;
    }
        
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getAppType(){
        return appType;
    }
        
    public void setAppType(String appType) {
        this.appType = appType;
    }
    
    public Integer getVersionCode(){
        return versionCode;
    }
        
    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }
    
    public String getVersionName(){
        return versionName;
    }
        
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
    
    public String getUpdConent(){
        return updConent;
    }
        
    public void setUpdConent(String updConent) {
        this.updConent = updConent;
    }
    
    public Integer getIsMust(){
        return isMust;
    }
        
    public void setIsMust(Integer isMust) {
        this.isMust = isMust;
    }
    
    public Integer getDownloadCount(){
        return downloadCount;
    }
        
    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
    
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getEshopNo() {
		return eshopNo;
	}

	public void setEshopNo(String eshopNo) {
		this.eshopNo = eshopNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
