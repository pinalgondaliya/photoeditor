package com.example.photoeditor;
import android.graphics.Bitmap;
import android.graphics.Color;
import java.lang.reflect.Array;

public class ImageHelper {
    public static final int SIZE = 3;
    public double Factor = 1.0d;
    public double[][] Matrix;
    public double Offset = 1.0d;

    public ImageHelper(int i) {
        this.Matrix = (double[][]) Array.newInstance(double.class, new int[]{i, i});
    }

    public void setAll(double d) {
        for (int i = 0; i < 3; i++) {
            for (int i2 = 0; i2 < 3; i2++) {
                this.Matrix[i][i2] = d;
            }
        }
    }

    public void applyConfig(double[][] dArr) {
        for (int i = 0; i < 3; i++) {
            for (int i2 = 0; i2 < 3; i2++) {
                this.Matrix[i][i2] = dArr[i][i2];
            }
        }
    }

    public static Bitmap computeConvolution3x3(Bitmap bitmap, ImageHelper imageHelper) {
        ImageHelper imageHelper2 = imageHelper;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        int i = 3;
        int[][] iArr = (int[][]) Array.newInstance(int.class, new int[]{3, 3});
        int i2 = 0;
        while (i2 < height - 2) {
            int i3 = 0;
            while (i3 < width - 2) {
                for (int i4 = 0; i4 < i; i4++) {
                    for (int i5 = 0; i5 < i; i5++) {
                        iArr[i4][i5] = bitmap.getPixel(i3 + i4, i2 + i5);
                    }
                    Bitmap bitmap2 = bitmap;
                }
                Bitmap bitmap3 = bitmap;
                int alpha = Color.alpha(iArr[1][1]);
                int i6 = 0;
                int i7 = 0;
                int i8 = 0;
                int i9 = 0;
                while (i6 < i) {
                    int i10 = i9;
                    int i11 = i8;
                    int i12 = i7;
                    int i13 = 0;
                    while (i13 < i) {
                        double d = (double) i12;
                        int[][] iArr2 = iArr;
                        double red = (double) Color.red(iArr[i6][i13]);
                        double d2 = imageHelper2.Matrix[i6][i13];
                        Double.isNaN(red);
                        Double.isNaN(d);
                        i12 = (int) (d + (red * d2));
                        double d3 = (double) i11;
                        double green = (double) Color.green(iArr2[i6][i13]);
                        double d4 = imageHelper2.Matrix[i6][i13];
                        Double.isNaN(green);
                        Double.isNaN(d3);
                        i11 = (int) (d3 + (green * d4));
                        double d5 = (double) i10;
                        double blue = (double) Color.blue(iArr2[i6][i13]);
                        double d6 = imageHelper2.Matrix[i6][i13];
                        Double.isNaN(blue);
                        Double.isNaN(d5);
                        i10 = (int) (d5 + (blue * d6));
                        i13++;
                        i2 = i2;
                        iArr = iArr2;
                        i = 3;
                    }
                    int[][] iArr3 = iArr;
                    int i14 = i2;
                    i6++;
                    i7 = i12;
                    i8 = i11;
                    i9 = i10;
                    i = 3;
                }
                int[][] iArr4 = iArr;
                int i15 = i2;
                double d7 = (double) i7;
                double d8 = imageHelper2.Factor;
                Double.isNaN(d7);
                int i16 = (int) ((d7 / d8) + imageHelper2.Offset);
                if (i16 < 0) {
                    i16 = 0;
                } else if (i16 > 255) {
                    i16 = 255;
                }
                double d9 = (double) i8;
                double d10 = imageHelper2.Factor;
                Double.isNaN(d9);
                int i17 = (int) ((d9 / d10) + imageHelper2.Offset);
                if (i17 < 0) {
                    i17 = 0;
                } else if (i17 > 255) {
                    i17 = 255;
                }
                double d11 = (double) i9;
                double d12 = imageHelper2.Factor;
                Double.isNaN(d11);
                int i18 = (int) ((d11 / d12) + imageHelper2.Offset);
                if (i18 < 0) {
                    i18 = 0;
                } else if (i18 > 255) {
                    i18 = 255;
                }
                i3++;
                createBitmap.setPixel(i3, i15 + 1, Color.argb(alpha, i16, i17, i18));
                i2 = i15;
                iArr = iArr4;
                i = 3;
            }
            Bitmap bitmap4 = bitmap;
            int[][] iArr5 = iArr;
            i2++;
            i = 3;
        }
        return createBitmap;
    }
}
