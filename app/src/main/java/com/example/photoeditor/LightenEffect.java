package com.example.photoeditor;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import androidx.core.view.ViewCompat;


public class LightenEffect {
    Bitmap bmp = Utils.tempBitmap;
    Context mcontext;

    public LightenEffect(Context context) {
        this.mcontext = context;
    }

    public Bitmap doBrightness(int i) {
        int width = this.bmp.getWidth();
        int height = this.bmp.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, this.bmp.getConfig());
        for (int i2 = 0; i2 < width; i2++) {
            for (int i3 = 0; i3 < height; i3++) {
                int pixel = this.bmp.getPixel(i2, i3);
                int alpha = Color.alpha(pixel);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                int i4 = red + i;
                if (i4 > 255) {
                    i4 = 255;
                } else if (i4 < 0) {
                    i4 = 0;
                }
                int i5 = green + i;
                if (i5 > 255) {
                    i5 = 255;
                } else if (i5 < 0) {
                    i5 = 0;
                }
                int i6 = blue + i;
                if (i6 > 255) {
                    i6 = 255;
                } else if (i6 < 0) {
                    i6 = 0;
                }
                createBitmap.setPixel(i2, i3, Color.argb(alpha, i4, i5, i6));
            }
        }
        return createBitmap;
    }

    public Bitmap adjustedContrast(double d) {
        LightenEffect lightenEffect = this;
        int width = lightenEffect.bmp.getWidth();
        int height = lightenEffect.bmp.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, lightenEffect.bmp.getConfig());
        Canvas canvas = new Canvas();
        canvas.setBitmap(createBitmap);
        canvas.drawBitmap(lightenEffect.bmp, 0.0f, 0.0f, new Paint(ViewCompat.MEASURED_STATE_MASK));
        double pow = Math.pow((d + 100.0d) / 100.0d, 2.0d);
        int i = 0;
        while (i < width) {
            int i2 = 0;
            while (i2 < height) {
                int pixel = lightenEffect.bmp.getPixel(i, i2);
                int alpha = Color.alpha(pixel);
                double red = (double) Color.red(pixel);
                Double.isNaN(red);
                int i3 = (int) (((((red / 255.0d) - 0.5d) * pow) + 0.5d) * 255.0d);
                if (i3 < 0) {
                    i3 = 0;
                } else if (i3 > 255) {
                    i3 = 255;
                }
                int i4 = width;
                double green = (double) Color.green(pixel);
                Double.isNaN(green);
                int i5 = (int) (((((green / 255.0d) - 0.5d) * pow) + 0.5d) * 255.0d);
                if (i5 < 0) {
                    i5 = 0;
                } else if (i5 > 255) {
                    i5 = 255;
                }
                double blue = (double) Color.blue(pixel);
                Double.isNaN(blue);
                int i6 = (int) (((((blue / 255.0d) - 0.5d) * pow) + 0.5d) * 255.0d);
                if (i6 < 0) {
                    i6 = 0;
                } else if (i6 > 255) {
                    i6 = 255;
                }
                createBitmap.setPixel(i, i2, Color.argb(alpha, i3, i5, i6));
                i2++;
                lightenEffect = this;
                width = i4;
            }
            int i7 = width;
            i++;
            lightenEffect = this;
        }
        return createBitmap;
    }

    public Bitmap sharpenImage(double d) {
        double[][] dArr = {new double[]{0.0d, -2.0d, 0.0d}, new double[]{-2.0d, d, -2.0d}, new double[]{0.0d, -2.0d, 0.0d}};
        ImageHelper imageHelper = new ImageHelper(3);
        imageHelper.applyConfig(dArr);
        imageHelper.Factor = d - 8.0d;
        return ImageHelper.computeConvolution3x3(this.bmp, imageHelper);
    }

    public Bitmap createSepiaToningEffect(int i, double d, double d2, double d3) {
        int width = this.bmp.getWidth();
        int height = this.bmp.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, this.bmp.getConfig());
        int i2 = 0;
        while (i2 < width) {
            for (int i3 = 0; i3 < height; i3++) {
                int pixel = this.bmp.getPixel(i2, i3);
                int alpha = Color.alpha(pixel);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                double d4 = (double) red;
                Double.isNaN(d4);
                double d5 = (double) green;
                Double.isNaN(d5);
                double d6 = (double) blue;
                Double.isNaN(d6);
                double d7 = (double) ((int) ((d4 * 0.3d) + (d5 * 0.59d) + (d6 * 0.11d)));
                double d8 = (double) i;
                Double.isNaN(d8);
                Double.isNaN(d7);
                int i4 = (int) ((d8 * d) + d7);
                int i5 = 255;
                if (i4 > 255) {
                    i4 = 255;
                }
                Double.isNaN(d8);
                Double.isNaN(d7);
                int i6 = i2;
                int i7 = (int) (d7 + (d8 * d2));
                if (i7 > 255) {
                    i7 = 255;
                }
                Double.isNaN(d8);
                Double.isNaN(d7);
                int i8 = (int) (d7 + (d8 * d3));
                if (i8 <= 255) {
                    i5 = i8;
                }
                i2 = i6;
                createBitmap.setPixel(i2, i3, Color.argb(alpha, i4, i7, i5));
            }
            int i9 = i;
            i2++;
        }
        return createBitmap;
    }

    public Bitmap setSaturation(float f) {
        Bitmap createBitmap = Bitmap.createBitmap(this.bmp.getWidth(), this.bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(f);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(this.bmp, 0.0f, 0.0f, paint);
        return createBitmap;
    }
}

