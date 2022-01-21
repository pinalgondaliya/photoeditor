package com.example.photoeditor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class LomoFilter {
    static {
        System.loadLibrary("AndroidImageFilter");
    }

    public static Bitmap changeToLomo(Bitmap bitmap, double d) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.reset();
        double d2 = (double) 0.7480315f;
        Double.isNaN(d2);
        float f = (float) (0.2d + d2);
        Double.isNaN(d2);
        colorMatrix.setScale(f, (float) (d2 + 0.4d), f, 1.0f);
        ColorMatrix colorMatrix2 = new ColorMatrix();
        colorMatrix2.reset();
        colorMatrix2.setSaturation(0.85f);
        ColorMatrix colorMatrix3 = new ColorMatrix();
        colorMatrix3.reset();
        colorMatrix3.postConcat(colorMatrix);
        colorMatrix3.postConcat(colorMatrix2);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix3));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        int[] iArr = new int[(width * height)];
        Bitmap bitmap2 = createBitmap;
        int i = width;
        int i2 = width;
        int i3 = height;
        bitmap2.getPixels(iArr, 0, i, 0, 0, i2, i3);
        bitmap2.setPixels(NativeFilterFunc.lomoAddBlckRound(iArr, width, height, d), 0, i, 0, 0, i2, i3);
        return createBitmap;
    }
}
