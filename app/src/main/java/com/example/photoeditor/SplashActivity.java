package com.example.photoeditor;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {

    /* renamed from: b */
    static Bitmap f183b;
    static Bitmap bmOut;
    static Bitmap outputBitmap;
    public static final int progress_bar_type = 0;
    static Bitmap scaledBitmap;
    Animation Anim;
    LinearLayout Include_sliderview;
    LinearLayout SliderView;
    View.OnClickListener ThumbColorClick = new View.OnClickListener() {
        public void onClick(View view) {
            int intValue = ((Integer) view.getTag()).intValue();
            SplashActivity.this.files1[intValue] = SplashActivity.this.f192ta.getColor(intValue, 0);
            SplashActivity splashActivity = SplashActivity.this;
            splashActivity.colPicker(splashActivity.files1[intValue]);
        }
    };
    ImageView back;
    Bitmap bit;
    public boolean blur_clicked = false;
    Bitmap bmp;

    /* renamed from: bs */
    Bitmap f184bs;

    /* renamed from: c */
    int f185c;
    int col;
    ImageView colorEffect;
    public boolean color_clicked = false;
    LinearLayout color_layout;
    ImageView don;

    /* renamed from: dv */
    DrawView f186dv;
    int[] files1;
    FrameLayout flEditor;
    int fl_height;
    int fl_width;
    FrameLayout fram;
    int getheightofview;
    ImageView gray;
    public boolean gry_clicked = false;
    public boolean gry_clicked_for_blur = false;

    /* renamed from: h */
    int f187h;

    /* renamed from: hh */
    int f188hh;
    Bitmap icon;
    String image;
    ImageView imglow;
    Bitmap imgoverbitmap;
    public boolean isPhotosave;
    int linbottomheight;
    int linbottomwidth;
    int linheight;
    int linsliderheight;
    int linslidermwidth;
    int linwidth;
    File[] listFile1;
    ArrayList<LinearLayout> ll_backgrond = new ArrayList<>();

    /* renamed from: ln */
    LinearLayout f189ln;
    LinearLayout lnmain;
    public List<Path> moldPathList = new ArrayList();
    Bitmap mybits;
    int newh;
    int neww;

    /* renamed from: om */
    DisplayMetrics f190om;
    ImageView original;
    /* access modifiers changed from: private */
    public ProgressDialog pDialog;

    /* renamed from: pd */
    ProgressDialog f191pd;
    int pos = 0;
    ImageView redo;
    RelativeLayout rel;
    ImageView reset;
    public boolean reset_clicked = false;
    ImageView save;
    Bitmap savedbits;
    /* access modifiers changed from: private */
    public SeekBar seekk;
    ImageView size;
    public boolean size_clicked = false;
    SharedPreferences spref;
    boolean success;

    /* renamed from: ta */
    TypedArray f192ta;
    TextView text;
    LinearLayout toplinear;
    ImageView undo;

    /* renamed from: w */
    int f193w;

    /* renamed from: ww */
    int f194ww;
    ImageView zoom;
    ZoomingLayout zoomble;

    public void colorclick(View view) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_splash);
        this.f189ln = (LinearLayout) findViewById(R.id.colorlinear);
        this.lnmain = (LinearLayout) findViewById(R.id.mainslider);
        this.f186dv = new DrawView(this);
        this.f186dv.setScaleType(ImageView.ScaleType.FIT_XY);
        this.flEditor = (FrameLayout) findViewById(R.id.frame);
        this.mybits = Utils.imageHolder;
        this.f190om = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(this.f190om);
        this.fl_height = this.f190om.heightPixels;
        this.fl_width = this.f190om.widthPixels;
        Utils.brushsize = 20;
        this.spref = getSharedPreferences("MyPrefsFile", 0);
        this.imglow = (ImageView) findViewById(R.id.imglow);
        this.fram = (FrameLayout) findViewById(R.id.frame);
        this.reset = (ImageView) findViewById(R.id.reset_button);
        this.undo = (ImageView) findViewById(R.id.undo);
        this.redo = (ImageView) findViewById(R.id.redo);
        this.gray = (ImageView) findViewById(R.id.gray);
        this.original = (ImageView) findViewById(R.id.original);
        this.colorEffect = (ImageView) findViewById(R.id.colorEffect);
        this.size = (ImageView) findViewById(R.id.size);
        this.don = (ImageView) findViewById(R.id.don);
        this.back = (ImageView) findViewById(R.id.btn_back_back);
        this.save = (ImageView) findViewById(R.id.okbtn);
        this.seekk = (SeekBar) findViewById(R.id.slider_view_seek_bar);
        this.original.setImageResource(R.drawable.ic_original_select);
        this.zoomble = (ZoomingLayout) findViewById(R.id.zoomble);
        this.zoom = (ImageView) findViewById(R.id.zoom);
        this.f185c = this.spref.getInt("color", SupportMenu.CATEGORY_MASK);
        this.col = SupportMenu.CATEGORY_MASK;
        this.color_layout = (LinearLayout) findViewById(R.id.myImages);
        this.f192ta = getResources().obtainTypedArray(R.array.colors);
        int[] iArr = new int[this.f192ta.length()];
        this.files1 = new int[this.f192ta.length()];
        this.zoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (SplashActivity.this.f186dv.enablezoom) {
                    SplashActivity.this.f186dv.enablezoom = false;
                    SplashActivity.this.zoomble.enablezoom = false;
                } else {
                    SplashActivity.this.f186dv.enablezoom = true;
                    SplashActivity.this.zoomble.enablezoom = true;
                }
                SplashActivity.this.original.setImageResource(R.drawable.ic_original);
                SplashActivity.this.colorEffect.setImageResource(R.drawable.ic_color);
                SplashActivity.this.size.setImageResource(R.drawable.ic_size);
                SplashActivity.this.gray.setImageResource(R.drawable.ic_gray);
                SplashActivity.this.reset.setImageResource(R.drawable.ic_reset);
                SplashActivity.this.zoom.setImageResource(R.drawable.ic_zoom_select);
            }
        });
        this.size.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                SplashActivity.this.original.setImageResource(R.drawable.ic_original);
                SplashActivity.this.colorEffect.setImageResource(R.drawable.ic_color);
                SplashActivity.this.size.setImageResource(R.drawable.ic_size_select);
                SplashActivity.this.gray.setImageResource(R.drawable.ic_gray);
                SplashActivity.this.reset.setImageResource(R.drawable.ic_reset);
                SplashActivity.this.zoom.setImageResource(R.drawable.ic_zoom);
                SplashActivity.this.flEditor.setDrawingCacheEnabled(false);
                SplashActivity.this.flEditor.setDrawingCacheEnabled(true);
                SplashActivity.this.flEditor.buildDrawingCache();
                SplashActivity splashActivity = SplashActivity.this;
                splashActivity.f184bs = Bitmap.createBitmap(splashActivity.flEditor.getDrawingCache());
                SplashActivity.this.flEditor.removeView(SplashActivity.this.f186dv);
                SplashActivity splashActivity2 = SplashActivity.this;
                splashActivity2.f186dv = new DrawView(splashActivity2);
                SplashActivity.this.flEditor.addView(SplashActivity.this.f186dv);
                SplashActivity.this.f186dv.setImageBitmap(SplashActivity.this.f184bs);
                SplashActivity splashActivity3 = SplashActivity.this;
                splashActivity3.Include_sliderview = (LinearLayout) splashActivity3.findViewById(R.id.sliderlinear);
                SplashActivity.this.Include_sliderview.setVisibility(0);
                SplashActivity.this.seekk.setProgress(Utils.brushsize);
                SplashActivity.this.seekk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @SuppressLint("WrongConstant")
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        SplashActivity.this.Include_sliderview.setVisibility(8);
                    }

                    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                        SplashActivity.this.f186dv.setBrushSize(SplashActivity.this.seekk.getProgress() + 10);
                        Utils.brushsize = SplashActivity.this.seekk.getProgress() + 10;
                    }
                });
            }
        });
        this.original.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashActivity.this.original.setImageResource(R.drawable.ic_original_select);
                SplashActivity.this.colorEffect.setImageResource(R.drawable.ic_color);
                SplashActivity.this.size.setImageResource(R.drawable.ic_size);
                SplashActivity.this.gray.setImageResource(R.drawable.ic_gray);
                SplashActivity.this.reset.setImageResource(R.drawable.ic_reset);
                SplashActivity.this.zoom.setImageResource(R.drawable.ic_zoom);
                SplashActivity.this.flEditor.setDrawingCacheEnabled(false);
                SplashActivity.this.flEditor.setDrawingCacheEnabled(true);
                SplashActivity.this.flEditor.buildDrawingCache();
                SplashActivity splashActivity = SplashActivity.this;
                splashActivity.f184bs = Bitmap.createBitmap(splashActivity.flEditor.getDrawingCache());
                SplashActivity.this.flEditor.removeView(SplashActivity.this.f186dv);
                SplashActivity splashActivity2 = SplashActivity.this;
                splashActivity2.f186dv = new DrawView(splashActivity2);
                SplashActivity.this.flEditor.addView(SplashActivity.this.f186dv);
                SplashActivity.this.imglow.setImageBitmap(SplashActivity.this.mybits);
                SplashActivity.this.f186dv.setImageBitmap(SplashActivity.this.f184bs);
                SplashActivity.this.f186dv.setBrushSize(Utils.brushsize);
            }
        });
        this.colorEffect.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                SplashActivity.this.lnmain.setVisibility(8);
                SplashActivity.this.f189ln.setVisibility(0);
                if (Build.VERSION.SDK_INT >= 11) {
                    new addGradientThumbToHs().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
                } else {
                    new addGradientThumbToHs().execute(new Void[0]);
                }
            }
        });
        this.gray.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashActivity.this.original.setImageResource(R.drawable.ic_original);
                SplashActivity.this.colorEffect.setImageResource(R.drawable.ic_color);
                SplashActivity.this.size.setImageResource(R.drawable.ic_size);
                SplashActivity.this.gray.setImageResource(R.drawable.ic_gray_select);
                SplashActivity.this.reset.setImageResource(R.drawable.ic_reset);
                SplashActivity.this.zoom.setImageResource(R.drawable.ic_zoom);
                SplashActivity.this.flEditor.setDrawingCacheEnabled(false);
                SplashActivity.this.flEditor.setDrawingCacheEnabled(true);
                SplashActivity.this.flEditor.buildDrawingCache();
                SplashActivity splashActivity = SplashActivity.this;
                splashActivity.f184bs = Bitmap.createBitmap(splashActivity.flEditor.getDrawingCache());
                SplashActivity.this.flEditor.removeView(SplashActivity.this.f186dv);
                SplashActivity splashActivity2 = SplashActivity.this;
                splashActivity2.f186dv = new DrawView(splashActivity2);
                SplashActivity.this.flEditor.addView(SplashActivity.this.f186dv);
                SplashActivity.this.imglow.setImageBitmap(SplashActivity.bmOut);
                SplashActivity.this.f186dv.setImageBitmap(SplashActivity.this.f184bs);
                SplashActivity.this.f186dv.setBrushSize(Utils.brushsize);
            }
        });
        this.redo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashActivity.this.f186dv.onClickRedo();
            }
        });
        this.undo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashActivity.this.f186dv.onClickUndo();
            }
        });
        this.reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashActivity.this.original.setImageResource(R.drawable.ic_original_select);
                SplashActivity.this.colorEffect.setImageResource(R.drawable.ic_color);
                SplashActivity.this.size.setImageResource(R.drawable.ic_size);
                SplashActivity.this.gray.setImageResource(R.drawable.ic_gray);
                SplashActivity.this.reset.setImageResource(R.drawable.ic_reset);
                SplashActivity.this.zoom.setImageResource(R.drawable.ic_zoom);
                SplashActivity.this.flEditor.removeView(SplashActivity.this.f186dv);
                SplashActivity splashActivity = SplashActivity.this;
                splashActivity.f186dv = new DrawView(splashActivity);
                SplashActivity.this.flEditor.addView(SplashActivity.this.f186dv);
                SplashActivity.this.f186dv.setScaleType(ImageView.ScaleType.FIT_XY);
                SplashActivity.this.f186dv.setImageBitmap(SplashActivity.bmOut);
                SplashActivity.this.imglow.setImageBitmap(SplashActivity.this.mybits);
                SplashActivity.this.f186dv.setBrushSize(Utils.brushsize);
            }
        });
        this.back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashActivity.this.onBackPressed();
            }
        });
        this.save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashActivity.this.next();
            }
        });
        this.don.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                SplashActivity.this.lnmain.setVisibility(0);
                SplashActivity.this.f189ln.setVisibility(8);
            }
        });
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Bitmap bitmapResize = SplashActivity.this.bitmapResize();
                SplashActivity.this.flEditor.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                SplashActivity.this.imglow.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                LinearLayout linearLayout = (LinearLayout) SplashActivity.this.findViewById(R.id.toplinear);
                new loadimage().execute(new Bitmap[]{bitmapResize});
            }
        }, 500);
    }

    public Bitmap getResizedBitmapp(Bitmap bitmap, int i) {
        int i2;
        float width = ((float) bitmap.getWidth()) / ((float) bitmap.getHeight());
        if (width > 1.0f) {
            i2 = (int) (((float) i) / width);
        } else {
            int i3 = (int) (((float) i) * width);
            i2 = i;
            i = i3;
        }
        return Bitmap.createScaledBitmap(bitmap, i, i2, true);
    }

    public static Bitmap doGreyscale(Bitmap bitmap) {
        bmOut = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                int pixel = bitmap.getPixel(i, i2);
                int alpha = Color.alpha(pixel);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                double d = (double) red;
                Double.isNaN(d);
                double d2 = (double) green;
                Double.isNaN(d2);
                double d3 = (double) blue;
                Double.isNaN(d3);
                int i3 = (int) ((d * 0.299d) + (d2 * 0.587d) + (d3 * 0.114d));
                bmOut.setPixel(i, i2, Color.argb(alpha, i3, i3, i3));
            }
        }
        return bmOut;
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int i) {
        if (i != 0) {
            return null;
        }
        this.pDialog = new ProgressDialog(this);
        this.pDialog.setMessage("Loading");
        this.pDialog.setProgress(2);
        this.pDialog.setMax(100);
        this.pDialog.setProgressStyle(0);
        this.pDialog.setCancelable(false);
        this.pDialog.setCanceledOnTouchOutside(false);
        this.pDialog.getVolumeControlStream();
        this.pDialog.show();
        return this.pDialog;
    }

    public class loadimage extends AsyncTask<Bitmap, Bitmap, Bitmap> {
        int progress = 0;

        public loadimage() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            SplashActivity.this.showDialog(0);
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(String... strArr) {
            SplashActivity.this.pDialog.setProgress(Integer.parseInt(strArr[0]));
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Bitmap... bitmapArr) {
            SplashActivity.doGreyscale(SplashActivity.this.mybits);
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            SplashActivity.this.dismissDialog(0);
            SplashActivity.this.pDialog.dismiss();
            SplashActivity.this.imglow.setImageBitmap(SplashActivity.this.mybits);
            SplashActivity.this.flEditor.addView(SplashActivity.this.f186dv);
            SplashActivity.this.f186dv.setImageBitmap(SplashActivity.bmOut);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bitmap, int i, int i2, boolean z) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i2) / ((float) width), ((float) i) / ((float) height));
        outputBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return outputBitmap;
    }

    public void next() {
        Utils.saveBitmap = getFrameBitmap();
        setResult(-1, new Intent());
        finish();
    }

    public Bitmap getFrameBitmap() {
        this.flEditor.setDrawingCacheEnabled(false);
        this.flEditor.setDrawingCacheEnabled(true);
        this.flEditor.buildDrawingCache();
        Bitmap createBitmap = Bitmap.createBitmap(this.flEditor.getDrawingCache());
        this.flEditor.destroyDrawingCache();
        return createBitmap;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class addGradientThumbToHs extends AsyncTask<Void, Void, Bitmap> {
        public addGradientThumbToHs() {
            SplashActivity.this.f191pd = new ProgressDialog(SplashActivity.this);
            SplashActivity.this.f191pd.setMessage("Loading...");
            SplashActivity.this.f191pd.setCancelable(false);
            SplashActivity.this.f191pd.show();
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            SplashActivity.this.color_layout.removeAllViewsInLayout();
            SplashActivity.this.color_layout.refreshDrawableState();
            SplashActivity.this.ll_backgrond.clear();
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            SplashActivity.this.pos = 0;
            for (int i = 0; i < SplashActivity.this.f192ta.length(); i++) {
                SplashActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        LinearLayout linearLayout = new LinearLayout(SplashActivity.this);
                        int i = SplashActivity.this.f193w > 720 ? ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION : 100;
                        int i2 = i + 3;
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i2);
                        layoutParams.setMargins(2, 2, 2, 2);
                        linearLayout.setLayoutParams(layoutParams);
                        linearLayout.setGravity(17);
                        linearLayout.setPadding(1, 1, 1, 1);
                        ImageView imageView = new ImageView(SplashActivity.this);
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(i, i));
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        SplashActivity.this.files1[SplashActivity.this.pos] = SplashActivity.this.f192ta.getColor(SplashActivity.this.pos, 0);
                        Bitmap createBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
                        createBitmap.eraseColor(SplashActivity.this.files1[SplashActivity.this.pos]);
                        imageView.setImageBitmap(createBitmap);
                        imageView.setTag(Integer.valueOf(SplashActivity.this.pos));
                        SplashActivity.this.pos++;
                        linearLayout.addView(imageView);
                        imageView.setOnClickListener(SplashActivity.this.ThumbColorClick);
                        SplashActivity.this.color_layout.addView(linearLayout);
                        SplashActivity.this.ll_backgrond.add(linearLayout);
                    }
                });
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            SplashActivity.this.f191pd.dismiss();
        }
    }

    public void colPicker(int i) {
        this.original.setImageResource(R.drawable.ic_original);
        this.colorEffect.setImageResource(R.drawable.ic_color);
        this.size.setImageResource(R.drawable.ic_size);
        this.gray.setImageResource(R.drawable.ic_gray);
        this.reset.setImageResource(R.drawable.ic_reset);
        this.zoom.setImageResource(R.drawable.ic_zoom);
        this.flEditor.setDrawingCacheEnabled(false);
        this.flEditor.setDrawingCacheEnabled(true);
        this.flEditor.buildDrawingCache();
        this.f184bs = Bitmap.createBitmap(this.flEditor.getDrawingCache());
        this.flEditor.removeView(this.f186dv);
        this.f186dv = new DrawView(this);
        this.flEditor.addView(this.f186dv);
        this.imglow.setImageBitmap(this.mybits);
        this.f186dv.setImageBitmap(this.f184bs);
        this.f186dv.setBrushSize(Utils.brushsize);
        this.imglow.setImageBitmap(Utils.changeImageColor(this.mybits, i));
    }

    public Bitmap bitmapResize() {
        int width = this.flEditor.getWidth();
        int height = this.flEditor.getHeight();
        int width2 = this.mybits.getWidth();
        int height2 = this.mybits.getHeight();
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
        return Bitmap.createScaledBitmap(this.mybits, width, height, true);
    }
}

