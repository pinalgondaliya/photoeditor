package com.example.photoeditor;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class AdjustActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    Bitmap bit;
    ImageView bright_close;
    SeekBar bright_seek_bar;
    ImageView brightness;
    ImageView btnBack;
    ImageView btnNext;
    ImageView contrast;
    FrameLayout fl_edit;
    ImageView image_edit;
    LightenEffect lightenEffect;

    /* renamed from: no */
    int f139no;
    LinearLayout rl_bightness;
    ImageView saturation;
    ImageView sharpen;

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_adjust);
        findviewByID();
        this.bit = Utils.imageHolder;
        Utils.tempBitmap = this.bit;
        new Handler().postDelayed(new Runnable() {
            @SuppressLint("WrongConstant")
            public void run() {
                Bitmap bitmapResize = AdjustActivity.this.bitmapResize();
                AdjustActivity.this.fl_edit.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                AdjustActivity.this.image_edit.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                AdjustActivity.this.image_edit.setImageBitmap(bitmapResize);
                AdjustActivity.this.image_edit.setAlpha(0.0f);
                AdjustActivity.this.image_edit.setVisibility(0);
                AdjustActivity.this.image_edit.animate().alpha(1.0f).setDuration(1000).start();
            }
        }, 500);
    }

    @SuppressLint("WrongConstant")
    public void findviewByID() {
        this.rl_bightness = (LinearLayout) findViewById(R.id.rl_bightness);
        this.rl_bightness.setVisibility(8);
        this.bright_close = (ImageView) findViewById(R.id.bright_close);
        this.fl_edit = (FrameLayout) findViewById(R.id.fl_edit);
        this.image_edit = (ImageView) findViewById(R.id.image_edit);
        this.btnNext = (ImageView) findViewById(R.id.btnNext);
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.brightness = (ImageView) findViewById(R.id.brightness);
        this.contrast = (ImageView) findViewById(R.id.contrast);
        this.saturation = (ImageView) findViewById(R.id.saturation);
        this.sharpen = (ImageView) findViewById(R.id.sharpen);
        this.bright_seek_bar = (SeekBar) findViewById(R.id.bright_seek_bar);
        this.brightness.setOnClickListener(this);
        this.contrast.setOnClickListener(this);
        this.saturation.setOnClickListener(this);
        this.sharpen.setOnClickListener(this);
        this.btnNext.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
        this.bright_close.setOnClickListener(this);
        this.bright_seek_bar.setOnSeekBarChangeListener(this);
    }

    public Bitmap getFrameBitmap() {
        this.fl_edit.postInvalidate();
        this.fl_edit.setDrawingCacheEnabled(true);
        this.fl_edit.buildDrawingCache();
        Bitmap createBitmap = Bitmap.createBitmap(this.fl_edit.getDrawingCache());
        this.fl_edit.destroyDrawingCache();
        return createBitmap;
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

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bright_close /*2131296362*/:
                this.rl_bightness.setVisibility(8);
                return;
            case R.id.brightness /*2131296364*/:
                this.rl_bightness.setVisibility(0);
                this.f139no = 1;
                this.bright_seek_bar.setMax(100);
                this.bright_seek_bar.setProgress(50);
                this.lightenEffect = new LightenEffect(this);
                return;
            case R.id.btnBack /*2131296371*/:
                onBackPressed();
                return;
            case R.id.btnNext /*2131296383*/:
              next();
             return;
            case R.id.contrast /*2131296440*/:
                this.rl_bightness.setVisibility(0);
                this.f139no = 2;
                this.bright_seek_bar.setMax(100);
                this.bright_seek_bar.setProgress(50);
                this.lightenEffect = new LightenEffect(this);
                return;
            case R.id.saturation /*2131296647*/:
                this.rl_bightness.setVisibility(0);
                this.f139no = 3;
                this.bright_seek_bar.setMax(512);
                this.bright_seek_bar.setProgress(256);
                this.lightenEffect = new LightenEffect(this);
                return;
            case R.id.sharpen /*2131296667*/:
                this.rl_bightness.setVisibility(0);
                this.f139no = 4;
                this.bright_seek_bar.setMax(100);
                this.bright_seek_bar.setProgress(50);
                this.lightenEffect = new LightenEffect(this);
                return;
            default:
                return;
        }
    }

    private void next() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Utils.saveBitmap = AdjustActivity.this.getFrameBitmap();
                AdjustActivity.this.setResult(-1, new Intent ());
                AdjustActivity.this.finish();
            }
        }, 500);
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        new dolightenBg(seekBar.getProgress()).execute(new Void[0]);
    }

    private class dolightenBg extends AsyncTask<Void, Void, Bitmap> {

        /* renamed from: p */
        int f140p;

        /* renamed from: pd */
        ProgressDialog f141pd;

        public dolightenBg(int i) {
            this.f140p = i;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            this.f141pd = new ProgressDialog(AdjustActivity.this);
            this.f141pd.setMessage("Loading...");
            this.f141pd.setCanceledOnTouchOutside(false);
            this.f141pd.setCancelable(false);
            this.f141pd.show();
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            AdjustActivity.this.setLightenonSeekBar(this.f140p);
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            AdjustActivity.this.image_edit.setImageBitmap(Utils.tempBitmap);
            this.f141pd.dismiss();
        }
    }

    public void setLightenonSeekBar(int i) {
        int i2 = this.f139no;
        if (i2 == 1) {
            Utils.tempBitmap = this.lightenEffect.doBrightness(i);
        } else if (i2 == 2) {
            Utils.tempBitmap = this.lightenEffect.adjustedContrast((double) i);
        } else if (i2 == 3) {
            Utils.tempBitmap = this.lightenEffect.setSaturation(((float) i) / 256.0f);
        } else if (i2 == 4) {
            Utils.tempBitmap = this.lightenEffect.sharpenImage((double) i);
        }
    }

}

