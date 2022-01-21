package com.example.photoeditor;

import android.graphics.Bitmap;

public class BitmapFilter {
    public static final int AVERAGE_BLUR_STYLE = 4;
    public static final int COLOR_CYAN = 2;
    public static final int COLOR_GREEN = 15;
    public static final int COLOR_YELLOW = 14;
    public static final int GAUSSIAN_BLUR_STYLE = 7;
    public static final int GOTHAM_STYLE = 10;
    public static final int GRAY_STYLE = 1;
    public static final int HDR_STYLE = 6;
    public static final int LIGHT_STYLE = 3;
    public static final int LOMO_STYLE = 16;
    public static final int MOTION_BLUR_STYLE = 9;
    public static final int NONE = 0;
    public static final int OLD_STYLE = 5;
    public static final int PHOTOTHUMB1_STYLE = 17;
    public static final int PHOTOTHUMB2_STYLE = 18;
    public static final int PIXELATE_STYLE = 11;
    public static final int RELIEF_STYLE = 12;
    public static final int SOFT_GLOW_STYLE = 8;
    public static final int TOTAL_FILTER_NUM = 15;
    public static final int TV_STYLE = 13;

    public static Bitmap changeStyle(Bitmap bitmap, int i, Object... objArr) {
        if (i == 1) {
            return GrayFilter.changeToGray(bitmap);
        }
        if (i == 12) {
            return ReliefFilter.changeToRelief(bitmap);
        }
        if (i == 4) {
            if (objArr.length < 1) {
                return BlurFilter.changeToAverageBlur(bitmap, 5);
            }
            return BlurFilter.changeToAverageBlur(bitmap,10);
        } else if (i == 11) {
            if (objArr.length < 1) {
                return PixelateFilter.changeToPixelate(bitmap, 5);
            }
            return PixelateFilter.changeToPixelate(bitmap, 10);
        } else if (i == 13) {
            return TvFilter.changeToTV(bitmap);
        } else {
            if (i == 5) {
                return OldFilter.changeToOld(bitmap);
            }
            if (i == 3) {
                if (objArr.length >= 3) {
                    return LightFilter.changeToLight(bitmap, 10, 15, 20);
                }
                int width = bitmap.getWidth() / 2;
                int height = bitmap.getHeight() / 2;
                return LightFilter.changeToLight(bitmap, width, height, Math.min(width, height));
            } else if (i == 16) {
                if (objArr.length < 1) {
                    return LomoFilter.changeToLomo(bitmap, (double) (((bitmap.getWidth() / 2) * 95) / 100));
                }
                return LomoFilter.changeToLomo(bitmap, 10);
            } else if (i == 6) {
                return HDRFilter.changeToHDR(bitmap);
            } else {
                if (i == 7) {
                    if (objArr.length < 1) {
                        return GaussianBlurFilter.changeToGaussianBlur(bitmap, 1.2d);
                    }
                    return GaussianBlurFilter.changeToGaussianBlur(bitmap,10);
                } else if (i == 8) {
                    if (objArr.length < 1) {
                        return SoftGlowFilter.softGlowFilter(bitmap, 0.6d);
                    }
                    return SoftGlowFilter.softGlowFilter(bitmap, 10);
                } else if (i != 9) {
                    return i == 10 ? GothamFilter.changeToGotham(bitmap) : bitmap;
                } else {
                    if (objArr.length < 2) {
                        return MotionBlurFilter.changeToMotionBlur(bitmap, 5, 1);
                    }
                    return MotionBlurFilter.changeToMotionBlur(bitmap, 10, 15);
                }
            }
        }
    }
}
