package com.example.photoeditor;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class DotsProgressBar extends View {
    private int heightSize;
    /* access modifiers changed from: private */
    public int mDotCount = 5;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public int mIndex = 0;
    private Paint mPaint = new Paint(1);
    private Paint mPaintFill = new Paint(1);
    private float mRadius;
    /* access modifiers changed from: private */
    public Runnable mRunnable = new Runnable() {
        public void run() {
            DotsProgressBar dotsProgressBar = DotsProgressBar.this;
            int unused = dotsProgressBar.mIndex = dotsProgressBar.mIndex + DotsProgressBar.this.step;
            if (DotsProgressBar.this.mIndex < 0) {
                int unused2 = DotsProgressBar.this.mIndex = 1;
                int unused3 = DotsProgressBar.this.step = 1;
            } else if (DotsProgressBar.this.mIndex > DotsProgressBar.this.mDotCount - 1) {
                if (DotsProgressBar.this.mDotCount - 2 >= 0) {
                    DotsProgressBar dotsProgressBar2 = DotsProgressBar.this;
                    int unused4 = dotsProgressBar2.mIndex = dotsProgressBar2.mDotCount - 2;
                    int unused5 = DotsProgressBar.this.step = -1;
                } else {
                    int unused6 = DotsProgressBar.this.mIndex = 0;
                    int unused7 = DotsProgressBar.this.step = 1;
                }
            }
            DotsProgressBar.this.invalidate();
            DotsProgressBar.this.mHandler.postDelayed(DotsProgressBar.this.mRunnable, 300);
        }
    };
    private int margin = 7;
    /* access modifiers changed from: private */
    public int step = 1;
    private int widthSize;

    public DotsProgressBar(Context context) {
        super(context);
        init(context);
    }

    public DotsProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public DotsProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        this.mRadius = (float) context.getResources().getInteger(R.integer.circle_indicator_radius);
        this.mPaintFill.setStyle(Paint.Style.FILL);
        this.mPaintFill.setColor(-1);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(Color.parseColor("#b38cd2"));
        start();
    }

    public void setDotsCount(int i) {
        this.mDotCount = i;
    }

    public void start() {
        this.mIndex = -1;
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mHandler.post(this.mRunnable);
    }

    public void stop() {
        this.mHandler.removeCallbacks(this.mRunnable);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.widthSize = View.MeasureSpec.getSize(i);
        this.heightSize = (((int) this.mRadius) * 2) + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(this.widthSize, this.heightSize);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = this.mDotCount;
        float f = ((((float) this.widthSize) - ((((float) i) * this.mRadius) * 2.0f)) - ((float) ((i - 1) * this.margin))) / 2.0f;
        float f2 = (float) (this.heightSize / 2);
        for (int i2 = 0; i2 < this.mDotCount; i2++) {
            if (i2 == this.mIndex) {
                canvas.drawCircle(f, f2, this.mRadius, this.mPaintFill);
            } else {
                canvas.drawCircle(f, f2, this.mRadius, this.mPaint);
            }
            f += (this.mRadius * 2.0f) + ((float) this.margin);
        }
    }
}

