package com.example.photoeditor;

import android.graphics.Bitmap;

public class GaussianBlurFilter {
    static {
        System.loadLibrary("AndroidImageFilter");
    }

    public static Bitmap changeToGaussianBlur(Bitmap bitmap, double d) {
        if (((int) ((3.0d * d) + 1.0d)) != 1) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] iArr = new int[(width * height)];
            bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
            return Bitmap.createBitmap(NativeFilterFunc.discreteGaussianBlur(iArr, width, height, d), width, height, Bitmap.Config.ARGB_8888);
        }
        throw new IllegalArgumentException(String.format("sigma %f is too small", new Object[]{Double.valueOf(d)}));
    }
}
