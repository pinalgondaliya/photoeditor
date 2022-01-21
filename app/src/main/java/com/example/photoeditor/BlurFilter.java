package com.example.photoeditor;

import android.graphics.Bitmap;

public class BlurFilter {
    static {
        System.loadLibrary("AndroidImageFilter");
    }

    public static Bitmap changeToAverageBlur(Bitmap bitmap, int i) {
        if (i % 2 != 0) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] iArr = new int[(width * height)];
            bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
            return Bitmap.createBitmap(NativeFilterFunc.averageSmooth(iArr, width, height, i), width, height, Bitmap.Config.ARGB_8888);
        }
        throw new IllegalArgumentException(String.format("the maskSize must odd, but %d is an even", new Object[]{Integer.valueOf(i)}));
    }
}
