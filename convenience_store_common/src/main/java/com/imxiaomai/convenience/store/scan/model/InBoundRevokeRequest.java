package com.imxiaomai.convenience.store.scan.model;

/**
 * post,得到销退信息
 * Created by Li dajun
 * Date: 2015-11-10
 * Time: 15:21
 */
public class InBoundRevokeRequest extends BaseRequest {
    private static final long serialVersionUID = -285369126775759372L;

    /**  销退单号 */
    private String revokeNo;
    /** 操作人 */
    private String operator;
    /**便利店号*/
    private String eshopNo;

    public String getEshopNo() {
        return eshopNo;
    }

    public void setEshopNo(String eshopNo) {
        this.eshopNo = eshopNo;
    }

    public String getRevokeNo() {
        return revokeNo;
    }
    public void setRevokeNo(String revokeNo) {
        this.revokeNo = revokeNo;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }

}


