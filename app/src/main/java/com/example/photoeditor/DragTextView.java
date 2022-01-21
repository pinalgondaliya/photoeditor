package com.example.photoeditor;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;

public class DragTextView extends FrameLayout {

    /* renamed from: PI */
    private static final double f131PI = 3.14159265359d;
    Context fcontext;
    String fontstring = "";
    AutoResizeTextView ftvView;

    /* renamed from: h */
    Handler f132h = new Handler();
    String hinttext = "Add Text Here...";
    TextManagerListener listener;
    ImageView mDeleteView;
    ImageView mPushView;
    ImageView mTopView;
    private Point mViewCenter;
    PListener onPushTouch;
    VListener onTouchView;

    /* renamed from: r */
    Runnable f133r = new Runnable() {
        public void run() {
      //      DragTextView.this.setPushButtons();
            DragTextView.this.f132h.removeCallbacks(DragTextView.this.f133r);
        }
    };

    public DragTextView(Context context, String str) {
        super(context);
        this.fcontext = context;
        this.listener = (TextManagerListener) context;
        this.hinttext = str;
    }

    public String getTextString() {
        return "" + this.ftvView.getText().toString().replace(this.hinttext, "");
    }

    public int getTextColor() {
        return this.ftvView.getPaint().getColor();
    }

    public String getTextFontStyle() {
        return this.fontstring;
    }

    public FrameLayout.LayoutParams getTextParams() {
        return (FrameLayout.LayoutParams) this.ftvView.getLayoutParams();
    }

    public float getTextRotation() {
        return this.ftvView.getRotation();
    }

    public void SetTextColor(int i) {
        this.ftvView.setTextColor(i);
    }

