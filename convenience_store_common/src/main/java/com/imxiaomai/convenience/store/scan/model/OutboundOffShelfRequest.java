package com.imxiaomai.convenience.store.scan.model;

import java.util.Date;

/**
 * OutboundOffShelfRequest：出库下架请求参数
 *
 * @author zhc2054
 * @version 2015-8-4 10:12:03
 *
 */
public class OutboundOffShelfRequest extends BaseRequest {

    /** 序列化标识 */
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;
    /** 出库单号 */
    private String outboundNo;
    /** 出库类型 客户订单/集合单/任务单/调拨单/返厂单/报损单 */
    private Integer outboundType;
    /** SKU */
    private String sku;
    /** 应下数量 */
    private Integer planNum;
    /** 实下数量 */
    private Integer actualNum;
    /** 差异数量 */
    private Integer diffNum;
    /** 仓库编码 */
    private String eshopNo;
    /** 库区 */
    private String areaCode;
    /** 货位 */
    private String locationCode;
    /** 是否赠品 */
    private Integer isGift;
    /** 生产日期 */
    private Date operateTime;
    /** 有效期至 */
    private String operator;
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
    

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutboundNo(){
        return outboundNo;
    }

    public void setOutboundNo(String outboundNo) {
        this.outboundNo = outboundNo;
    }

    public Integer getOutboundType(){
        return outboundType;
    }

    public void setOutboundType(Integer outboundType) {
        this.outboundType = outboundType;
    }

    public String getSku(){
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getPlanNum(){
        return planNum;
    }

    public void setPlanNum(Integer planNum) {
        this.planNum = planNum;
    }

    public Integer getActualNum(){
        return actualNum;
    }

    public void setActualNum(Integer actualNum) {
        this.actualNum = actualNum;
    }

    public Integer getDiffNum(){
        return diffNum;
    }

    public void setDiffNum(Integer diffNum) {
        this.diffNum = diffNum;
    }

    public String getEshopNo() {
        return eshopNo;
    }

    public void setEshopNo(String eshopNo) {
        this.eshopNo = eshopNo;
    }

    public String getAreaCode(){
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLocationCode(){
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Integer getIsGift(){
        return isGift;
    }

    public void setIsGift(Integer isGift) {
        this.isGift = isGift;
    }

    public Date getOperateTime(){
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperator(){
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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
