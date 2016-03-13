package com.imxiaomai.convenience.store.scan.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 销退返架返回的信息
 * Created by Li dajun
 * Date: 2015-11-06
 * Time: 14:41
 */
public class InBoundRevokeOnShelfResponse implements Serializable {
    private static final long serialVersionUID = -4102585541188178687L;

    /**  销退单号 */
    private String revokeNo;
    /**  销退单状态 退货单状态：初始状态，入库中，入库完成*/
    private Integer revokeStatus;

    private ArrayList<InBoundRevokeProdOnShelfResponse> inBoundRevokeProds;

    public String getRevokeNo() {
        return revokeNo;
    }

    public void setRevokeNo(String revokeNo) {
        this.revokeNo = revokeNo;
    }

    public ArrayList<InBoundRevokeProdOnShelfResponse> getInBoundRevokeProds() {
        return inBoundRevokeProds;
    }

    public void setInBoundRevokeProds(
            ArrayList<InBoundRevokeProdOnShelfResponse> inBoundRevokeProds) {
        this.inBoundRevokeProds = inBoundRevokeProds;
    }

    public Integer getRevokeStatus() {
        return revokeStatus;
    }

    public void setRevokeStatus(Integer revokeStatus) {
        this.revokeStatus = revokeStatus;
    }
}
