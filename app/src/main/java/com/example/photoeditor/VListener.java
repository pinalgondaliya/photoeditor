package com.example.photoeditor;
import android.content.Context;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class VListener implements View.OnTouchListener {
    GestureDetector detector;
    int lastImgLeft;
    int lastImgTop;
    int lastPushBtnLeft;
    int lastPushBtnLeft1;
    int lastPushBtnLeft2;
    int lastPushBtnTop;
    int lastPushBtnTop1;
    int lastPushBtnTop2;
    private View mDeleteView;
    private View mPushView;
    private View mTopView;
    FrameLayout.LayoutParams pushBtnLP;
    FrameLayout.LayoutParams pushBtnLP1;
    FrameLayout.LayoutParams pushBtnLP2;
    Point pushPoint;
    TextManagerListener tListener;
    FrameLayout.LayoutParams viewLP;

    VListener(Context context, TextManagerListener textManagerListener, View view, View view2, View view3) {
        this.mPushView = view;
        this.mDeleteView = view2;
        this.mTopView = view3;
        this.tListener = textManagerListener;
        this.detector = new GestureDetector(context, new GestureTap());
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.detector.onTouchEvent(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.tListener.onTapListener(view);
            if (this.viewLP == null) {
                this.viewLP = (FrameLayout.LayoutParams) view.getLayoutParams();
            }
            if (this.pushBtnLP == null) {
                this.pushBtnLP = (FrameLayout.LayoutParams) this.mPushView.getLayoutParams();
            }
            if (this.pushBtnLP1 == null) {
                this.pushBtnLP1 = (FrameLayout.LayoutParams) this.mDeleteView.getLayoutParams();
            }
            if (this.pushBtnLP2 == null) {
                this.pushBtnLP2 = (FrameLayout.LayoutParams) this.mTopView.getLayoutParams();
            }
            this.pushPoint = getRawPoint(motionEvent);
            this.lastImgLeft = this.viewLP.leftMargin;
            this.lastImgTop = this.viewLP.topMargin;
            this.lastPushBtnLeft = this.pushBtnLP.leftMargin;
            this.lastPushBtnTop = this.pushBtnLP.topMargin;
            this.lastPushBtnLeft1 = this.pushBtnLP1.leftMargin;
            this.lastPushBtnTop1 = this.pushBtnLP1.topMargin;
            this.lastPushBtnLeft2 = this.pushBtnLP2.leftMargin;
            this.lastPushBtnTop2 = this.pushBtnLP2.topMargin;
            return false;
        } else if (action != 2) {
            return false;
        } else {
            Point rawPoint = getRawPoint(motionEvent);
            float f = rawPoint.x - this.pushPoint.x;
            float f2 = rawPoint.y - this.pushPoint.y;
            FrameLayout.LayoutParams layoutParams = this.viewLP;
            layoutParams.leftMargin = (int) (((float) this.lastImgLeft) + f);
            layoutParams.topMargin = (int) (((float) this.lastImgTop) + f2);
            view.setLayoutParams(layoutParams);
            FrameLayout.LayoutParams layoutParams2 = this.pushBtnLP;
            layoutParams2.leftMargin = (int) (((float) this.lastPushBtnLeft) + f);
            layoutParams2.topMargin = (int) (((float) this.lastPushBtnTop) + f2);
            this.mPushView.setLayoutParams(layoutParams2);
            FrameLayout.LayoutParams layoutParams3 = this.pushBtnLP1;
            layoutParams3.leftMargin = (int) (((float) this.lastPushBtnLeft1) + f);
            layoutParams3.topMargin = (int) (((float) this.lastPushBtnTop1) + f2);
            this.mDeleteView.setLayoutParams(layoutParams3);
            FrameLayout.LayoutParams layoutParams4 = this.pushBtnLP2;
            layoutParams4.leftMargin = (int) (((float) this.lastPushBtnLeft2) + f);
            layoutParams4.topMargin = (int) (((float) this.lastPushBtnTop2) + f2);
            this.mTopView.setLayoutParams(layoutParams4);
            return false;
        }
    }

    private Point getRawPoint(MotionEvent motionEvent) {
        return new Point((int) ((int) motionEvent.getRawX()), (int) ((int) motionEvent.getRawY()));
    }

    class GestureTap extends GestureDetector.SimpleOnGestureListener {
        GestureTap() {
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            VListener.this.tListener.onDoubleTapOnTextView();
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            VListener.this.tListener.onSingleTapOnTextView();
            return true;
        }
    }

    public void setView(View view, View view2, View view3) {
        this.mPushView = view;
        this.mDeleteView = view2;
        this.mTopView = view3;
    }
}
