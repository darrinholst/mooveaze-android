package com.google.mooveaze.lib;

public class Log {
    private static final String TAG = "mooveaze";

    public static void debug(String message) {
        android.util.Log.d(TAG, message);
    }

    public static void error(Throwable t) {
        android.util.Log.e(TAG, t.getMessage(), t);
    }
}
