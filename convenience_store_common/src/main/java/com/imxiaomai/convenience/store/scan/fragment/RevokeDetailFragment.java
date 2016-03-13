package com.imxiaomai.convenience.store.scan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.activity.ProductOnShelfDetailActivity;
import com.imxiaomai.convenience.store.scan.constants.HttpStatus;
import com.imxiaomai.convenience.store.scan.data.WaitOnShelfItem;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;
import com.imxiaomai.convenience.store.scan.manager.ProductManager;
import com.imxiaomai.convenience.store.scan.manager.RevokeManager;
import com.imxiaomai.convenience.store.scan.model.InBoundRevokeOnShelfRequest;
import com.imxiaomai.convenience.store.scan.model.InBoundRevokeOnShelfResponse;
import com.imxiaomai.convenience.store.scan.model.InBoundRevokeProdOnShelfResponse;
import com.imxiaomai.convenience.store.scan.model.InBoundRevokeRequest;
import com.imxiaomai.convenience.store.scan.rest.RevokeOnShelfRest;
import com.imxiaomai.convenience.store.scan.util.DateUtil;
import com.imxiaomai.convenience.store.scan.util.NumberTypeUtil;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

/**
 * 返架上架和消退上架的详情页面
 * Created by Li dajun
 * Date: 2015-10-27
 * Time: 16:50
 */
public class RevokeDetailFragment extends BaseFragment implements View.OnClickListener {

    //销退上架
    /**获取单号信息成功*/
    private static final int GET_REVOKE_INFO_SUCCESS = 8;
    /**获取单号信息失败*/
    private static final int GET_REVOKE_INFO_FAILED = 9;
    /**提交单个商品信息成功*/
    private static final int POST_REVOKE_GOODS_INFO_SUCCESS = 10;
    /**提交单个商品信息失败*/
    private static final int POST_REVOKE_GOODS_INFO_FAILED = 11;
    private ArrayList<InBoundRevokeProdOnShelfResponse> mRevokeProdsList;

    /**销退单号*/
    private String mRevokeNo;

    /**手工输入商品条形码*/
    private Button prdno_input_bt;
    /**查看待上架商品*/
    private Button details_bt;
    /**提交*/
    private Button submit_bt;
    /**选择生产日期*/
    private Button prd_date_bt;
    /**选择到期日*/
    private Button prd_expiration_bt;

    /**扫描商品条形码*/
    private EditText prd_no_et;
    /**输入商品数量*/
    private EditText pro_num_et;
    /**生产日期*/
    private EditText prd_date_et;
    /**到期日*/
    private EditText prd_expiration_et;

    /**商品名称*/
    private TextView prd_name_tv;
    /**保质期*/
    private TextView prd_period_tv;
    /**应上数量*/
    private TextView onshelfed_num_tv;

    /**原sku*/
    private String rawSku;
    /**该单号所代表的本地数据库是否可以删除 "" 代表可以删除 其他不可以删除*/
    private String mayDelete = "";

