package com.example.photoeditor;

public class NativeFilterFunc {
    public static native int[] averageSmooth(int[] iArr, int i, int i2, int i3);

    public static native int[] blockFilter(int[] iArr, int i, int i2);

    public static native int[] discreteGaussianBlur(int[] iArr, int i, int i2, double d);

    public static native int[] gothamFilter(int[] iArr, int i, int i2);

    public static native int[] hdrFilter(int[] iArr, int i, int i2);

    public static native int[] lightFilter(int[] iArr, int i, int i2, int i3, int i4, int i5);

    public static native int[] lomoAddBlckRound(int[] iArr, int i, int i2, double d);

    public static native int[] motionBlurFilter(int[] iArr, int i, int i2, int i3, int i4);

    public static native int[] neonFilter(int[] iArr, int i, int i2, int i3, int i4, int i5);

    public static native int[] oilFilter(int[] iArr, int i, int i2, int i3);

    public static native int[] pxelateFilter(int[] iArr, int i, int i2, int i3);

    public static native int[] reliefFilter(int[] iArr, int i, int i2);

    public static native int[] sharpenFilter(int[] iArr, int i, int i2);

    public static native int[] sketchFilter(int[] iArr, int i, int i2);

    public static native int[] softGlow(int[] iArr, int i, int i2, double d);

    public static native int[] tvFilter(int[] iArr, int i, int i2);
}
