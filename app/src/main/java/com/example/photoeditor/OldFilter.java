package com.example.photoeditor;

import android.graphics.Bitmap;
import android.graphics.Color;

public class OldFilter {
    public static Bitmap changeToOld(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        int i = 0;
        while (i < height) {
            int i2 = 0;
            while (i2 < width) {
                int i3 = (width * i) + i2;
                int i4 = iArr[i3];
                int red = Color.red(i4);
                int green = Color.green(i4);
                int blue = Color.blue(i4);
                double d = (double) red;
                Double.isNaN(d);
                double d2 = (double) green;
                Double.isNaN(d2);
                int i5 = i;
                double d3 = (double) blue;
                Double.isNaN(d3);
                int i6 = (int) ((0.393d * d) + (0.769d * d2) + (0.189d * d3));
                Double.isNaN(d);
                Double.isNaN(d2);
                Double.isNaN(d3);
                int i7 = (int) ((0.349d * d) + (0.686d * d2) + (0.168d * d3));
                Double.isNaN(d);
                Double.isNaN(d2);
                Double.isNaN(d3);
                int i8 = (int) ((d * 0.272d) + (d2 * 0.534d) + (d3 * 0.131d));
                if (i6 > 255) {
                    i6 = 255;
                }
                if (i7 > 255) {
                    i7 = 255;
                }
                if (i8 > 255) {
                    i8 = 255;
                }
                iArr[i3] = Color.argb(255, i6, i7, i8);
                i2++;
                i = i5;
            }
            i++;
        }
        return Bitmap.createBitmap(iArr, width, height, Bitmap.Config.ARGB_8888);
    }
}
