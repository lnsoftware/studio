package com.imxiaomai.convenience.store.scan.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.imxiaomai.convenience.store.barcode.BaseBarcodeManager;
import com.imxiaomai.convenience.store.barcode.ThintaBarcodeManager;
import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.StoreApplication;
import com.imxiaomai.convenience.store.scan.constants.AppConstants;
import com.imxiaomai.convenience.store.scan.fragment.BaseFragment;
import com.imxiaomai.convenience.store.scan.fragment.ProductOnShelfFragment;
import com.imxiaomai.convenience.store.scan.fragment.ProductWaitShelfFragment;
import com.imxiaomai.convenience.store.scan.util.IntentUtils;
import com.imxiaomai.convenience.store.scan.util.ScreenListener;
import com.imxiaomai.convenience.store.scan.util.ScreenListener.ScreenStateListener;

public class ProductOnShelfDetailActivity extends FragmentActivity implements
		OnClickListener {
	private BaseBarcodeManager mBarcodeMng;

	private ImageView backIv;
	private ImageView actionIv;
	private TextView titleTv;

	private TextView tvGuideOff;
	private TextView tvGuideOn;
	private BaseFragment fragment;
	
	// 屏幕监听
	private ScreenListener screen;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.product_onshelf_detail_activity);

		mBarcodeMng = ((StoreApplication) getApplication()).getBarcodeMng();
		if (mBarcodeMng == null) {
			mBarcodeMng = ThintaBarcodeManager.getInstance();
			((StoreApplication) getApplication()).setBarcodeMng(mBarcodeMng);
		}


		initTitleView();
		initView();
		setListener();
		loadData();
		initScreenListener();
	}

	private void initTitleView() {
		backIv = (ImageView) findViewById(R.id.back_iv);
		actionIv = (ImageView) findViewById(R.id.action_iv);
		titleTv = (TextView) findViewById(R.id.head_title_tv);
		backIv.setOnClickListener(this);
		actionIv.setVisibility(View.GONE);
		titleTv.setVisibility(View.VISIBLE);
		titleTv.setText("上架商品明细");
		
		if (this.findViewById(R.id.frame_container) != null) {
			showWaitfFragment();
		}
	}

	private void loadData() {
	}
	
	private void showWaitfFragment() {
		fragment =  new ProductWaitShelfFragment(); 
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.frame_container, fragment);
		transaction.commit();
	}
	private void showOnFragment() {
		fragment =  new ProductOnShelfFragment(); 
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.frame_container, fragment);
		transaction.commit();
	}

	private void initView() {
		tvGuideOff = (TextView) findViewById(R.id.tv_guid_off);
		tvGuideOn = (TextView) findViewById(R.id.tv_guid_on);
	}

	private void setListener() {
		tvGuideOff.setOnClickListener(this);
		tvGuideOn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.back_iv) {
			finish();
		} else if (id == R.id.tv_guid_off) {
		    // 待上架
			showWaitfFragment();
			tvGuideOff.setBackgroundResource(R.drawable.filter_item_bg_hl);
			tvGuideOn.setBackgroundResource(R.drawable.filter_item_bg);
		} else if (id == R.id.tv_guid_on) {
		    // 已上架
			showOnFragment();
			tvGuideOff.setBackgroundResource(R.drawable.filter_item_bg);
			tvGuideOn.setBackgroundResource(R.drawable.filter_item_bg_hl);
		}
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		fragment.onKeyUp(keyCode, event);
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	public void initScreenListener() {
		screen = new ScreenListener(this);
		screen.begin(new ScreenStateListener() {

            @Override
            public void onUserPresent() {
                long currentTime = System.currentTimeMillis();
                if ((currentTime - AppConstants.timeStamp) >= AppConstants.FLAG_TIMESTAMP) {
                	((StoreApplication) getApplication()).exit();
        			IntentUtils.showLoginActivity(ProductOnShelfDetailActivity.this);
        			finish();
                }
            }

            @Override
            public void onScreenOn() {
            }

            @Override
            public void onScreenOff() {
            	AppConstants.timeStamp = System.currentTimeMillis();
            }
        });
	}
	
}
