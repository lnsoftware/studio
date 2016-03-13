package com.imxiaomai.convenience.store.scan.model;

import java.util.Date;

/**
 * StockInventoryResult：货位库存实体类
 * 
 * @author zhc2054
 * @version 2015-8-21 9:51:40
 * 
 */
public class StockInventoryResult implements java.io.Serializable  {

    /** 序列化标识 */
	private static final long serialVersionUID = 1L;
	
    /** ID */
    private Long id; 
    /**  */
    private String inventoryNo; 
    /**  */
    private String inventoryTaskNo; 
    /** SKU */
    private String sku; 
    /** 便利店号 */
    private String eshopNo;
    /** 便利店名字 */
    private String eshopName;
    /**数量*/
    private Integer num;
    /** 盘点实际数量 */
    private Integer actualNum; 
    /** 是否进行过录入操作 **/
    private Integer isInputed;
    /** 操作时间 */
    private Date operateTime;
    /** 操作人 */
    private String operator;
    /** 有效标识 */
    private Integer yn; 
    
    public Long getId(){
        return id;
    }
        
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getInventoryNo(){
        return inventoryNo;
    }
        
    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }
    
    public String getInventoryTaskNo(){
        return inventoryTaskNo;
    }
        
    public void setInventoryTaskNo(String inventoryTaskNo) {
        this.inventoryTaskNo = inventoryTaskNo;
    }
    
    public String getSku(){
        return sku;
    }
        
    public void setSku(String sku) {
        this.sku = sku;
    }
    

    public String getEshopNo() {
        return eshopNo;
    }

    public void setEshopNo(String eshopNo) {
        this.eshopNo = eshopNo;
    }

    public String getEshopName(){
        return eshopName;
    }
        
    public void setEshopName(String eshopName) {
        this.eshopName = eshopName;
    }

    public Integer getActualNum(){
        return actualNum;
    }
        
    public void setActualNum(Integer actualNum) {
        this.actualNum = actualNum;
    }
    
    public Integer getYn(){
        return yn;
    }
        
    public void setYn(Integer yn) {
        this.yn = yn;
    }

	public Integer getIsInputed() {
		return isInputed;
	}

	public void setIsInputed(Integer isInputed) {
		this.isInputed = isInputed;
	}

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
