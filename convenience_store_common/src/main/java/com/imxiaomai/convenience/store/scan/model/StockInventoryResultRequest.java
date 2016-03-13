package com.imxiaomai.convenience.store.scan.model;

/**
 * StockInventoryResult：货位库存实体类
 * 
 * @author zhc2054
 * @version 2015-8-21 9:51:40
 * 
 */
public class StockInventoryResultRequest extends BaseRequest  {

    /** 序列化标识 */
	private static final long serialVersionUID = 1L;
	
    /**  */
    private String inventoryNo; 
    /**  */
    private String inventoryTaskNo; 
    /** SKU */
    private String sku; 
    /** 便利店 */
    private String eshopNo;
    /** 盘点实际数量 */
    private Integer actualNum;
    /**操作人*/
    private String operator;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public Integer getActualNum(){
        return actualNum;
    }
        
    public void setActualNum(Integer actualNum) {
        this.actualNum = actualNum;
    }
    
}
