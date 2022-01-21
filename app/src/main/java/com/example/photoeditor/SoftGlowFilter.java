package com.example.photoeditor;

import android.graphics.Bitmap;

public class SoftGlowFilter {
    static {
        System.loadLibrary("AndroidImageFilter");
    }

    public static Bitmap softGlowFilter(Bitmap bitmap, double d) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(NativeFilterFunc.softGlow(iArr, width, height, d), width, height, Bitmap.Config.ARGB_8888);
    }
}
