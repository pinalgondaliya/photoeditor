package com.example.photoeditor;

import android.graphics.Bitmap;

public class LightFilter {
    static {
        System.loadLibrary("AndroidImageFilter");
    }

    public static Bitmap changeToLight(Bitmap bitmap, int i, int i2, int i3) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(NativeFilterFunc.lightFilter(iArr, width, height, i, i2, i3), width, height, Bitmap.Config.ARGB_8888);
    }
}
