package com.imxiaomai.convenience.store.scan.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;

/**
 * 
 * @author lixiaoming
 * 
 */
public class AccountInfoFragment extends BaseFragment implements OnClickListener {

	private TextView accountTv;
	private TextView realNameTv;
	private TextView telTv;
	private TextView shopCodeTv;
	private TextView shopNameTv;
	private TextView shopTypeTv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.account_info_fragment, container, false);
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
		setHeadTitle("账户信息");
	}

	private void initView() {
		accountTv = (TextView) getActivity().findViewById(R.id.account_tv);
		realNameTv = (TextView) getActivity().findViewById(R.id.realname_tv);
		telTv = (TextView) getActivity().findViewById(R.id.tel_tv);
		shopCodeTv = (TextView) getActivity().findViewById(R.id.shop_code_tv);
		shopNameTv = (TextView) getActivity().findViewById(R.id.shop_name_tv);
		shopTypeTv = (TextView) getActivity().findViewById(R.id.shop_type_tv);
	}

	private void setListener() {
		setBackListener();
	}

	protected void goBack() {
		getActivity().onBackPressed();
	}

	private void initData() {
		accountTv.setText(AccountManager.getInstance().getOperator());
		realNameTv.setText(AccountManager.getInstance().getRealName());
		telTv.setText(AccountManager.getInstance().getTel());
		shopCodeTv.setText(AccountManager.getInstance().getShopCode());
		shopNameTv.setText(AccountManager.getInstance().getShopName());
		shopTypeTv.setText(AccountManager.getInstance().getShopType());
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onScanResult(String result) {
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
}
