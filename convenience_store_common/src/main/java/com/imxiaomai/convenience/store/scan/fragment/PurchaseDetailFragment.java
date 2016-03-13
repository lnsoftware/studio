package com.imxiaomai.convenience.store.scan.fragment;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.activity.ProductOnShelfDetailActivity;
import com.imxiaomai.convenience.store.scan.constants.HttpStatus;
import com.imxiaomai.convenience.store.scan.data.WaitOnShelfItem;
import com.imxiaomai.convenience.store.scan.fragment.DatePickerFragment.DateListener;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;
import com.imxiaomai.convenience.store.scan.manager.PurchaseManager;
import com.imxiaomai.convenience.store.scan.model.InBoundAffirmRequest;
import com.imxiaomai.convenience.store.scan.model.InboundRequest;
import com.imxiaomai.convenience.store.scan.model.PrePurchaseProductResponse;
import com.imxiaomai.convenience.store.scan.rest.PurchaseRest;
import com.imxiaomai.convenience.store.scan.util.DateUtil;
import com.imxiaomai.convenience.store.scan.util.NumberTypeUtil;
import com.imxiaomai.convenience.store.scan.util.StringUtil;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

/**
 * 采购上架
 */
public class PurchaseDetailFragment extends BaseFragment implements OnClickListener {
	protected static final int ONSHELF_SUCCESS = 0;
	protected static final int ONSHELF_FAILED = 1;
	private Button prdno_input_bt, prd_date_bt, prd_expiration_bt, prd_detail_bt, submit_bt;
	private EditText prd_no_et, prd_date_et, prd_expiration_et, pro_num_et;
	private TextView prd_name_tv, prd_period_tv, onshelfed_num_tv, prd_unit_tv,sale_size_tv,specification_tv;

	private PurchaseRest rest;

