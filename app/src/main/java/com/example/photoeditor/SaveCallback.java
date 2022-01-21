package com.example.photoeditor;

import android.net.Uri;

public interface SaveCallback extends Callback, com.isseiaoki.simplecropview.callback.SaveCallback {
    void onError();

    void onSuccess(Uri uri);

    @Override
    void onError(Throwable e);
}
