package com.example.photoeditor;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class StickerCommon {
    public static String message;
    Activity context;
    int height;
    int width;

    public StickerCommon(Context context2) {
        this.context = (Activity) context2;
    }

    public double getScreenSizeInInches() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d));
    }

    public int[] getScreenSizeInPixels() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.height = displayMetrics.heightPixels;
        this.width = displayMetrics.widthPixels;
        return new int[]{this.width, this.height};
    }
}

