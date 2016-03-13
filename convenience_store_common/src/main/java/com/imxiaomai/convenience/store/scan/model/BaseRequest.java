package com.imxiaomai.convenience.store.scan.model;

import java.io.Serializable;

import com.imxiaomai.convenience.store.scan.util.MD5Util;


/**
 * BaseRequest：添加加密属性
 * 
 * @author lixiaoming
 * @version 2015-9-8 19:16:22
 * 
 */
public class BaseRequest implements Serializable {

    /** 序列化标识 */
    protected static final long serialVersionUID = 1L;
    
    protected String timeStamp = String.valueOf(System.currentTimeMillis());
    
    protected String cipherText = MD5Util.md5AsLowerHex(timeStamp + "cb");

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getCipherText() {
		return cipherText;
	}

	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}

}
