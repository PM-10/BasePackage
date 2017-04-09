package kr.pm10.basepackage.utils;

import android.util.Log;

import kr.pm10.basepackage.BaseApplication;


public class DLog {
    static final String TAG = "DLog";

    public static String buildLogMsg(String message) {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder localStringBuilder = new StringBuilder();

        localStringBuilder.append("[");
        localStringBuilder.append(ste.getFileName().replace(".java", ""));
        localStringBuilder.append("::");
        localStringBuilder.append(ste.getMethodName());
        localStringBuilder.append("]");
        localStringBuilder.append(message);

        return localStringBuilder.toString();

    }

    public static final void d(String paramString) {
        Log.d(TAG, buildLogMsg(paramString));

    }

    public static final void d(int paramInt) {
        Log.d(TAG, buildLogMsg("" + paramInt));

    }

    public static final void d(String name, String paramString) {
        Log.d(TAG, buildLogMsg(name + ": " + paramString));

    }

    public static final void d(String name, int paramInt) {
        if (BaseApplication.DEBUG) {
            Log.d(TAG, buildLogMsg(name + ": " + paramInt));
        }
    }

    public static final void e(String paramString) {
        if (BaseApplication.DEBUG) {
            Log.e(TAG, buildLogMsg(paramString));
        }
    }

    public static final void e(String name, String paramString) {
        if (BaseApplication.DEBUG) {
            Log.e(TAG, buildLogMsg(name + ": " + paramString));
        }
    }

    public static final void e(String name, int paramInt) {
        if (BaseApplication.DEBUG) {
            Log.e(TAG, buildLogMsg(name + ": " + paramInt));
        }
    }

    public static final void i(String paramString) {
        if (BaseApplication.DEBUG) {
            Log.i(TAG, buildLogMsg(paramString));
        }
    }

    public static final void v(String paramString) {
        if (BaseApplication.DEBUG) {
            Log.v(TAG, buildLogMsg(paramString));
        }
    }

    public static final void w(String paramString) {
        if (BaseApplication.DEBUG) {
            Log.w(TAG, buildLogMsg(paramString));
        }
    }


}
