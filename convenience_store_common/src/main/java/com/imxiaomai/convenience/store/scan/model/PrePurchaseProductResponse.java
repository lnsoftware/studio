package com.imxiaomai.convenience.store.scan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * PrePurchaseProductResponse：采购单预报商品明细返回对象<br/>
 * 提供rest接口时方法的返回对象
 * 
 * @author zhc2054
 * @version 2015-8-3 21:10:17
 * 
 */
public class PrePurchaseProductResponse implements Serializable {

    /** 序列化标识 */
    private static final long serialVersionUID = 1L;
    
    private Integer inboundedNum;
    
    private String ownerCode;
    
    /** ID */
    private Long id; 
    /** 采购单号 */
    private String purchaseOrderNo; 
    /** SKU */
    private String sku; 
    /** 正品数量 */
    private Integer num; 
    /** 赠品数量 */
    private Integer giftNum; 
    /** 单位 */
    private String unit; 
    /** 名称 */
    private String name; 
    /** 价格 */
    private String price; 
    /** 规格 */
    private String specification; 
    /** 保质期 */
    private String period; 
    /**保质期天数*/
    private Integer selfLife;
    /** 创建时间 */
    private Date createTime; 
    /** 创建用户 */
    private String createUser; 
    /** 修改时间 */
    private Date updateTime; 
    /** 修改用户 */
    private String updateUser; 
    /** 有效标识 */
    private Integer yn; 
    
    public Integer getInboundedNum() {
		return inboundedNum;
	}

	public void setInboundedNum(Integer inboundedNum) {
		this.inboundedNum = inboundedNum;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public Long getId(){
        return id;
    }
        
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPurchaseOrderNo(){
        return purchaseOrderNo;
    }
        
    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }
    
    public String getSku(){
        return sku;
    }
        
    public void setSku(String sku) {
        this.sku = sku;
    }
    
    public Integer getNum(){
    	if(num == null) {
    		return 0;
    	}
        return num;
    }
        
    public void setNum(Integer num) {
        this.num = num;
    }
    
    public Integer getGiftNum(){
    	if(giftNum == null) {
    		return 0;
    	}
        return giftNum;
    }
        
    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }
    
    public String getUnit(){
        return unit;
    }
        
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public String getName(){
        return name;
    }
        
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPrice(){
        return price;
    }
        
    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getSpecification(){
        return specification;
    }
        
    public void setSpecification(String specification) {
        this.specification = specification;
    }
    
    public String getPeriod(){
        return period;
    }
        
    public void setPeriod(String period) {
        this.period = period;
    }
    
    public Integer getSelfLife() {
    	if(selfLife == null) {
    		return 0;
    	}
		return selfLife;
	}

	public void setSelfLife(Integer selfLife) {
		this.selfLife = selfLife;
	}

	public Date getCreateTime(){
        return createTime;
    }
        
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getCreateUser(){
        return createUser;
    }
        
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    public Date getUpdateTime(){
        return updateTime;
    }
        
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getUpdateUser(){
        return updateUser;
    }
        
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    
    public Integer getYn(){
        return yn;
    }
        
    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