    public void SetTextsize(int i, String str) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.ftvView.getLayoutParams();
        Rect rect = new Rect();
        TextPaint paint = this.ftvView.getPaint();
        paint.setTextSize((float) i);
        paint.getTextBounds(str, 0, str.length(), rect);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(rect.left + rect.width() + i, rect.bottom + rect.height() + i);
        layoutParams2.leftMargin = layoutParams.leftMargin;
        layoutParams2.topMargin = layoutParams.topMargin;
        this.ftvView.setLayoutParams(layoutParams2);
    }

    public void setShadowLayer(int i, int i2, int i3, int i4) {
        this.ftvView.setShadowLayer((float) i, (float) i2, (float) i3, i4);
    }

    @SuppressLint("WrongConstant")
    public void SetTextStyle(boolean z, boolean z2) {
        this.ftvView.getTypeface();
        if (z && z2) {
            AutoResizeTextView autoResizeTextView = this.ftvView;
            autoResizeTextView.setTypeface(autoResizeTextView.getTypeface(), 3);
        } else if (z) {
            AutoResizeTextView autoResizeTextView2 = this.ftvView;
            autoResizeTextView2.setTypeface(autoResizeTextView2.getTypeface(), 1);
        } else if (z2) {
            AutoResizeTextView autoResizeTextView3 = this.ftvView;
            autoResizeTextView3.setTypeface(autoResizeTextView3.getTypeface(), 2);
        } else {
            AutoResizeTextView autoResizeTextView4 = this.ftvView;
            autoResizeTextView4.setTypeface(autoResizeTextView4.getTypeface(), 0);
        }
    }

    public void SetTextUnderline(boolean z) {
        if (z) {
            AutoResizeTextView autoResizeTextView = this.ftvView;
            autoResizeTextView.setPaintFlags(autoResizeTextView.getPaintFlags() | 8);
            return;
        }
        AutoResizeTextView autoResizeTextView2 = this.ftvView;
        autoResizeTextView2.setPaintFlags(autoResizeTextView2.getPaintFlags() & -9);
    }

    public void SetTextAlignmentLeft() {
        this.ftvView.setGravity(3);
    }

    public void SetTextAlignmentRight() {
        this.ftvView.setGravity(5);
    }

    public void SetTextAlignmentCenter() {
        this.ftvView.setGravity(17);
    }

    public void SetTextTexture(Shader shader) {
        this.ftvView.getPaint().setShader(shader);
    }

    public void RemoveTextTexture() {
        this.ftvView.getPaint().setShader(new Shader());
    }

    public void SetTextFontStyle(Typeface typeface) {
        this.ftvView.setTypeface(typeface);
    }

    public void SetTextString(String str) {
        this.ftvView.setText(str);
    }

    @SuppressLint("WrongConstant")
    public void SetTextString(String str, String str2) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.ftvView.getLayoutParams();
        float textSize = this.ftvView.getPaint().getTextSize();
        this.ftvView.setText(str);
        this.ftvView.setPadding(20, 20, 30, 20);
        Rect rect = new Rect();
        TextPaint paint = this.ftvView.getPaint();
        paint.setTextSize(textSize);
        paint.getTextBounds(str2, 0, str2.length(), rect);
        int width = rect.left + rect.width();
        paint.getTextBounds(str, 0, str.length(), rect);
        int height = rect.bottom + rect.height();
        this.ftvView.measure(0, 0);
        this.ftvView.measure(View.MeasureSpec.makeMeasureSpec(width, 0), View.MeasureSpec.makeMeasureSpec(height, 0));
        int measuredWidth = this.ftvView.getMeasuredWidth();
        int measuredHeight = this.ftvView.getMeasuredHeight();
        layoutParams.width = measuredWidth;
        layoutParams.height = measuredHeight;
        layoutParams.leftMargin = layoutParams.leftMargin;
        layoutParams.topMargin = layoutParams.topMargin;
        this.ftvView.setLayoutParams(layoutParams);
        this.f132h.postDelayed(this.f133r, 100);
        invalidate();
    }

    public void setLayoutParmasExistView(FrameLayout.LayoutParams layoutParams, float f) {
        this.ftvView.setLayoutParams(layoutParams);
        this.ftvView.setRotation(f);
        this.f132h.postDelayed(this.f133r, 100);
    }

    public void SetRotation(float f) {
        this.ftvView.setRotation(f);
        this.f132h.postDelayed(this.f133r, 100);
    }

    public void SetRotationX(float f) {
        this.ftvView.setRotationX(f);
        this.f132h.postDelayed(this.f133r, 100);
    }

    public void SetRotationY(float f) {
        this.ftvView.setRotationY(f);
        this.f132h.postDelayed(this.f133r, 100);
    }

    public void setLayoutParmasExistView(FrameLayout.LayoutParams layoutParams) {
        this.ftvView.setLayoutParams(layoutParams);
      //  setPushButtons();
    }

    @SuppressLint("WrongConstant")
    public void HideBorderView() {
        this.mPushView.setVisibility(8);
        this.mDeleteView.setVisibility(8);
        this.mTopView.setVisibility(8);
        this.ftvView.setBackgroundColor(0);
    }

    @SuppressLint("WrongConstant")
    public void ShowBorderView(int i) {
        if (this.mPushView.getVisibility() == 8) {
            this.ftvView.setBackgroundResource(i);
            this.mPushView.setVisibility(0);
            this.mDeleteView.setVisibility(0);
            this.mTopView.setVisibility(0);
        }
    }

    private void setInitParams(String str) {
        Rect rect = new Rect();
        TextPaint paint = this.ftvView.getPaint();
        paint.setTextSize(50.0f);
        paint.getTextBounds(str, 0, str.length(), rect);
        int height = rect.bottom + rect.height() + 50;
        int width = rect.left + rect.width() + 100;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        layoutParams.leftMargin = 100;
        layoutParams.topMargin = 100;
        this.ftvView.setLayoutParams(layoutParams);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mPushView.getLayoutParams();
        layoutParams2.leftMargin = (layoutParams.leftMargin + width) - 24;
        layoutParams2.topMargin = (layoutParams.topMargin + height) - 24;
        this.mPushView.setLayoutParams(layoutParams2);
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mDeleteView.getLayoutParams();
        layoutParams3.leftMargin = layoutParams.leftMargin - 24;
        layoutParams3.topMargin = (layoutParams.topMargin + height) - 24;
        this.mDeleteView.setLayoutParams(layoutParams3);
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) this.mTopView.getLayoutParams();
        layoutParams4.leftMargin = layoutParams.leftMargin - 24;
        layoutParams4.topMargin = layoutParams.topMargin - 24;
        this.mTopView.setLayoutParams(layoutParams4);
    }

//    private void refreshImageCenter() {
//        this.mViewCenter = new Point((float) (this.ftvView.getLeft() + (this.ftvView.getWidth() / 2)), (float) (this.ftvView.getTop() + (this.ftvView.getHeight() / 2)));
//    }

    /* access modifiers changed from: private */
