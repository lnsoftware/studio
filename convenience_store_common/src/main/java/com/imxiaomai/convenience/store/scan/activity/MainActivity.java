package com.imxiaomai.convenience.store.scan.activity;

import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.imxiaomai.convenience.store.barcode.BaseBarcodeManager;
import com.imxiaomai.convenience.store.barcode.ThintaBarcodeManager;
import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.StoreApplication;
import com.imxiaomai.convenience.store.scan.constants.AppConstants;
import com.imxiaomai.convenience.store.scan.fragment.LcdMainFragment;
import com.imxiaomai.convenience.store.scan.receiver.NetBroadcastReceiver;
import com.imxiaomai.convenience.store.scan.util.IntentUtils;
import com.imxiaomai.convenience.store.scan.util.ScreenListener;
import com.imxiaomai.convenience.store.scan.util.ScreenListener.ScreenStateListener;

public class MainActivity extends FragmentActivity {
	
	private LcdMainFragment lcdMainFragment;
	private BaseBarcodeManager mBarcodeMng;
	private NetBroadcastReceiver netReceiver ;
	private IntentFilter filter = new IntentFilter();
	// 屏幕监听
	private ScreenListener screen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.main_activity);
		netReceiver = new NetBroadcastReceiver();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		if (this.findViewById(R.id.frame_container) != null) {
			if (savedInstanceState != null) {
				return;
			}
			mBarcodeMng = ((StoreApplication) getApplication()).getBarcodeMng();
			((StoreApplication) getApplication()).pushStack(this);
			
			if(mBarcodeMng == null) {
				mBarcodeMng = ThintaBarcodeManager.getInstance();
			}
			((StoreApplication) getApplication()).setBarcodeMng(mBarcodeMng);
			setFragment();
		}
		
		initScreenListener();
	}
	
	public void setFragment() {
		lcdMainFragment = new LcdMainFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.frame_container, lcdMainFragment);
		transaction.commit();
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (lcdMainFragment == null) {
			setFragment();
		}
		lcdMainFragment.onKeyUp(keyCode, event);
		return super.onKeyUp(keyCode, event);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(netReceiver, filter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(netReceiver);
	}
	
	public void initScreenListener() {
		screen = new ScreenListener(this);
		screen.begin(new ScreenStateListener() {

            @Override
            public void onUserPresent() {
                long currentTime = System.currentTimeMillis();
                if ((currentTime - AppConstants.timeStamp) >= AppConstants.FLAG_TIMESTAMP) {
                	((StoreApplication) getApplication()).exit();
        			IntentUtils.showLoginActivity(MainActivity.this);
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
