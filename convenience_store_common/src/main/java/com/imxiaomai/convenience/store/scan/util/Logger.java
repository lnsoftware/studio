package com.imxiaomai.convenience.store.scan.util;

import android.util.Log;

import static android.util.Log.*;

/**
 * Created with IntelliJ IDEA.
 * User: lixiaoming
 * Date: 13-5-29
 * Time: 下午2:38
 */
public class Logger {
    private static final String ALL_TAG = "xiaomai_log";
    private final String tag;
    private final int logLevel;
    private boolean isLoggerEnable = false;

    public Logger(String tag, int logLevel) {
        this.tag = tag;
        this.logLevel = logLevel;
    }

    public Logger(Class clazz, int logLevel) {
        this(clazz.getName(), logLevel);
    }

    public Logger(String tag) {
        this(tag, INFO);
    }

    public Logger(Class clazz) {
        this(clazz.getName(), INFO);
    }

    public boolean isLoggerEnable() {
        return isLoggerEnable;
    }

    public void setLoggerEnable(boolean loggerEnable) {
        isLoggerEnable = loggerEnable;
    }

    public void v(String format, Object... args) {
        if (isLoggable(VERBOSE)) {
            Log.v(tag, String.format(format, args));
            Log.v(ALL_TAG,tag+","+String.format(format, args));
        }
    }

    public void d(String format, Object... args) {
        if (isLoggable(DEBUG)) {
            Log.d(tag, String.format(format, args));
            Log.d(ALL_TAG,tag+","+String.format(format, args));
        }
    }

    public void i(String format, Object... args) {
        if (isLoggable(INFO)) {
            Log.i(tag, String.format(format, args));
            Log.i(ALL_TAG,tag+","+String.format(format, args));
        }
    }

    public void w(String format, Object... args) {
        if (isLoggable(WARN)) {
            Log.w(tag, String.format(format, args));
            Log.w(ALL_TAG,tag+","+String.format(format, args));
        }
    }

    public void e(String format, Object... args) {
        if (isLoggable(ERROR)) {
            Log.e(tag, String.format(format, args));
            Log.e(ALL_TAG,tag+","+String.format(format, args));
        }
    }

    public void wtf(String format, Object... args) {
        if (isLoggable(ASSERT)) {
            Log.wtf(tag, String.format(format, args));
            Log.wtf(ALL_TAG,tag+","+String.format(format, args));
        }
    }

    private boolean isLoggable(int level) {
        if (!isLoggerEnable) {
            return false;
        }

        return level >= logLevel;
    }

}
