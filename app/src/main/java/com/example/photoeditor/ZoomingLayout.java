package com.example.photoeditor;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;

public class ZoomingLayout extends RelativeLayout implements ScaleGestureDetector.OnScaleGestureListener {
    private static final float MAX_ZOOM = 4.0f;
    private static final float MIN_ZOOM = 1.0f;
    private static final String TAG = "ZoomLayout";
    /* access modifiers changed from: private */

    /* renamed from: dx */
    public float f129dx = 0.0f;
    /* access modifiers changed from: private */

    /* renamed from: dy */
    public float f130dy = 0.0f;
    public boolean enablezoom = true;
    private float lastScaleFactor = 0.0f;
    /* access modifiers changed from: private */
    public Mode mode = Mode.NONE;
    /* access modifiers changed from: private */
    public float prevDx = 0.0f;
    /* access modifiers changed from: private */
    public float prevDy = 0.0f;
    /* access modifiers changed from: private */
    public float scale = MIN_ZOOM;
    /* access modifiers changed from: private */
    public float startX = 0.0f;
    /* access modifiers changed from: private */
    public float startY = 0.0f;

    private enum Mode {
        NONE,
        DRAG,
        ZOOM
    }

    public ZoomingLayout(Context context) {
        super(context);
        init(context);
    }

    public ZoomingLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ZoomingLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (ZoomingLayout.this.enablezoom) {
                    return false;
                }
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    Log.i(ZoomingLayout.TAG, "DOWN");
                    if (ZoomingLayout.this.scale > ZoomingLayout.MIN_ZOOM) {
                        Mode unused = ZoomingLayout.this.mode = Mode.DRAG;
                        float unused2 = ZoomingLayout.this.startX = motionEvent.getX() - ZoomingLayout.this.prevDx;
                        float unused3 = ZoomingLayout.this.startY = motionEvent.getY() - ZoomingLayout.this.prevDy;
                    }
                } else if (action == 1) {
                    Log.i(ZoomingLayout.TAG, "UP");
                    Mode unused4 = ZoomingLayout.this.mode = Mode.NONE;
                    ZoomingLayout zoomingLayout = ZoomingLayout.this;
                    float unused5 = zoomingLayout.prevDx = zoomingLayout.f129dx;
                    ZoomingLayout zoomingLayout2 = ZoomingLayout.this;
                    float unused6 = zoomingLayout2.prevDy = zoomingLayout2.f130dy;
                } else if (action != 2) {
                    if (action == 5) {
                        Mode unused7 = ZoomingLayout.this.mode = Mode.ZOOM;
                    } else if (action == 6) {
                        Mode unused8 = ZoomingLayout.this.mode = Mode.DRAG;
                    }
                } else if (ZoomingLayout.this.mode == Mode.DRAG) {
                    float unused9 = ZoomingLayout.this.f129dx = motionEvent.getX() - ZoomingLayout.this.startX;
                    float unused10 = ZoomingLayout.this.f130dy = motionEvent.getY() - ZoomingLayout.this.startY;
                }
                scaleGestureDetector.onTouchEvent(motionEvent);
                if ((ZoomingLayout.this.mode == Mode.DRAG && ZoomingLayout.this.scale >= ZoomingLayout.MIN_ZOOM) || ZoomingLayout.this.mode == Mode.ZOOM) {
                    ZoomingLayout.this.getParent().requestDisallowInterceptTouchEvent(true);
                    float width = ((((float) ZoomingLayout.this.child().getWidth()) - (((float) ZoomingLayout.this.child().getWidth()) / ZoomingLayout.this.scale)) / 2.0f) * ZoomingLayout.this.scale;
                    float height = ((((float) ZoomingLayout.this.child().getHeight()) - (((float) ZoomingLayout.this.child().getHeight()) / ZoomingLayout.this.scale)) / 2.0f) * ZoomingLayout.this.scale;
                    ZoomingLayout zoomingLayout3 = ZoomingLayout.this;
                    float unused11 = zoomingLayout3.f129dx = Math.min(Math.max(zoomingLayout3.f129dx, -width), width);
                    ZoomingLayout zoomingLayout4 = ZoomingLayout.this;
                    float unused12 = zoomingLayout4.f130dy = Math.min(Math.max(zoomingLayout4.f130dy, -height), height);
                    Log.i(ZoomingLayout.TAG, "Width: " + ZoomingLayout.this.child().getWidth() + ", scale " + ZoomingLayout.this.scale + ", dx " + ZoomingLayout.this.f129dx + ", max " + width);
                    ZoomingLayout.this.applyScaleAndTranslation();
                }
                return true;
            }
        });
    }

    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        Log.i(TAG, "onScaleBegin");
        return true;
    }

    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        Log.i(TAG, "onScale" + scaleFactor);
        if (this.lastScaleFactor == 0.0f || Math.signum(scaleFactor) == Math.signum(this.lastScaleFactor)) {
            this.scale *= scaleFactor;
            this.scale = Math.max(MIN_ZOOM, Math.min(this.scale, MAX_ZOOM));
            this.lastScaleFactor = scaleFactor;
            return true;
        }
        this.lastScaleFactor = 0.0f;
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        Log.i(TAG, "onScaleEnd");
    }

    /* access modifiers changed from: private */
    public void applyScaleAndTranslation() {
        child().setScaleX(this.scale);
        child().setScaleY(this.scale);
        child().setTranslationX(this.f129dx);
        child().setTranslationY(this.f130dy);
    }

    /* access modifiers changed from: private */
    public View child() {
        return getChildAt(0);
    }
}
