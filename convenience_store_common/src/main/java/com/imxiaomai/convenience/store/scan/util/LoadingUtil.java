package com.imxiaomai.convenience.store.scan.util;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingUtil {

    Context mContext;

    public LoadingUtil(Context c) {
        mContext = c;
    }

    public void showProgress(int textId) {
        try {
            showProgress(mContext.getString(textId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * show一个可以取消的dialog
     * @param text
     */
    public void showProgress(String text) {
        showDialog(text,true);
    }

    /**
     * show一个不可以取消的dialog
     * @param text
     */
    public void showProgressNoCancelable(String text) {
        showDialog(text,false);
    }

    private void showDialog(String text,boolean b) {
        try {
            closeProgress();
            ProgressDialog dialog = createProgressDialog(text);
            dialog.setCancelable(b);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isShowing() {
        if (mDialog == null) {
            return false;
        }

        return mDialog.isShowing();
    }

    public void closeProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    private ProgressDialog mDialog = null;

    private ProgressDialog createProgressDialog(String text) {
        if (mDialog == null) {
            mDialog = ProgressDialog.show(mContext, "", text);
            //LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //View contentView = inflater.inflate(R.layout.dialog_progress, null);
            //mDialog.setProgressStyle(R.style.TransparentDialog);
            //mDialog.setContentView(contentView);
            mDialog.setCancelable(true);
        }
        return mDialog;
    }

}
