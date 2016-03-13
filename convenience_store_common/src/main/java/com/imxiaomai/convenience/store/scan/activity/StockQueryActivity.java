package com.imxiaomai.convenience.store.scan.activity;

import com.imxiaomai.convenience.store.scan.fragment.StockQueryFragment;

public class StockQueryActivity extends BaseFragmentActivity {

	@Override
	public void newFragmentObj() {
		fragment = new StockQueryFragment();
	}
}
