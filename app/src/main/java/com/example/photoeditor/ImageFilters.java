package com.example.photoeditor;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.core.view.ViewCompat;
import java.util.Random;

public class ImageFilters {
    public static final int COLOR_MAX = 255;
    public static final int COLOR_MIN = 0;
    public static final double FULL_CIRCLE_DEGREE = 360.0d;
    public static final double HALF_CIRCLE_DEGREE = 180.0d;

    /* renamed from: PI */
    public static final double f85PI = 3.14159d;
    public static final double RANGE = 256.0d;

    public Bitmap applyHighlightEffect(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth() + 96, bitmap.getHeight() + 96, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint();
        paint.setMaskFilter(new BlurMaskFilter(15.0f, BlurMaskFilter.Blur.NORMAL));
        int[] iArr = new int[2];
        Bitmap extractAlpha = bitmap.extractAlpha(paint, iArr);
        Paint paint2 = new Paint();
        paint2.setColor(-1);
        canvas.drawBitmap(extractAlpha, (float) iArr[0], (float) iArr[1], paint2);
        extractAlpha.recycle();
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        return createBitmap;
    }

    public Bitmap applyInvertEffect(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        for (int i = 0; i < height; i++) {
            for (int i2 = 0; i2 < width; i2++) {
                int pixel = bitmap.getPixel(i2, i);
                createBitmap.setPixel(i2, i, Color.argb(Color.alpha(pixel), 255 - Color.red(pixel), 255 - Color.green(pixel), 255 - Color.blue(pixel)));
            }
        }
        return createBitmap;
    }

