package com.imxiaomai.convenience.store.scan.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

/**
 * 全局dialog
 * 禁用返回键,禁止点击屏幕消失
 * Created by Li dajun
 * Date: 2015-09-06
 * Time: 15:27
 */
public class SystemDialog extends ProgressDialog{
    public static final int LOADDATA_FAILED = 100;
    public static final int LOADDATA_SUCCESS = 99;
    public static final int NETWORK_FAIL = 103;
    public SystemDialog(Context context, String title, String meassage) {
        super(context);
        setTitle(title);
        setMessage(meassage);
        setCancelable(false);//禁用返回键
        setCanceledOnTouchOutside(false);//禁止点击屏幕消失
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//样式为系统dialog
    }
}
