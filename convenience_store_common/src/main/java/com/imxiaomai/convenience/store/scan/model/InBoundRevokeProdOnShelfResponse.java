package com.imxiaomai.convenience.store.scan.model;

/**
 * 销退返架返回的商品信息
 * Created by Li dajun
 * Date: 2015-11-09
 * Time: 19:13
 */
public class InBoundRevokeProdOnShelfResponse extends BaseRequest{

    private static final long serialVersionUID = -4102585541188178687L;

    /**  SKU */
    private String revokeNo;
    /**  SKU */
    private String sku;
    /**  商品名称 */
    private String name;
    /** 调拨数量 */
    private Integer num;
    /** 总数量 */
    private Integer sumNum;
    /** 已上架数量 */
    private Integer inboundedNum;
    /**
     * 便利店号
     */
    private String eshopNo;

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

    public Integer getSumNum() {
        return sumNum;
    }

    public void setSumNum(Integer sumNum) {
        this.sumNum = sumNum;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getInboundedNum() {
        return inboundedNum;
    }

    public void setInboundedNum(Integer inboundedNum) {
        this.inboundedNum = inboundedNum;
    }
}
