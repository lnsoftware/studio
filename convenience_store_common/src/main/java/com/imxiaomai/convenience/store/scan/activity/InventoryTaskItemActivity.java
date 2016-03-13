package com.imxiaomai.convenience.store.scan.activity;

import com.imxiaomai.convenience.store.scan.fragment.InventoryTaskItemFragment;

/**
 * 盘点作业的item
 */
public class InventoryTaskItemActivity extends BaseFragmentActivity {

	@Override
	public void newFragmentObj() {
		fragment = new InventoryTaskItemFragment();
	}

}
