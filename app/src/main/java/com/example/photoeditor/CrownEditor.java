package com.example.photoeditor;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.ItemTouchHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CrownEditor extends Activity implements View.OnClickListener {
    public static final int RESULT_FROM_ADDTEXT = 999;
    public static final int RESULT_FROM_ADJUST = 444;
    public static final int RESULT_FROM_CROPPER = 888;
    public static final int RESULT_FROM_EFFECTS = 222;
    //public static final int RESULT_FROM_FREE_CROP = 114;
    public static final int RESULT_FROM_GALLERY = 212;
    public static final int RESULT_FROM_ORIENTATION = 777;
    public static final int RESULT_FROM_PHOTO = 666;
    public static final int RESULT_FROM_SPLASH = 555;
    public static final int RESULT_FROM_STICKER = 101;
    public static final int RESULT_FROM_STICKER_LIB = 111;
    public static final int RESULT_FROM_STICK_LIB = 112;
    public static final int RESULT_FROM_STICK_TXT = 113;
    public static final int RESULT_FROM_TAG = 333;
    Animation Anim;
    public String Sticker_path;
    View.OnClickListener ThumbFilterClick = new View.OnClickListener() {
        public void onClick(View view) {
            AllEffects allEffects = CrownEditor.this.all;
            allEffects.setFilter(Integer.parseInt(view.getTag() + ""));
            CrownEditor.this.image_edit.setImageBitmap(CrownEditor.this.all.AllEffects());
        }
    };
    ImageView add_photo;
    ImageView adjust;
    AllEffects all;
    ImageView btnBack;
    ImageView btnDone;
    ArrayList<ImageButton> btnList = new ArrayList<>();
    ImageView btnNext;
    boolean check;
    int count;
    Bitmap cropImage;
    ImageView cropper;
    DisplayMetrics dis;
    EditText edText;
    ImageView edit;
    LinearLayout filter_layout;
    FrameLayout fl_edit;
   // ImageView free_crop;

    /* renamed from: h */
    int f142h;
    /* access modifiers changed from: private */
    public ImageFilter iFilter;
    ImageView image_edit;
    public boolean image_filter = false;
    InputMethodManager imm;
    int lastY = 0;
    LinearLayout linearLayout;
    ArrayList<LinearLayout> ll_backgrond = new ArrayList<>();
    private ArrayList<View> mViews;
    RelativeLayout main_slider;
    int moveY = 0;
    ImageView my_filters;
    ImageView mytext;
//    public View.OnClickListener onclickbtn = new View.OnClickListener() {
//        public void onClick(final View view) {
//            C07561 r0 = new DialogInterface.OnClickListener() {
//                @SuppressLint("WrongConstant")
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    if (i == -1) {
//                        CrownEditor.this.textList.get(Integer.parseInt(view.getTag().toString())).setVisibility(8);
//                    }
//                }
//            };
//            new AlertDialog.Builder(CrownEditor.this).setMessage("Delete this TAG?").setPositiveButton("Yes", r0).setNegativeButton("No", r0).show();
//        }
//    };


    /* renamed from: pd */
    ProgressDialog f143pd;
    PopupWindow popupEditText;
    int pos = 0;
    PopupWindow pwindo;
    RelativeLayout rl_editor;
    RelativeLayout rl_filter;
    RelativeLayout rl_frame;
    RelativeLayout rl_seekbar;
    RelativeLayout rl_shape;
    RelativeLayout body;
    View rootView;
    ImageView splash;
    ImageView sticker;
    ImageView tag;
    ArrayList<View> textList = new ArrayList<>();
    public View.OnTouchListener textTouch = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == 0) {
                CrownEditor.this.moveY = (int) motionEvent.getY();
                CrownEditor crownEditor = CrownEditor.this;
                crownEditor.lastY = crownEditor.moveY;
                return true;
            } else if (action != 2) {
                return true;
            } else {
                CrownEditor.this.moveY = (int) motionEvent.getY();
                layoutParams.topMargin += CrownEditor.this.moveY;
                layoutParams.leftMargin = 0;
                view.setLayoutParams(layoutParams);
                return true;
            }
        }
    };
    Typeface tf1;
    Bitmap tmp;
    int f144w;

    /* access modifiers changed from: protected */
    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_crown_editor2);
        this.dis = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(this.dis);
        this.mViews = new ArrayList<>();
        this.f142h = this.dis.heightPixels;
        this.f144w = this.dis.widthPixels;
        findviewByID();
        this.cropImage = Utils.imageHolder;
        this.all = new AllEffects(this);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Bitmap bitmapResize = CrownEditor.this.bitmapResize();
                if (bitmapResize.getHeight() > 0 && bitmapResize.getWidth() > 0) {
                    CrownEditor.this.fl_edit.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                    CrownEditor.this.image_edit.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                }
                CrownEditor.this.image_edit.setImageBitmap(CrownEditor.this.cropImage);
                CrownEditor.this.all.setBitmap(CrownEditor.this.cropImage);
                CrownEditor.this.image_edit.setVisibility(0);
            }
        }, 500);
        this.rl_filter.setVisibility( 0b1000 );
        this.rl_frame.setVisibility(8);
        this.rl_shape.setVisibility(8);
        this.rl_seekbar.setVisibility(8);
        this.image_edit.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }
    public void findviewByID() {
        this.body = (RelativeLayout) findViewById ( R.id.body );
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.btnNext = (ImageView) findViewById(R.id.btnNext);
        this.rl_editor = (RelativeLayout) findViewById(R.id.main);
        this.fl_edit = (FrameLayout) findViewById(R.id.fl_edit);
        this.image_edit = (ImageView) findViewById(R.id.image_edit);
        this.rl_filter = (RelativeLayout) findViewById(R.id.rl_filter);
        this.rl_frame = (RelativeLayout) findViewById(R.id.rl_frame);
        this.rl_shape = (RelativeLayout) findViewById(R.id.rl_shape);
        this.rl_seekbar = (RelativeLayout) findViewById(R.id.rl_seekbar);
        this.tag = (ImageView) findViewById(R.id.tag);
        this.sticker = (ImageView) findViewById(R.id.sticker);
        this.edit = (ImageView) findViewById(R.id.edit);
        this.adjust = (ImageView) findViewById(R.id.adjust);
        this.splash = (ImageView) findViewById(R.id.splash);
        this.add_photo = (ImageView) findViewById(R.id.add_photo);
        this.cropper = (ImageView) findViewById(R.id.cropper);
        this.my_filters = (ImageView) findViewById(R.id.my_filters);
        this.mytext = (ImageView) findViewById(R.id.mytext);
        this.filter_layout = (LinearLayout) findViewById(R.id.filter_layout);
        this.btnBack.setOnClickListener(this);
        this.btnNext.setOnClickListener(this);
        this.tag.setOnClickListener(this);
        this.sticker.setOnClickListener(this);
        this.edit.setOnClickListener(this);
        this.adjust.setOnClickListener(this);
        this.splash.setOnClickListener(this);
        this.add_photo.setOnClickListener(this);
        this.cropper.setOnClickListener(this);
        this.my_filters.setOnClickListener(this);
        this.mytext.setOnClickListener(this);
    }
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.add_photo /*2131296336*/:
                Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, RESULT_FROM_GALLERY);
                return;


            case R.id.adjust /*2131296339*/:
                Utils.imageHolder = this.cropImage;
                startActivityForResult(new Intent(this, AdjustActivity.class), RESULT_FROM_ADJUST);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                return;


            case R.id.btnBack /*2131296371*/:
                onBackPressed();
                return;


            case R.id.btnNext /*2131296383*/:
                next();
                return;

            case R.id.cropper /*2131296442*/:
                Utils.imageHolder = this.cropImage;
                startActivityForResult(new Intent(this, MainCroper.class), RESULT_FROM_CROPPER);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                return;

            case R.id.edit /*2131296458*/:
                Utils.imageHolder = this.cropImage;
                startActivityForResult(new Intent(this, OrientationActivity.class), RESULT_FROM_ORIENTATION);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                return;


            case R.id.my_filters /*2131296576*/:
                Utils.imageHolder = this.cropImage;
                startActivityForResult(new Intent(this, EffectsActivity.class), RESULT_FROM_EFFECTS);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                return;


            case R.id.mytext /*2131296578*/:
                Utils.imageHolder = this.cropImage;
                startActivityForResult(new Intent(this, TextAdd.class), 112);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                return;


            case R.id.splash /*2131296682*/:
                Utils.imageHolder = this.cropImage;
                startActivityForResult(new Intent(this, SplashActivity.class), 555);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                return;

            case R.id.sticker /*2131296695*/:
                Utils.imageHolder = this.cropImage;
                startActivityForResult(new Intent(this, StickerLibrary.class), 111);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                return;


            case R.id.tag /*2131296713*/:
                Utils.imageHolder = this.cropImage;
                startActivityForResult(new Intent(this, TextAdd.class), RESULT_FROM_ADDTEXT);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                return;
            default:
                return;
        }
    }

    private Bitmap getBitmapFromAsset(String str, String str2) throws IOException {
        AssetManager assets = getAssets();
        InputStream open = assets.open(str + "/" + str2);
        Bitmap decodeStream = BitmapFactory.decodeStream(open);
        open.close();
        return decodeStream;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            return;
        }
        if (i == 101) {
            Bitmap bitmap = Utils.saveBitmap;
            this.cropImage = bitmap;
            this.image_edit.setImageBitmap(bitmap);
        } else if (i == 212) {
            Utils.selectedImageUri = intent.getData();
            Utils.imageHolder = this.cropImage;
            startActivityForResult(new Intent(this, PhotoActivity.class), RESULT_FROM_PHOTO);
            overridePendingTransition(R.anim.slide_up, R.anim.stay);
        } else if (i == 222) {
            Bitmap bitmap2 = Utils.saveBitmap;
            this.cropImage = bitmap2;
            this.image_edit.setImageBitmap(bitmap2);
        } else if (i == 333) {
            Bitmap bitmap3 = Utils.saveBitmap;
            this.cropImage = bitmap3;
            this.image_edit.setImageBitmap(bitmap3);
        } else if (i == 444) {
            Bitmap bitmap4 = Utils.saveBitmap;
            this.cropImage = bitmap4;
            this.image_edit.setImageBitmap(bitmap4);
        } else if (i == 555) {
            Bitmap bitmap5 = Utils.saveBitmap;
            this.cropImage = bitmap5;
            this.image_edit.setImageBitmap(bitmap5);
        } else if (i == 666) {
            Bitmap bitmap6 = Utils.saveBitmap;
            this.cropImage = bitmap6;
            this.image_edit.setImageBitmap(bitmap6);
        } else if (i == 777) {
            Bitmap bitmap7 = Utils.saveBitmap;
            this.cropImage = bitmap7;
            this.image_edit.setImageBitmap(bitmap7);
        } else if (i == 888) {
            this.cropImage = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/cropped/temp.jpg");
            Bitmap bitmapResize = bitmapResize();
            this.cropImage = bitmapResize;
            this.fl_edit.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
            this.image_edit.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
            this.image_edit.setImageBitmap(bitmapResize);
        } else if (i != 999) {
            switch (i) {
                case 111:
                    Utils.imageHolder = this.cropImage;
                    startActivityForResult(new Intent(this, StickerActivity.class), 101);
                    overridePendingTransition(R.anim.slide_up, R.anim.stay);
                    return;
                case 112:
                    Utils.imageHolder = this.cropImage;
                    startActivityForResult(new Intent(this, StickerText.class), 113);
                    return;
                case 113:
                    Bitmap bitmap8 = Utils.saveBitmap;
                    this.cropImage = bitmap8;
                    this.image_edit.setImageBitmap(bitmap8);
                    return;
                case 114:
                    this.cropImage = Utils.saveBitmap;
                    Bitmap bitmapResize2 = bitmapResize();
                    this.cropImage = bitmapResize2;
                    this.fl_edit.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize2.getWidth(), bitmapResize2.getHeight()));
                    this.image_edit.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize2.getWidth(), bitmapResize2.getHeight()));
                    this.image_edit.setImageBitmap(bitmapResize2);
                    return;
                default:
                    return;
            }
        } else {
            Utils.imageHolder = this.cropImage;
            startActivityForResult(new Intent(this, SnapActivity.class), RESULT_FROM_TAG);
            overridePendingTransition(R.anim.slide_up, R.anim.stay);
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor managedQuery = managedQuery(uri, new String[]{"_data"}, (String) null, (String[]) null, (String) null);
        int columnIndexOrThrow = managedQuery.getColumnIndexOrThrow("_data");
        managedQuery.moveToFirst();
        return managedQuery.getString(columnIndexOrThrow);
    }

    public Bitmap getFrameBitmap() {
        this.fl_edit.postInvalidate();
        this.fl_edit.setDrawingCacheEnabled(true);
        this.fl_edit.buildDrawingCache();
        Bitmap createBitmap = Bitmap.createBitmap(this.fl_edit.getDrawingCache());
        this.fl_edit.setDrawingCacheEnabled(false);
        this.fl_edit.destroyDrawingCache();
        return createBitmap;
    }

    public Bitmap bitmapResize() {
        try {
            int width = this.fl_edit.getWidth();
            int height = this.fl_edit.getHeight();
            int width2 = this.cropImage.getWidth();
            int height2 = this.cropImage.getHeight();
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
            return Bitmap.createScaledBitmap(this.cropImage, width, height, true);
        } catch (Exception e) {
            Log.d("EEE", e.toString());
            return this.cropImage;
        }
    }

    public void next() {
        Utils.saveBitmap = getFrameBitmap();
        setResult(-1, new Intent());
        finish();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private class addFilterThumbToHs extends AsyncTask<Void, Void, Bitmap> {
        Bitmap bmp;

        public addFilterThumbToHs(Bitmap bitmap) {
            this.bmp = bitmap;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            CrownEditor.this.filter_layout.removeAllViewsInLayout();
            CrownEditor.this.filter_layout.refreshDrawableState();
        }
        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            new BitmapFactory.Options().inJustDecodeBounds = true;
            for ( int i = 0; i < 19; i++) {
                final Bitmap applyStyle = CrownEditor.this.iFilter.applyStyle(i);
                CrownEditor.this.runOnUiThread(new Runnable() {
                    public void run() {
                        LinearLayout linearLayout = new LinearLayout(CrownEditor.this.getApplicationContext());
                        int i = CrownEditor.this.f144w > 720 ? ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION : 100;
                        int i2 = i + 3;
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i2);
                        layoutParams.setMargins(2, 2, 2, 2);
                        linearLayout.setLayoutParams(layoutParams);
                        linearLayout.setGravity(17);
                        linearLayout.setPadding(1, 1, 1, 1);
                        ImageView imageView = new ImageView(CrownEditor.this.getApplicationContext());
                        imageView.setLayoutParams(new ActionBar.LayoutParams(i, i));
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        imageView.setImageBitmap(applyStyle);
                        imageView.setTag(Integer.valueOf(i));
                        linearLayout.addView(imageView);
                        imageView.setOnClickListener(CrownEditor.this.ThumbFilterClick);
                        CrownEditor.this.filter_layout.addView(linearLayout);
                    }
                });
            }
            return null;
        }
        public void onPostExecute(Bitmap bitmap) {
            CrownEditor crownEditor = CrownEditor.this;
            crownEditor.tmp = crownEditor.cropImage;
        }
    }
}
