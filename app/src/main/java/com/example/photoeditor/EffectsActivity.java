package com.example.photoeditor;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EffectsActivity extends Activity implements View.OnClickListener {
    Animation Anim;
    View.OnClickListener ThumbDesignClick = new View.OnClickListener() {
        public void onClick(View view) {
            int intValue = ((Integer) view.getTag()).intValue();
            try {
                ImageLoader instance = ImageLoader.getInstance();
                Bitmap loadImageSync = instance.loadImageSync("assets://design/" + EffectsActivity.this.designlist.get(intValue));
                Canvas canvas = new Canvas(Bitmap.createBitmap(EffectsActivity.this.f151w, EffectsActivity.this.f151w, Bitmap.Config.ARGB_8888));
                Matrix matrix = new Matrix();
                matrix.setTranslate(0.0f, (float) (EffectsActivity.this.f151w % loadImageSync.getWidth()));
                canvas.setMatrix(matrix);
                Paint paint = new Paint();
                paint.setShader(new BitmapShader(loadImageSync, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
                canvas.drawPaint(paint);
                EffectsActivity.this.image_effect.setAlpha(0.5f);
                EffectsActivity.this.image_effect.setImageBitmap(loadImageSync);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    View.OnClickListener ThumbPaperClick = new View.OnClickListener() {
        public void onClick(View view) {
            int intValue = ((Integer) view.getTag()).intValue();
            ImageLoader instance = ImageLoader.getInstance();
            Bitmap loadImageSync = instance.loadImageSync("assets://paper/" + EffectsActivity.this.paperlist.get(intValue));
            Canvas canvas = new Canvas(Bitmap.createBitmap(EffectsActivity.this.f151w, EffectsActivity.this.f151w, Bitmap.Config.ARGB_8888));
            Matrix matrix = new Matrix();
            if (loadImageSync != null) {
                matrix.setTranslate(0.0f, (float) (EffectsActivity.this.f151w % loadImageSync.getWidth()));
            }
            canvas.setMatrix(matrix);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(loadImageSync, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            canvas.drawPaint(paint);
            EffectsActivity.this.image_effect.setAlpha(0.5f);
            EffectsActivity.this.image_effect.setImageBitmap(loadImageSync);
        }
    };
    Bitmap bit;
    ImageView btnBack;
    ImageView btnNext;
    ImageView design;
    LinearLayout designLayout;
    List<String> designlist;
    DisplayMetrics dis;
    FrameLayout fl_edit;

    /* renamed from: h */
    int f149h;
    private int hMinus;
    private int htpx;
    ImageView image_edit;
    ImageView image_effect;
    LinearLayout linearLayout;
    ArrayList<LinearLayout> ll_design = new ArrayList<>();
    ArrayList<LinearLayout> ll_paper = new ArrayList<>();
    ImageView paper;
    List<String> paperlist;

    /* renamed from: pd */
    ProgressDialog f150pd;
    int pos = 0;
    int pos1 = 0;
    RelativeLayout rl_design;
    RelativeLayout rl_paper;

    /* renamed from: w */
    int f151w;
    private int wdpx;

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            int i6 = i3 / 2;
            int i7 = i4 / 2;
            while (i6 / i5 >= i2 && i7 / i5 >= i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        String[] strArr;
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_effects);
        this.dis = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(this.dis);
        this.f149h = this.dis.heightPixels;
        this.f151w = this.dis.widthPixels;
        this.bit = Utils.imageHolder;
        this.wdpx = (int) TypedValue.applyDimension(1, (float) getResources().getInteger(R.integer.thumb_size), getResources().getDisplayMetrics());
        this.htpx = (int) TypedValue.applyDimension(1, (float) getResources().getInteger(R.integer.thumb_size), getResources().getDisplayMetrics());
        findviewByID();
        String[] strArr2 = null;
        try {
            strArr = getAssets().list("paper");
            try {
                strArr2 = getAssets().list("design");
            } catch (IOException e) {
                e = e;
                e.printStackTrace();
                this.paperlist = Arrays.asList(strArr);
                this.designlist = Arrays.asList(strArr2);
                new Handler().postDelayed(new Runnable() {
                    @SuppressLint("WrongConstant")
                    public void run() {
                        Bitmap bitmapResize = EffectsActivity.this.bitmapResize();
                        EffectsActivity.this.fl_edit.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                        EffectsActivity.this.image_edit.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                        EffectsActivity.this.image_effect.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                        EffectsActivity.this.image_edit.setImageBitmap(bitmapResize);
                        EffectsActivity.this.image_edit.setVisibility(0);
                    }
                }, 500);
            }
        } catch (IOException e2) {
            IOException e = e2;
            strArr = null;
            e.printStackTrace();
            this.paperlist = Arrays.asList(strArr);
            this.designlist = Arrays.asList(strArr2);
            new Handler().postDelayed(new Runnable() {
                @SuppressLint("WrongConstant")
                public void run() {
                    Bitmap bitmapResize = EffectsActivity.this.bitmapResize();
                    EffectsActivity.this.fl_edit.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                    EffectsActivity.this.image_edit.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                    EffectsActivity.this.image_effect.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                    EffectsActivity.this.image_edit.setImageBitmap(bitmapResize);
                    EffectsActivity.this.image_edit.setVisibility(0);
                }
            }, 500);
        }
        this.paperlist = Arrays.asList(strArr);
        this.designlist = Arrays.asList(strArr2);
        new Handler().postDelayed(new Runnable() {
            @SuppressLint("WrongConstant")
            public void run() {
                Bitmap bitmapResize = EffectsActivity.this.bitmapResize();
                EffectsActivity.this.fl_edit.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                EffectsActivity.this.image_edit.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                EffectsActivity.this.image_effect.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                EffectsActivity.this.image_edit.setImageBitmap(bitmapResize);
                EffectsActivity.this.image_edit.setVisibility(0);
            }
        }, 500);
    }

    @SuppressLint("WrongConstant")
    public void findviewByID() {
        this.rl_paper = (RelativeLayout) findViewById(R.id.rl_paper);
        this.rl_design = (RelativeLayout) findViewById(R.id.rl_design);
        this.rl_paper.setVisibility(8);
        this.rl_design.setVisibility(8);
        this.fl_edit = (FrameLayout) findViewById(R.id.fl_edit);
        this.image_edit = (ImageView) findViewById(R.id.image_edit);
        this.image_effect = (ImageView) findViewById(R.id.image_effect);
        this.paper = (ImageView) findViewById(R.id.paper);
        this.design = (ImageView) findViewById(R.id.design);
        this.linearLayout = (LinearLayout) findViewById(R.id.paper_layout);
        this.designLayout = (LinearLayout) findViewById(R.id.design_layout);
        this.btnNext = (ImageView) findViewById(R.id.btnNext);
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.paper.setOnClickListener(this);
        this.design.setOnClickListener(this);
        this.btnNext.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
    }

    public void next() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Utils.saveBitmap = EffectsActivity.this.getFrameBitmap();
                EffectsActivity.this.setResult(-1, new Intent());
                EffectsActivity.this.finish();
            }
        }, 500);
    }

    public Bitmap getFrameBitmap() {
        this.fl_edit.postInvalidate();
        this.fl_edit.setDrawingCacheEnabled(true);
        this.fl_edit.buildDrawingCache();
        Bitmap createBitmap = Bitmap.createBitmap(this.fl_edit.getDrawingCache());
        this.fl_edit.destroyDrawingCache();
        return createBitmap;
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack /*2131296371*/:
                onBackPressed();
                return;
            case R.id.btnNext /*2131296383*/:
                next();
                return;
            case R.id.design /*2131296448*/:
                if (this.rl_design.getVisibility() == 0) {
                    this.rl_design.setVisibility(8);
                    this.Anim = AnimationUtils.loadAnimation(this, R.anim.close_menu);
                    this.Anim.setDuration(200);
                    this.rl_design.startAnimation(this.Anim);
                } else {
                    this.designLayout.removeAllViews();
                    for (int i = 0; i < this.designlist.size(); i++) {
                        LinearLayout linearLayout2 = new LinearLayout(getApplicationContext());
                        int i2 = this.f151w > 720 ? 150 : 100;
                        int i3 = i2 + 3;
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i3, i3);
                        layoutParams.setMargins(2, 2, 2, 2);
                        linearLayout2.setLayoutParams(layoutParams);
                        linearLayout2.setGravity(17);
                        linearLayout2.setPadding(1, 1, 1, 1);
                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setLayoutParams(new ActionBar.LayoutParams(i2, i2));
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        try {
                            ImageLoader instance = ImageLoader.getInstance();
                            instance.displayImage("assets://design/" + this.designlist.get(i), imageView);
                            imageView.setTag(Integer.valueOf(i));
                            this.pos1 = this.pos1 + 1;
                            linearLayout2.addView(imageView);
                            imageView.setOnClickListener(this.ThumbDesignClick);
                            this.designLayout.addView(linearLayout2);
                            this.ll_design.add(linearLayout2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    this.rl_design.setVisibility(0);
                    this.Anim = AnimationUtils.loadAnimation(this, R.anim.open_menu);
                    this.Anim.setDuration(200);
                    this.rl_design.startAnimation(this.Anim);
                }
                this.rl_paper.setVisibility(8);
                return;
            case R.id.paper /*2131296604*/:
                if (this.rl_paper.getVisibility() == 0) {
                    this.rl_paper.setVisibility(8);
                    this.Anim = AnimationUtils.loadAnimation(this, R.anim.close_menu);
                    this.Anim.setDuration(200);
                    this.rl_paper.startAnimation(this.Anim);
                } else {
                    this.linearLayout.removeAllViews();
                    for (int i4 = 0; i4 < this.paperlist.size(); i4++) {
                        LinearLayout linearLayout3 = new LinearLayout(getApplicationContext());
                        int i5 = this.f151w > 720 ? 150 : 100;
                        int i6 = i5 + 3;
                        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i6, i6);
                        layoutParams2.setMargins(2, 2, 2, 2);
                        linearLayout3.setLayoutParams(layoutParams2);
                        linearLayout3.setGravity(17);
                        linearLayout3.setPadding(1, 1, 1, 1);
                        ImageView imageView2 = new ImageView(getApplicationContext());
                        imageView2.setLayoutParams(new ActionBar.LayoutParams(i5, i5));
                        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
                        try {
                            ImageLoader instance2 = ImageLoader.getInstance();
                            instance2.displayImage("assets://paper/" + this.paperlist.get(i4), imageView2);
                            imageView2.setTag(Integer.valueOf(i4));
                            this.pos1 = this.pos1 + 1;
                            linearLayout3.addView(imageView2);
                            imageView2.setOnClickListener(this.ThumbPaperClick);
                            this.linearLayout.addView(linearLayout3);
                            this.ll_paper.add(linearLayout3);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    this.rl_paper.setVisibility(0);
                    this.Anim = AnimationUtils.loadAnimation(this, R.anim.open_menu);
                    this.Anim.setDuration(200);
                    this.rl_paper.startAnimation(this.Anim);
                }
                this.rl_design.setVisibility(8);
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

    /* access modifiers changed from: private */
    public Bitmap getBitmapFromAsset(String str, String str2) throws IOException {
        AssetManager assets = getAssets();
        InputStream open = assets.open(str + "/" + str2);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(open, (Rect) null, options);
        int i = this.f151w > 720 ? ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION : 100;
        options.inSampleSize = calculateInSampleSize(options, i, i);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(open, (Rect) null, options);
    }


    private class addPaperThumbToHs extends AsyncTask<Void, Void, Bitmap> {
        public addPaperThumbToHs() {
            EffectsActivity.this.f150pd = new ProgressDialog(EffectsActivity.this);
            EffectsActivity.this.f150pd.setMessage("Loading...");
            EffectsActivity.this.f150pd.setCancelable(false);
            EffectsActivity.this.f150pd.show();
        }

        public void onPreExecute() {
            EffectsActivity.this.linearLayout.removeAllViewsInLayout();
            EffectsActivity.this.linearLayout.refreshDrawableState();
            EffectsActivity.this.ll_paper.clear();
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            EffectsActivity effectsActivity = EffectsActivity.this;
            effectsActivity.pos = 0;
            Log.d("paperlist123", String.valueOf(effectsActivity.paperlist.size()));
            for (int i = 0; i < EffectsActivity.this.paperlist.size(); i++) {
                EffectsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        LinearLayout linearLayout = new LinearLayout(EffectsActivity.this.getApplicationContext());
                        int i = EffectsActivity.this.f151w > 720 ? 150 : 100;
                        int i2 = i + 3;
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i2);
                        layoutParams.setMargins(2, 2, 2, 2);
                        linearLayout.setLayoutParams(layoutParams);
                        linearLayout.setGravity(17);
                        linearLayout.setPadding(1, 1, 1, 1);
                        ImageView imageView = new ImageView(EffectsActivity.this.getApplicationContext());
                        imageView.setLayoutParams(new ActionBar.LayoutParams(i, i));
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        try {
                            imageView.setImageBitmap(EffectsActivity.this.getBitmapFromAsset("paper", EffectsActivity.this.paperlist.get(EffectsActivity.this.pos)));
                            imageView.setTag(Integer.valueOf(EffectsActivity.this.pos));
                            EffectsActivity.this.pos++;
                            linearLayout.addView(imageView);
                            imageView.setOnClickListener(EffectsActivity.this.ThumbPaperClick);
                            EffectsActivity.this.linearLayout.addView(linearLayout);
                            EffectsActivity.this.ll_paper.add(linearLayout);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            EffectsActivity.this.f150pd.dismiss();
        }
    }

    private class addDesignThumbToHs extends AsyncTask<Void, Void, Bitmap> {
        public addDesignThumbToHs() {
            EffectsActivity.this.f150pd = new ProgressDialog(EffectsActivity.this);
            EffectsActivity.this.f150pd.setMessage("Loading...");
            EffectsActivity.this.f150pd.setCancelable(false);
            EffectsActivity.this.f150pd.show();
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            EffectsActivity.this.designLayout.removeAllViewsInLayout();
            EffectsActivity.this.designLayout.refreshDrawableState();
            EffectsActivity.this.ll_design.clear();
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            EffectsActivity.this.pos1 = 0;
            for (int i = 0; i < EffectsActivity.this.designlist.size(); i++) {
                EffectsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        LinearLayout linearLayout = new LinearLayout(EffectsActivity.this.getApplicationContext());
                        int i = EffectsActivity.this.f151w > 720 ? 150 : 100;
                        int i2 = i + 3;
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i2);
                        layoutParams.setMargins(2, 2, 2, 2);
                        linearLayout.setLayoutParams(layoutParams);
                        linearLayout.setGravity(17);
                        linearLayout.setPadding(1, 1, 1, 1);
                        ImageView imageView = new ImageView(EffectsActivity.this.getApplicationContext());
                        imageView.setLayoutParams(new ActionBar.LayoutParams(i, i));
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        try {
                            imageView.setImageBitmap(EffectsActivity.this.getBitmapFromAsset("design", EffectsActivity.this.designlist.get(EffectsActivity.this.pos1)));
                            imageView.setTag(Integer.valueOf(EffectsActivity.this.pos1));
                            EffectsActivity.this.pos1++;
                            linearLayout.addView(imageView);
                            imageView.setOnClickListener(EffectsActivity.this.ThumbDesignClick);
                            EffectsActivity.this.designLayout.addView(linearLayout);
                            EffectsActivity.this.ll_design.add(linearLayout);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            return null;
        }

        public void onPostExecute(Bitmap bitmap) {
            EffectsActivity.this.f150pd.dismiss();
        }
    }
}

