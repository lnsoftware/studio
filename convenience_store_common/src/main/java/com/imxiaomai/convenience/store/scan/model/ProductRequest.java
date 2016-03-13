package com.imxiaomai.convenience.store.scan.model;


/**
 * ProductRequest：商品基础请求参数
 * 
 * @author zhc2054
 * @version 2015-8-4 19:16:22
 * 
 */
public class ProductRequest extends BaseRequest {

    /** 序列化标识 */
    private static final long serialVersionUID = 1L;
    
	private String eshopNo;
	
	public String getEshopNo() {
		return eshopNo;
	}
	
	public void setEshopNo(String eshopNo) {
		this.eshopNo = eshopNo;
	}
}
