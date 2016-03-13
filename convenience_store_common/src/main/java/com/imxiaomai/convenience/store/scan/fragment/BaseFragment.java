package com.imxiaomai.convenience.store.scan.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imxiaomai.convenience.store.barcode.BaseBarcodeManager;
import com.imxiaomai.convenience.store.barcode.BaseBarcodeManager.ResultListener;
import com.imxiaomai.convenience.store.barcode.ThintaBarcodeManager;
import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.StoreApplication;
import com.imxiaomai.convenience.store.scan.http.ServiceResult;
import com.imxiaomai.convenience.store.scan.util.LoadingUtil;
import com.imxiaomai.convenience.store.scan.util.Logger;
import com.imxiaomai.convenience.store.scan.util.StringUtil;
import com.imxiaomai.convenience.store.scan.util.TtsPlayer;
import com.imxiaomai.convenience.store.scan.zxing.ZxScanningActivity;

public abstract class BaseFragment extends Fragment {

	protected static final int LOADDATA_SUCCESS = 100;
	protected static final int LOADDATA_FAILED = 101;
	protected static final int LOADDATA_DEFAULT = 102;
	protected static final int LOADDATA_NUll = 109;

	protected TextView head_title_tv;
	protected ImageView back_iv, action_iv;
	protected BaseBarcodeManager mBarcodeMng;
	protected TtsPlayer TtsPlayer;

	protected ServiceResult serviceResult;
	protected LoadingUtil loadingUtil;
	protected Logger logger;
	protected FragmentActivity activity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mBarcodeMng = ((StoreApplication) getActivity().getApplication()).getBarcodeMng();
		if(mBarcodeMng == null) {
			mBarcodeMng = ThintaBarcodeManager.getInstance();
			((StoreApplication) getActivity().getApplication()).setBarcodeMng(mBarcodeMng);
		}
		TtsPlayer = new TtsPlayer(getActivity());
		activity = getActivity();
	}

	protected void initHeadView() {
		head_title_tv = (TextView) getActivity().findViewById(R.id.head_title_tv);
		back_iv = (ImageView) getActivity().findViewById(R.id.back_iv);
		action_iv = (ImageView) getActivity().findViewById(R.id.action_iv);
	}

	protected LoadingUtil getloadUtil() {
		if (loadingUtil == null) {
			loadingUtil = new LoadingUtil(getActivity());
		}
		return loadingUtil;
	}

	protected Logger getLogger() {
		if (logger == null) {
			logger = new Logger(BaseFragment.class);	
			logger.setLoggerEnable(true);
		}
		return logger;
	}
	
	/**
	 * 设置头部右侧图标是否可见
	 * 
	 * @param isVisible
	 */
	protected void setActionVisible(int isVisible) {
		action_iv.setVisibility(isVisible);
	}

	/**
	 * 设置头部右侧图标
	 * 
	 * @param isVisible
	 */
	protected void setActionImage(int resId) {
		action_iv.setImageResource(resId);
	}

	/**
	 * 设置头部左侧图标是否可见
	 * 
	 * @param isVisible
	 */
	protected void setBackVisible(int isVisible) {
		back_iv.setVisibility(isVisible);
	}

	/**
	 * 设置头部左侧图标
	 * 
	 * @param isVisible
	 */
	protected void setBackImage(int resId) {
		back_iv.setImageResource(resId);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	protected void setHeadTitle(String title) {
		head_title_tv.setText(title);
	}

	protected void setBackListener() {
		back_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goBack();
			}
		});

		action_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				goAction();
			}

		});
	}

	protected void setActionListener() {
		action_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				goAction();
			}

		});
	}

	@Override
	public void onResume() {
		super.onResume();
		mBarcodeMng.init();
		mBarcodeMng.setResultListener(new ResultListener() {

			@Override
			public void onResult(String result) {
				onScanResult(result);
			}
		});
	}

	protected void goBack() {
		closeInput();
		toFinishActivity();
	}

	protected void goAction() {

	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == 141 || keyCode == 142 || keyCode == 167 || keyCode == 166) {
			if (mBarcodeMng != null) mBarcodeMng.scan(); 
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Intent intent = new Intent(getFragmentActivity(), ZxScanningActivity.class);
   		    startActivityForResult(intent, ZxScanningActivity.INTENT_CODE_SCAN);
            return true;
        }
		return false;
	}

	public void onScanResult(String result) {
		if (StringUtil.empty(result))
			return;
		TtsPlayer.playText("scan_0");
	}

	@SuppressLint("NewApi")
	protected void inputBarcode(String titile) {
		final View view = getDialogEditView();
		AlertDialog dialog = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth)//
				.setTitle(titile)//
				.setView(view)//
				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						closeInput();
					}
				})//
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						onDialogSubmit(view, dialog);
					}
				})//
				.create();
		dialog.show();

		dialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						dialog.dismiss();
						closeInput();
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
						onDialogSubmit(view, dialog);
					} else {
						// return onCtrlKeyClick(keyCode, event);
					}
				}
				return false;
			}
		});

		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	protected void onDialogSubmit(final View view, DialogInterface dialog) {
		EditText eidt = (EditText) view.findViewById(0x00001);
		onScanResult(eidt.getText().toString());
		dialog.dismiss();
		closeInput();
	}

	protected View getDialogEditView() {
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setPadding(15, 15, 15, 15);
		layout.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		final EditText edit = new EditText(getActivity());
		edit.setId(0x00001);
		edit.setKeyListener(new NumberKeyListener() {
			@Override
			public int getInputType() {
				return InputType.TYPE_CLASS_NUMBER;
			}

			@Override
			protected char[] getAcceptedChars() {
				char[] numberChars = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd', 'e', 'f',
						'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 'v', 't', 'u', 'v', 'w', 'x',
						'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
						'R', 'S', 'V', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' , '_' , '-' };
				return numberChars;
			}
		});
		lp.gravity = Gravity.CENTER;
		edit.setLayoutParams(lp);
		layout.addView(edit);

		// TextView tip = new HtmlTextView(this);
		// tip.setLayoutParams(lp);
		// tip.setText(R.string.keyboard_help_text1);
		// layout.addView(tip);

		return layout;
	}

	/**
	 * 关闭键盘事件
	 */
	protected void closeInput() {
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null && getActivity().getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	protected FragmentActivity getFragmentActivity() {
		return activity;
	}
	
	protected void toFinishActivity() {
		if (activity != null) {
			activity.finish();
		}
	}
	
	protected String getCommonString(int id) {
        if (getFragmentActivity() != null) {
            return getFragmentActivity().getResources().getString(id);
        } else {
            Context context = StoreApplication.getContext();
            if (null != context) {
                return context.getString(id);
            }
            return null;
        }
    }
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
            case ZxScanningActivity.INTENT_CODE_SCAN:
                // 扫一扫返回
                if(null != data){
                    String result = data.getStringExtra(ZxScanningActivity.INTENT_SCAN);
                    if (result == null) {
                        return;
                    }
                    onScanResult(result);
                }
                break;
            }
        }
    }
}
