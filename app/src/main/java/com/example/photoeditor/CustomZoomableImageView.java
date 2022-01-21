package com.example.photoeditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import androidx.core.view.MotionEventCompat;

public class CustomZoomableImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final int INVALID_POINTER_ID = -1;
    private static final String LOG_TAG = "TouchImageView";
    private Paint backgroundPaint;
    float bmHeight;
    float bmWidth;
    private Paint borderPaint;
    float bottom;
    int height;
    private int mActivePointerId;
    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX;
    private float mPosY;
    private ScaleGestureDetector mScaleDetector;
    /* access modifiers changed from: private */
    public float mScaleFactor;
    Matrix matrix;
    float origHeight;
    float origWidth;
    float pivotPointX;
    float pivotPointY;
    float redundantXSpace;
    float redundantYSpace;
    float right;

    /* renamed from: sX */
    public float f82sX;
    float saveScale;
    int width;

    public CustomZoomableImageView(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public CustomZoomableImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomZoomableImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.borderPaint = null;
        this.backgroundPaint = null;
        this.mPosX = 0.0f;
        this.mPosY = 0.0f;
        this.f82sX = 0.9f;
        this.saveScale = 1.0f;
        this.matrix = new Matrix();
        this.mActivePointerId = -1;
        this.mScaleFactor = 1.0f;
        this.pivotPointX = 0.0f;
        this.pivotPointY = 0.0f;
        this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        this.borderPaint = new Paint();
        this.borderPaint.setARGB(0, 255, 128, 0);
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(4.0f);
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setARGB(0, 255, 255, 255);
        this.backgroundPaint.setStyle(Paint.Style.FILL);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mScaleDetector.onTouchEvent(motionEvent);
        int action = motionEvent.getAction() & 255;
        int i = 0;
        if (action == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.mLastTouchX = x;
            this.mLastTouchY = y;
            this.mActivePointerId = motionEvent.getPointerId(0);
        } else if (action == 1) {
            this.mActivePointerId = -1;
        } else if (action == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            float x2 = motionEvent.getX(findPointerIndex);
            float y2 = motionEvent.getY(findPointerIndex);
            if (!this.mScaleDetector.isInProgress()) {
                this.mPosX += x2 - this.mLastTouchX;
                this.mPosY += y2 - this.mLastTouchY;
                invalidate();
            }
            this.mLastTouchX = x2;
            this.mLastTouchY = y2;
        } else if (action == 3) {
            this.mActivePointerId = -1;
        } else if (action == 6) {
            int action2 = (motionEvent.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
            if (motionEvent.getPointerId(action2) == this.mActivePointerId) {
                if (action2 == 0) {
                    i = 1;
                }
                this.mLastTouchX = motionEvent.getX(i);
                this.mLastTouchY = motionEvent.getY(i);
                this.mActivePointerId = motionEvent.getPointerId(i);
            }
        }
        return true;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(0.0f, 0.0f, (float) (getWidth() - 1), (float) (getHeight() - 1), this.borderPaint);
    }

    public void onDraw(Canvas canvas) {
        canvas.drawRect(0.0f, 0.0f, (float) (getWidth() - 1), (float) (getHeight() - 1), this.backgroundPaint);
        if (getDrawable() != null) {
            canvas.save();
            canvas.translate(this.mPosX, this.mPosY);
            Matrix matrix2 = new Matrix();
            float f = this.mScaleFactor;
            matrix2.postScale(f, f, this.pivotPointX, this.pivotPointY);
            canvas.drawBitmap(((BitmapDrawable) getDrawable()).getBitmap(), matrix2, (Paint) null);
            canvas.restore();
        }
    }

    public void setImageDrawable(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        this.mPosX = 0.0f;
        this.mLastTouchX = 0.0f;
        this.mPosY = 0.0f;
        this.mLastTouchY = 0.0f;
        float strokeWidth = (float) ((int) this.borderPaint.getStrokeWidth());
        float f = (float) intrinsicWidth;
        float f2 = (float) intrinsicHeight;
        this.mScaleFactor = Math.min((((float) getLayoutParams().width) - strokeWidth) / f, (((float) getLayoutParams().height) - strokeWidth) / f2);
        this.pivotPointX = ((((float) getLayoutParams().width) - strokeWidth) - ((float) ((int) (f * this.mScaleFactor)))) / 2.0f;
        this.pivotPointY = ((((float) getLayoutParams().height) - strokeWidth) - ((float) ((int) (f2 * this.mScaleFactor)))) / 2.0f;
        super.setImageDrawable(drawable);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            CustomZoomableImageView customZoomableImageView = CustomZoomableImageView.this;
            float unused = customZoomableImageView.mScaleFactor = customZoomableImageView.mScaleFactor * scaleGestureDetector.getScaleFactor();
            CustomZoomableImageView.this.pivotPointX = scaleGestureDetector.getFocusX();
            CustomZoomableImageView.this.pivotPointY = scaleGestureDetector.getFocusY();
            Log.d(CustomZoomableImageView.LOG_TAG, "mScaleFactor " + CustomZoomableImageView.this.mScaleFactor);
            Log.d(CustomZoomableImageView.LOG_TAG, "pivotPointY " + CustomZoomableImageView.this.pivotPointY + ", pivotPointX= " + CustomZoomableImageView.this.pivotPointX);
            CustomZoomableImageView customZoomableImageView2 = CustomZoomableImageView.this;
            float unused2 = customZoomableImageView2.mScaleFactor = Math.max(0.05f, customZoomableImageView2.mScaleFactor);
            CustomZoomableImageView.this.invalidate();
            return true;
        }
    }
}
