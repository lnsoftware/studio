package com.imxiaomai.convenience.store.scan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.activity.PurchaseDetailActivity;
import com.imxiaomai.convenience.store.scan.util.NumberTypeUtil;
import com.imxiaomai.convenience.store.scan.util.StringUtil;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

/**
 * 
 * 采购上架
 * @author lixiaoming
 *
 */
public class PurchaseFragment extends BaseFragment implements OnClickListener {
	
	private Button action_bt,input_bt;
	private EditText order_num_et;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.purchase_fragment, container, false);
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
		setActionVisible(View.GONE);
		setHeadTitle("采购上架");
		
		action_bt = (Button) getActivity().findViewById(R.id.action_bt);
		input_bt = (Button) getActivity().findViewById(R.id.input_bt);
		order_num_et = (EditText) getActivity().findViewById(R.id.order_num_et);
		
	}

	private void setListener() {
		setBackListener();
		action_bt.setOnClickListener(this);
		input_bt.setOnClickListener(this);
	}

	private void initData() {

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.action_bt) {
			String orderNum = order_num_et.getText().toString().trim();
			if(StringUtil.empty(orderNum)) {
				return;
			}
			Intent intent = new Intent(getActivity(),PurchaseDetailActivity.class);
			intent.putExtra("orderNum", orderNum);
			startActivity(intent);
		} else if (id == R.id.input_bt){
			inputBarcode("输入单号");
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onScanResult(String result) {
		super.onScanResult(result);
		result = NumberTypeUtil.autoAddPo(result);
		if(NumberTypeUtil.isPurchaseNum(result)) {
			order_num_et.setText(result);
			action_bt.performClick();
		} else {
			ToastUtils.showShort(getActivity(), "非法的采购单编号！");
		}
	}
}
