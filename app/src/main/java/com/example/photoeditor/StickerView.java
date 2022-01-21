package com.example.photoeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
public class StickerView extends androidx.appcompat.widget.AppCompatImageView {

    private static final float BITMAP_SCALE = 0.4f;
    private static final String TAG = "StickerView";
    private float MAX_SCALE = 1.2f;
    private float MIN_SCALE = 0.5f;
    private Bitmap deleteBitmap;
    private int deleteBitmapHeight;
    private int deleteBitmapWidth;
    private DisplayMetrics dm;
    private Rect dst_delete;
    private Rect dst_flipV;
    private Rect dst_resize;
    private Rect dst_top;
    private Bitmap flipVBitmap;
    private int flipVBitmapHeight;
    private int flipVBitmapWidth;
    private double halfDiagonalLength;
    private boolean isHorizonMirror = false;
    public boolean isInEdit = true;
    private boolean isInResize = false;
    private boolean isInSide;
    private boolean isPointerDown = false;
    private float lastLength;
    private float lastRotateDegree;
    private float lastX;
    private float lastY;
    private Paint localPaint;
    private Bitmap mBitmap;
    private int mScreenHeight;
    private int mScreenWidth;
    private final Matrix matrix = new Matrix();
    private final PointF mid = new PointF();
    private float oldDis;
    private OperationListener operationListener;
    private float oringinWidth = 0.0f;
    private final float pointerLimitDis = 20.0f;
    private final float pointerZoomCoeff = 0.09f;
    private Bitmap resizeBitmap;
    private int resizeBitmapHeight;
    private int resizeBitmapWidth;
    private final long stickerId = 0;
    private int topBitmapHeight;
    private int topBitmapWidth;

    public interface OperationListener {
        void onDeleteClick();

        void onEdit(StickerView stickerView);

        void onTop(StickerView stickerView);
    }

    public boolean getEditMode() {
        return this.isInEdit;
    }

    public StickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public StickerView(Context context) {
        super(context);
        init();
    }

