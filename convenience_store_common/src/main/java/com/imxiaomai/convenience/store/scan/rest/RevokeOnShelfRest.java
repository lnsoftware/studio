package com.imxiaomai.convenience.store.scan.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.imxiaomai.convenience.store.scan.http.AsyncHttpResponseHandlerBase;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.model.InBoundRevokeOnShelfRequest;
import com.imxiaomai.convenience.store.scan.model.InBoundRevokeOnShelfResponse;
import com.imxiaomai.convenience.store.scan.model.InBoundRevokeRequest;
import com.imxiaomai.convenience.store.scan.util.GsonUtil;
import com.imxiaomai.convenience.store.scan.util.RestServiceUtil;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * 销退上架
 * Created by Li dajun
 * Date: 2015-10-30
 * Time: 14:57
 */
public class RevokeOnShelfRest {

//销退返架

    /**
     * POST请求,通过销退单号得到,订单信息
     * @param request 单号
     * @param serviceResult	返回的实体
     */
    public void postRevokeShelfOrderInfo(Context context,InBoundRevokeRequest request, ServiceResult serviceResult) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(request));
        RestServiceUtil.post(context, HttpConstant.POST_REVOKE_SHELF_ORDER_INFO, entity,
                new AsyncHttpResponseHandlerBase(serviceResult));
    }

    /**
     * 解析json
     * @param json
     * @return BoxOrderResponse 完成解析的实体类
     */
    public InBoundRevokeOnShelfResponse parseRevokeInfo(String json) {
        try {
            Gson gson = GsonUtil.getInstance();
            InBoundRevokeOnShelfResponse bean = gson.fromJson(json,new TypeToken<InBoundRevokeOnShelfResponse>() {
            }.getType());
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post请求,上传商品信息
     * @param context
     * @param request	要上传的实体
     * @param serviceResult	请求返回值
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    public void postRevokeProductInfo(Context context, InBoundRevokeOnShelfRequest request, ServiceResult serviceResult)
            throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(GsonUtil.getInstance().toJson(request));
        RestServiceUtil.post(context, HttpConstant.REVOKE_SHELF_ON_SHELF, entity,
                new AsyncHttpResponseHandlerBase(serviceResult));
    }



}
