package com.imxiaomai.convenience.store.scan.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.imxiaomai.convenience.store.scan.constants.AppConstants;

import java.util.Set;

public class SharedPreferencesUtil {
    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_SET, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static void putInt(String key, int value, Context context) {
        getSharedPreferences(context).edit().putInt(key, value).commit();
    }

    public static void putBoolean(String key, boolean value, Context context) {
        getSharedPreferences(context).edit().putBoolean(key, value).commit();
    }

    public static void putFloat(String key, float value, Context context) {
        getSharedPreferences(context).edit().putFloat(key, value).commit();
    }

    public static void putLong(String key, long value, Context context) {
        getSharedPreferences(context).edit().putLong(key, value).commit();
    }

    public static void putString(String key, String value, Context context) {
        getSharedPreferences(context).edit().putString(key, value).commit();
    }

    @SuppressLint("NewApi")
    public static void putStringSet(String key, Set<String> values,
                                    Context context) {
        getSharedPreferences(context).edit().putStringSet(key, values).commit();
    }

    public static float getFloat(String key, Context context) {
        return getSharedPreferences(context).getFloat(key, 0f);
    }

    public static int getInt(String key, Context context) {
        return getSharedPreferences(context).getInt(key, 0);
    }

    public static long getLong(String key, Context context) {
        return getSharedPreferences(context).getLong(key, 0l);
    }

    public static String getString(String key, Context context) {
        return getSharedPreferences(context).getString(key, "");
    }

    public static boolean getBoolean(String key, boolean defaultv, Context context) {
        return getSharedPreferences(context).getBoolean(key, defaultv);
    }

    @SuppressLint("NewApi")
    public static Set<String> getStringSet(String key, Context context) {
        return getSharedPreferences(context).getStringSet(key, null);
    }

}
