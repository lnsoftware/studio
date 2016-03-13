package com.imxiaomai.convenience.store.scan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * CustOrderProductResponse：客户订单商品明细返回对象<br/>
 * 提供rest接口时方法的返回对象
 *
 * @author zhc2054
 * @version 2015-8-4 9:27:17
 *
 */
public class CustOrderProductResponse implements Serializable {

    /** 序列化标识 */
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;
    /** 订单号 */
    private String orderNo;
    /**  */
    private Integer orderType;
    /** SKU */
    private String sku;
    /** 仓库编号 */
    private String warehouseCode;
    /** 商品类型 */
    private String productType;
    /** 数量 */
    private Integer num;
    /** 名称 */
    private String name;
    /** 单价 */
    private String price;
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


    private String owner;
    private Integer offShelfNum;
    private String roadCode;
    private String locationCode;
    private String unit;


    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo(){
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderType(){
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getSku(){
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getWarehouseCode(){
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getProductType(){
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getOffShelfNum() {
		return offShelfNum;
	}

	public void setOffShelfNum(Integer offShelfNum) {
		this.offShelfNum = offShelfNum;
	}

	public String getRoadCode() {
		return roadCode;
	}

	public void setRoadCode(String roadCode) {
		this.roadCode = roadCode;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
