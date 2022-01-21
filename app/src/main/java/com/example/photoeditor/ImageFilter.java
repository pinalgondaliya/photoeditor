package com.example.photoeditor;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.core.view.InputDeviceCompat;
public class ImageFilter {
    static Bitmap originBitmap;
    Bitmap changeBitmap;
    Context context;
    ImageFilters imgFilter = new ImageFilters();

    public ImageFilter(Context context2, Bitmap bitmap) {
        originBitmap = bitmap;
        this.context = context2;
    }

    public Bitmap applyStyle(int i) {
        if (i == 0) {
            this.changeBitmap = originBitmap;
        } else if (i == 11) {
            this.changeBitmap = BitmapFilter.changeStyle(originBitmap, 11, 10);
        } else if (i == 2) {
            this.changeBitmap = this.imgFilter.applyShadingFilter(originBitmap, -16711681);
        } else if (i == 3) {
            int width = originBitmap.getWidth();
            int height = originBitmap.getHeight();
            this.changeBitmap = BitmapFilter.changeStyle(originBitmap, 3, Integer.valueOf(width / 3), Integer.valueOf(height / 2), Integer.valueOf(width / 2));
        } else if (i == 4) {
            this.changeBitmap = BitmapFilter.changeStyle(originBitmap, 4, 15);
        } else if (i == 7) {
            this.changeBitmap = BitmapFilter.changeStyle(originBitmap, 7, Double.valueOf(1.2d));
        } else if (i == 8) {
            this.changeBitmap = BitmapFilter.changeStyle(originBitmap, 8, Double.valueOf(0.6d));
        } else if (i != 9) {
            switch (i) {
                case 14:
                    this.changeBitmap = this.imgFilter.applyShadingFilter(originBitmap, InputDeviceCompat.SOURCE_ANY);
                    break;
                case 15:
                    this.changeBitmap = this.imgFilter.applyShadingFilter(originBitmap, -16711936);
                    break;
                case 16:
                    Bitmap bitmap = originBitmap;
                    double width2 = (double) bitmap.getWidth();
                    Double.isNaN(width2);
                    this.changeBitmap = BitmapFilter.changeStyle(bitmap, 16, Double.valueOf(((width2 / 2.0d) * 95.0d) / 100.0d));
                    break;
                case 17:
                    this.changeBitmap = this.imgFilter.applyContrastEffect(originBitmap, 70.0d);
                    break;
                case 18:
                    this.changeBitmap = this.imgFilter.applyTintEffect(originBitmap, 100);
                    break;
                default:
                    this.changeBitmap = BitmapFilter.changeStyle(originBitmap, i, new Object[0]);
                    break;
            }
        } else {
            this.changeBitmap = BitmapFilter.changeStyle(originBitmap, 9, 15, 1);
        }
        return this.changeBitmap;
    }
}
