package com.imxiaomai.convenience.store.scan.model;

import java.io.Serializable;

public class InBoundCheckResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 订单编号
	private String orderNo;
	
	// 货主
	private String owner;
	
	// SKU
	private String sku;
	
	// 商品名称
	private String name;
	
	// 商品数量
	private Integer num;
	
	// 已抽检数
	private Integer samplingNum;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getSamplingNum() {
		return samplingNum;
	}

	public void setSamplingNum(Integer samplingNum) {
		this.samplingNum = samplingNum;
	}
	
}
