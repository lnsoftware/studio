package com.imxiaomai.convenience.store.scan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.activity.InventoryTaskActivity;
import com.imxiaomai.convenience.store.scan.activity.OutBoundActivity;
import com.imxiaomai.convenience.store.scan.activity.PurchaseActivity;
import com.imxiaomai.convenience.store.scan.activity.RevokeActivity;
import com.imxiaomai.convenience.store.scan.activity.SettingActivity;
import com.imxiaomai.convenience.store.scan.activity.StockQueryActivity;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LcdMainFragment extends BaseFragment {

	private GridView main_gv;
	private static final String[] functions = new String[] {"[1]库存查询", "[2]采购上架", "[3]出库确认", "[4]盘        点","[5]退货管理"};
	private List<HashMap<String, String>> functionList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.lcd_main_fragment, container, false);
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
		setHeadTitle("便利店系统");
		setBackVisible(View.GONE);
		setActionVisible(View.VISIBLE);
		main_gv = (GridView) getActivity().findViewById(R.id.main_gv);
		main_gv.setAdapter(new SimpleAdapter(getActivity(), loadFunctions(),
				R.layout.main_item, new String[] { "function" },
				new int[] { R.id.item_tv }));
	}

	private void setListener() {
		main_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(position);
			}
		});

		setActionListener();
	}

	@Override
	protected void goAction() {
		super.goAction();
		Intent settingIntent = new Intent(getActivity(), SettingActivity.class);
		startActivity(settingIntent);
	}

	@Override
	public void onResume() {
		super.onResume();
		// TtsPlayer.playText("rk_scan_start_tts");
	}

	protected void startActivity(int position) {
		Intent intent = null;
		switch (position) {
		case 0:
			//跳转到库存查询页面
			intent = new Intent(getActivity(), StockQueryActivity.class);
			startActivity(intent);
			break;
		case 1:
			//跳转到采购上架页面
			intent = new Intent(getActivity(), PurchaseActivity.class);
			startActivity(intent);
			break;
		//case 2:
		//跳转到缺货登记页面
		//	break;
		case 2:
			//跳转到出库确认页面
			intent = new Intent(getActivity(), OutBoundActivity.class);
			startActivity(intent);
			break;
		case 3:
			//跳转到盘点页面
			intent = new Intent(getActivity(), InventoryTaskActivity.class);
			startActivity(intent);
			break;
        case 4:
            //跳转到退货管理页面
            intent = new Intent(getActivity(), RevokeActivity.class);
            startActivity(intent);
            break;
		default:
			break;
		}
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	public List<HashMap<String, String>> loadFunctions() {
		if (functionList != null) {
			return functionList;
		}

		functionList = new ArrayList<HashMap<String, String>>();
		int length = functions.length;
		for (int i = 0; i < length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("function", functions[i]);
			functionList.add(map);
		}
		return functionList;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		int validKeyCode = keyCode - 8;
		if (validKeyCode >= 0 && validKeyCode < functions.length) {
			// int position = validKeyCode -1 ;
			// if(position < 0) {
			// position = 7;
			// }
			main_gv.performItemClick(main_gv.getChildAt(validKeyCode),
					validKeyCode, main_gv.getItemIdAtPosition(validKeyCode));
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onScanResult(String result) {
//		if (!TextUtils.isEmpty(result)) {
			ToastUtils.showShort(getActivity(), result);
//		}
	}
}
