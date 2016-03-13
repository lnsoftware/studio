package com.imxiaomai.convenience.store.scan.data;

import java.io.Serializable;

public class WaitOnShelfItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prdNo;
	private String prdName;
	private String planNum;
	private String onShelfNum;
	private String waitOnShelfNum;

	public String getPrdNo() {
		return prdNo;
	}

	public void setPrdNo(String prdNo) {
		this.prdNo = prdNo;
	}

	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	public String getPlanNum() {
		return planNum;
	}

	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}

	public String getOnShelfNum() {
		return onShelfNum;
	}

	public void setOnShelfNum(String onShelfNum) {
		this.onShelfNum = onShelfNum;
	}

	public String getWaitOnShelfNum() {
		return waitOnShelfNum;
	}

	public void setWaitOnShelfNum(String waitOnShelfNum) {
		this.waitOnShelfNum = waitOnShelfNum;
	}

}
