package com.example.photoeditor;

import android.graphics.Bitmap;

public class MotionBlurFilter {
    static {
        System.loadLibrary("AndroidImageFilter");
    }

    public static Bitmap changeToMotionBlur(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(NativeFilterFunc.motionBlurFilter(iArr, width, height, i, i2), width, height, Bitmap.Config.ARGB_8888);
    }
}
