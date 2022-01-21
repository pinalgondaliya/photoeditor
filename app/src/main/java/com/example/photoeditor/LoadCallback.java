package com.example.photoeditor;

public interface LoadCallback extends Callback, com.isseiaoki.simplecropview.callback.LoadCallback {
    void onError();

    void onSuccess();

    @Override
    void onError(Throwable e);
}
