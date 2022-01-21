package com.example.photoeditor;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SingleFingerView extends LinearLayout {
    private float _1dp;
    private boolean hasSetParamsForView;
    private boolean mCenterInParent;
    private Drawable mImageDrawable;
    private float mImageHeight;
    private float mImageWidth;
    private int mLeft;
    public ImageView mPushDelete;
    private Drawable mPushDeleteDrawable;
    private Drawable mPushImageDrawable;
    private float mPushImageHeight;
    private float mPushImageWidth;
    public ImageView mPushView;
    private int mTop;
    public ImageView mView;

    public SingleFingerView(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public void setDrawable(Drawable drawable) {
        this.mImageDrawable = drawable;
        this.mImageWidth = (float) (this.mImageDrawable.getIntrinsicWidth() * 2);
        this.mImageHeight = (float) (this.mImageDrawable.getIntrinsicHeight() * 2);
        setViewToAttr(1000, 1000);
    }

    public void setStickerAlpha(float f) {
        this.mView.setAlpha(f);
    }

    public SingleFingerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SingleFingerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCenterInParent = true;
        this.mLeft = 0;
        this.mTop = 0;
        this.hasSetParamsForView = false;
        this._1dp = TypedValue.applyDimension(1, 1.0f, context.getResources().getDisplayMetrics());
        parseAttr(context, attributeSet);
        View inflate = View.inflate(context, R.layout.test_image_view_with_delete, (ViewGroup) null);
        addView(inflate, -1, -1);
        this.mPushView = (ImageView) inflate.findViewById(R.id.push_view);
        this.mPushDelete = (ImageView) inflate.findViewById(R.id.push_delete);
        this.mView = (ImageView) inflate.findViewById(R.id.view);
        this.mPushView.setOnTouchListener(new PushBtnTouchListener(this.mView));
        this.mPushDelete.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("WrongConstant")
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0) {
                    return true;
                }
                SingleFingerView.this.setVisibility(4);
                return true;
            }
        });
        this.mView.setOnTouchListener(new ViewOnTouchListener(this.mPushView, this.mPushDelete));
    }

    private void parseAttr(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes;
        if (attributeSet != null && (obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SingleFingerView)) != null) {
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 0) {
                    this.mCenterInParent = obtainStyledAttributes.getBoolean(index, true);
                } else if (index == 1) {
                    this.mImageDrawable = obtainStyledAttributes.getDrawable(index);
                } else if (index == 2) {
                    this.mImageHeight = obtainStyledAttributes.getDimension(index, this._1dp * 200.0f);
                } else if (index == 3) {
                    this.mImageWidth = obtainStyledAttributes.getDimension(index, this._1dp * 200.0f);
                } else if (index == 6) {
                    this.mPushImageDrawable = obtainStyledAttributes.getDrawable(index);
                } else if (index == 5) {
                    this.mPushDeleteDrawable = obtainStyledAttributes.getDrawable(index);
                } else if (index == 8) {
                    this.mPushImageWidth = obtainStyledAttributes.getDimension(index, this._1dp * 50.0f);
                } else if (index == 7) {
                    this.mPushImageHeight = obtainStyledAttributes.getDimension(index, this._1dp * 50.0f);
                } else if (index == 4) {
                    this.mLeft = (int) obtainStyledAttributes.getDimension(index, this._1dp * 0.0f);
                } else if (index == 9) {
                    this.mTop = (int) obtainStyledAttributes.getDimension(index, this._1dp * 0.0f);
                }
            }
        }
    }
    private void setViewToAttr(int i, int i2) {
        int i3;
        Drawable drawable = this.mImageDrawable;
        if (drawable != null) {
            this.mView.setBackgroundDrawable(drawable);
        }
        Drawable drawable2 = this.mPushDeleteDrawable;
        if (drawable2 != null) {
            this.mPushDelete.setBackgroundDrawable(drawable2);
        }
        Drawable drawable3 = this.mPushImageDrawable;
        if (drawable3 != null) {
            this.mPushView.setBackgroundDrawable(drawable3);
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mView.getLayoutParams();
        layoutParams.width = (int) this.mImageWidth;
        layoutParams.height = (int) this.mImageHeight;
        int i4 = 0;
        if (this.mCenterInParent) {
            i3 = (i / 2) - (layoutParams.width / 2);
            i4 = (i2 / 2) - (layoutParams.height / 2);
        } else {
            i3 = this.mLeft;
            if (i3 <= 0) {
                i3 = 0;
            }
            int i5 = this.mTop;
            if (i5 > 0) {
                i4 = i5;
            }
        }
        layoutParams.leftMargin = i3;
        layoutParams.topMargin = i4;
        this.mView.setLayoutParams(layoutParams);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mPushView.getLayoutParams();
        layoutParams2.width = (int) this.mPushImageWidth;
        layoutParams2.height = (int) this.mPushImageHeight;
        layoutParams2.leftMargin = (int) ((((float) layoutParams.leftMargin) + this.mImageWidth) - (this.mPushImageWidth / 2.0f));
        layoutParams2.topMargin = (int) ((((float) layoutParams.topMargin) + this.mImageHeight) - (this.mPushImageHeight / 2.0f));
        this.mPushView.setLayoutParams(layoutParams2);
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mPushDelete.getLayoutParams();
        layoutParams3.width = (int) this.mPushImageWidth;
        layoutParams3.height = (int) this.mPushImageHeight;
        layoutParams3.leftMargin = (int) (((float) (layoutParams.leftMargin + layoutParams.width)) - (this.mPushImageWidth / 2.0f));
        layoutParams3.topMargin = (int) (((float) layoutParams.topMargin) + (this.mPushImageHeight / 2.0f));
        this.mPushDelete.setLayoutParams(layoutParams3);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setParamsForView(i, i2);
    }

    private void setParamsForView(int i, int i2) {
        int i3;
        int i4;
        if (getLayoutParams() != null && !this.hasSetParamsForView) {
            this.hasSetParamsForView = true;
            if (getLayoutParams().width == -1) {
                i3 = View.MeasureSpec.getSize(i);
            } else {
                i3 = getLayoutParams().width;
            }
            if (getLayoutParams().height == -1) {
                i4 = View.MeasureSpec.getSize(i2);
            } else {
                i4 = getLayoutParams().height;
            }
            setViewToAttr(i3, i4);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            hidePushView();
        }
        return super.onTouchEvent(motionEvent);
    }

    @SuppressLint("WrongConstant")
    public void hidePushView() {
        this.mPushView.setVisibility(8);
        this.mPushDelete.setVisibility(8);
    }

    @SuppressLint("WrongConstant")
    public void showPushView() {
        this.mPushView.setVisibility(0);
        this.mPushDelete.setVisibility(0);
    }
}

