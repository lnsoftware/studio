package com.imxiaomai.convenience.store.scan.fragment;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.constants.HttpStatus;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;
import com.imxiaomai.convenience.store.scan.model.StockLocationRequest;
import com.imxiaomai.convenience.store.scan.model.StockLocationResponse;
import com.imxiaomai.convenience.store.scan.rest.StockQueryRest;
import com.imxiaomai.convenience.store.scan.util.CActionSheetDialog;
import com.imxiaomai.convenience.store.scan.util.DateUtil;

import com.imxiaomai.convenience.store.scan.util.NumberTypeUtil;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

/**
 * 库存查询
 */
public class StockQueryFragment extends BaseFragment implements OnClickListener {
	
	private EditText skuEt;
	private TextView eshopNameTv;
	private TextView goodsNameTv;
	private TextView specificationTv;
	private TextView stockNumTv;
	private TextView goodsUnitTv;
	private TextView productionDateTv;
	private TextView expireTimeTv;
	private TextView recordNumTv;
	private TextView skuTv;

	private Button inputBt;
	private Button btnPrevious;
	private Button btnNext;
	
	private List<StockLocationResponse> beanList;
	private StockQueryRest stockQueryRest;
	private int curPosition = 0;
	
	private CActionSheetDialog shareDialog;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what) {
			case LOADDATA_SUCCESS:
				getloadUtil().closeProgress();
				if(beanList != null && beanList.size() > 0) {
					curPosition = 0;
					refreshViewInfo();
				} else {
					resetViewInfo();
					ToastUtils.showShort(getActivity(), "没有查询到库存信息");
				}
				break;
			case LOADDATA_FAILED:
				resetViewInfo();
				getloadUtil().closeProgress();
				String message = (msg.obj != null ? (String)msg.obj : "查询到库存信息失败");
				ToastUtils.showShort(getActivity(), message);
				break;
			case LOADDATA_DEFAULT:
				resetViewInfo();
				getloadUtil().closeProgress();
				break;
			}
		}
		
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.stock_query_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initTitleView();
		initView();
		setListener();
		initData();
	}

	private void initTitleView() {
		initHeadView();
		setActionVisible(View.GONE);
		setHeadTitle("库存查询");
	}

	private void initView() {
		skuEt = (EditText) getActivity().findViewById(R.id.sku_et);
		eshopNameTv = (TextView) getActivity().findViewById(R.id.eshop_name_tv);
		goodsNameTv = (TextView) getActivity().findViewById(R.id.goods_name_tv);
		specificationTv = (TextView) getActivity().findViewById(R.id.specification_tv);
		stockNumTv = (TextView) getActivity().findViewById(R.id.stock_num_tv);
		goodsUnitTv = (TextView) getActivity().findViewById(R.id.goods_unit_tv);
		productionDateTv = (TextView) getActivity().findViewById(R.id.production_date_tv);
		expireTimeTv = (TextView) getActivity().findViewById(R.id.expire_time_tv);
		recordNumTv = (TextView) getActivity().findViewById(R.id.record_num_tv);
        skuTv = (TextView) getActivity().findViewById(R.id.sku_num_tv);
		inputBt = (Button) getActivity().findViewById(R.id.input_bt);
		
		btnPrevious = (Button) getActivity().findViewById(R.id.btn_previous);
		btnNext = (Button) getActivity().findViewById(R.id.btn_next);
	}
	
	private void setListener() {
		setBackListener();
		btnPrevious.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		inputBt.setOnClickListener(this);
	}

	private void initData() {
		stockQueryRest = new StockQueryRest();
		serviceResult = new StockQueryServiceResult();
	}

	/**
	 * 查询库存信息
	 */
	private void loadData(String locationCode, String sku) {
		StockLocationRequest req = new StockLocationRequest();
		String eshopNo = AccountManager.getInstance().getShopCode();
		getLogger().i("StockQueryFragment loadData eshopNo = %s", eshopNo);
		req.setEshopNo(AccountManager.getInstance().getShopCode());
		req.setSku(sku);
		try {
			stockQueryRest.getStockQueryList(getActivity(), req, serviceResult);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新页面数据
	 * 
	 */
	private void refreshViewInfo() {
		if (curPosition >= 0 && curPosition < beanList.size()) {
			StockLocationResponse response = beanList.get(curPosition);
			if (response == null) return;
			// 便利店名称
			eshopNameTv.setText(response.getEshopName());
			// 商品名称
			goodsNameTv.setText(response.getProductName());
	         // 商品规格
			specificationTv.setText(response.getSpecification());
			// sku
            skuTv.setText(response.getSku());
            // 库存数量
            stockNumTv.setText(String.valueOf(response.getValidNum()));
            // 销售单位
            goodsUnitTv.setText(String.valueOf(response.getUnit()));
            // 生产日期
			if (response.getProduceTime() > 0) {
				productionDateTv.setText(String.valueOf(DateUtil.getDateStr(response.getProduceTime())));
			} else {
				productionDateTv.setText("");
			}
			
	        // 有效期结束日期
			if (response.getExpireTime() > 0) {
				expireTimeTv.setText(String.valueOf(DateUtil.getDateStr(response.getExpireTime())));	
			} else {
				expireTimeTv.setText("");
			}
            
			// 当前条数
			recordNumTv.setText(String.format("%d/%d", (curPosition + 1), beanList.size()));
		}
	}
	
	/**
	 * 重置页面数据
	 */
	private void resetViewInfo() {
	    eshopNameTv.setText("");
		goodsNameTv.setText("");
		specificationTv.setText("");
	    skuTv.setText("");
		stockNumTv.setText("");
		goodsUnitTv.setText("");
		productionDateTv.setText("");
		expireTimeTv.setText("");
		recordNumTv.setText("");
        skuEt.setText("");
		if (beanList != null) beanList.clear();
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.btn_previous) {
			curPosition--;
			if (beanList != null && beanList.size() > 0) {
				if (curPosition <= 0) {
					curPosition = 0;
				} 
				refreshViewInfo();
			}
		} else if (id == R.id.btn_next) {
			curPosition++;
			if (beanList != null  && beanList.size() > 0) {
				if (curPosition >= beanList.size()) {
					curPosition = (beanList.size() - 1);
				}
				refreshViewInfo();
			}
		} else if (id == R.id.input_bt) {
			inputBarcode("输入国标码");
		}
	}

	@Override
	public void onScanResult(String result) {
		super.onScanResult(result);
		getloadUtil().showProgress("加载中...");
		
	    if(NumberTypeUtil.isGbCodeOrSku(result)) {
			skuEt.setText(result);
			String sku = NumberTypeUtil.gb2Sku(result);
			if (!TextUtils.isEmpty(sku)) {
				loadData("", sku);
			} else {
				handler.sendEmptyMessage(LOADDATA_DEFAULT);
				ToastUtils.showShort(getActivity(), getString(R.string.not_found_sku));
			}
		} else {
			handler.sendEmptyMessage(LOADDATA_DEFAULT);
			ToastUtils.showShort(getActivity(), "非法的国标码");
		}
	}
	
	class StockQueryServiceResult implements ServiceResult {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			try {
				JSONObject jsonObject = new JSONObject(new String(responseBody));
				getLogger().i("lxm-stockquery:%s", jsonObject.toString());
				if(jsonObject != null && jsonObject.getInt("code") == HttpStatus.OK.getCode()) {
					beanList = stockQueryRest.parseStockQueryList(jsonObject.getString("result"));
					handler.sendEmptyMessage(LOADDATA_SUCCESS);
				} else {
					handler.sendEmptyMessage(LOADDATA_FAILED);
				}
			} catch (JSONException e) {
				handler.sendEmptyMessage(LOADDATA_FAILED);
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
		    try {
		        Log.d("StockQueryFragment",  "StockQueryServiceResult onFailure " + new String(responseBody));
                getLogger().i("StockQueryFragment StockQueryServiceResult onFailure %s", new String(responseBody));
            } catch (Exception e) {
                e.printStackTrace();
            }
			Message msg = new Message();
			msg.what = LOADDATA_FAILED;
			msg.obj = getCommonString(R.string.network_disable);	
			handler.sendMessage(msg);
			getLogger().i("lxm-stockquery:%s", "fail");
		}
		
	}
}