//    public void setPushButtons() {
//        refreshImageCenter();
//        Point point = this.mViewCenter;
//        float rotation = this.ftvView.getRotation();
//        Point anglePoint = getAnglePoint(point, new Point((float) (this.ftvView.getLeft() + this.ftvView.getWidth()), (float) (this.ftvView.getTop() + this.ftvView.getHeight())), rotation);
//        LayoutParams layoutParams = (LayoutParams) this.mPushView.getLayoutParams();
//        layoutParams.leftMargin = (int) (anglePoint.x - 20.0f);
//        layoutParams.topMargin = (int) (anglePoint.y - 20.0f);
//        this.mPushView.setLayoutParams(layoutParams);
//        Point anglePoint2 = getAnglePoint(point, new Point((float) this.ftvView.getLeft(), (float) (this.ftvView.getTop() + this.ftvView.getHeight())), rotation);
//        LayoutParams layoutParams2 = (LayoutParams) this.mDeleteView.getLayoutParams();
//        layoutParams2.leftMargin = (int) (anglePoint2.x - 20.0f);
//        layoutParams2.topMargin = (int) (anglePoint2.y - 10.0f);
//        this.mDeleteView.setLayoutParams(layoutParams2);
//        Point anglePoint3 = getAnglePoint(point, new Point((float) this.ftvView.getLeft(), (float) (this.ftvView.getTop() + this.ftvView.getHeight())), rotation);
//        LayoutParams layoutParams3 = (LayoutParams) this.mTopView.getLayoutParams();
//        layoutParams3.leftMargin = (int) (anglePoint3.x - 20.0f);
//        layoutParams3.topMargin = (int) (anglePoint3.y / 3.0f);
//        this.mTopView.setLayoutParams(layoutParams3);
//    }

    private float getDistance(Point point, Point point2) {
        return ((float) ((int) (Math.sqrt((double) (((point.x - point2.x) * (point.x - point2.x)) + ((point.y - point2.y) * (point.y - point2.y)))) * 100.0d))) / 100.0f;
    }

    private Point getAnglePoint(Point point, Point point2, float f) {
        float distance = getDistance(point, point2);
        double d = (double) f;
        Double.isNaN(d);
        double d2 = (d * f131PI) / 180.0d;
        double acos = Math.acos((double) ((point2.x - point.x) / distance));
        double d3 = (double) point.x;
        double d4 = (double) distance;
        double cos = Math.cos(acos + d2);
        Double.isNaN(d4);
        Double.isNaN(d3);
        double acos2 = Math.acos((double) ((point2.x - point.x) / distance));
        double d5 = (double) point.y;
        double sin = Math.sin(d2 + acos2);
        Double.isNaN(d4);
        Double.isNaN(d5);
        return new Point();
    }

    public void intializeview(int i, int i2, int i3, int i4, int i5) {
        setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        FrameLayout frameLayout = new FrameLayout(this.fcontext);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.ftvView = new AutoResizeTextView(this.fcontext, this.listener);
        this.ftvView.setText(this.hinttext);
        this.ftvView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.ftvView.setTextSize(0, 1080.0f);
        this.ftvView.setBackgroundResource(i4);
        this.ftvView.setGravity(17);
        this.ftvView.setClickable(true);
        this.ftvView.setFocusable(true);
        this.ftvView.setFocusableInTouchMode(true);
        frameLayout.addView(this.ftvView);
        this.mPushView = new ImageView(this.fcontext);
        this.mPushView.setLayoutParams(new FrameLayout.LayoutParams(i5, i5, 51));
        this.mPushView.setBackgroundResource(i);
        this.mPushView.setClickable(true);
        this.mPushView.setFocusable(true);
        this.mPushView.setFocusableInTouchMode(true);
        frameLayout.addView(this.mPushView);
        this.mDeleteView = new ImageView(this.fcontext);
        this.mDeleteView.setLayoutParams(new FrameLayout.LayoutParams(i5, i5, 51));
        this.mDeleteView.setBackgroundResource(i2);
        this.mDeleteView.setClickable(true);
        this.mDeleteView.setFocusable(true);
        this.mDeleteView.setFocusableInTouchMode(true);
        frameLayout.addView(this.mDeleteView);
        this.mTopView = new ImageView(this.fcontext);
        this.mTopView.setLayoutParams(new FrameLayout.LayoutParams(i5, i5, 51));
        this.mTopView.setClickable(true);
        this.mTopView.setFocusable(true);
        this.mTopView.setFocusableInTouchMode(true);
        frameLayout.addView(this.mTopView);
        setInitParams(this.hinttext);
        this.onTouchView = new VListener(this.fcontext, this.listener, this.mPushView, this.mDeleteView, this.mTopView);
        this.ftvView.setOnTouchListener(this.onTouchView);
        this.onPushTouch = new PListener(this.ftvView, this.mTopView, this.mDeleteView, this.listener);
        this.mPushView.setOnTouchListener(this.onPushTouch);
        this.mDeleteView.setOnTouchListener(new DListener(this.listener));
        this.mTopView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        addView(frameLayout);
        this.listener.onAddViewListener(this);
    }

    @SuppressLint("WrongConstant")
    public void visibilityGone() {
        setVisibility(8);
    }

    @SuppressLint("WrongConstant")
    public void visibilityShow() {
        setVisibility(0);
    }

    public float getTextSize() {
        return this.ftvView.getTextSize();
    }

    public void setTextSize(float f) {
        this.ftvView.setTextSize(f);
    }
}

