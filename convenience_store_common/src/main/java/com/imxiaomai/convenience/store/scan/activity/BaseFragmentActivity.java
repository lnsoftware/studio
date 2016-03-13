package com.imxiaomai.convenience.store.scan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.StoreApplication;
import com.imxiaomai.convenience.store.scan.constants.AppConstants;
import com.imxiaomai.convenience.store.scan.fragment.BaseFragment;
import com.imxiaomai.convenience.store.scan.util.IntentUtils;
import com.imxiaomai.convenience.store.scan.util.NetworkUtil;
import com.imxiaomai.convenience.store.scan.util.ScreenListener;
import com.imxiaomai.convenience.store.scan.util.ScreenListener.ScreenStateListener;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

public abstract class BaseFragmentActivity extends FragmentActivity {

    public BaseFragment fragment;

    private WmsReceiver netReceiver;
    private IntentFilter filter = new IntentFilter();

    //接受广播
    private String RECE_DATA_ACTION = "com.se4500.onDecodeComplete";
    // 屏幕监听
    protected ScreenListener screen;
    //扫描结束action
    private final static String SCAN_ACTION = "urovo.rcv.message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        netReceiver = new WmsReceiver();
        filter.addAction("com.sim.action.SIMSCAN");
        //注册系统广播  接受扫描到的数据
        filter.addAction(RECE_DATA_ACTION);
        filter.addAction(SCAN_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        if (this.findViewById(R.id.frame_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            setFragment();
        }

    }

    public void setFragment() {
        newFragmentObj();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, fragment);
        transaction.commit();
    }

    public void initScreenListener() {
        if (screen == null) {
            screen = new ScreenListener(this);
        }
        screen.begin(new ScreenStateListener() {

            @Override
            public void onUserPresent() {
                long currentTime = System.currentTimeMillis();
                if ((currentTime - AppConstants.timeStamp) >= AppConstants.FLAG_TIMESTAMP) {
                    ((StoreApplication) getApplication()).exit();
                    IntentUtils.showLoginActivity(BaseFragmentActivity.this);
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

    public int getLayoutResource() {
        return R.layout.base_activity;
    }

    public abstract void newFragmentObj();

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (fragment == null) {
            setFragment();
        }
        fragment.onKeyUp(keyCode, event);
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(netReceiver, filter);
        initScreenListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(netReceiver);
        if (screen != null)
            screen.unregisterListener();
    }

    private class WmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            this.abortBroadcast();

            final String action = intent.getAction();

            if ("com.sim.action.SIMSCAN".equals(action)) {
                fragment.onScanResult(intent.getStringExtra("value"));
            } else if (action.equals(RECE_DATA_ACTION)) {
                fragment.onScanResult(intent.getStringExtra("se4500"));
            } else if (action.equals(SCAN_ACTION)) {
                byte[] barcode = intent.getByteArrayExtra("barocode");
                fragment.onScanResult(new String(barcode));
            } else if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                if (!NetworkUtil.isAvaliable(context)) {
                    ToastUtils.showShort(context, "网络已经断开,请检查网络设置!");
                }
            }
        }

    }
}
