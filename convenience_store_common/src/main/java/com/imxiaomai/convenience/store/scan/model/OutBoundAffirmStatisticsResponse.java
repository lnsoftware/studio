package com.imxiaomai.convenience.store.scan.model;

import java.io.Serializable;

import com.imxiaomai.convenience.store.scan.enums.DistributionTypeEnum;

/**
 * @author hubenquan
 * RF接口/出库确认/获取订单汇总信息响应实体类。
 */
public class OutBoundAffirmStatisticsResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderNo;
	private Integer orderStatus;
	private String ownerCode;
	private String ownerName;
	private int skuNum; //sku总数
	private int num; //商品总数
	private String maikeName;
	private String maikePhone;
	private String custName;
	private DistributionTypeEnum distributionType;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOwnerCode() {
		return ownerCode;
	}
	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public int getSkuNum() {
		return skuNum;
	}
	public void setSkuNum(int skuNum) {
		this.skuNum = skuNum;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getMaikeName() {
		return maikeName;
	}
	public void setMaikeName(String maikeName) {
		this.maikeName = maikeName;
	}
	public String getMaikePhone() {
		return maikePhone;
	}
	public void setMaikePhone(String maikePhone) {
		this.maikePhone = maikePhone;
	}
	/**
	 * @return the orderStatus
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public DistributionTypeEnum getDistributionType() {
		return distributionType;
	}
	public void setDistributionType(DistributionTypeEnum distributionType) {
		this.distributionType = distributionType;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	
}
