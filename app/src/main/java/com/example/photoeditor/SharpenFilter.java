package com.example.photoeditor;

import android.graphics.Bitmap;

public class SharpenFilter {
    static {
        System.loadLibrary("AndroidImageFilter");
    }

    public static Bitmap changeToSharpen(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(NativeFilterFunc.sharpenFilter(iArr, width, height), width, height, Bitmap.Config.ARGB_8888);
    }
}
