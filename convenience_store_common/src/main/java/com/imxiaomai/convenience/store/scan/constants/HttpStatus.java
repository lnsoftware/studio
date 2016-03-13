package com.imxiaomai.convenience.store.scan.constants;

public enum HttpStatus {
	OK(200),OK_NODATA(2200),SERVERERROR(500);
	private int code;
	private HttpStatus(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
