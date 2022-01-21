package com.example.photoeditor;
import android.view.View;
import android.widget.FrameLayout;

public interface TextManagerListener {
    void onAddViewListener(DragTextView dragTextView);

    void onDoubleTapOnTextView();

    void onLayoutParamsTvModified(FrameLayout.LayoutParams layoutParams);

    void onSingleTapOnTextView();

    boolean onSizeLessExceed(boolean z);

    void onTapListener(View view);

    void onTextDeleteTapListener();

    void onTextTopTapListener();
}