    private RevokeManager mRevokeManager = RevokeManager.getInstance();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getloadUtil().closeProgress();// 关闭提示
            int what = msg.what;
            switch (what) {
                case LOADDATA_FAILED:
                    ToastUtils.showLong(getActivity(), TextUtils.isEmpty((String)msg.obj)?"获取信息失败":(String)msg.obj);
                    toFinishActivity();
                    break;

                //销退返架
                case GET_REVOKE_INFO_SUCCESS:
                    InBoundRevokeOnShelfResponse revokeResponse = (InBoundRevokeOnShelfResponse) msg.obj;
                    Integer revokeStatus = revokeResponse.getRevokeStatus();
                    switch (revokeStatus) {
                        case 0://"初始状态"
                        case 30://入库中
                                //如果以前维护过该单号,先把该单号所代表的本地数据库清空
                                if (RevokeManager.getInstance().getRevokeNo().contains(revokeResponse.getRevokeNo())) {

                                    //如果保存过这个单号所有代表的明细,把数据中的相关内容清空
                                    if (mRevokeManager.getRevokeNo().contains(revokeResponse.getRevokeNo())) {
                                        //先拿到保存之前的状态
                                        mayDelete = mRevokeManager.mayDeleteForRevokeNo(revokeResponse.getRevokeNo()) ? "" : "1";
                                        mRevokeManager.deleteRevokeNo(revokeResponse.getRevokeNo());
                                    }
                                }
                                //可以返架
                                setHeadTitle("销退:"+ revokeResponse.getRevokeNo());
                                parseRevokeGoods(revokeResponse);
                            break;
                        case 90://"入库完成"
                                //如果保存过这个单号所有代表的明细,把数据中的相关内容清空
                                if (mRevokeManager.getRevokeNo().contains(revokeResponse.getRevokeNo())) {
                                    mRevokeManager.deleteRevokeNo(revokeResponse.getRevokeNo());
                                }
                            ToastUtils.showLong(getActivity(), "该单号已入库完成");
                            toFinishActivity();
                            break;
                    }

                    break;
                case GET_REVOKE_INFO_FAILED:
                    ToastUtils.showLong(getActivity(), TextUtils.isEmpty((String) msg.obj) ? "该单号已入库完成" : (String) msg.obj);
                    toFinishActivity();
                    break;
                case POST_REVOKE_GOODS_INFO_SUCCESS:
                        mRevokeManager.deleteProductForRevokeNoAndSku(mRevokeNo, mRevokeProdsList.get(0).getSku());
                        mRevokeProdsList.remove(0);
                    if (null != msg.obj) {
                        ToastUtils.showLong(getActivity(), TextUtils.isEmpty((String) msg.obj) ? "信息返回不正确" : "上架"+ msg.obj);
                    }
                        parseNextGoodsForRevoke();
                    break;
                case POST_REVOKE_GOODS_INFO_FAILED:
                    ToastUtils.showLong(getActivity(), null == msg.obj?"获取返回信息失败":(String) msg.obj);
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.revoke_detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        initHeadView();
        setActionVisible(View.GONE);
        prdno_input_bt = (Button) getActivity().findViewById(R.id.prdno_input_bt);
        details_bt = (Button) getActivity().findViewById(R.id.details_bt);
        submit_bt = (Button) getActivity().findViewById(R.id.submit_bt);
        prd_date_bt = (Button) getActivity().findViewById(R.id.prd_date_bt);
        prd_expiration_bt = (Button) getActivity().findViewById(R.id.prd_expiration_bt);

        prd_no_et = (EditText) getActivity().findViewById(R.id.prd_no_et);
        pro_num_et = (EditText) getActivity().findViewById(R.id.pro_num_et);
        prd_date_et = (EditText) getActivity().findViewById(R.id.prd_date_et);
        prd_expiration_et = (EditText) getActivity().findViewById(R.id.prd_expiration_et);

        prd_name_tv = (TextView) getActivity().findViewById(R.id.prd_name_tv);
        prd_period_tv = (TextView) getActivity().findViewById(R.id.prd_period_tv);
        onshelfed_num_tv = (TextView) getActivity().findViewById(R.id.onshelfed_num_tv);
    }

    private void initData() {
        String revokeNo = getActivity().getIntent().getStringExtra("revokeNo");
        checkRevokeNum(revokeNo);
        setHeadTitle("退货管理");
    }



