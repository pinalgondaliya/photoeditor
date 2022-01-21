package com.example.photoeditor;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.TextView;

public class AutoResizeTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final int NO_LINE_LIMIT = -1;
    private final RectF _availableSpaceRect;
    private boolean _initialized;
    private int _maxLines;
    private float _maxTextSize;
    private float _minTextSize;
    /* access modifiers changed from: private */
    public TextPaint _paint;
    private final SizeTester _sizeTester;
    /* access modifiers changed from: private */
    public float _spacingAdd;
    /* access modifiers changed from: private */
    public float _spacingMult;
    /* access modifiers changed from: private */
    public int _widthLimit;
    TextManagerListener tlistener;

    private interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    public AutoResizeTextView(Context context, TextManagerListener textManagerListener) {
        this(context, (AttributeSet) null, 0);
        this.tlistener = textManagerListener;
    }

    public AutoResizeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AutoResizeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this._availableSpaceRect = new RectF();
        this._spacingMult = 1.0f;
        this._spacingAdd = 0.0f;
        this._initialized = false;
        this._minTextSize = TypedValue.applyDimension(2, 2.0f, getResources().getDisplayMetrics());
        this._maxTextSize = getTextSize();
        this._paint = new TextPaint(getPaint());
        if (this._maxLines == 0) {
            this._maxLines = -1;
        }
        this._sizeTester = new SizeTester() {
            final RectF textRect = new RectF();

            public int onTestSize(int i, RectF rectF) {
                String str;
                AutoResizeTextView.this._paint.setTextSize((float) i);
                TransformationMethod transformationMethod = AutoResizeTextView.this.getTransformationMethod();
                if (transformationMethod != null) {
                    str = transformationMethod.getTransformation(AutoResizeTextView.this.getText(), AutoResizeTextView.this).toString();
                } else {
                    str = AutoResizeTextView.this.getText().toString();
                }
                String str2 = str;
                if (AutoResizeTextView.this.getMaxLines() == 1) {
                    this.textRect.bottom = AutoResizeTextView.this._paint.getFontSpacing();
                    this.textRect.right = AutoResizeTextView.this._paint.measureText(str2);
                } else {
                    StaticLayout staticLayout = new StaticLayout(str2, AutoResizeTextView.this._paint, AutoResizeTextView.this._widthLimit, Layout.Alignment.ALIGN_NORMAL, AutoResizeTextView.this._spacingMult, AutoResizeTextView.this._spacingAdd, true);
                    if (AutoResizeTextView.this.getMaxLines() != -1 && staticLayout.getLineCount() > AutoResizeTextView.this.getMaxLines()) {
                        return 1;
                    }
                    this.textRect.bottom = (float) staticLayout.getHeight();
                    int i2 = -1;
                    for (int i3 = 0; i3 < staticLayout.getLineCount(); i3++) {
                        if (((float) i2) < staticLayout.getLineRight(i3) - staticLayout.getLineLeft(i3)) {
                            i2 = ((int) staticLayout.getLineRight(i3)) - ((int) staticLayout.getLineLeft(i3));
                        }
                    }
                    this.textRect.right = (float) i2;
                }
                this.textRect.offsetTo(0.0f, 0.0f);
                return rectF.contains(this.textRect) ? -1 : 1;
            }
        };
        this._initialized = true;
    }

    public void setAllCaps(boolean z) {
        super.setAllCaps(z);
        callAdjustTextsize();
    }

    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        callAdjustTextsize();
    }

    public void setTextSize(float f) {
        this._maxTextSize = f;
        callAdjustTextsize();
    }

    public void setMaxLines(int i) {
        super.setMaxLines(i);
        this._maxLines = i;
        callAdjustTextsize();
    }

    public int getMaxLines() {
        return this._maxLines;
    }

    public void setSingleLine() {
        super.setSingleLine();
        this._maxLines = 1;
    }

    /* access modifiers changed from: package-private */
    public void callAdjustTextsize() {
        adjustTextSize();
        TextManagerListener textManagerListener = this.tlistener;
        if (textManagerListener != null) {
            textManagerListener.onLayoutParamsTvModified((FrameLayout.LayoutParams) getLayoutParams());
        }
    }

    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
        if (z) {
            this._maxLines = 1;
        } else {
            this._maxLines = -1;
        }
        callAdjustTextsize();
    }

    public void setLines(int i) {
        super.setLines(i);
        this._maxLines = i;
        callAdjustTextsize();
    }

    public void setTextSize(int i, float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        this._maxTextSize = TypedValue.applyDimension(i, f, resources.getDisplayMetrics());
        callAdjustTextsize();
    }

    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this._spacingMult = f2;
        this._spacingAdd = f;
    }

    public void setMinTextSize(float f) {
        this._minTextSize = f;
        callAdjustTextsize();
    }

    private void adjustTextSize() {
        if (this._initialized) {
            int i = (int) this._minTextSize;
            int measuredHeight = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            this._widthLimit = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            if (this._widthLimit > 0) {
                this._paint = new TextPaint(getPaint());
                RectF rectF = this._availableSpaceRect;
                rectF.right = (float) this._widthLimit;
                rectF.bottom = (float) measuredHeight;
                superSetTextSize(i);
            }
        }
    }

    private void superSetTextSize(int i) {
        super.setTextSize(0, (float) binarySearch(i, (int) this._maxTextSize, this._sizeTester, this._availableSpaceRect));
    }

    private int binarySearch(int i, int i2, SizeTester sizeTester, RectF rectF) {
        int i3 = i2 - 1;
        int i4 = i;
        while (i <= i3) {
            i4 = (i + i3) >>> 1;
            int onTestSize = sizeTester.onTestSize(i4, rectF);
            if (onTestSize >= 0) {
                if (onTestSize <= 0) {
                    break;
                }
                i4--;
                i3 = i4;
            } else {
                int i5 = i4 + 1;
                i4 = i;
                i = i5;
            }
        }
        return i4;
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        callAdjustTextsize();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            callAdjustTextsize();
        }
    }
}
