package com.example.photoeditor;

public interface SimpleValueAnimator {
    void addAnimatorListener(SimpleValueAnimatorListener simpleValueAnimatorListener);

    void cancelAnimation();

    boolean isAnimationStarted();

    void startAnimation(long j);
}
