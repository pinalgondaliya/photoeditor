package com.example.photoeditor;

import android.graphics.Bitmap;

public class OilFilter {
    static {
        System.loadLibrary("AndroidImageFilter");
    }

    public static Bitmap changeToOil(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(NativeFilterFunc.oilFilter(iArr, width, height, i), width, height, Bitmap.Config.ARGB_8888);
    }
}
