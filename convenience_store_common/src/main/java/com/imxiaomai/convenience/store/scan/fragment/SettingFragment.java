package com.imxiaomai.convenience.store.scan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.StoreApplication;
import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.imxiaomai.convenience.store.scan.service.DownloadService;
import com.imxiaomai.convenience.store.scan.service.LoadDataService;
import com.imxiaomai.convenience.store.scan.util.CActionPromptDialog;
import com.imxiaomai.convenience.store.scan.util.CActionPromptDialog.CActionListener;
import com.imxiaomai.convenience.store.scan.util.DeviceUtil;
import com.imxiaomai.convenience.store.scan.util.IntentUtils;

/**
 * 
 * @author lixiaoming
 * 
 */
public class SettingFragment extends BaseFragment implements OnClickListener {

	private View layoutGetInfo;
	private View layoutCheckUpdate;
	private View layoutAccountInfo;
	private View layoutLogout;
	private TextView checkUpdateTv;
	private View layoutScroll;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.setting_fragment, container, false);
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
		setHeadTitle("设置");
	}

	private void initView() {
		layoutGetInfo = getActivity().findViewById(R.id.layout_get_info);
		layoutCheckUpdate = getActivity().findViewById(R.id.layout_check_update);
		layoutAccountInfo = getActivity().findViewById(R.id.layout_account_info);
		layoutLogout = getActivity().findViewById(R.id.layout_logout);
		checkUpdateTv = (TextView) getActivity().findViewById(R.id.check_update_tv);
		layoutScroll =  getActivity().findViewById(R.id.layout_scroll);
	}

	private void setListener() {
		setBackListener();
		layoutGetInfo.setOnClickListener(this);
		layoutCheckUpdate.setOnClickListener(this);
		layoutAccountInfo.setOnClickListener(this);
		layoutLogout.setOnClickListener(this);
		layoutScroll.setOnClickListener(this);
	}

	protected void goBack() {
		getActivity().onBackPressed();
	}

	private void initData() {
		checkUpdateTv.setText(String.format(getString(R.string.set_msg_check_update), DeviceUtil.getVersionName(StoreApplication.getContext())));
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.layout_get_info) {
			startPrdSkuService();
		} else if (id == R.id.layout_check_update) {
			startDownloadService();
		} else if (id == R.id.layout_account_info) {
			IntentUtils.showAccountInfoActivity(getActivity());
		} else if (id == R.id.layout_logout) {
			((StoreApplication) getActivity().getApplication()).exit();
			IntentUtils.showLoginActivity(getActivity());
			getActivity().finish();
		} else if (id == R.id.layout_scroll) {
			showAboutInfo();
		}
	}

	private void startPrdSkuService() {
		Intent service = new Intent(getActivity(), LoadDataService.class);
		getActivity().startService(service);
	}
	
	private void startDownloadService() {
		Intent downloadService = new Intent(getActivity(), DownloadService.class);
        downloadService.putExtra("isLoadData", false);
        getActivity().startService(downloadService);
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
	
	int clickCount = 0;
	private void showAboutInfo() {
		clickCount ++;
		if (clickCount == 10) {
			clickCount = 0;
			String url = HttpConstant.SERVER_URL;	
			StringBuffer content = new StringBuffer();
			content.append("\n");
			content.append(url);
			CActionPromptDialog aboutDialog = new CActionPromptDialog(getActivity());
			String versionName = DeviceUtil.getVersionName(getActivity());
			int versionCode = DeviceUtil.getVersionCode(getActivity());
			content.append("\n版本名 ").append(versionName).append("\n 版本号 ").append(versionCode);
			aboutDialog.setTitle("\n"+getString(R.string.app_name));
			aboutDialog.setContent(content.toString());
			aboutDialog.setActionButton("确定", new CActionListener() {
				@Override
				public void onAction() {
					clickCount = 0;
				}
			});
			aboutDialog.show();
		}
	}
}