    public Bitmap applyGreyscaleEffect(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                int pixel = bitmap.getPixel(i, i2);
                int alpha = Color.alpha(pixel);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                double d = (double) red;
                Double.isNaN(d);
                double d2 = (double) green;
                Double.isNaN(d2);
                double d3 = (double) blue;
                Double.isNaN(d3);
                int i3 = (int) ((d * 0.299d) + (d2 * 0.587d) + (d3 * 0.114d));
                createBitmap.setPixel(i, i2, Color.argb(alpha, i3, i3, i3));
            }
        }
        return createBitmap;
    }

    public Bitmap applyGammaEffect(Bitmap bitmap, double d, double d2, double d3) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[256];
        int[] iArr2 = new int[256];
        int[] iArr3 = new int[256];
        int i = 0;
        for (int i2 = 256; i < i2; i2 = 256) {
            double d4 = (double) i;
            Double.isNaN(d4);
            double d5 = d4 / 255.0d;
            int[] iArr4 = iArr;
            iArr4[i] = Math.min(255, (int) ((Math.pow(d5, 1.0d / d) * 255.0d) + 0.5d));
            int i3 = i;
            iArr2[i3] = Math.min(255, (int) ((Math.pow(d5, 1.0d / d2) * 255.0d) + 0.5d));
            iArr3[i3] = Math.min(255, (int) ((Math.pow(d5, 1.0d / d3) * 255.0d) + 0.5d));
            i = i3 + 1;
            iArr = iArr4;
        }
        int[] iArr5 = iArr;
        for (int i4 = 0; i4 < width; i4++) {
            for (int i5 = 0; i5 < height; i5++) {
                int pixel = bitmap.getPixel(i4, i5);
                createBitmap.setPixel(i4, i5, Color.argb(Color.alpha(pixel), iArr5[Color.red(pixel)], iArr2[Color.green(pixel)], iArr3[Color.blue(pixel)]));
            }
            Bitmap bitmap2 = bitmap;
        }
        return createBitmap;
    }

    public Bitmap applyColorFilterEffect(Bitmap bitmap, double d, double d2, double d3) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                Bitmap bitmap2 = bitmap;
                int pixel = bitmap.getPixel(i, i2);
                int alpha = Color.alpha(pixel);
                double red = (double) Color.red(pixel);
                Double.isNaN(red);
                double green = (double) Color.green(pixel);
                Double.isNaN(green);
                double blue = (double) Color.blue(pixel);
                Double.isNaN(blue);
                createBitmap.setPixel(i, i2, Color.argb(alpha, (int) (red * d), (int) (green * d2), (int) (blue * d3)));
            }
            Bitmap bitmap3 = bitmap;
        }
        return createBitmap;
    }

    public Bitmap applySepiaToningEffect(Bitmap bitmap, int i, double d, double d2, double d3) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        int i2 = 0;
        while (i2 < width) {
            for (int i3 = 0; i3 < height; i3++) {
                int pixel = bitmap.getPixel(i2, i3);
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
            Bitmap bitmap2 = bitmap;
            int i9 = i;
            i2++;
        }
        return createBitmap;
    }

    public Bitmap applyDecreaseColorDepthEffect(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        for (int i2 = 0; i2 < width; i2++) {
            for (int i3 = 0; i3 < height; i3++) {
                int pixel = bitmap.getPixel(i2, i3);
                int alpha = Color.alpha(pixel);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                int i4 = i / 2;
                int i5 = red + i4;
                int i6 = (i5 - (i5 % i)) - 1;
                if (i6 < 0) {
                    i6 = 0;
                }
                int i7 = green + i4;
                int i8 = (i7 - (i7 % i)) - 1;
                if (i8 < 0) {
                    i8 = 0;
                }
                int i9 = blue + i4;
                int i10 = (i9 - (i9 % i)) - 1;
                if (i10 < 0) {
                    i10 = 0;
                }
                createBitmap.setPixel(i2, i3, Color.argb(alpha, i6, i8, i10));
            }
        }
        return createBitmap;
    }

    public Bitmap applyContrastEffect(Bitmap bitmap, double d) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        double pow = Math.pow((d + 100.0d) / 100.0d, 2.0d);
        for (int i = 0; i < width; i++) {
            int i2 = 0;
            while (i2 < height) {
                int pixel = bitmap.getPixel(i, i2);
                int alpha = Color.alpha(pixel);
                double red = (double) Color.red(pixel);
                Double.isNaN(red);
                int i3 = (int) (((((red / 255.0d) - 0.5d) * pow) + 0.5d) * 255.0d);
                int i4 = 255;
                if (i3 < 0) {
                    i3 = 0;
                } else if (i3 > 255) {
                    i3 = 255;
                }
                int i5 = width;
                int i6 = height;
                double red2 = (double) Color.red(pixel);
                Double.isNaN(red2);
                int i7 = (int) (((((red2 / 255.0d) - 0.5d) * pow) + 0.5d) * 255.0d);
                if (i7 < 0) {
                    i7 = 0;
                } else if (i7 > 255) {
                    i7 = 255;
                }
                double red3 = (double) Color.red(pixel);
                Double.isNaN(red3);
                int i8 = (int) (((((red3 / 255.0d) - 0.5d) * pow) + 0.5d) * 255.0d);
                if (i8 < 0) {
                    i4 = 0;
                } else if (i8 <= 255) {
                    i4 = i8;
                }
                createBitmap.setPixel(i, i2, Color.argb(alpha, i3, i7, i4));
                i2++;
                width = i5;
                height = i6;
            }
            Bitmap bitmap2 = bitmap;
            int i9 = width;
            int i10 = height;
        }
        return createBitmap;
    }

    public Bitmap applyBrightnessEffect(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        for (int i2 = 0; i2 < width; i2++) {
            for (int i3 = 0; i3 < height; i3++) {
                int pixel = bitmap.getPixel(i2, i3);
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

    public Bitmap applyGaussianBlurEffect(Bitmap bitmap) {
        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(new double[][]{new double[]{1.0d, 2.0d, 1.0d}, new double[]{2.0d, 4.0d, 2.0d}, new double[]{1.0d, 2.0d, 1.0d}});
        convolutionMatrix.Factor = 16.0d;
        convolutionMatrix.Offset = 0.0d;
        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }

    public Bitmap applySharpenEffect(Bitmap bitmap, double d) {
        double[][] dArr = {new double[]{0.0d, -2.0d, 0.0d}, new double[]{-2.0d, d, -2.0d}, new double[]{0.0d, -2.0d, 0.0d}};
        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(dArr);
        convolutionMatrix.Factor = d - 8.0d;
        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }

    public Bitmap applyMeanRemovalEffect(Bitmap bitmap) {
        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(new double[][]{new double[]{-1.0d, -1.0d, -1.0d}, new double[]{-1.0d, 9.0d, -1.0d}, new double[]{-1.0d, -1.0d, -1.0d}});
        convolutionMatrix.Factor = 1.0d;
        convolutionMatrix.Offset = 0.0d;
        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }

    public Bitmap applySmoothEffect(Bitmap bitmap, double d) {
        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(3);
        convolutionMatrix.setAll(1.0d);
        convolutionMatrix.Matrix[1][1] = d;
        convolutionMatrix.Factor = d + 8.0d;
        convolutionMatrix.Offset = 1.0d;
        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }

    public Bitmap applyEmbossEffect(Bitmap bitmap) {
        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(new double[][]{new double[]{-1.0d, 0.0d, -1.0d}, new double[]{0.0d, 4.0d, 0.0d}, new double[]{-1.0d, 0.0d, -1.0d}});
        convolutionMatrix.Factor = 1.0d;
        convolutionMatrix.Offset = 127.0d;
        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }

    public Bitmap applyEngraveEffect(Bitmap bitmap) {
        ConvolutionMatrix convolutionMatrix = new ConvolutionMatrix(3);
        convolutionMatrix.setAll(0.0d);
        convolutionMatrix.Matrix[0][0] = -2.0d;
        convolutionMatrix.Matrix[1][1] = 2.0d;
        convolutionMatrix.Factor = 1.0d;
        convolutionMatrix.Offset = 95.0d;
        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }

    public Bitmap applyBoostEffect(Bitmap bitmap, int i, float f) {
        int i2 = i;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        for (int i3 = 0; i3 < width; i3++) {
            for (int i4 = 0; i4 < height; i4++) {
                int pixel = bitmap.getPixel(i3, i4);
                int alpha = Color.alpha(pixel);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                if (i2 == 1) {
                    red = (int) (((float) red) * (f + 1.0f));
                    if (red > 255) {
                        red = 255;
                    }
                } else if (i2 == 2) {
                    green = (int) (((float) green) * (f + 1.0f));
                    if (green > 255) {
                        green = 255;
                    }
                } else if (i2 == 3 && (blue = (int) (((float) blue) * (f + 1.0f))) > 255) {
                    blue = 255;
                }
                createBitmap.setPixel(i3, i4, Color.argb(alpha, red, green, blue));
            }
            Bitmap bitmap2 = bitmap;
        }
        return createBitmap;
    }

    public Bitmap applyRoundCornerEffect(Bitmap bitmap, float f) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        Rect rect = new Rect(0, 0, width, height);
        canvas.drawRoundRect(new RectF(rect), f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    public Bitmap applyWaterMarkEffect(Bitmap bitmap, String str, int i, int i2, int i3, int i4, int i5, boolean z) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        Paint paint = new Paint();
        paint.setColor(i3);
        paint.setAlpha(i4);
        paint.setTextSize((float) i5);
        paint.setAntiAlias(true);
        paint.setUnderlineText(z);
        canvas.drawText(str, (float) i, (float) i2, paint);
        return createBitmap;
    }

    public Bitmap applyTintEffect(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        double d = (double) i;
        Double.isNaN(d);
        double d2 = (d * 3.14159d) / 180.0d;
        int sin = (int) (Math.sin(d2) * 256.0d);
        int cos = (int) (Math.cos(d2) * 256.0d);
        for (int i2 = 0; i2 < height; i2++) {
            for (int i3 = 0; i3 < width; i3++) {
                int i4 = (i2 * width) + i3;
                int i5 = (iArr[i4] >> 16) & 255;
                int i6 = (iArr[i4] >> 8) & 255;
                int i7 = iArr[i4] & 255;
                int i8 = i6 * 59;
                int i9 = i7 * 11;
                int i10 = (((i5 * 70) - i8) - i9) / 100;
                int i11 = i5 * -30;
                int i12 = ((i11 + (i6 * 41)) - i9) / 100;
                int i13 = ((i11 - i8) + (i7 * 89)) / 100;
                int i14 = (((i5 * 30) + i8) + i9) / 100;
                int i15 = ((sin * i13) + (cos * i10)) / 256;
                int i16 = ((i13 * cos) - (i10 * sin)) / 256;
                int i17 = ((i15 * -51) - (i16 * 19)) / 100;
                int i18 = i15 + i14;
                if (i18 < 0) {
                    i18 = 0;
                } else if (i18 > 255) {
                    i18 = 255;
                }
                int i19 = i17 + i14;
                if (i19 < 0) {
                    i19 = 0;
                } else if (i19 > 255) {
                    i19 = 255;
                }
                int i20 = i14 + i16;
                if (i20 < 0) {
                    i20 = 0;
                } else if (i20 > 255) {
                    i20 = 255;
                }
                iArr[i4] = i20 | -16777216 | (i18 << 16) | (i19 << 8);
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return createBitmap;
    }

    public Bitmap applyFleaEffect(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int i2 = 0; i2 < width; i2++) {
                int i3 = (i * width) + i2;
                iArr[i3] = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)) | iArr[i3];
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return createBitmap;
    }

    public Bitmap applyBlackFilter(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int i2 = 0; i2 < width; i2++) {
                int i3 = (i * width) + i2;
                int red = Color.red(iArr[i3]);
                int green = Color.green(iArr[i3]);
                int blue = Color.blue(iArr[i3]);
                int nextInt = random.nextInt(255);
                if (red < nextInt && green < nextInt && blue < nextInt) {
                    iArr[i3] = Color.rgb(0, 0, 0);
                }
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return createBitmap;
    }

    public Bitmap applySnowEffect(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int i2 = 0; i2 < width; i2++) {
                int i3 = (i * width) + i2;
                int red = Color.red(iArr[i3]);
                int green = Color.green(iArr[i3]);
                int blue = Color.blue(iArr[i3]);
                int nextInt = random.nextInt(255);
                if (red > nextInt && green > nextInt && blue > nextInt) {
                    iArr[i3] = Color.rgb(255, 255, 255);
                }
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return createBitmap;
    }

    public Bitmap applyShadingFilter(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i2 = 0; i2 < height; i2++) {
            for (int i3 = 0; i3 < width; i3++) {
                int i4 = (i2 * width) + i3;
                iArr[i4] = iArr[i4] & i;
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return createBitmap;
    }

    public Bitmap applySaturationFilter(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        float[] fArr = new float[3];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        int i2 = 0;
        while (i2 < height) {
            int i3 = 0;
            while (i3 < width) {
                int i4 = (i2 * width) + i3;
                Color.colorToHSV(iArr[i4], fArr);
                fArr[1] = fArr[1] * ((float) i);
                fArr[1] = (float) Math.max(0.0d, Math.min((double) fArr[1], 1.0d));
                iArr[i4] = iArr[i4] | Color.HSVToColor(fArr);
                i3++;
                i2 = i2;
            }
            int i5 = i;
            i2++;
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return createBitmap;
    }

    public Bitmap applyHueFilter(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        float[] fArr = new float[3];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i2 = 0; i2 < height; i2++) {
            for (int i3 = 0; i3 < width; i3++) {
                int i4 = (i2 * width) + i3;
                Color.colorToHSV(iArr[i4], fArr);
                fArr[0] = fArr[0] * ((float) i);
                fArr[0] = (float) Math.max(0.0d, Math.min((double) fArr[0], 360.0d));
                iArr[i4] = iArr[i4] | Color.HSVToColor(fArr);
            }
            int i5 = i;
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return createBitmap;
    }

    public Bitmap applyReflection(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        int i = height / 2;
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, i, width, i, matrix, false);
        Bitmap createBitmap2 = Bitmap.createBitmap(width, i + height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap2);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        float f = (float) height;
        float f2 = (float) (height + 4);
        Canvas canvas2 = canvas;
        float f3 = f;
        float f4 = (float) width;
        canvas2.drawRect(0.0f, f3, f4, f2, new Paint());
        canvas.drawBitmap(createBitmap, 0.0f, f2, (Paint) null);
        Paint paint = new Paint();
        paint.setShader(new LinearGradient(0.0f, (float) bitmap.getHeight(), 0.0f, (float) (createBitmap2.getHeight() + 4), 1895825407, ViewCompat.MEASURED_SIZE_MASK, Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas2.drawRect(0.0f, f3, f4, (float) (createBitmap2.getHeight() + 4), paint);
        return createBitmap2;
    }
}
