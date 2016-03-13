package com.imxiaomai.convenience.store.scan.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RemoteViews;

import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.StoreApplication;
import com.imxiaomai.convenience.store.scan.constants.AppConstants;
import com.imxiaomai.convenience.store.scan.constants.HttpStatus;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;
import com.imxiaomai.convenience.store.scan.manager.BroadcastManager;
import com.imxiaomai.convenience.store.scan.model.AppVersionRequest;
import com.imxiaomai.convenience.store.scan.model.AppVersionResponse;
import com.imxiaomai.convenience.store.scan.rest.CheckUpdateRest;
import com.imxiaomai.convenience.store.scan.util.DeviceUtil;
import com.imxiaomai.convenience.store.scan.util.Logger;
import com.imxiaomai.convenience.store.scan.util.ToastUtils;
import com.imxiaomai.convenience.store.scan.util.Utils;

public class DownloadService extends Service {
	public static final String APP_DOWNLOAD_URL = "app_download_url";
	public static final String APP_VERSION = "app_version";
	private static Logger logger = new Logger(DownloadService.class);
	
	private static final int mDownloadNotifyId = 10000000;
	private NotificationManager mManager;
	private Notification mNotify;

	private int mFileSize = 0;
	private int mPos = 0;

	private String fileName;
	private boolean useSD = false;
	private CheckUpdateRest checkUpdateRest;
	private CheckUpdateServiceResult serviceResult;
    /**是否加载基础数据*/
    boolean isLoadData = true;


    public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        isLoadData = intent.getBooleanExtra("isLoadData", true);
        //checkAppUpdate();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

	private void performDownload(String url, String appVersion) {
		if (url != null && url.trim().length() > 0) {
			new DownloadThread(url, appVersion).start();
		}
	}

	/**
	 * 检查更新
	 * 
	 */
	private void checkAppUpdate() {
		if (checkUpdateRest == null) {
			checkUpdateRest = new CheckUpdateRest();
		}
		serviceResult = new CheckUpdateServiceResult();
		AppVersionRequest request =  new AppVersionRequest();
		request.setAppType(AppConstants.APP_UPDATE_FLAG);
		request.setEshopNo(AccountManager.getInstance().getShopCode());
		try {
			checkUpdateRest.createCheckUpdate(getApplicationContext(), request, serviceResult);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			stopSelf();
		}
	}

