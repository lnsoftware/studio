package com.imxiaomai.convenience.store.scan.activity;

import com.imxiaomai.convenience.store.barcode.BaseBarcodeManager;
import com.imxiaomai.convenience.store.barcode.ThintaBarcodeManager;
import com.imxiaomai.convenience.store.scan.StoreApplication;
import com.imxiaomai.convenience.store.scan.fragment.LoginFragment;

public class LoginActivity extends BaseFragmentActivity {
	private BaseBarcodeManager mBarcodeMng;
	
	@Override
	public void newFragmentObj() {
		mBarcodeMng = ThintaBarcodeManager.getInstance();
		((StoreApplication) getApplication()).setBarcodeMng(mBarcodeMng);
		fragment = new LoginFragment();
	}

}
