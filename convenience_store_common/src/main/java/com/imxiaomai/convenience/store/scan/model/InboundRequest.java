package com.imxiaomai.convenience.store.scan.model;

import java.util.Date;

/**
 * PrePurchaseProductRequest：采购单上架请求参数
 * 
 * @author zhc2054
 * @version 2015-8-3 21:10:17
 * 
 */
public class InboundRequest extends BaseRequest {

    /** 序列化标识 */
    private static final long serialVersionUID = 1L;
    
    private String eshopNo;
    
    private String ownerCode;
    
    /** 采购单号 */
    private String purchaseOrderNo; 
    /** SKU */
    private String sku; 
    /**
     * 货位号
     */
    private String locationCode;
    /** 正品数量 */
    private Integer num; 
    
    private String produceDate;
	
	/** 有效期至 */
    private String validUtilDate;
    
    /**
     * 操作人。
     */
    private String operator; 
    /**
     * 操作时间。
     */
    private Date operateTime;

	public String getEshopNo() {
		return eshopNo;
	}

	public void setEshopNo(String eshopNo) {
		this.eshopNo = eshopNo;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getProduceDate() {
		return produceDate;
	}

	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}

	public String getValidUtilDate() {
		return validUtilDate;
	}

	public void setValidUtilDate(String validUtilDate) {
		this.validUtilDate = validUtilDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	} 
}
