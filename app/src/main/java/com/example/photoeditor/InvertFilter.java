package com.example.photoeditor;

import android.graphics.Bitmap;
import android.graphics.Color;

public class InvertFilter {
    public static Bitmap chageToInvert(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                int i3 = (i2 * width) + i;
                iArr[i3] = Color.rgb(255 - Color.red(iArr[i3]), 255 - Color.green(iArr[i3]), 255 - Color.blue(iArr[i3]));
                createBitmap.setPixel(i, i2, iArr[i3]);
            }
        }
        return createBitmap;
    }
}