    public StickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.dst_delete = new Rect();
        this.dst_resize = new Rect();
        this.dst_flipV = new Rect();
        this.dst_top = new Rect();
        Paint paint = new Paint();
        this.localPaint = paint;
        paint.setColor(getResources().getColor(R.color.black));
        this.localPaint.setAntiAlias(true);
        this.localPaint.setDither(true);
        this.localPaint.setStyle(Paint.Style.STROKE);
        this.localPaint.setStrokeWidth(2.0f);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.dm = displayMetrics;
        this.mScreenWidth = displayMetrics.widthPixels;
        this.mScreenHeight = this.dm.heightPixels;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        if (this.mBitmap != null) {
            float[] fArr = new float[9];
            this.matrix.getValues(fArr);
            float f = fArr[2] + (fArr[0] * 0.0f) + (fArr[1] * 0.0f);
            float f2 = (fArr[3] * 0.0f) + (fArr[4] * 0.0f) + fArr[5];
            float width = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * 0.0f) + fArr[2];
            float width2 = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * 0.0f) + fArr[5];
            float height = (fArr[0] * 0.0f) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
            float height2 = (fArr[3] * 0.0f) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
            float width3 = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
            float width4 = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
            canvas.save();
            canvas2.drawBitmap(this.mBitmap, this.matrix, null);
            this.dst_delete.left = (int) (width - ((float) (this.deleteBitmapWidth / 2)));
            this.dst_delete.right = (int) (((float) (this.deleteBitmapWidth / 2)) + width);
            this.dst_delete.top = (int) (width2 - ((float) (this.deleteBitmapHeight / 2)));
            this.dst_delete.bottom = (int) (((float) (this.deleteBitmapHeight / 2)) + width2);
            this.dst_resize.left = (int) (width3 - ((float) (this.resizeBitmapWidth / 2)));
            this.dst_resize.right = (int) (width3 + ((float) (this.resizeBitmapWidth / 2)));
            this.dst_resize.top = (int) (width4 - ((float) (this.resizeBitmapHeight / 2)));
            this.dst_resize.bottom = (int) (((float) (this.resizeBitmapHeight / 2)) + width4);
            this.dst_top.left = (int) (f - ((float) (this.flipVBitmapWidth / 2)));
            this.dst_top.right = (int) (((float) (this.flipVBitmapWidth / 2)) + f);
            this.dst_top.top = (int) (f2 - ((float) (this.flipVBitmapHeight / 2)));
            this.dst_top.bottom = (int) (((float) (this.flipVBitmapHeight / 2)) + f2);
            this.dst_flipV.left = (int) (height - ((float) (this.topBitmapWidth / 2)));
            this.dst_flipV.right = (int) (((float) (this.topBitmapWidth / 2)) + height);
            this.dst_flipV.top = (int) (height2 - ((float) (this.topBitmapHeight / 2)));
            this.dst_flipV.bottom = (int) (((float) (this.topBitmapHeight / 2)) + height2);
            if (this.isInEdit) {
                Canvas canvas3 = canvas;
                canvas3.drawLine(f, f2, width, width2, this.localPaint);
                float f3 = width3;
                float f4 = width4;
                canvas3.drawLine(width, width2, f3, f4, this.localPaint);
                float f5 = height;
                float f6 = height2;
                canvas3.drawLine(f5, f6, f3, f4, this.localPaint);
                canvas3.drawLine(f5, f6, f, f2, this.localPaint);
                canvas2.drawBitmap(this.deleteBitmap, null, this.dst_delete, null);
                canvas2.drawBitmap(this.resizeBitmap, null, this.dst_resize, null);
                canvas2.drawBitmap(this.flipVBitmap, null, this.dst_flipV, null);
            }
            canvas.restore();
        }
    }
    public void setImageResource(int i) {
        setBitmap(BitmapFactory.decodeResource(getResources(), i));
    }

    public void replaceBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        invalidate();
    }

    public void setBitmap(Bitmap bitmap) {
        this.matrix.reset();
        this.mBitmap = bitmap;
        setDiagonalLength();
        initBitmaps();
        int width = this.mBitmap.getWidth();
        int height = this.mBitmap.getHeight();
        this.oringinWidth = (float) width;
        float f = (this.MIN_SCALE + this.MAX_SCALE) / 2.0f;
        int i = width / 2;
        int i2 = height / 2;
        this.matrix.postScale(f, f, (float) i, (float) i2);
        Matrix matrix2 = this.matrix;
        int i3 = this.mScreenWidth;
        matrix2.postTranslate((float) ((i3 / 2) - i), (float) ((i3 / 2) - i2));
        invalidate();
    }

    private void setDiagonalLength() {
        this.halfDiagonalLength = Math.hypot(this.mBitmap.getWidth(), this.mBitmap.getHeight()) / 2.0d;
    }

    private void initBitmaps() {
        if (this.mBitmap.getWidth() >= this.mBitmap.getHeight()) {
            float f = (float) (this.mScreenWidth / 8);
            if (((float) this.mBitmap.getWidth()) < f) {
                this.MIN_SCALE = 1.0f;
            } else {
                this.MIN_SCALE = (f * 1.0f) / ((float) this.mBitmap.getWidth());
            }
            int width = this.mBitmap.getWidth();
            int i = this.mScreenWidth;
            if (width > i) {
                this.MAX_SCALE = 1.0f;
            } else {
                this.MAX_SCALE = (((float) i) * 1.0f) / ((float) this.mBitmap.getWidth());
            }
        } else {
            float f2 = (float) (this.mScreenWidth / 8);
            if (((float) this.mBitmap.getHeight()) < f2) {
                this.MIN_SCALE = 1.0f;
            } else {
                this.MIN_SCALE = (f2 * 1.0f) / ((float) this.mBitmap.getHeight());
            }
            int height = this.mBitmap.getHeight();
            int i2 = this.mScreenWidth;
            if (height > i2) {
                this.MAX_SCALE = 1.0f;
            } else {
                this.MAX_SCALE = (((float) i2) * 1.0f) / ((float) this.mBitmap.getHeight());
            }
        }
        this.deleteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.canceleditunpress);
        this.flipVBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.horizantal_unpress);
        this.resizeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zoom_unpress);
        this.deleteBitmapWidth = (int) (((float) this.deleteBitmap.getWidth()) * BITMAP_SCALE);
        this.deleteBitmapHeight = (int) (((float) this.deleteBitmap.getHeight()) * BITMAP_SCALE);
        this.resizeBitmapWidth = (int) (((float) this.resizeBitmap.getWidth()) * BITMAP_SCALE);
        this.resizeBitmapHeight = (int) (((float) this.resizeBitmap.getHeight()) * BITMAP_SCALE);
        this.flipVBitmapWidth = (int) (((float) this.flipVBitmap.getWidth()) * BITMAP_SCALE);
        this.flipVBitmapHeight = (int) (((float) this.flipVBitmap.getHeight()) * BITMAP_SCALE);
    }


    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        boolean handled = true;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isInButton(event, dst_delete)) {
                    if (operationListener != null) {
                        operationListener.onDeleteClick();
                    }
                } else if (isInResize(event)) {
                    isInResize = true;
                    lastRotateDegree = rotationToStartPoint(event);
                    midPointToStartPoint(event);
                    lastLength = diagonalLength(event);
                } else if (isInButton(event, dst_flipV)) {

                    PointF localPointF = new PointF();
                    midDiagonalPoint(localPointF);
                    matrix.postScale(-1.0F, 1.0F, localPointF.x, localPointF.y);
                    isHorizonMirror = !isHorizonMirror;
                    invalidate();
                } else if (isInButton(event, dst_top)) {
                    bringToFront();
                    if (operationListener != null) {
                        operationListener.onTop(this);
                    }
                } else if (isInBitmap(event)) {
                    isInSide = true;
                    lastX = event.getX(0);
                    lastY = event.getY(0);
                } else {
                    handled = false;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (spacing(event) > pointerLimitDis) {
                    oldDis = spacing(event);
                    isPointerDown = true;
                    midPointToStartPoint(event);
                } else {
                    isPointerDown = false;
                }
                isInSide = false;
                isInResize = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isPointerDown) {
                    float scale;
                    float disNew = spacing(event);
                    if (disNew == 0 || disNew < pointerLimitDis) {
                        scale = 1;
                    } else {
                        scale = disNew / oldDis;
                        scale = (scale - 1) * pointerZoomCoeff + 1;
                    }
                    float scaleTemp = (scale * Math.abs(dst_flipV.left - dst_resize.left)) / oringinWidth;
                    if (((scaleTemp <= MIN_SCALE)) && scale < 1 ||
                            (scaleTemp >= MAX_SCALE) && scale > 1) {
                        scale = 1;
                    } else {
                        lastLength = diagonalLength(event);
                    }
                    matrix.postScale(scale, scale, mid.x, mid.y);
                    invalidate();
                } else if (isInResize) {

                    matrix.postRotate((rotationToStartPoint(event) - lastRotateDegree) * 2, mid.x, mid.y);
                    lastRotateDegree = rotationToStartPoint(event);

                    float scale = diagonalLength(event) / lastLength;

                    if (((diagonalLength(event) / halfDiagonalLength <= MIN_SCALE)) && scale < 1 ||
                            (diagonalLength(event) / halfDiagonalLength >= MAX_SCALE) && scale > 1) {
                        scale = 1;
                        if (!isInResize(event)) {
                            isInResize = false;
                        }
                    } else {
                        lastLength = diagonalLength(event);
                    }
                    matrix.postScale(scale, scale, mid.x, mid.y);

                    invalidate();
                } else if (isInSide) {
                    float x = event.getX(0);
                    float y = event.getY(0);
                    matrix.postTranslate(x - lastX, y - lastY);
                    lastX = x;
                    lastY = y;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isInResize = false;
                isInSide = false;
                isPointerDown = false;
                break;

        }
        if (handled && operationListener != null) {
            operationListener.onEdit(this);
        }
        return handled;
    }


    private boolean isInBitmap(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = motionEvent;
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float f = (fArr[3] * 0.0f) + (fArr[4] * 0.0f) + fArr[5];
        float width = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * 0.0f) + fArr[5];
        float height = (fArr[3] * 0.0f) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
        float width2 = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
        float width3 = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
        return pointInRect(new float[]{(fArr[0] * 0.0f) + (fArr[1] * 0.0f) + fArr[2], (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * 0.0f) + fArr[2], width2, (fArr[0] * 0.0f) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2]}, new float[]{f, width, width3, height}, motionEvent2.getX(0), motionEvent2.getY(0));
    }

    private boolean pointInRect(float[] fArr, float[] fArr2, float f, float f2) {
        double hypot = Math.hypot( (fArr[0] - fArr[1]),  (fArr2[0] - fArr2[1]));
        double hypot2 = Math.hypot( (fArr[1] - fArr[2]),  (fArr2[1] - fArr2[2]));
        double hypot3 = Math.hypot( (fArr[3] - fArr[2]),  (fArr2[3] - fArr2[2]));
        double hypot4 = Math.hypot( (fArr[0] - fArr[3]),  (fArr2[0] - fArr2[3]));
        double hypot5 = Math.hypot( (f - fArr[0]),  (f2 - fArr2[0]));
        double hypot6 = Math.hypot( (f - fArr[1]),  (f2 - fArr2[1]));
        double hypot7 = Math.hypot( (f - fArr[2]),  (f2 - fArr2[2]));
        double hypot8 = Math.hypot( (f - fArr[3]),  (f2 - fArr2[3]));
        double d2 = ((hypot + hypot5) + hypot6) / 2.0d;
        double d3 = ((hypot2 + hypot6) + hypot7) / 2.0d;
        double d4 = ((hypot3 + hypot7) + hypot8) / 2.0d;
        double d5 = ((hypot4 + hypot8) + hypot5) / 2.0d;
        return Math.abs((hypot * hypot2) - (((Math.sqrt((((d2 - hypot) * d2) * (d2 - hypot5)) * (d2 - hypot6)) + Math.sqrt((((d3 - hypot2) * d3) * (d3 - hypot6)) * (d3 - hypot7))) + Math.sqrt((((d4 - hypot3) * d4) * (d4 - hypot7)) * (d4 - hypot8))) + Math.sqrt((((d5 - hypot4) * d5) * (d5 - hypot8)) * (d5 - hypot5)))) < 0.5d;
    }

    private boolean isInButton(MotionEvent motionEvent, Rect rect) {
        int i = rect.left;
        int i2 = rect.right;
        int i3 = rect.top;
        int i4 = rect.bottom;
        return !(motionEvent.getX(0) < ((float) i)) && !(motionEvent.getX(0) > ((float) i2)) && !(motionEvent.getY(0) < ((float) i3)) && !(motionEvent.getY(0) > ((float) i4));
    }

    private boolean isInResize(MotionEvent motionEvent) {
        int i = this.dst_resize.top - 20;
        int i2 = this.dst_resize.right + 20;
        int i3 = this.dst_resize.bottom + 20;
        return !(motionEvent.getX(0) < ((float) (this.dst_resize.left - 20))) && !(motionEvent.getX(0) > ((float) i2)) && !(motionEvent.getY(0) < ((float) i)) && !(motionEvent.getY(0) > ((float) i3));
    }

    private void midPointToStartPoint(MotionEvent motionEvent) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        this.mid.set(((((fArr[0] * 0.0f) + (fArr[1] * 0.0f)) + fArr[2]) + motionEvent.getX(0)) / 2.0f, ((((fArr[3] * 0.0f) + (fArr[4] * 0.0f)) + fArr[5]) + motionEvent.getY(0)) / 2.0f);
    }

    private void midDiagonalPoint(@NonNull PointF pointF) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float width = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
        pointF.set(((((fArr[0] * 0.0f) + (fArr[1] * 0.0f)) + fArr[2]) + width) / 2.0f, ((((fArr[3] * 0.0f) + (fArr[4] * 0.0f)) + fArr[5]) + (((fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * ((float) this.mBitmap.getHeight()))) + fArr[5])) / 2.0f);
    }

    private float rotationToStartPoint(MotionEvent motionEvent) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY(0) - (((fArr[3] * 0.0f) + (fArr[4] * 0.0f)) + fArr[5])), (double) (motionEvent.getX(0) - (((fArr[0] * 0.0f) + (fArr[1] * 0.0f)) + fArr[2]))));
    }

    private float diagonalLength(MotionEvent motionEvent) {
        return (float) Math.hypot((double) (motionEvent.getX(0) - this.mid.x), (double) (motionEvent.getY(0) - this.mid.y));
    }

    private float spacing(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 2) {
            return 0.0f;
        }
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    public void setOperationListener(OperationListener operationListener2) {
        this.operationListener = operationListener2;
    }

    public void setInEdit(boolean z) {
        this.isInEdit = z;
        invalidate();
    }
}
