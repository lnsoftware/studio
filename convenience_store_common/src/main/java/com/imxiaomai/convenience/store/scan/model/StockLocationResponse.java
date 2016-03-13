package com.imxiaomai.convenience.store.scan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * StockLocationResponse：货位库存返回对象<br/>
 * 提供rest接口时方法的返回对象
 * 
 * @author zhc2054
 * @version 2015-8-4 19:16:22
 * 
 */
public class StockLocationResponse implements Serializable {
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
    /** 仓库 */
    private String warehouseCode; 
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
    /** 销售单位*/
    private String unit;
    /** 生产日期 */
    private long produceTime;

    private long expireTime;
    private int badNum;
    private String validNow;
    private double price;
    private String eshopName;
    private String eshopNo;
    private String validDate;
    private int eshopType;
    private String specification;
    /** 商品名称*/
    private String productName;
    private double sumPrice;

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getBadNum() {
        return badNum;
    }

    public void setBadNum(int badNum) {
        this.badNum = badNum;
    }

    public String getValidNow() {
        return validNow;
    }

    public void setBadNum(String validNow) {
        this.validNow = validNow;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getEshopName() {
        return eshopName;
    }

    public void setEshopName(String eshopName) {
        this.eshopName = eshopName;
    }

    public String getEshopNo() {
        return eshopNo;
    }

    public void setEshopNo(String eshopNo) {
        this.eshopNo = eshopNo;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public int getEshopType() {
        return eshopType;
    }

    public void setEshopType(int eshopType) {
        this.eshopType = eshopType;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

	public long getProduceTime() {
		return produceTime;
	}

	public void setProduceTime(long produceTime) {
		this.produceTime = produceTime;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

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
    
    public String getWarehouseCode(){
        return warehouseCode;
    }
        
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
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
        return validNum == null ? 0 : validNum;
    }
        
    public void setValidNum(Integer validNum) {
        this.validNum = validNum;
    }
    
    public Integer getReservedNum(){
        return reservedNum == null ? 0 : reservedNum;
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
}
