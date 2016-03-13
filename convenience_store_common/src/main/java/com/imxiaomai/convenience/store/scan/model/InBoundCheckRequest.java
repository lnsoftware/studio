package com.imxiaomai.convenience.store.scan.model;

import java.util.Date;

public class InBoundCheckRequest extends BaseRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 订单编号
	private String orderNo;
	
	// SKU
	private String sku;

	// 抽检数量
	private Integer samplingNum;
	
	// 正品检数
	private Integer normalPrdNum;
	
	// 操作人
	private String operator;
	
	// 操作时间
	private Date operTime;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getSamplingNum() {
		return samplingNum;
	}

	public void setSamplingNum(Integer samplingNum) {
		this.samplingNum = samplingNum;
	}

	public Integer getNormalPrdNum() {
		return normalPrdNum;
	}

	public void setNormalPrdNum(Integer normalPrdNum) {
		this.normalPrdNum = normalPrdNum;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	
}