	class CheckUpdateServiceResult implements ServiceResult {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			try {
				JSONObject jsonObject = new JSONObject(new String(responseBody));
				if (checkUpdateRest != null && jsonObject != null && jsonObject.getInt("code") == HttpStatus.OK.getCode()) {
					Log.i("lxm-checkupdate:", jsonObject.toString());
					AppVersionResponse appVersionResponse = checkUpdateRest.parseCheckUpdateInfo(jsonObject.getString("result"));
					if(appVersionResponse != null) {
						int versionCode = appVersionResponse.getVersionCode();
						String versionName = appVersionResponse.getVersionName();
						String url = appVersionResponse.getUrl();
						String updateContent = appVersionResponse.getUpdConent();
						int isMust = appVersionResponse.getIsMust();
						
						if (DeviceUtil.getVersionCode(StoreApplication.getContext()) >= versionCode) {
							BroadcastManager.getInstance().sendBroadcast(BroadcastManager.PROMPT_DOWNLOAD);
							stopSelf();
							return;
						}
						
						if (versionCode ==0 || TextUtils.isEmpty(url)) {
						} else {
							showUpdateDialog(versionCode, versionName, url, updateContent, isMust);
						}
					} else {
						stopSelf();
					}
					// 升级  
				} else {
					stopSelf();
				}
			} catch (JSONException e) {
				e.printStackTrace();
                stopSelf();
			}
			checkUpdateRest = null;
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			checkUpdateRest = null;
			Log.i("lxm-checkupdate:", "fail");
            stopSelf();
		}

	}

	private void showUpdateDialog(final int versionCode, final String versionName, final String updateUrl, String updateNotice, int isMust) {
		int pkgVersionCode = 0;
		PackageInfo pi = null;
		Context context = StoreApplication.getContext();
		try {
			pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			System.out.println(e.getMessage());
            stopSelf();
		}

		pkgVersionCode = pi.versionCode;

		if (pkgVersionCode <= 0 || versionCode <= 0 || TextUtils.isEmpty(versionName) || TextUtils.isEmpty(updateUrl)) {
			return;
		}

		if (versionCode <= pkgVersionCode) {
			return;
		}

		if (!TextUtils.isEmpty(updateNotice)) {
			updateNotice = updateNotice.replace("|", "\n");
		}
		
		if (isMust == 0) {
			// 普通升级
			Dialog dialog = new AlertDialog.Builder(StoreApplication.getContext())
			.setTitle("升级提示").setMessage(updateNotice)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					performDownload(updateUrl, versionName);
				}
			}).setNeutralButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					DownloadService.this.stopSelf();
				}
			}).create();
			dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.setCancelable(false);//禁用返回键
            dialog.setCanceledOnTouchOutside(false);//禁止点击屏幕消失
			dialog.show();
		} else if (isMust == 1) {
			// 强制升级
			Dialog dialog = new AlertDialog.Builder(StoreApplication.getContext())
			.setTitle("升级提示").setMessage(updateNotice)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					performDownload(updateUrl, versionName);
				}
			}).setCancelable(false).create();
			dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			dialog.show();
		}
		
	}

	private boolean downloadFile(boolean showNotification, String url,
			String filename) {

		try {
			URL u = new URL(url);
			URLConnection conn = u.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			mFileSize = conn.getContentLength();
			logger.i("url:%s, size:%d cacheName:%s", url, mFileSize, filename);

			if (mFileSize <= 0) {
				throw new RuntimeException(this.getResources().getString(R.string.unknow_filesize));
			}
			if (is == null) {
				throw new RuntimeException(this.getResources().getString(R.string.cannt_getfile));
			}

			if (showNotification) {
				mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				mNotify = new Notification(android.R.drawable.stat_sys_download, this.getResources().getString(R.string.update_title), System.currentTimeMillis());
				Intent notificationIntent = new Intent(this, StoreApplication.class);
				PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
				mNotify.contentIntent = contentIntent;
				mNotify.contentView = new RemoteViews(getPackageName(), R.layout.notification_progress);

				mManager.notify(mDownloadNotifyId, mNotify);
				new UpdateNotification().start();
			}
			FileOutputStream os;
			if (useSD) {
				os = new FileOutputStream(filename);
			} else {
				os = DownloadService.this.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
			}

			byte buf[] = new byte[1024];
			int numread;
			while ((numread = is.read(buf)) != -1) {
				os.write(buf, 0, numread);
				mPos += numread;
			}
			is.close();
			os.close();
			logger.i("read finish");
			return true;

		} catch (Exception e) {
			ToastUtils.showShort(StoreApplication.getContext(), "下载失败！");
			e.printStackTrace();
			logger.i("error:%s", e.toString());
			return false;
		} finally {
			if (showNotification) {
				mManager.cancel(mDownloadNotifyId);
			}
		}
	}

    /**
     * 打开升级页面
     * @param filename
     */
	private void openFile(String filename) {

		try {
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			Uri uri = Uri.fromFile(new File(filename));
			logger.i("uri:%s", uri.toString());
			intent.setDataAndType(uri,
                    "application/vnd.android.package-archive");
			this.startActivity(intent);
            isLoadData = false;
            stopSelf();
            System.exit(0);
        } catch (Exception e) {
			e.printStackTrace();
            stopSelf();
		}
	}

	private class UpdateNotification extends Thread {
		public void run() {
			while (mPos < mFileSize) {

				double f = mFileSize / 1024d / 1024d;
				int per = mPos * 100 / mFileSize;
				String str = getResources().getString(R.string.downloading) + per + "%(" + String.format("%.2f", f) + "M)";

				mNotify.contentView.setTextViewText(R.id.down_tv, str);
				mNotify.contentView.setProgressBar(R.id.pb, mFileSize, mPos, false);
				mManager.notify(mDownloadNotifyId, mNotify);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
                    stopSelf();
				}
			}
		}
	}

    /**
     * 下载
     */
	private class DownloadThread extends Thread {
		private String url = "";
		private String appVersion = "";

		private DownloadThread(String url, String appVersion) {
			this.url = url;
			this.appVersion = appVersion;
		}

		public void run() {
			try {

				fileName = "xiaomai_" + appVersion + ".apk";
				String filePath = "/data/data/" + getPackageName() + "/files/"+ fileName;

				if (Utils.isSdPresent()) {
					useSD = true;
					filePath = new File(Utils.getSdcardDownLoadPath(), fileName).getAbsolutePath();
				}

				File file = new File(filePath);
				if (!file.exists()) {
					file.getParentFile().mkdirs();
					file.createNewFile();
				}

				downloadFile(true, url, filePath);
                openFile(filePath);

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}


    /**
     * 服务销毁时通过isLoadData来判断是否需要加载基础数据
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isLoadData) {
            Intent service = new Intent(DownloadService.this, LoadDataService.class);
            DownloadService.this.startService(service);
        }
    }
}
