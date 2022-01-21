package com.example.photoeditor;

import android.view.View;

public class MyUtils {
    public static int height;
    public static int isFromScreen;
    public static int width;

    public static void setSize(View view, int i, int i2, boolean z) {
        view.getLayoutParams().width = m62w(i);
        if (z) {
            view.getLayoutParams().height = m61h(i2);
            return;
        }
        view.getLayoutParams().height = m62w(i2);
    }

    /* renamed from: w */
    public static int m62w(int i) {
        return (width * i) / 1080;
    }

    /* renamed from: h */
    public static int m61h(int i) {
        int i2 = height;
        if (i2 <= 1280 || i2 >= 1920) {
            return (height * i) / 1920;
        }
        return (i2 * i) / 1280;
    }
}