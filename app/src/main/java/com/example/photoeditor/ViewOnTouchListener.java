package com.example.photoeditor;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


class ViewOnTouchListener implements View.OnTouchListener {
    int lastImgLeft;
    int lastImgTop;
    int lastPushBtnLeft;
    int lastPushBtnTop;
    int lastPushDeleteLeft;
    int lastPushDeleteTop;
    private View mPushDelete;
    private View mPushView;
    FrameLayout.LayoutParams pushBtnLP;
    FrameLayout.LayoutParams pushDELETELP;
    Point pushPoint;
    FrameLayout.LayoutParams viewLP;

    ViewOnTouchListener(View view, View view2) {
        this.mPushView = view;
        this.mPushDelete = view2;
    }

    @SuppressLint("WrongConstant")
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            for (int i = 0; i < EditorActivity.viewsList.size(); i++) {
                EditorActivity.viewsList.get(i).hidePushView();
            }
            EditorActivity.selectedPos = Integer.parseInt("" + ((SingleFingerView) view.getParent().getParent()).getTag());
            this.mPushView.setVisibility(0);
            this.mPushDelete.setVisibility(0);
            if (this.viewLP == null) {
                this.viewLP = (FrameLayout.LayoutParams) view.getLayoutParams();
            }
            if (this.pushBtnLP == null) {
                this.pushBtnLP = (FrameLayout.LayoutParams) this.mPushView.getLayoutParams();
                this.pushDELETELP = (FrameLayout.LayoutParams) this.mPushDelete.getLayoutParams();
            }
            this.pushPoint = getRawPoint(motionEvent);
            this.lastImgLeft = this.viewLP.leftMargin;
            this.lastImgTop = this.viewLP.topMargin;
            this.lastPushBtnLeft = this.pushBtnLP.leftMargin;
            this.lastPushBtnTop = this.pushBtnLP.topMargin;
            this.lastPushDeleteLeft = this.pushDELETELP.leftMargin;
            this.lastPushDeleteTop = this.pushDELETELP.topMargin;
        } else if (action == 2) {
            Point rawPoint = getRawPoint(motionEvent);
            int f = rawPoint.x - this.pushPoint.x;
            int f2 = rawPoint.y - this.pushPoint.y;
            FrameLayout.LayoutParams layoutParams = this.viewLP;
            layoutParams.leftMargin = (int) (((int) this.lastImgLeft) + f);
            layoutParams.topMargin = (int) (((int) this.lastImgTop) + f2);
            view.setLayoutParams(layoutParams);
            FrameLayout.LayoutParams layoutParams2 = this.pushBtnLP;
            layoutParams2.leftMargin = (int) (((int) this.lastPushBtnLeft) + f);
            layoutParams2.topMargin = (int) (((int) this.lastPushBtnTop) + f2);
            this.mPushView.setLayoutParams(layoutParams2);
            FrameLayout.LayoutParams layoutParams3 = this.pushDELETELP;
            layoutParams3.leftMargin = (int) (((int) this.lastPushDeleteLeft) + f);
            layoutParams3.topMargin = (int) (((int) this.lastPushDeleteTop) + f2);
            this.mPushDelete.setLayoutParams(layoutParams3);
        }
        return false;
    }

    private Point getRawPoint(MotionEvent motionEvent) {
        return new Point((int) ((int) motionEvent.getRawX()), (int) ((int) motionEvent.getRawY()));
    }
}
