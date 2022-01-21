package com.example.photoeditor;

import android.util.Log;

public class Logger {
    private static final String TAG = "SimpleCropView";
    public static boolean enabled = false;

    /* renamed from: e */
    public static void m59e(String str) {
        if (enabled) {
            Log.e(TAG, str);
        }
    }

    /* renamed from: e */
    public static void m60e(String str, Throwable th) {
        if (enabled) {
            Log.e(TAG, str, th);
        }
    }
}
