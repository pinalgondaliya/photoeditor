package com.example.photoeditor;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import java.util.concurrent.locks.ReentrantLock;

final class ImageLoadingInfo {
    final ImageAware imageAware;
    final ImageLoadingListener listener;
    final ReentrantLock loadFromUriLock;
    final String memoryCacheKey;
    final com.nostra13.universalimageloader.core.DisplayImageOptions options;
    final ImageLoadingProgressListener progressListener;
    final ImageSize targetSize;
    final String uri;

    public ImageLoadingInfo(String str, ImageAware imageAware2, ImageSize imageSize, String str2, DisplayImageOptions displayImageOptions, ImageLoadingListener imageLoadingListener, ImageLoadingProgressListener imageLoadingProgressListener, ReentrantLock reentrantLock) {
        this.uri = str;
        this.imageAware = imageAware2;
        this.targetSize = imageSize;
        this.options = displayImageOptions;
        this.listener = imageLoadingListener;
        this.progressListener = imageLoadingProgressListener;
        this.loadFromUriLock = reentrantLock;
        this.memoryCacheKey = str2;
    }
}
