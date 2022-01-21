package com.example.photoeditor;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import java.io.IOException;
import java.io.InputStream;

public class AllEffects {
    final int FLIP_HORIZONTAL = 2;
    final int FLIP_VERTICAL = 1;
    Bitmap bitmap;

    /* renamed from: c */
    Context f81c;
    boolean cascade = false;
    int filterIndex = -1;
    int frameIndex = 0;
    String frameName;
    boolean h_flip = false;
    boolean shadow = false;
    Bitmap tmp;
    boolean v_flip = false;

    public AllEffects(Context context) {
        this.f81c = context;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setFilter(int i) {
        this.filterIndex = i;
    }

    public void setFrame(int i, String str) {
        this.frameIndex = i;
        this.frameName = str;
    }

    public void setShadow(boolean z) {
        this.shadow = z;
    }

    public boolean getShadow() {
        return this.shadow;
    }

    public void setCascade(boolean z) {
        this.cascade = z;
    }

    public boolean getCascade() {
        return this.cascade;
    }

    public void setH_flip(boolean z) {
        this.h_flip = z;
    }

    public boolean getH_flip() {
        return this.h_flip;
    }

    public void setV_flip(boolean z) {
        this.v_flip = z;
    }

    public boolean getV_flip() {
        return this.v_flip;
    }

    public Bitmap AllEffects() {
        this.tmp = this.bitmap;
        if (this.filterIndex > 0) {
            this.tmp = new ImageFilter(this.f81c, this.tmp).applyStyle(this.filterIndex);
        }
        if (this.frameIndex > -1) {
            try {
                this.tmp = getFrame(getBitmapFromAsset("Frames", this.frameName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (this.h_flip) {
            this.tmp = flip(this.tmp, 2);
        }
        if (this.v_flip) {
            this.tmp = flip(this.tmp, 1);
        }
        if (this.cascade) {
            this.tmp = getImage(this.tmp);
        }
        if (this.shadow) {
            this.tmp = highlightImage(this.tmp);
        }
        return this.tmp;
    }

    public Bitmap highlightImage(Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap2.getWidth() + 96, bitmap2.getHeight() + 96, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint();
        paint.setMaskFilter(new BlurMaskFilter(40.0f, BlurMaskFilter.Blur.NORMAL));
        Bitmap extractAlpha = bitmap2.extractAlpha(paint, new int[2]);
        Paint paint2 = new Paint();
        paint2.setColor(Color.parseColor("#000000"));
        canvas.drawBitmap(extractAlpha, -12.0f, -12.0f, paint2);
        extractAlpha.recycle();
        canvas.drawBitmap(bitmap2, 24.0f, 24.0f, (Paint) null);
        return createBitmap;
    }

    public Bitmap getFrame(Bitmap bitmap2) {
        Bitmap bitmap3;
        if (this.filterIndex > 0) {
            bitmap3 = this.tmp;
        } else {
            bitmap3 = this.bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap3.getWidth(), bitmap3.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap3, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap2, bitmap3.getWidth(), bitmap3.getHeight(), false), 0.0f, 0.0f, (Paint) null);
        return createBitmap;
    }

    public Bitmap flip(Bitmap bitmap2, int i) {
        Matrix matrix = new Matrix();
        if (i == 1) {
            matrix.preScale(1.0f, -1.0f);
        } else if (i != 2) {
            return null;
        } else {
            matrix.preScale(-1.0f, 1.0f);
        }
        return Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
    }

    public Bitmap getImage(Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, (Paint) null);
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap2, bitmap2.getWidth() - 120, bitmap2.getHeight() - 120, false);
        Bitmap createScaledBitmap2 = Bitmap.createScaledBitmap(bitmap2, bitmap2.getWidth() - 240, bitmap2.getHeight() - 240, false);
        if (this.shadow) {
            createScaledBitmap = highlightImage(createScaledBitmap);
            createScaledBitmap2 = highlightImage(createScaledBitmap2);
        }
        float f = (float) 40;
        canvas.drawBitmap(createScaledBitmap, f, f, (Paint) null);
        canvas.drawBitmap(createScaledBitmap2, 100.0f, 100.0f, (Paint) null);
        return createBitmap;
    }

    private Bitmap getBitmapFromAsset(String str, String str2) throws IOException {
        AssetManager assets = this.f81c.getAssets();
        InputStream open = assets.open(str + "/" + str2);
        Bitmap decodeStream = BitmapFactory.decodeStream(open);
        open.close();
        return decodeStream;
    }
}

