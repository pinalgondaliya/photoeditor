package com.example.photoeditor;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class OrientationActivity extends Activity implements View.OnClickListener {
    AllEffects all;
    Bitmap bit;
    ImageView btnBack;
    ImageView btnNext;
    FrameLayout fl_edit;
    ImageView flip_horizontal;
    ImageView flip_vertical;
    ImageView image_edit;
    ImageView image_rotate_left;
    ImageView image_rotate_right;


    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_orientation);
        findviewByID();
        this.bit = Utils.imageHolder;
        this.all.setBitmap(this.bit);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Bitmap bitmapResize = OrientationActivity.this.bitmapResize();
                OrientationActivity.this.fl_edit.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                OrientationActivity.this.image_edit.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                OrientationActivity.this.image_edit.setImageBitmap(OrientationActivity.this.bit);
            }
        }, 500);
    }

    public void findviewByID() {
        this.all = new AllEffects(this);
        this.image_rotate_left = (ImageView) findViewById(R.id.image_rotate_left);
        this.image_rotate_right = (ImageView) findViewById(R.id.image_rotate_right);
        this.flip_horizontal = (ImageView) findViewById(R.id.flip_horizontal);
        this.flip_vertical = (ImageView) findViewById(R.id.flip_vertical);
        this.fl_edit = (FrameLayout) findViewById(R.id.fl_edit);
        this.image_edit = (ImageView) findViewById(R.id.image_edit);
        this.btnNext = (ImageView) findViewById(R.id.btnNext);
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.image_rotate_left.setOnClickListener(this);
        this.image_rotate_right.setOnClickListener(this);
        this.flip_horizontal.setOnClickListener(this);
        this.flip_vertical.setOnClickListener(this);
        this.btnNext.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
    }

    public void next() {
        Utils.saveBitmap = getFrameBitmap();
        setResult(-1, new Intent());
        finish();
    }

    public Bitmap getFrameBitmap() {
        this.fl_edit.postInvalidate();
        this.fl_edit.setDrawingCacheEnabled(true);
        this.fl_edit.buildDrawingCache();
        Bitmap createBitmap = Bitmap.createBitmap(this.fl_edit.getDrawingCache());
        this.fl_edit.destroyDrawingCache();
        return createBitmap;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack /*2131296371*/:
                onBackPressed();
                return;
            case R.id.btnNext /*2131296383*/:
                next();
                return;
            case R.id.flip_horizontal /*2131296476*/:
                if (this.all.getV_flip()) {
                    this.all.setV_flip(false);
                } else {
                    this.all.setV_flip(true);
                }
                this.image_edit.setImageBitmap(this.all.AllEffects());
                return;
            case R.id.flip_vertical /*2131296477*/:
                if (this.all.getH_flip()) {
                    this.all.setH_flip(false);
                } else {
                    this.all.setH_flip(true);
                }
                this.image_edit.setImageBitmap(this.all.AllEffects());
                return;
            case R.id.image_rotate_left /*2131296513*/:
                this.image_edit.getRotation();
                ImageView imageView = this.image_edit;
                imageView.setRotation(imageView.getRotation() - 90.0f);
                return;
            case R.id.image_rotate_right /*2131296514*/:
                ImageView imageView2 = this.image_edit;
                imageView2.setRotation(imageView2.getRotation() + 90.0f);
                return;
            default:
                return;
        }
    }

    public Bitmap bitmapResize() {
        int width = this.fl_edit.getWidth();
        int height = this.fl_edit.getHeight();
        int width2 = this.bit.getWidth();
        int height2 = this.bit.getHeight();
        if (width2 >= height2) {
            int i = (height2 * width) / width2;
            if (i > height) {
                width = (width * height) / i;
            } else {
                height = i;
            }
        } else {
            int i2 = (width2 * height) / height2;
            if (i2 > width) {
                height = (height * width) / i2;
            } else {
                width = i2;
            }
        }
        return Bitmap.createScaledBitmap(this.bit, width, height, true);
    }

}

