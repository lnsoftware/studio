package com.imxiaomai.convenience.store.scan.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * StockInventoryTask：实体类
 * 
 * @author zhc2054
 * @version 2015-8-21 9:51:40
 * 
 */
public class StockInventoryTaskResponse implements Serializable  {

    /** 序列化标识 */
	private static final long serialVersionUID = 1L;
	
    /** 盘点任务单号 */
    private String inventoryTaskNo; 
    /** 盘点单号 */
    private String inventoryNo;
    /** 初始化/盘点中/盘点完成 */
    private Integer taskStatus;
    /** 任务操作人 */
    private String taskOperator;
    /**操作时间*/
    private Date operateTime;

    private List<StockInventoryResult> inventoryResultList;
    
    public String getInventoryTaskNo(){
        return inventoryTaskNo;
    }
        
    public void setInventoryTaskNo(String inventoryTaskNo) {
        this.inventoryTaskNo = inventoryTaskNo;
    }
    
    public String getInventoryNo(){
        return inventoryNo;
    }
        
    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }
    
    public Integer getTaskStatus(){
        return taskStatus;
    }
        
    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }
    
    public String getTaskOperator(){
        return taskOperator;
    }
        
    public void setTaskOperator(String taskOperator) {
        this.taskOperator = taskOperator;
    }

	public List<StockInventoryResult> getInventoryResultList() {
		return inventoryResultList;
	}

	public void setInventoryResultList(List<StockInventoryResult> inventoryResultList) {
		this.inventoryResultList = inventoryResultList;
	}

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
