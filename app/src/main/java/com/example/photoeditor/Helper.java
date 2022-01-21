package com.example.photoeditor;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Helper {
    public static int height;
    public static int width;

    public static String formatLength(long j) {
        return String.format("%02d:%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toHours(j)), Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(j))), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)))});
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("dd MMM yyyy , HH:mm", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    public static void getHeightAndWidth(Context context) {
        getHeight(context);
        getWidth(context);
    }

    public static int getWidth(Context context) {
        width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static int getHeight(Context context) {
        height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public static int setHeight(int i) {
        return (height * i) / 1920;
    }

    public static int setWidth(int i) {
        return (width * i) / 1080;
    }

    public static void setSize(View view, int i, int i2) {
        view.getLayoutParams().height = setHeight(i2);
        view.getLayoutParams().width = setWidth(i);
    }

    public static void setSize(View view, int i, int i2, boolean z) {
        if (z) {
            view.getLayoutParams().height = setWidth(i2);
            view.getLayoutParams().width = setWidth(i);
            return;
        }
        view.getLayoutParams().height = setHeight(i2);
        view.getLayoutParams().width = setHeight(i);
    }

    public static void setMargin(View view, int i, int i2, int i3, int i4) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(setWidth(i), setWidth(i2), setWidth(i3), setWidth(i4));
    }
}