    private void setListener() {
        setBackListener();
        prdno_input_bt.setOnClickListener(this);
        details_bt.setOnClickListener(this);
        submit_bt.setOnClickListener(this);
        prd_date_bt.setOnClickListener(this);
        prd_expiration_bt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.prdno_input_bt) {
            prd_no_et.setText("");
            inputBarcode("输入商品条形码或sku");
        }else if (id == R.id.details_bt) {
            if (RevokeManager.getInstance().isEmpty()) {
                ToastUtils.showShort(getActivity(), "没有待上架商品");
                return;
            }
            checkWaitOnShelfGoods();
        }else if (id == R.id.submit_bt) {
            submint();
        }else if (id == R.id.prd_date_bt) {
            showDatePickerDialog(prd_date_et);
        }else if (id == R.id.prd_expiration_bt) {
            showDatePickerDialog(prd_expiration_et);
        }
    }

    /**
     * 查看待上商品
     */
    private void checkWaitOnShelfGoods() {
        ArrayList<WaitOnShelfItem> waitList = new ArrayList<WaitOnShelfItem>();
        WaitOnShelfItem item;
        ArrayList<InBoundRevokeProdOnShelfResponse> list = mRevokeManager.getProductListForRevokeNo(mRevokeNo);
        if (list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                item = new WaitOnShelfItem();
                InBoundRevokeProdOnShelfResponse bean = list.get(i);
                item.setPrdName(bean.getName());
                item.setPrdNo(bean.getSku());
                item.setOnShelfNum(bean.getInboundedNum()+"");
                item.setPlanNum(bean.getNum() + "");
                item.setWaitOnShelfNum((bean.getNum()-bean.getInboundedNum())+"");
                waitList.add(item);
            }
            Intent intent = new Intent(getActivity(), ProductOnShelfDetailActivity.class);
            intent.putExtra("waitDatas", waitList);
            startActivity(intent);
        } else {
            toFinishActivity();
            ToastUtils.showShort(getActivity(), "请重试");
        }
    }

    /**
     * 提交
     */
    private void submint() {
            if (TextUtils.isEmpty(prd_no_et.getText().toString().trim())) {
                ToastUtils.showShort(getActivity(),"商品条码不能为空");
                return;
            //如果输入的数量不等于应上数量
            } else if ((Integer.parseInt(pro_num_et.getText().toString().trim()) != Integer.parseInt(onshelfed_num_tv.getText().toString().trim()))) {
                ToastUtils.showShort(getActivity(), "请确保实上数量等于于应上数量");
                return;
            }

                submitGoodsForRevoke();
    }

    /**
     * 提交销退商品
     */
    private void submitGoodsForRevoke() {
        getloadUtil().showProgressNoCancelable("正在提交商品数据...");// 打开提示
        InBoundRevokeOnShelfRequest request = new InBoundRevokeOnShelfRequest();
        InBoundRevokeProdOnShelfResponse response = mRevokeProdsList.get(0);
        request.setNum(Integer.valueOf(pro_num_et.getText().toString().trim()));//上传商品数量
        request.setSku(response.getSku());//上传sku
        request.setEshopNo(AccountManager.getInstance().getShopCode());//上传便利店号
        request.setRevokeNo(mRevokeNo);//上传单号
        request.setProductName(response.getName());//上传商品名称
        request.setOperTime(new Date());//上传操作时间
        request.setSumNum(response.getNum());//上传应上总数量
        request.setOperator(AccountManager.getInstance().getUsername());//上传操作人
        if (!TextUtils.isEmpty(prd_date_et.getText().toString().trim())) {
            request.setProduceDate(DateUtil.getDate(prd_date_et.getText().toString().trim()));
        }
        if (!TextUtils.isEmpty(prd_expiration_et.getText().toString().trim())) {
            request.setProduceDate(DateUtil.getDate(prd_expiration_et.getText().toString().trim()));
        }
        request.setUnit(ProductManager.getInstance(getFragmentActivity()).getProduct(response.getSku()).getUnit());//上传商品单位
        request.setSpecification(ProductManager.getInstance(getFragmentActivity()).getProduct(response.getSku()).getSpecification());//上传商品描述

        final Message msg = Message.obtain();
        final RevokeOnShelfRest rest = new RevokeOnShelfRest();
        try {
            rest.postRevokeProductInfo(getActivity(), request, new ServiceResult() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject jsonObject = new JSONObject(new String(responseBody));
                        if (jsonObject.getInt("code") == HttpStatus.OK.getCode()) {//成功
                            msg.obj = jsonObject.getString("message");
                            msg.what = POST_REVOKE_GOODS_INFO_SUCCESS;
                            mHandler.sendMessage(msg);
                        } else if (jsonObject != null) {
                            msg.obj = jsonObject.getString("message");
                            msg.what = POST_REVOKE_GOODS_INFO_FAILED;
                            mHandler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        msg.what = LOADDATA_FAILED;
                        msg.obj = getActivity().getResources().getString(R.string.resut_parse_failed);
                        mHandler.sendMessage(msg);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    try {
                        JSONObject jsonObject = new JSONObject(new String(responseBody));
                        msg.what = POST_REVOKE_GOODS_INFO_FAILED;
                        msg.obj = jsonObject.getString("message");
                        mHandler.sendMessage(msg);
                    } catch (JSONException e) {
                        msg.what = LOADDATA_FAILED;
                        if (getFragmentActivity() != null) {
            				msg.obj = getFragmentActivity().getResources().getString(R.string.resut_parse_failed);	
            			} else {
            				msg.obj = "操作失败";
            			}
                        mHandler.sendMessage(msg);
                        e.printStackTrace();
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            msg.what = LOADDATA_FAILED;
            msg.obj = getActivity().getResources().getString(R.string.resut_parse_failed);
            mHandler.sendMessage(msg);
            e.printStackTrace();
        }
    }


    @Override
    public void onScanResult(String result) {
        super.onScanResult(result);
        //如果还没有扫描过商品条码
        if (TextUtils.isEmpty(prd_no_et.getText().toString().trim())) {
            String sku = NumberTypeUtil.gb2Sku(result);
            if (TextUtils.isEmpty(sku)) {
                ToastUtils.showShort(getActivity(), "请先扫描商品条码或sku");
                return;
            }else if (sku.equals(rawSku)) {//如果扫到的是想要的商品条码
                prd_no_et.setText(result);
            }else if (!sku.equals(rawSku)) {
                ToastUtils.showShort(getActivity(),"请扫描正确的条码");
            }
        }
    }



    /**
     * 检查销退单号是否正确
     * @param revokeNo
     */
    private void checkRevokeNum(String revokeNo) {
        getloadUtil().showProgressNoCancelable("正在检查单号是否正确...");// 打开提示
        final RevokeOnShelfRest rest = new RevokeOnShelfRest();
        InBoundRevokeRequest request = new InBoundRevokeRequest();
        request.setRevokeNo(revokeNo);
        request.setOperator(AccountManager.getInstance().getUsername());
        request.setEshopNo(AccountManager.getInstance().getShopCode());
        final Message msg = Message.obtain();
        try {
            rest.postRevokeShelfOrderInfo(getActivity(), request, new ServiceResult() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject jsonObject = new JSONObject(new String(responseBody));
                        if (jsonObject != null && jsonObject.getInt("code") == HttpStatus.OK.getCode()) {//订单号验证成功
                            InBoundRevokeOnShelfResponse respons = rest.parseRevokeInfo(jsonObject.getString("result"));
                            msg.obj = respons;
                            msg.what = GET_REVOKE_INFO_SUCCESS;
                            mHandler.sendMessage(msg);
                        } else if (jsonObject.getInt("code") == HttpStatus.OK_NODATA.getCode()) {
                            msg.obj = "单号不正确";
                            msg.what = GET_REVOKE_INFO_FAILED;
                            mHandler.sendMessage(msg);
                        } else if (jsonObject != null) {
                            msg.obj = jsonObject.getString("message");
                            msg.what = GET_REVOKE_INFO_FAILED;
                            mHandler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        msg.what = LOADDATA_FAILED;
                        msg.obj = getActivity().getResources().getString(R.string.resut_parse_failed);
                        mHandler.sendMessage(msg);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    try {
                        JSONObject jsonObject = new JSONObject(new String(responseBody));
                        msg.what = GET_REVOKE_INFO_FAILED;
                        msg.obj = jsonObject.getString("message");
                        mHandler.sendMessage(msg);
                    } catch (JSONException e) {
                        msg.what = LOADDATA_FAILED;
                        if (getFragmentActivity() != null) {
                            msg.obj = getFragmentActivity().getResources().getString(R.string.resut_parse_failed);
                        } else {
                            msg.obj = "操作失败";
                        }
                        mHandler.sendMessage(msg);
                        e.printStackTrace();
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            msg.what = LOADDATA_FAILED;
            msg.obj = getActivity().getResources().getString(R.string.resut_parse_failed);
            mHandler.sendMessage(msg);
            e.printStackTrace();
        }
    }


    /**
     * 解析销退数据
     * @param revokeResponse
     */
    private void parseRevokeGoods(InBoundRevokeOnShelfResponse revokeResponse) {

        String eshopNo = revokeResponse.getInBoundRevokeProds().get(0).getEshopNo();

        if (!eshopNo.equals(AccountManager.getInstance().getShopCode())) {
            ToastUtils.showLong(getActivity(), "该订单不是本店的，请核实");
            toFinishActivity();
        } else if (revokeResponse.getInBoundRevokeProds().size() < 1) {
            ToastUtils.showLong(getActivity(), "该订单中没有商品");
            toFinishActivity();
        }

        mRevokeNo = revokeResponse.getRevokeNo();//得到订单号

        //如果保存过这个单号所有代表的明细,把数据中的相关内容清空
        if (mRevokeManager.getRevokeNo().contains(mRevokeNo)) {
            //先拿到保存之前的状态
            mayDelete = mRevokeManager.mayDeleteForRevokeNo(mRevokeNo) ? "" : "1";
            mRevokeManager.deleteRevokeNo(mRevokeNo);
        }
        //如果没有保存,就保存一份到本地
        mRevokeProdsList = revokeResponse.getInBoundRevokeProds();
        mRevokeManager.saveReturnShelfByRevokeNo(mRevokeProdsList, mRevokeNo, mayDelete);

        parseNextGoodsForRevoke();

    }

    /**
     * 解析下一个销退商品
     */
    private void parseNextGoodsForRevoke() {
        clearData();
        if (mRevokeProdsList.size() > 0) {
            InBoundRevokeProdOnShelfResponse bean = mRevokeProdsList.get(0);
            prd_name_tv.setText(bean.getName());//回显名称
            onshelfed_num_tv.setText(String.format("%d", bean.getNum()));//回显应上商品数量
            pro_num_et.setText(String.format("%d", bean.getNum()));//默认填上应上商品数量
            rawSku = bean.getSku();//拿到商品的sku
            //得到保质期天数
            int period = Integer.parseInt(ProductManager.getInstance(getFragmentActivity()).getProduct(bean.getSku()).getPeriod());
            //大于0，就是效期产品
            if (period > 0) {
                prd_period_tv.setText(period + "");
            } else {
                prd_period_tv.setText("");
                prd_date_bt.setEnabled(false);
                prd_expiration_bt.setEnabled(false);
            }
        } else {
            ToastUtils.showShort(getActivity(), "该单号的商品已上架完成");
            toFinishActivity();
        }
    }

    /**
     *  验证日期
     */
    public void showDatePickerDialog(final View view) {
        DatePickerFragment datePicker = DatePickerFragment.newInstance();
        datePicker.setDateListener(new DatePickerFragment.DateListener() {

            @Override
            public void setDate(Date date) {
                if (view.getId() == R.id.prd_date_et) {
                    // 判断生产日期不能大于当前
                    if(DateUtil.dayCompare(new Date(), date)) {
                        ToastUtils.showShort(getActivity(), "生产日期不能大于当前日期");
                        return;
                    }
                    prd_expiration_et.setText(DateUtil.getBefor(date, Integer.parseInt(ProductManager.getInstance(getFragmentActivity()).getProduct(rawSku).getPeriod())));
                } else {
                    // 判断有效期日期不能大于当前
                    if(DateUtil.dayCompare(date, new Date())) {
                        ToastUtils.showShort(getActivity(), "有效期至不能小于当前日期");
                        return;
                    }
                    prd_date_et.setText(DateUtil.getBefor(date, -Integer.parseInt(ProductManager.getInstance(getFragmentActivity()).getProduct(rawSku).getPeriod())));
                }

                ((EditText) view).setText(DateUtil.getDateStr(date));
            }
        });
        datePicker.show(getFragmentManager(), "datePicker");
    }

    private void clearData() {
        prd_no_et.setText("");
        pro_num_et.setText("");
        prd_date_et.setText("");
        prd_expiration_et.setText("");

        prd_name_tv.setText("");
        prd_period_tv.setText("");
        onshelfed_num_tv.setText("");

        rawSku = "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
