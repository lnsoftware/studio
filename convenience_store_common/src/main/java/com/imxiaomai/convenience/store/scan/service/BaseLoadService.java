package com.imxiaomai.convenience.store.scan.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.util.SystemDialog;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;

public class BaseLoadService extends Service {
	protected SystemDialog mDialog;
	protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            closeDialog();
            switch (msg.what) {
                case SystemDialog.LOADDATA_SUCCESS:
                    break;
                case SystemDialog.LOADDATA_FAILED:
                    ToastUtils.showShort(BaseLoadService.this, String.valueOf(msg.obj));
                    break;
                case SystemDialog.NETWORK_FAIL:
                    ToastUtils.showShort(BaseLoadService.this, "网络不佳或服务端无响应");
                    break;
            }
        }
    };

    protected void showDialog(String content) {
    	if (mDialog == null) {
    		mDialog = new SystemDialog(this, "提示", content);
    	}
    	  mDialog.show();
    	  
    	  LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          View contentView = inflater.inflate(R.layout.dialog_progress, null);
          ((TextView)contentView.findViewById(R.id.dialog_txt)).setText(content);
          mDialog.setContentView(contentView);
          
    }
    
    protected void closeDialog() {
    	if (mDialog == null) return;
    	  mDialog.dismiss();
    }
    
    
    /**
     * 加载数据失败
     */
    protected void loadDataFailed(String message) {
    	Message msg = new Message();
        msg.what = SystemDialog.LOADDATA_FAILED;
        msg.obj = message;
        mHandler.sendMessage(msg);
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 销毁时把消息去除
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
