package com.example.photoeditor;

import android.graphics.Bitmap;

public class TvFilter {
    static {
        System.loadLibrary("AndroidImageFilter");
    }

    public static final Bitmap changeToTV(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(NativeFilterFunc.tvFilter(iArr, width, height), width, height, Bitmap.Config.ARGB_8888);
    }
}