	private String purchaseOrderNo;
	List<PrePurchaseProductResponse> beanList;
	private PrePurchaseProductResponse currPrd;

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			getloadUtil().closeProgress();
			switch (msg.what) {
			case LOADDATA_SUCCESS:
				if (beanList == null || beanList.size() <= 0) {
					ToastUtils.showShort(getActivity(), "未找到商品信息!");
					toFinishActivity();
				} else {
					PurchaseManager.getInstance(getActivity()).saveList(beanList);
				}
				break;
			case LOADDATA_FAILED:
				String message = ((msg != null && msg.obj != null) ? (String)msg.obj : "获取商品信息失败!");
				ToastUtils.showShort(getActivity(), message);
				toFinishActivity();
				break;
			case ONSHELF_SUCCESS:
				updateLocal();
				resetViewInfo();
				ToastUtils.showShort(getActivity(), "商品上架成功!");
				break;
			case ONSHELF_FAILED:
				String messageTo = ((msg != null && msg.obj != null) ? (String)msg.obj : "商品上架失败!");
				ToastUtils.showShort(getActivity(), messageTo);
				break;
			}

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.purchase_detail_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setListener();
		initData();
	}

	private void initView() {
		initHeadView();
		setHeadTitle("采购上架");
		setActionVisible(View.GONE);

		prdno_input_bt = (Button) getActivity().findViewById(R.id.prdno_input_bt);
		prd_date_bt = (Button) getActivity().findViewById(R.id.prd_date_bt);
		prd_expiration_bt = (Button) getActivity().findViewById(R.id.prd_expiration_bt);
		prd_detail_bt = (Button) getActivity().findViewById(R.id.prd_detail_bt);
		submit_bt = (Button) getActivity().findViewById(R.id.submit_bt);

		prd_no_et = (EditText) getActivity().findViewById(R.id.prd_no_et);
		prd_date_et = (EditText) getActivity().findViewById(R.id.prd_date_et);
		prd_expiration_et = (EditText) getActivity().findViewById(R.id.prd_expiration_et);
		pro_num_et = (EditText) getActivity().findViewById(R.id.pro_num_et);

		prd_name_tv = (TextView) getActivity().findViewById(R.id.prd_name_tv);
		prd_period_tv = (TextView) getActivity().findViewById(R.id.prd_period_tv);
		onshelfed_num_tv = (TextView) getActivity().findViewById(R.id.onshelfed_num_tv);
		prd_unit_tv = (TextView) getActivity().findViewById(R.id.prd_unit_tv);

        sale_size_tv = (TextView) getActivity().findViewById(R.id.sale_size_tv);//增加销售规格
        specification_tv = (TextView) getActivity().findViewById(R.id.specification_tv);//增加包装规格
	}

	private void setListener() {
		setBackListener();
		prdno_input_bt.setOnClickListener(this);
		prd_date_bt.setOnClickListener(this);
		prd_expiration_bt.setOnClickListener(this);
		prd_detail_bt.setOnClickListener(this);
		submit_bt.setOnClickListener(this);
	}

	private void initData() {
		purchaseOrderNo = getActivity().getIntent().getStringExtra("orderNum");
		getloadUtil().showProgress("加载中...");
		loadData();
	}

	private void loadData() {
		serviceResult = new LoadDataServiceResult();
		if (rest == null) {
			rest = new PurchaseRest();
		}

		if (StringUtil.empty(purchaseOrderNo)) {
			ToastUtils.showLong(getActivity(), "采购单号为空，请重新扫描采购单号！");
		}
		
		InBoundAffirmRequest req = new InBoundAffirmRequest();
        String eshopNo = AccountManager.getInstance().getShopCode();
        getLogger().i("PurchseDetailFragment loadData eshopNo = %s", eshopNo);
        req.setEshopNo(AccountManager.getInstance().getShopCode());
        req.setPurchaseOrderNo(purchaseOrderNo);

		try {
            rest.getPrdList(getActivity(), req, serviceResult);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
	}

	/**
	 * 上架
	 */
	private void onShelf() {
		if (currPrd == null) {
			return;
		}
		InboundRequest request = new InboundRequest();
		request.setPurchaseOrderNo(purchaseOrderNo);
		request.setSku(NumberTypeUtil.gb2Sku(prd_no_et.getText().toString()));
	    request.setEshopNo(AccountManager.getInstance().getShopCode());
		request.setNum(StringUtil.toInt(pro_num_et.getText().toString()));
		request.setProduceDate(prd_date_et.getText().toString());
		request.setValidUtilDate(prd_expiration_et.getText().toString());
		request.setOperator(AccountManager.getInstance().getOperator());
		request.setOperateTime(new Date());
	    //request.setOwnerCode(currPrd.getOwnerCode());

		if (!validator(request)) {
			return;
		}
		getloadUtil().showProgress("上架中...");
		serviceResult = new OnSelfServiceResult();
		if (rest == null) {
			rest = new PurchaseRest();
		}

		try {
			rest.onShelf(getActivity(), request, serviceResult);
		} catch (UnsupportedEncodingException e) {
			handler.sendEmptyMessage(ONSHELF_FAILED);
			e.printStackTrace();
		}
	}

	/**
	 *  验证提交数据
	 */
	public boolean validator(InboundRequest request) {
		if (StringUtil.empty(request.getSku())) {
			ToastUtils.showShort(getActivity(), "商品码不能为空！");
			return false;
		}
		if (currPrd.getNum() - currPrd.getInboundedNum() <= 0) {
			ToastUtils.showShort(getActivity(), "该商品已经上架完成！");
			return false;
		}
		if (request.getNum() <= 0) {
			ToastUtils.showShort(getActivity(), "上架数量必须大于0！");
			return false;
		}
		if ((request.getNum() - (currPrd.getNum() - currPrd.getInboundedNum())) > 0) {
			ToastUtils.showShort(getActivity(), "上架数量不能大于 可上架数量！");
			return false;
		}

		return true;
	}

	/**
	 *  验证日期
	 */
	public void showDatePickerDialog(final View view) {
		DatePickerFragment datePicker = DatePickerFragment.newInstance();
		datePicker.setDateListener(new DateListener() {

			@Override
			public void setDate(Date date) {
				if (view.getId() == R.id.prd_date_et) {
					// 判断生产日期不能大于当前
					if(DateUtil.dayCompare(new Date(), date)) {
						ToastUtils.showShort(getActivity(), "生产日期不能大于当前日期");
						return;	
					}
					prd_expiration_et.setText(DateUtil.getBefor(date, currPrd.getSelfLife()));
				} else {
					// 判断有效期日期不能大于当前
					if(DateUtil.dayCompare(date, new Date())) {
						ToastUtils.showShort(getActivity(), "有效期至不能小于当前日期");
						return;	
					} 
					prd_date_et.setText(DateUtil.getBefor(date, -currPrd.getSelfLife()));
				}
				
				((EditText) view).setText(DateUtil.getDateStr(date));
			}
		});
		datePicker.show(getFragmentManager(), "datePicker");
	}
	
	/**
	 * 查看待上架商品明细
	 * 
	 */
	private void intoWaitOnShelfProduct() {
		getloadUtil().showProgress("加载中......");
		List<WaitOnShelfItem> onDatas = new ArrayList<WaitOnShelfItem>();
		List<WaitOnShelfItem> waitDatas = new ArrayList<WaitOnShelfItem>();
		List<PrePurchaseProductResponse> responses = PurchaseManager.getInstance(getActivity()).getList(purchaseOrderNo);
		if (responses != null) {
			for (PrePurchaseProductResponse response : responses) {
				WaitOnShelfItem item = new WaitOnShelfItem();
				item.setPrdNo(response.getSku());
				item.setPrdName(response.getName());
				item.setPlanNum(response.getNum() == null ? "0" : String.valueOf(response.getNum().intValue()));
				item.setOnShelfNum(response.getInboundedNum() == null ? "0" : String.valueOf(response.getInboundedNum().intValue()));
				if (response.getNum() != null && response.getInboundedNum() != null) {
					int waitNum = response.getNum().intValue() - response.getInboundedNum().intValue();
					item.setWaitOnShelfNum(String.valueOf(waitNum));
				} else {
					item.setWaitOnShelfNum("0");
				}
				if ("0".equals(item.getWaitOnShelfNum())) {
					onDatas.add(item);
				} else {
					waitDatas.add(item);
				}
				
			}
		}
		getloadUtil().closeProgress();
		if (onDatas.size() > 0 || waitDatas.size() > 0) {
			Intent intent = new Intent(getActivity(),ProductOnShelfDetailActivity.class);
			intent.putExtra("onDatas", (Serializable)onDatas);
			intent.putExtra("waitDatas", (Serializable)waitDatas);
			getActivity().startActivity(intent);
		} else {
			ToastUtils.show(getActivity(), "没有待上架的商品", Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 获取指定sku商品信息
	 * 
	 * @param sku
	 */
	private void getPrd(String sku) {
		currPrd = PurchaseManager.getInstance(getActivity()).getProduct(purchaseOrderNo, sku);
		if (currPrd == null) {
			ToastUtils.showShort(getActivity(), R.string.not_fond_prdinfo);
			return;
		}
		refreshViewInfo(currPrd);
	}

	/**
	 * 显示指定商品信息
	 * 
	 * @param prd
	 */
	@SuppressLint("NewApi")
	private void refreshViewInfo(PrePurchaseProductResponse prd) {
		prd_name_tv.setText(prd.getName());

        if (currPrd.getSelfLife() > 0) {
            // 保质期大于0
			prd_date_bt.setBackground(getActivity().getResources().getDrawable(R.drawable.button));
			prd_expiration_bt.setBackground(getActivity().getResources().getDrawable(R.drawable.button));
			prd_date_bt.setEnabled(true);
			prd_expiration_bt.setEnabled(true);
			prd_period_tv.setText(String.valueOf(prd.getSelfLife())); // 保质期
		} else {
			prd_period_tv.setText("非效期商品"); // 保质期
			prd_date_bt.setBackground(getActivity().getResources().getDrawable(R.drawable.button_disable));
			prd_expiration_bt.setBackground(getActivity().getResources().getDrawable(R.drawable.button_disable));
			prd_date_bt.setEnabled(false);
			prd_expiration_bt.setEnabled(false);
		}
		onshelfed_num_tv.setText(prd.getInboundedNum() + "/" + prd.getNum()); // 入库数量
		pro_num_et.setText((prd.getNum() - prd.getInboundedNum()) + ""); // 数量
		prd_unit_tv.setText(prd.getUnit()); // 单位

		// 原型上没有销售规格和包装规格不显示
//        ProductResponse product = ProductManager.getInstance(getActivity()).getProduct(prd.getSku());
//        if (product != null && product.getSpecification() != null) {
//            specification_tv.setText(product.getSpecification());//增加包装规格
//        } else {
//            specification_tv.setText("");//增加包装规格
//        }
//        if (product != null && product.getSaleSize() != null) {
//              sale_size_tv.setText(product.getSaleSize());//增加销售规格
//        } else {
//              sale_size_tv.setText("");//增加销售规格
//        }
	}

	/**
	 * 重置商品信息
	 */
	private void resetViewInfo() {
		prd_name_tv.setText("");
		prd_period_tv.setText("");
		onshelfed_num_tv.setText("");
		prd_no_et.setText("");
		prd_date_et.setText("");
		prd_expiration_et.setText("");
		pro_num_et.setText("");
		prd_unit_tv.setText("");

        specification_tv.setText("");
        sale_size_tv.setText("");
	}

	/**
	 * 更新本地数据
	 */
	protected void updateLocal() {
		PurchaseManager.getInstance(getActivity()).updateNum(currPrd.getPurchaseOrderNo(), currPrd.getSku(),
				StringUtil.toInt(pro_num_et.getText().toString()) + currPrd.getInboundedNum());
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.prdno_input_bt) {
			inputBarcode("输入商品条码");
		} else if (id == R.id.prd_date_bt) {
			if (StringUtil.empty(prd_no_et.getText().toString())) {
				ToastUtils.showShort(getActivity(), "请先输入商品条码！");
				return;
			}
			showDatePickerDialog(prd_date_et);
		} else if (id == R.id.prd_expiration_bt) {
			if (StringUtil.empty(prd_no_et.getText().toString())) {
				ToastUtils.showShort(getActivity(), "请先输入商品条码！");
				return;
			}
			showDatePickerDialog(prd_expiration_et);
		} else if (id == R.id.submit_bt) {
			onShelf();
		} else if (id == R.id.prd_detail_bt){
			intoWaitOnShelfProduct();
		}
	}

	@Override
	public void onScanResult(String result) {
		super.onScanResult(result);

	 if (NumberTypeUtil.isGbCodeOrSku(result)) {
			String sku = NumberTypeUtil.gb2Sku(result);
			if (StringUtil.empty(sku)) {
				ToastUtils.showShort(getActivity(), R.string.not_found_sku);
				return;
			}
			prd_no_et.setText(result);
			getPrd(sku);
		}
	}
	
	private class LoadDataServiceResult implements ServiceResult {
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			try {
				JSONObject json = new JSONObject(new String(responseBody));
				if (json.getInt("code") == HttpStatus.OK.getCode()) {
					beanList = rest.parsePrdList(json.getString("result"));
					Message msg = new Message();
					msg.what = LOADDATA_SUCCESS;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = LOADDATA_FAILED;
					msg.obj = json.getString("message");
					handler.sendMessage(msg);
				}
			} catch (JSONException e) {
				Message msg = new Message();
				msg.what = LOADDATA_FAILED;
				msg.obj = getCommonString(R.string.resut_parse_failed);
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			Message msg = new Message();
			msg.what = LOADDATA_FAILED;
			msg.obj = getCommonString(R.string.network_disable);
			handler.sendMessage(msg);
		}

	}

	private class OnSelfServiceResult implements ServiceResult {
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			try {
				JSONObject json = new JSONObject(new String(responseBody));
				if (json.getInt("code") == HttpStatus.OK.getCode()) {
					Message msg = new Message();
					msg.what = ONSHELF_SUCCESS;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = ONSHELF_FAILED;
					msg.obj = json.getString("message");
					handler.sendMessage(msg);
				}
			} catch (JSONException e) {
				Message msg = new Message();
				msg.what = ONSHELF_FAILED;
				msg.obj = getCommonString(R.string.resut_parse_failed);
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			Message msg = new Message();
			msg.what = ONSHELF_FAILED;
			msg.obj = getCommonString(R.string.network_disable);	
			handler.sendMessage(msg);
		}

	}

}
