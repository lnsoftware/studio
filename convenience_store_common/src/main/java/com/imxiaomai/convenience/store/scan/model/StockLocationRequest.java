package com.imxiaomai.convenience.store.scan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * StockLocationRequest：货位库存请求参数
 * 
 * @author zhc2054
 * @version 2015-8-4 19:16:22
 * 
 */
public class StockLocationRequest extends BaseRequest {

    /** 序列化标识 */
    private static final long serialVersionUID = 1L;
    
    /** ID */
    private Long id; 
    /** SKU */
    private String sku; 
    /** 货主编号 */
    private String ownerCode; 
    /** 货主名称 */
    private String ownerName; 
    /** 便利店编号 */
    private String eshopNo; 
    /** 库区类型 */
    private Integer areaType; 
    /** 库区 */
    private String areaCode; 
    /** 巷道 */
    private String roadCode; 
    /** 货位 */
    private String locationCode; 
    /** 批次号 */
    private String batchNo; 
    /** 质量等级 */
    private Integer qualityLevel; 
    /** 是否赠品 */
    private Integer isGift; 
    /** 可用库存数量 */
    private Integer validNum; 
    /** 预占库存数量 */
    private Integer reservedNum; 
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
    
    /**
     * 操作人。
     */
    private String operator; 
    /**
     * 操作时间。
     */
    private Date operateTime;
    
    public Long getId(){
        return id;
    }
        
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getSku(){
        return sku;
    }
        
    public void setSku(String sku) {
        this.sku = sku;
    }
    
    public String getOwnerCode(){
        return ownerCode;
    }
        
    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }
    
    public String getOwnerName(){
        return ownerName;
    }
        
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public String getEshopNo(){
        return eshopNo;
    }
        
    public void setEshopNo(String eshopNo) {
        this.eshopNo = eshopNo;
    }
    
    public Integer getAreaType(){
        return areaType;
    }
        
    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }
    
    public String getAreaCode(){
        return areaCode;
    }
        
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public String getRoadCode(){
        return roadCode;
    }
        
    public void setRoadCode(String roadCode) {
        this.roadCode = roadCode;
    }
    
    public String getLocationCode(){
        return locationCode;
    }
        
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
    
    public String getBatchNo(){
        return batchNo;
    }
        
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
    
    public Integer getQualityLevel(){
        return qualityLevel;
    }
        
    public void setQualityLevel(Integer qualityLevel) {
        this.qualityLevel = qualityLevel;
    }
    
    public Integer getIsGift(){
        return isGift;
    }
        
    public void setIsGift(Integer isGift) {
        this.isGift = isGift;
    }
    
    public Integer getValidNum(){
        return validNum;
    }
        
    public void setValidNum(Integer validNum) {
        this.validNum = validNum;
    }
    
    public Integer getReservedNum(){
        return reservedNum;
    }
        
    public void setReservedNum(Integer reservedNum) {
        this.reservedNum = reservedNum;
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
    
}
