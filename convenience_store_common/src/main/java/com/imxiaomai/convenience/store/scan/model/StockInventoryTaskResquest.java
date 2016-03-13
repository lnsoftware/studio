package com.imxiaomai.convenience.store.scan.model;

import java.util.Date;

/**
 * StockInventoryTask：获取盘点任务实体类
 * 
 * @author zhc2054
 * @version 2015-8-21 9:51:40
 * 
 */
public class StockInventoryTaskResquest extends BaseRequest  {

    /** 序列化标识 */
	private static final long serialVersionUID = 1L;
	
    /** 操作人 */
    private String operator;
    /** 盘点单号 */
    private String inventoryTaskNo;
    /** 操作时间 */
    private Date operateTime;
    /**便利店编号*/
    private String eshopNo;

    public String getInventoryTaskNo() {
        return inventoryTaskNo;
    }

    public void setInventoryTaskNo(String inventoryTaskNo) {
        this.inventoryTaskNo = inventoryTaskNo;
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
}
