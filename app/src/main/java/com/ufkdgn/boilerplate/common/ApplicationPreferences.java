package com.ufkdgn.boilerplate.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class ApplicationPreferences {
    private static final String TAG = "ApplicationPreferences";
    private static final String storedVersionKey = "storedVersion";
    private static final String defaultApiKey = "defaultApi";
    private static final String splashDurationKey = "splashDuration";

    public static String getStoredVersionNumber(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains(storedVersionKey)) {
            return prefs.getString(storedVersionKey, "");
        }
        return "";
    }

    public static String getVersionBuildNumber(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (Exception e) {
            Log.e(TAG, "getVersionBuildNumber: exception occured", e);
        }
        return "";
    }

    public static String getDefaultApiUrl(Context context) {
        return getStringFromMetaData(context, defaultApiKey);
    }

    public static int getSplashDuration(Context context) {
        return getIntegerFromMetaData(context, splashDurationKey);
    }

    private static String getStringFromMetaData(Context context, String name) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            return bundle.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "ConfigurationUtils:getMetaData -> Unable to load meta-data: " + e.getMessage());
        }
        return null;
    }

    private static int getIntegerFromMetaData(Context context, String name) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            return bundle.getInt(name);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "ConfigurationUtils:getMetaData -> Unable to load meta-data: " + e.getMessage());
        }
        return 0;
    }

    private static boolean getBooleanFromMetaData(Context context, String name) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            return bundle.getBoolean(name);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "ConfigurationUtils:getMetaData -> Unable to load meta-data: " + e.getMessage());
        }
        return false;
    }
}
