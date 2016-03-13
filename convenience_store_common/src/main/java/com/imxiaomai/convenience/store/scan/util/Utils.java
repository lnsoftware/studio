package com.imxiaomai.convenience.store.scan.util;

import android.os.Environment;

import java.io.File;

public class Utils {

    public static File getSdcardDownLoadPath() {
        File sd = Environment.getExternalStorageDirectory();
        String tmpname = (new StringBuilder("download/")).toString();
        File fullname = new File(sd, tmpname);
        if (!fullname.exists())
            fullname.mkdirs();
        return fullname;
    }

    public static boolean isSdPresent() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}
