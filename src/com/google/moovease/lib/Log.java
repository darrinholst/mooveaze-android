package com.google.moovease.lib;

public class Log {
    private static final String TAG = "mooveaze";

    public static void debug(String message) {
        android.util.Log.d(TAG, message);
    }
}
