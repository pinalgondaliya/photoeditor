package com.example.photoeditor;

import android.view.MotionEvent;
import android.view.View;

public class DListener implements View.OnTouchListener {
    TextManagerListener tListener;

    public DListener(TextManagerListener textManagerListener) {
        this.tListener = textManagerListener;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            return false;
        }
        if (action != 1) {
            return action != 5 ? false : false;
        }
        this.tListener.onTextDeleteTapListener();
        return false;
    }
}

