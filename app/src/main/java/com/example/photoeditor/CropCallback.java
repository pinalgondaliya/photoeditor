package com.example.photoeditor;

import android.graphics.Bitmap;

public interface CropCallback extends Callback, com.isseiaoki.simplecropview.callback.CropCallback {
    void onError();

    void onSuccess(Bitmap bitmap);

    @Override
    void onError(Throwable e);
}
