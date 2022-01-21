package com.example.photoeditor;


import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

class PushBtnTouchListener implements View.OnTouchListener {

    /* renamed from: PI */
    private static final double f206PI = 3.14159265359d;
    private FrameLayout.LayoutParams imgLP;
    double lastComAngle;
    int lastImgAngle;
    int lastImgHeight;
    int lastImgLeft;
    int lastImgTop;
    int lastImgWidth;
    int lastPushBtnLeft;
    int lastPushBtnTop;
    int lastPushDELETELeft;
    int lastPushDELETETop;
    float lastX = -1.0f;
    float lastY = -1.0f;
    private View mView;
    private Point mViewCenter;
    private FrameLayout.LayoutParams pushBtnLP;
    private FrameLayout.LayoutParams pushDELETELP;
    int pushImgHeight;
    int pushImgWidth;
    Point pushPoint;

    public PushBtnTouchListener(View view) {
        this.mView = view;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.pushBtnLP = (FrameLayout.LayoutParams) view.getLayoutParams();
            this.imgLP = (FrameLayout.LayoutParams) this.mView.getLayoutParams();
            this.pushDELETELP = (FrameLayout.LayoutParams) ((SingleFingerView) view.getParent().getParent()).mPushDelete.getLayoutParams();
            this.pushPoint = getPushPoint(this.pushBtnLP, motionEvent);
            this.lastImgWidth = this.imgLP.width;
            this.lastImgHeight = this.imgLP.height;
            this.lastImgLeft = this.imgLP.leftMargin;
            this.lastImgTop = this.imgLP.topMargin;
            this.lastImgAngle = (int) this.mView.getRotation();
            this.lastPushBtnLeft = this.pushBtnLP.leftMargin;
            this.lastPushBtnTop = this.pushBtnLP.topMargin;
            this.lastPushDELETELeft = this.pushDELETELP.leftMargin;
            this.lastPushDELETETop = this.pushDELETELP.topMargin;
            this.pushImgWidth = this.pushBtnLP.width;
            this.pushImgHeight = this.pushBtnLP.height;
            this.lastX = motionEvent.getRawX();
            this.lastY = motionEvent.getRawY();
            refreshImageCenter();
        } else if (action != 1) {
            if (action == 2) {
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                float f = this.lastX;
                if (f != -1.0f && Math.abs(rawX - f) < 5.0f && Math.abs(rawY - this.lastY) < 5.0f) {
                    return false;
                }
                this.lastX = rawX;
                this.lastY = rawY;
                Point point = this.mViewCenter;
                Point point2 = this.pushPoint;
                Point pushPoint2 = getPushPoint(this.pushBtnLP, motionEvent);
                float distance = getDistance(point, point2);
                float distance2 = getDistance(point, pushPoint2);
                float f2 = distance2 / distance;
                int i = this.lastImgWidth;
                int i2 = (int) (((float) i) * f2);
                int i3 = this.lastImgHeight;
                int i4 = (int) (((float) i3) * f2);
                FrameLayout.LayoutParams layoutParams = this.imgLP;
                layoutParams.leftMargin = this.lastImgLeft - ((i2 - i) / 2);
                layoutParams.topMargin = this.lastImgTop - ((i4 - i3) / 2);
                layoutParams.width = i2;
                layoutParams.height = i4;
                this.mView.setLayoutParams(layoutParams);
                double d = 180.0d;
                double acos = (Math.acos((double) ((((point2.x - point.x) * (pushPoint2.x - point.x)) + ((point2.y - point.y) * (pushPoint2.y - point.y))) / (distance * distance2))) * 180.0d) / f206PI;
                if (Double.isNaN(acos)) {
                    double d2 = this.lastComAngle;
                    if (d2 < 90.0d || d2 > 270.0d) {
                        d = 0.0d;
                    }
                    acos = d;
                } else if ((pushPoint2.y - point.y) * (point2.x - point.x) < (point2.y - point.y) * (pushPoint2.x - point.x)) {
                    acos = 360.0d - acos;
                }
                this.lastComAngle = acos;
                double d3 = (double) this.lastImgAngle;
                Double.isNaN(d3);
                float f3 = ((float) (d3 + acos)) % 360.0f;
                this.mView.setRotation(f3);
                Point anglePoint = getAnglePoint(point, new Point((int) (this.mView.getLeft() + this.mView.getWidth()), (int) (this.mView.getTop() + this.mView.getHeight())), f3);
                this.pushBtnLP.leftMargin = (int) (anglePoint.x - ((float) (this.pushImgWidth / 2)));
                this.pushBtnLP.topMargin = (int) (anglePoint.y - ((float) (this.pushImgHeight / 2)));
                this.pushDELETELP.leftMargin = this.mView.getRight();
                this.pushDELETELP.topMargin = this.mView.getTop();
                view.setLayoutParams(this.pushBtnLP);
                ((SingleFingerView) view.getParent().getParent()).mPushDelete.setLayoutParams(this.pushDELETELP);
            } else if (action != 5) {
            }
        }
        return false;
    }

    private void refreshImageCenter() {
        this.mViewCenter = new Point((int) (this.mView.getLeft() + (this.mView.getWidth() / 2)), (int) (this.mView.getTop() + (this.mView.getHeight() / 2)));
    }

    private Point getPushPoint(FrameLayout.LayoutParams layoutParams, MotionEvent motionEvent) {
        return new Point((int) (layoutParams.leftMargin + ((int) motionEvent.getX())), (int) (layoutParams.topMargin + ((int) motionEvent.getY())));
    }

    private float getDistance(Point point, Point point2) {
        return ((float) ((int) (Math.sqrt((double) (((point.x - point2.x) * (point.x - point2.x)) + ((point.y - point2.y) * (point.y - point2.y)))) * 100.0d))) / 100.0f;
    }

    private Point getAnglePoint(Point point, Point point2, float f) {
        float distance = getDistance(point, point2);
        double d = (double) f;
        Double.isNaN(d);
        double d2 = (d * f206PI) / 180.0d;
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
        return new Point((int) ((int) (d3 + (cos * d4))), (int) ((int) (d5 + (d4 * sin))));
    }
}

