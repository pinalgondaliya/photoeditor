package com.example.photoeditor;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.Iterator;

public class DrawView extends androidx.appcompat.widget.AppCompatImageView {
    private static final float TOUCH_TOLERANCE = 4.0f;
    int blurSize = 30;
    int brushSize = 30;
    Context context;
    public boolean enablezoom = true;
    int eraserSize = 20;

    /* renamed from: ft */
    Path.FillType f125ft = Path.FillType.WINDING;
    public int height;
    private boolean isInverse;
    private Bitmap mBitmap;
    private Paint mBitmapPaint;
    public Canvas mCanvas;
    private Paint mPaint;
    private Path mPath;

    /* renamed from: mX */
    private float f126mX;

    /* renamed from: mY */
    private float f127mY;
    private ArrayList<PathSelectModel> paths = new ArrayList<>();
    private ArrayList<PathSelectModel> undonePaths = new ArrayList<>();
    public int width;

    public DrawView(Activity activity) {
        super(activity);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mPaint.setDither(true);
        this.mPaint.setColor(-16711936);
        this.mPaint.setMaskFilter((MaskFilter) null);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStrokeWidth((float) this.brushSize);
        this.context = activity;
        this.mPath = new Path();
        this.mBitmapPaint = new Paint();
        setLayerType(2, this.mBitmapPaint);
        this.mBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        this.mCanvas = new Canvas(this.mBitmap);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, (Paint) null);
        Iterator<PathSelectModel> it = this.paths.iterator();
        while (it.hasNext()) {
            PathSelectModel next = it.next();
            Paint paint = next.f128p;
            Path path = next.path;
            path.setFillType(this.f125ft);
            canvas.drawPath(path, paint);
        }
        canvas.drawPath(this.mPath, this.mPaint);
    }

    private void touch_start(float f, float f2) {
        this.mPath.moveTo(f, f2);
        this.mPath.setFillType(this.f125ft);
        this.f126mX = f;
        this.f127mY = f2;
    }

    private void touch_move(float f, float f2) {
        float abs = Math.abs(f - this.f126mX);
        float abs2 = Math.abs(f2 - this.f127mY);
        if (abs >= TOUCH_TOLERANCE || abs2 >= TOUCH_TOLERANCE) {
            Path path = this.mPath;
            float f3 = this.f126mX;
            float f4 = this.f127mY;
            path.quadTo(f3, f4, (f + f3) / 2.0f, (f2 + f4) / 2.0f);
            this.f126mX = f;
            this.f127mY = f2;
            this.mCanvas.drawPath(this.mPath, this.mPaint);
        }
    }

    private void touch_up() {
        this.mPath.lineTo(this.f126mX, this.f127mY);
        this.mCanvas.drawPath(this.mPath, this.mPaint);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setAlpha(255);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth((float) this.brushSize);
        this.paths.add(new PathSelectModel(this.mPath, paint));
        this.mPath = new Path();
    }

    public void setFillType(Path.FillType fillType) {
        if (fillType == Path.FillType.WINDING) {
            this.f125ft = Path.FillType.INVERSE_WINDING;
        } else {
            this.f125ft = Path.FillType.INVERSE_WINDING;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.enablezoom) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            touch_start(x, y);
            invalidate();
        } else if (action == 1) {
            touch_up();
            invalidate();
        } else if (action == 2) {
            touch_move(x, y);
            invalidate();
        }
        return true;
    }

    public void resetPath(boolean z) {
        this.mPath.reset();
    }

    public void inversePaint() {
        setFillType(this.f125ft);
        invalidate();
    }

    public void onClickRedo() {
        if (this.undonePaths.size() > 0) {
            ArrayList<PathSelectModel> arrayList = this.paths;
            ArrayList<PathSelectModel> arrayList2 = this.undonePaths;
            arrayList.add(arrayList2.remove(arrayList2.size() - 1));
            invalidate();
            return;
        }
        Toast.makeText(getContext(), "Can't Redo More",Toast.LENGTH_LONG).show();
    }

    public void setBrushSize(int i) {
        this.brushSize = i;
        this.mPaint.setStrokeWidth((float) this.brushSize);
    }

    public void setHardnessSize(int i) {
        this.blurSize = i;
        this.mPaint.setMaskFilter(new BlurMaskFilter((float) this.blurSize, BlurMaskFilter.Blur.NORMAL));
    }

    public void onClickUndo() {
        if (this.paths.size() > 0) {
            ArrayList<PathSelectModel> arrayList = this.undonePaths;
            ArrayList<PathSelectModel> arrayList2 = this.paths;
            arrayList.add(arrayList2.remove(arrayList2.size() - 1));
            invalidate();
            return;
        }
        Toast.makeText(getContext(), "Can't Undo More", Toast.LENGTH_LONG ).show();
    }
}
