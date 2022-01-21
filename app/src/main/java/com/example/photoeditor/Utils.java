package com.example.photoeditor;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.recyclerview.widget.ItemTouchHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Utils {
    public static int StickerOpaProgsVal = 9;
    public static float StickerOpacity = 1.0f;
    public static final String TEMP_FILE_NAME = "temp.jpg";
    public static final String TEMP_FILE_NAME2 = "temp2.jpg";
    public static String add_text = null;
    public static int bright = 0;
    public static int brushsize = 30;
    public static byte[] bytearray = null;
    public static int cont = 0;
    public static int counter = 0;
    public static Bitmap cropBitmap = null;
    public static int height = 0;
    public static Bitmap imageHolder = null;
    public static int imgHeight = 0;
    public static int imgWidth = 0;
    public static Bitmap originalBitmap = null;
    public static ArrayList<String> packArr = null;
    public static boolean packageisLoad = false;
    public static int satur = 0;
    public static Bitmap saveBitmap = null;
    public static Uri selectedImageUri = null;
    public static Uri selectedImageUri2 = null;
    public static int shar = 0;
    public static Bitmap stickHolder = null;
    public static Bitmap tempBitmap = null;
    public static Bitmap textDeleteIcon = null;
    public static Bitmap textDragableIcon = null;
    public static String textFrom = "";
    public static String urSticker;
    public static Uri uriHolder;
    public static int width;

    public static File saveBitmapImage(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(), TEMP_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File saveBitmapImage2(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(), TEMP_FILE_NAME2);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static Bitmap changeImageColor(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() - 1, bitmap.getHeight() - 1);
        Paint paint = new Paint();
        paint.setColorFilter(new LightingColorFilter(i, 1));
        paint.setAlpha(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        new Canvas(createBitmap).drawBitmap(createBitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static Bitmap rotateImageIfRequired(Context context, Bitmap bitmap, Uri uri) throws IOException {
        ExifInterface exifInterface;
        InputStream openInputStream = context.getContentResolver().openInputStream(uri);
        if (Build.VERSION.SDK_INT > 23) {
            exifInterface = new ExifInterface(openInputStream);
        } else {
            exifInterface = new ExifInterface(uri.getPath());
        }
        int attributeInt = exifInterface.getAttributeInt("Orientation", 1);
        if (attributeInt == 3) {
            return rotateImage(bitmap, 180);
        }
        if (attributeInt == 6) {
            return rotateImage(bitmap, 90);
        }
        if (attributeInt != 8) {
            return bitmap;
        }
        return rotateImage(bitmap, 270);
    }

    public static Bitmap rotateImage(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return createBitmap;
    }
}