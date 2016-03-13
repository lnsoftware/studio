package com.imxiaomai.convenience.store.scan.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.imxiaomai.convenience.store.scan.model.OutBoundAffirmRequest;
import com.imxiaomai.convenience.store.scan.model.OutBoundAffirmStatisticsResponse;
import com.imxiaomai.convenience.store.scan.rest.OutBoundRest;
import com.imxiaomai.convenience.store.scan.util.NumberTypeUtil;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 出库确认
 * 
 * @author lixiaoming
 *
 */
public class OutBoundFragment extends BaseFragment implements OnClickListener {

    private static final int OUT_BOUND_SUCCESS = 121;
    private static final int OUT_BOUND_FAILED = 122;
	private EditText etOrderNo;
	private TextView tvOrderNum;
	private TextView tvSkuNum;
	private TextView dispatchingTv;
	private TextView tvMaikeName;
	private TextView tvMaikePhone;
	private TextView tvCustName;
	private Button btnOutbound;
	private Button btnInput;
	
	private String orderNo;
	private OutBoundRest outBoundRest;
	private OutBoundAffirmStatisticsResponse  outBoundResponse;
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
            getloadUtil().closeProgress();

			switch(msg.what) {
			case LOADDATA_SUCCESS:
				if (outBoundResponse == null) {
					ToastUtils.showShort(getActivity(), "没有出库订单信息");
                    toFinishActivity();
				} else {
					refreshViewInfo();
				}
				break;
			case LOADDATA_FAILED:
				ToastUtils.showShort(getActivity(), (msg.obj != null ? (String)msg.obj : "获取出库订单信息失败"));
                toFinishActivity();
                break;
            case OUT_BOUND_SUCCESS:
                resetViewInfo();
                ToastUtils.showShort(getActivity(), "出库成功");
                break;
            case OUT_BOUND_FAILED:
                ToastUtils.showShort(getActivity(), ((msg != null && msg.obj != null) ? (String)msg.obj : "出库失败"));
                toFinishActivity();
                break;
            }
		}
		
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.out_bound_fragment, container, false);
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
		setHeadTitle("出库确认");
	}

	private void initView() {
		etOrderNo = (EditText) getActivity().findViewById(R.id.order_no_et);
		tvOrderNum = (TextView) getActivity().findViewById(R.id.order_num_tv);
		tvSkuNum = (TextView) getActivity().findViewById(R.id.sku_num_tv);
		dispatchingTv = (TextView) getActivity().findViewById(R.id.dispatching_tv);
		tvMaikeName = (TextView) getActivity().findViewById(R.id.maike_name_tv);
		tvMaikePhone = (TextView) getActivity().findViewById(R.id.maike_phone_tv);
		tvCustName = (TextView) getActivity().findViewById(R.id.cust_name_tv);
		btnOutbound = (Button) getActivity().findViewById(R.id.btn_out_bound);
		btnInput = (Button) getActivity().findViewById(R.id.input_bt);
	}
	
	private void setListener() {
		setBackListener();
		setActionListener();
		btnOutbound.setOnClickListener(this);
		btnInput.setOnClickListener(this);
	}

	private void initData() {
		outBoundRest = new OutBoundRest();
	}

	/**
	 * 获取出库订单信息
	 */
	private void loadData(String mOrderNo) {
		if (TextUtils.isEmpty(mOrderNo)) return;
		getloadUtil().showProgress("加载中...");
		
		serviceResult = new OutBoundOrderInfoServiceResult();
        OutBoundAffirmRequest req = new OutBoundAffirmRequest();
        req.setOrderNo(mOrderNo);
        req.setEshopNo(AccountManager.getInstance().getShopCode());
        try {
            outBoundRest.getOutBoundOrderInfo(getFragmentActivity(),req,new OutBoundOrderInfoServiceResult());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(LOADDATA_FAILED);
        }
	}
	
	/**
	 * 出库订单信息确认
	 */
	private void onOutBoundConfirm() {
		if (TextUtils.isEmpty(orderNo)) return;
		getloadUtil().showProgress("加载中...");
		
		serviceResult = new OutBoundConfirmServiceResult();
		OutBoundAffirmRequest req = new OutBoundAffirmRequest();
		req.setOrderNo(orderNo);
        req.setMaikeName(outBoundResponse.getMaikeName());
        req.setMaikePhone(outBoundResponse.getMaikePhone());
		req.setOperator(AccountManager.getInstance().getOperator());
        req.setEshopNo(AccountManager.getInstance().getShopCode());
		req.setOperateTime(new Date());
		try {
			outBoundRest.onOutBoundConfirm(getActivity(), req, serviceResult);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(LOADDATA_FAILED);
		}
	}
	
	/**
	 * 更新页面信息
	 */
	private void refreshViewInfo() {
		if (outBoundResponse == null) return;
		orderNo = outBoundResponse.getOrderNo();
		etOrderNo.setText(String.valueOf(outBoundResponse.getOrderNo()));
		tvOrderNum.setText(String.valueOf(outBoundResponse.getNum()));
		tvSkuNum.setText(String.valueOf(outBoundResponse.getSkuNum()));
		dispatchingTv.setText(outBoundResponse.getDistributionType().getName());
		if (!TextUtils.isEmpty(outBoundResponse.getMaikeName())) {
			tvMaikeName.setText(String.valueOf(outBoundResponse.getMaikeName()));
		}
 	    if (!TextUtils.isEmpty(outBoundResponse.getMaikePhone())) {
 	    	tvMaikePhone.setText(String.valueOf(outBoundResponse.getMaikePhone()));
 	    }
		if (!TextUtils.isEmpty(outBoundResponse.getCustName())) {
			tvCustName.setText(String.valueOf(outBoundResponse.getCustName()));
		}
	}
	
	/**
	 * 重置页面信息
	 */
	private void resetViewInfo() {
		outBoundResponse = null;
		orderNo = "";
		etOrderNo.setText("");
		tvOrderNum.setText("");
		tvSkuNum.setText("");
		dispatchingTv.setText("");
		tvMaikeName.setText("");
		tvMaikePhone.setText("");
		tvCustName.setText("");
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.btn_out_bound) {
			if (TextUtils.isEmpty(orderNo)) {
				ToastUtils.showShort(getActivity(), "出库订单信息为空");
			} else {
				onOutBoundConfirm();
			}
		} else if (id == R.id.input_bt) {
			inputBarcode("请输入订单号");
		}
	}
	
	@Override
	public void onScanResult(String result) {
		super.onScanResult(result);
		if(TextUtils.isEmpty(result)) return;
		
		String orderNoCheck = NumberTypeUtil.autoAddXM(result);
		if (TextUtils.isEmpty(orderNoCheck)) {
			ToastUtils.showShort(getActivity(), "订单号不正确");
			return;
		} else {
			loadData(orderNoCheck);
		}
	}
	
	/**
	 * 获取出库订单信息回调
	 * 
	 * @author lixiaoming
	 *
	 */
	class OutBoundOrderInfoServiceResult implements ServiceResult {
        Message msg = Message.obtain();
        @Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			try {
				JSONObject jsonObject = new JSONObject(new String(responseBody));
				getLogger().i("lxm-outBound:%s", jsonObject.toString());
				if (jsonObject != null && jsonObject.getInt("code") == HttpStatus.OK.getCode()) {
					outBoundResponse = outBoundRest.parseOutBoundOrderInfo(jsonObject.getString("result"));
					handler.sendEmptyMessage(LOADDATA_SUCCESS);
				} else if (jsonObject != null) {
					msg.obj = jsonObject.getString("message");
					msg.what = LOADDATA_FAILED;
					handler.sendMessage(msg);
				}
			} catch (JSONException e) {
				msg.what = LOADDATA_FAILED;
				msg.obj = getCommonString(R.string.resut_parse_failed);
				handler.sendMessage(msg);
				e.printStackTrace();
			}	
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			msg.what = LOADDATA_FAILED;
			msg.obj = getCommonString(R.string.network_disable);
			handler.sendMessage(msg);
			getLogger().i("lxm-outBound:%s", "fail");
		}
		
	}
	
	/**
	 * 出库订单确认回调
	 * 
	 * @author lixiaoming
	 *
	 */
	class OutBoundConfirmServiceResult implements ServiceResult {
        Message msg = Message.obtain();
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			try {
				JSONObject jsonObject = new JSONObject(new String(responseBody));
				getLogger().i("lxm-outBoundConfirm:%s", jsonObject.toString());
				if (jsonObject != null && jsonObject.getInt("code") == HttpStatus.OK.getCode()) {
					handler.sendEmptyMessage(OUT_BOUND_SUCCESS);
				} else if (jsonObject != null) {
					msg.obj = jsonObject.getString("message");
					msg.what = OUT_BOUND_FAILED;
					handler.sendMessage(msg);
				}
			} catch (JSONException e) {
				handler.sendEmptyMessage(OUT_BOUND_FAILED);
				e.printStackTrace();
			}
			
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			msg.what = OUT_BOUND_FAILED;
			msg.obj = getCommonString(R.string.network_disable);
            handler.sendMessage(msg);
			getLogger().i("lxm-outBoundConfirm:%s", "fail");
			
		}
		
	}

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
