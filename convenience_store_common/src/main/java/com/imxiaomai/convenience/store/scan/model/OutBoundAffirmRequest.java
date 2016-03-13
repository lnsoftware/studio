package com.imxiaomai.convenience.store.scan.model;

import java.util.Date;

public class OutBoundAffirmRequest extends BaseRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderNo;
	/**
     * 操作人。
     */
    private String operator;
    private String eshopNo;
    private String maikeName;
    private String maikePhone;
    /**
     * 操作时间。
     */
    private Date operateTime;

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

    public String getEshopNo() {
        return eshopNo;
    }

    public void setEshopNo(String eshopNo) {
        this.eshopNo = eshopNo;
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
}
