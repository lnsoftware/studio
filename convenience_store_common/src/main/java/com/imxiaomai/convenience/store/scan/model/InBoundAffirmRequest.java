package com.imxiaomai.convenience.store.scan.model;

public class InBoundAffirmRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    private String purchaseOrderNo;

    private String eshopNo;

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getEshopNo() {
        return eshopNo;
    }

    public void setEshopNo(String eshopNo) {
        this.eshopNo = eshopNo;
    }

}
