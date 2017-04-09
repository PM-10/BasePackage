package kr.pm10.basepackage.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;


public class BasePreferenceManager {

    private static SharedPreferences sharedPreferences;

    public static void setContext(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void putSharedPreference(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static void putSharedPreference(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public static void putSharedPreference(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public static void putSharedPreference(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void putSharedPreference(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static void putSharedPreference(String key, HashSet<String> value) {
        sharedPreferences.edit().putStringSet(key, value).apply();
    }


    public static int getSharedPreference(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static float getSharedPreference(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static Long getSharedPreference(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static boolean getSharedPreference(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static String getSharedPreference(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public static Set<String> getSharedPreference(String key, HashSet<String> defaultValue) {
        return sharedPreferences.getStringSet(key, defaultValue);
    }

}
