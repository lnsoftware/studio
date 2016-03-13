package com.imxiaomai.convenience.store.scan.model;

import java.util.Date;

/**
 * 销退返架
 * Created by Li dajun
 * Date: 2015-11-06
 * Time: 14:36
 */
public class InBoundRevokeOnShelfRequest extends BaseRequest {
    private static final long serialVersionUID = -285369126775759372L;

    /**  销退单号 */
    private String revokeNo;
    /** SKU */
    private String sku;
    /** 便利店号 */
    private String eshopNo;
    /** 上架数量 */
    private Integer num;
    /** 总数量 */
    private Integer sumNum;


    /** 生产日期 */
    private Date produceDate;
    /** 有效期至 */
    private Date validUtilDate;
    /** 操作人 */
    private String operator;
    /**  操作时间 */
    private Date operTime;
    /**单位*/
    private String unit;
    /**描述*/
    private String specification;
    /**商品名称*/
    private String productName;

    public Integer getSumNum() {
        return sumNum;
    }

    public void setSumNum(Integer sumNum) {
        this.sumNum = sumNum;
    }
    public String getRevokeNo() {
        return revokeNo;
    }

    public void setRevokeNo(String revokeNo) {
        this.revokeNo = revokeNo;
    }

    public String getEshopNo() {
        return eshopNo;
    }

    public void setEshopNo(String eshopNo) {
        this.eshopNo = eshopNo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    public Date getValidUtilDate() {
        return validUtilDate;
    }

    public void setValidUtilDate(Date validUtilDate) {
        this.validUtilDate = validUtilDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
}
