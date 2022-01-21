package com.example.photoeditor;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
public class BitmapCompression {

    /* renamed from: h */
    static int h;

    /* renamed from: w */
    static int w;

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            int i6 = i3 / 2;
            int i7 = i4 / 2;
            while (i6 / i5 > i2 && i7 / i5 > i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    public static Bitmap scaleCenterCrop(Bitmap bitmap, int i, int i2) {
        float f = (float) i2;
        float width = (float) bitmap.getWidth();
        float f2 = (float) i;
        float height = (float) bitmap.getHeight();
        float max = Math.max(f / width, f2 / height);
        float f3 = width * max;
        float f4 = max * height;
        float f5 = (f - f3) / 2.0f;
        float f6 = (f2 - f4) / 2.0f;
        RectF rectF = new RectF(f5, f6, f3 + f5, f4 + f6);
        Bitmap createBitmap = Bitmap.createBitmap(i2, i, bitmap.getConfig());
        new Canvas(createBitmap).drawBitmap(bitmap, (Rect) null, rectF, (Paint) null);
        return createBitmap;
    }

    public static Bitmap decodeFile(File file, int i, int i2) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i3 = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), (Rect) null, options);
            while ((options.outWidth / i3) / 2 >= i2 && (options.outHeight / i3) / 2 >= i) {
                i3 *= 2;
            }
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = i3;
            return BitmapFactory.decodeStream(new FileInputStream(file), (Rect) null, options2);
        } catch (FileNotFoundException unused) {
            return null;
        }
    }

    public static Bitmap adjustImageOrientation(File file, Bitmap bitmap) {
        try {
            int i = 0;
            int attributeInt = new ExifInterface(file.getAbsolutePath()).getAttributeInt("Orientation", 0);
            if (attributeInt != 0) {
                if (attributeInt == 1) {
                    i = 1;
                } else if (attributeInt == 3) {
                    i = 180;
                } else if (attributeInt == 6) {
                    i = 90;
                } else if (attributeInt == 8) {
                    i = 270;
                }
            }
            Matrix matrix = new Matrix();
            w = bitmap.getWidth();
            h = bitmap.getHeight();
            if (i > 1) {
                matrix.preRotate((float) i);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, false);
            }
            return bitmap.copy(Bitmap.Config.ARGB_8888, true);
        } catch (IOException unused) {
            return null;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        switch (i) {
            case 1:
                return bitmap;
            case 2:
                matrix.setScale(-1.0f, 1.0f);
                break;
            case 3:
                matrix.setRotate(180.0f);
                break;
            case 4:
                matrix.setRotate(180.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 5:
                matrix.setRotate(90.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 6:
                matrix.setRotate(90.0f);
                break;
            case 7:
                matrix.setRotate(-90.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 8:
                matrix.setRotate(-90.0f);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return createBitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources resources, int i, int i2, int i3) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, i, options);
        options.inSampleSize = calculateInSampleSize(options, i2, i3);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, i, options);
    }

    public static int getOrientation(Context context, Uri uri) {
        Cursor query = context.getContentResolver().query(uri, new String[]{"orientation"}, (String) null, (String[]) null, (String) null);
        if (query.getCount() != 1) {
            return -1;
        }
        query.moveToFirst();
        return query.getInt(0);
    }

    public static Bitmap getCorrectlyOrientedImage(Context context, Uri uri, int i) throws IOException {
        int i2;
        int i3;
        Bitmap bitmap;
        InputStream openInputStream = context.getContentResolver().openInputStream(uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(openInputStream, (Rect) null, options);
        openInputStream.close();
        int orientation = getOrientation(context, uri);
        if (orientation == 90 || orientation == 270) {
            i2 = options.outHeight;
            i3 = options.outWidth;
        } else {
            i2 = options.outWidth;
            i3 = options.outHeight;
        }
        InputStream openInputStream2 = context.getContentResolver().openInputStream(uri);
        if (i2 > i || i3 > i) {
            float f = (float) i;
            float max = Math.max(((float) i2) / f, ((float) i3) / f);
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = (int) max;
            bitmap = BitmapFactory.decodeStream(openInputStream2, (Rect) null, options2);
        } else {
            bitmap = BitmapFactory.decodeStream(openInputStream2);
        }
        Bitmap bitmap2 = bitmap;
        openInputStream2.close();
        if (orientation <= 0) {
            return bitmap2;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate((float) orientation);
        return Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
    }

    public static Bitmap adjustImageOrientationUri(Context context, Uri uri, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        Cursor query = context.getContentResolver().query(uri, new String[]{"orientation"}, (String) null, (String[]) null, (String) null);
        if (query.getCount() == 1) {
            query.moveToFirst();
            int i = query.getInt(0);
            w = bitmap.getWidth();
            h = bitmap.getHeight();
            if (i > 0) {
                matrix.preRotate((float) i);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, false);
            }
        }
        return bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }
}


