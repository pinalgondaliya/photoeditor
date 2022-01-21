package com.example.photoeditor;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StickerText extends Activity implements View.OnClickListener, TextManagerListener {
    Animation Anim;
    public final int RESULT_FROM_EDIT_TEXT = CrownEditor.RESULT_FROM_EFFECTS;
    public final int RESULT_FROM_SNAP = 111;
    View.OnClickListener ThumbFontClick = new View.OnClickListener() {
        public void onClick(View view) {
            int intValue = ((Integer) view.getTag()).intValue();
            StickerText stickerText = StickerText.this;
            StickerText.this.onTextFontChange(stickerText.getTextTypeface(stickerText.fontlist.get(intValue)));
        }
    };
    AbsoluteLayout absolute;
    ImageView add_new;
    Bitmap bit;
    int blue;
    LinearLayout bottom;
    ImageView btnBack;
    ImageView btnNext;
    int col;
    ImageView color;
    ImageView color_image;
    Bitmap colorbits;
    ArrayList<DragTextView> dList = new ArrayList<>();
    float downx = 0.0f;
    float downy = 0.0f;
    int drawScreenHeight;
    int drawScreenWidth;
    FrameLayout flTextManager;
    FrameLayout fl_edit;
    ImageView font;
    LinearLayout fontLayout;
    List<String> fontlist;
    int green;
    ImageView hide_show;
    IEditImageInfo iEditInfo = new IEditImageInfo();
    ImageView image_edit;
    ArrayList<RelativeLayout> ll_font = new ArrayList<>();

    /* renamed from: pd */
    ProgressDialog f200pd;
    int pos = 0;
    int red;
    RelativeLayout rl_color;
    RelativeLayout rl_font;
    int screenHeight;
    int screenWidth;
    DragTextView selectview = null;
    String text;
    float upx = 0.0f;
    float upy = 0.0f;

    public void onSingleTapOnTextView() {
    }

    public void onTextTopTapListener() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        String[] strArr;
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_sticker_text);
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
        findviewByID();
        this.bit = Utils.imageHolder;
        this.text = Utils.add_text;
        try {
            strArr = getAssets().list("font");
        } catch (IOException e) {
            e.printStackTrace();
            strArr = null;
        }
        this.fontlist = Arrays.asList(strArr);
        this.fl_edit.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                StickerText.this.fl_edit.getViewTreeObserver().removeOnPreDrawListener(this);
                StickerText stickerText = StickerText.this;
                stickerText.drawScreenWidth = stickerText.fl_edit.getWidth();
                StickerText stickerText2 = StickerText.this;
                stickerText2.drawScreenHeight = stickerText2.fl_edit.getHeight();
                StickerText stickerText3 = StickerText.this;
                stickerText3.absolute = new AbsoluteLayout(stickerText3.getApplicationContext());
                LinearLayout linearLayout = new LinearLayout(StickerText.this.getApplicationContext());
                linearLayout.setLayoutParams(new FrameLayout.LayoutParams(StickerText.this.screenWidth, StickerText.this.screenWidth));
                linearLayout.setGravity(17);
                linearLayout.setX(0.0f);
                linearLayout.setY(0.0f);
                StickerText stickerText4 = StickerText.this;
                stickerText4.addTextManagerView(stickerText4.drawScreenWidth, StickerText.this.drawScreenHeight);
                StickerText.this.setTextPreviewInfo();
                return true;
            }
        });
        new Handler().postDelayed(new Runnable() {
            @SuppressLint("WrongConstant")
            public void run() {
                Bitmap bitmapResize = StickerText.this.bitmapResize();
                StickerText.this.fl_edit.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                StickerText.this.image_edit.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                StickerText.this.image_edit.setImageBitmap(bitmapResize);
                StickerText.this.image_edit.setVisibility(0);
                StickerText.this.add_text();
            }
        }, 500);
        this.image_edit.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint({"WrongConstant", "ClickableViewAccessibility"})
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (StickerText.this.rl_color.getVisibility() == 0) {
                    StickerText.this.rl_color.setVisibility(8);
                }
                if (StickerText.this.rl_font.getVisibility() == 0) {
                    StickerText.this.rl_font.setVisibility(8);
                }
                if (StickerText.this.selectview != null) {
                    StickerText.this.selectview.HideBorderView();
                }
                StickerText.this.selectview = null;
                return false;
            }
        });
        this.hide_show.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    Iterator<DragTextView> it = StickerText.this.dList.iterator();
                    while (it.hasNext()) {
                        it.next().visibilityShow();
                    }
                }
                if (motionEvent.getAction() == 0) {
                    Iterator<DragTextView> it2 = StickerText.this.dList.iterator();
                    while (it2.hasNext()) {
                        it2.next().visibilityGone();
                    }
                }
                return true;
            }
        });
    }

    @SuppressLint({"WrongConstant", "ClickableViewAccessibility"})
    public void findviewByID() {
        this.rl_font = (RelativeLayout) findViewById(R.id.rl_font);
        this.rl_font.setVisibility(8);
        this.rl_color = (RelativeLayout) findViewById(R.id.rl_color);
        this.rl_color.setVisibility(8);
        this.color_image = (ImageView) findViewById(R.id.color_image);
        this.bottom = (LinearLayout) findViewById(R.id.bottom);
        this.hide_show = (ImageView) findViewById(R.id.hide_show);
        this.add_new = (ImageView) findViewById(R.id.add_new);
        this.fl_edit = (FrameLayout) findViewById(R.id.fl_edit);
        this.image_edit = (ImageView) findViewById(R.id.image_edit);
        this.btnNext = (ImageView) findViewById(R.id.btnNext);
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.fontLayout = (LinearLayout) findViewById(R.id.font_layout);
        this.font = (ImageView) findViewById(R.id.font);
        this.color = (ImageView) findViewById(R.id.color);
        this.btnNext.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
        this.add_new.setOnClickListener(this);
        this.font.setOnClickListener(this);
        this.color.setOnClickListener(this);
        this.colorbits = BitmapFactory.decodeResource(getResources(), R.drawable.nko_colormap);
        this.color_image.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    StickerText.this.downx = motionEvent.getX();
                    StickerText.this.downy = motionEvent.getY();
                } else if (action == 1) {
                    StickerText.this.upx = motionEvent.getX();
                    StickerText.this.upy = motionEvent.getY();
                    StickerText.this.color_image.invalidate();
                } else if (action == 2) {
                    int pixel = StickerText.this.colorbits.getPixel((int) motionEvent.getX(), (int) motionEvent.getY());
                    StickerText.this.red = Color.red(pixel);
                    StickerText.this.blue = Color.blue(pixel);
                    StickerText.this.green = Color.green(pixel);
                    StickerText stickerText = StickerText.this;
                    stickerText.col = Color.rgb(stickerText.red, StickerText.this.green, StickerText.this.blue);
                    StickerText stickerText2 = StickerText.this;
                    stickerText2.onTextColorChange(stickerText2.col);
                }
                return true;
            }
        });
    }

    public void add_text() {
        DragTextView dragTextView = new DragTextView(this, this.text);
        dragTextView.intializeview(R.drawable.icon_resize, R.drawable.icon_delete, R.drawable.icon_rotate, R.drawable.border_textview, dpToPx(24));
        this.flTextManager.addView(dragTextView);
        this.dList.add(dragTextView);
    }

    public int dpToPx(int i) {
        return Math.round(((float) i) * getApplicationContext().getResources().getDisplayMetrics().density);
    }

    public void next() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Iterator<DragTextView> it = StickerText.this.dList.iterator();
                while (it.hasNext()) {
                    DragTextView next = it.next();
                    if (StickerText.this.selectview != null) {
                        StickerText.this.selectview.HideBorderView();
                    }
                    StickerText.this.selectview = null;
                }
                Utils.saveBitmap = StickerText.this.getFrameBitmap();
                StickerText.this.setResult(-1, new Intent());
                StickerText.this.finish();
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new:
                startActivityForResult(new Intent(this, TextAdd.class), 111);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                return;
            case R.id.btnBack:
                onBackPressed();
                return;
            case R.id.btnNext:
                next();
                return;
            case R.id.color:
                color_show_hide();
                return;
            case R.id.font:
                font_show_hide();
                return;
            default:
                return;
        }
    }

    @SuppressLint("WrongConstant")
    public void color_show_hide() {
        if (this.rl_color.getVisibility() == 0) {
            this.rl_color.setVisibility(8);
            this.Anim = AnimationUtils.loadAnimation(this, R.anim.close_menu);
            this.Anim.setDuration(200);
            this.rl_color.startAnimation(this.Anim);
            return;
        }
        this.rl_color.setVisibility(0);
        this.Anim = AnimationUtils.loadAnimation(this, R.anim.open_menu);
        this.Anim.setDuration(200);
        this.rl_color.startAnimation(this.Anim);
    }

    @SuppressLint("WrongConstant")
    public void font_show_hide() {
        if (this.rl_font.getVisibility() == 0) {
            this.rl_font.setVisibility(8);
            this.Anim = AnimationUtils.loadAnimation(this, R.anim.close_menu);
            this.Anim.setDuration(200);
            this.rl_font.startAnimation(this.Anim);
        } else {
            this.rl_font.setVisibility(0);
            this.Anim = AnimationUtils.loadAnimation(this, R.anim.open_menu);
            this.Anim.setDuration(200);
            this.rl_font.startAnimation(this.Anim);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            new addFontThumbToHs().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
        } else {
            new addFontThumbToHs().execute(new Void[0]);
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

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            this.text = "";
        } else if (i == 111) {
            DragTextView dragTextView = this.selectview;
            if (dragTextView != null) {
                dragTextView.HideBorderView();
            }
            this.selectview = null;
            this.text = Utils.add_text;
            add_text();
        } else if (i == 222) {
            this.text = Utils.add_text;
            DragTextView dragTextView2 = this.selectview;
            if (dragTextView2 != null) {
                dragTextView2.SetTextString(this.text);
                this.selectview.getTextString().equals("");
            }
        }
    }

    /* access modifiers changed from: private */
    public void addTextManagerView(int i, int i2) {
        this.flTextManager = new FrameLayout(this);
        this.flTextManager.setLayoutParams(new FrameLayout.LayoutParams(i, i2));
        this.fl_edit.addView(this.flTextManager);
    }

    /* access modifiers changed from: private */
    public void setTextPreviewInfo() {
        FrameLayout frameLayout = this.flTextManager;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
            if (this.iEditInfo.textarr != null) {
                int size = this.iEditInfo.textarr.size();
                for (int i = 0; i < size; i++) {
                    this.selectview = null;
                    DragTextView dragTextView = new DragTextView(this, "Some \nText Here...");
                    dragTextView.intializeview(R.drawable.icon_resize, R.drawable.icon_delete, R.drawable.border_textview, R.drawable.icon_rotate, dpToPx(24));
                    dragTextView.SetTextString(this.iEditInfo.textarr.get(i).usertext);
                    dragTextView.SetTextColor(this.iEditInfo.textarr.get(i).usercolor);
                    this.iEditInfo.textarr.get(i).userfont.equals("");
                    dragTextView.setLayoutParmasExistView(this.iEditInfo.textarr.get(i).params, this.iEditInfo.textarr.get(i).paramsRotation);
                    this.flTextManager.addView(dragTextView);
                    this.selectview = null;
                    dragTextView.HideBorderView();
                }
            }
        }
    }

    public void onAddViewListener(DragTextView dragTextView) {
        this.selectview = dragTextView;
    }

    public void onTapListener(View view) {
        DragTextView dragTextView = (DragTextView) view.getParent().getParent();
        if (dragTextView != this.selectview) {
            this.selectview = dragTextView;
            this.selectview.ShowBorderView(R.drawable.border_textview);
            new Runnable() {
                public void run() {
                    int childCount = StickerText.this.flTextManager.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        DragTextView dragTextView = (DragTextView) StickerText.this.flTextManager.getChildAt(i);
                        if (dragTextView != StickerText.this.selectview) {
                            dragTextView.HideBorderView();
                        }
                    }
                }
            }.run();
        }
    }

    public void onDoubleTapOnTextView() {
        Utils.textFrom = this.text;
        startActivityForResult(new Intent(this, TextAdd.class), CrownEditor.RESULT_FROM_EFFECTS);
    }

    public void onTextDeleteTapListener() {
        DragTextView dragTextView = this.selectview;
        if (dragTextView != null) {
            this.flTextManager.removeView(dragTextView);
            this.selectview = null;
        }
    }

    public void onLayoutParamsTvModified(FrameLayout.LayoutParams layoutParams) {
        DragTextView dragTextView = this.selectview;
        if (dragTextView != null) {
            dragTextView.setLayoutParmasExistView(layoutParams);
        }
    }

    public boolean onSizeLessExceed(boolean z) {
        return this.selectview.getTextSize() < 24.0f;
    }

    public void onTextColorChange(int i) {
        DragTextView dragTextView = this.selectview;
        if (dragTextView != null) {
            dragTextView.SetTextColor(i);
        }
    }

    public void onTextFontChange(Typeface typeface) {
        DragTextView dragTextView = this.selectview;
        if (dragTextView != null) {
            dragTextView.SetTextFontStyle(typeface);
        }
    }

    public Typeface getTextTypeface(String str) {
        AssetManager assets = getAssets();
        return Typeface.createFromAsset(assets, "font/" + str);
    }

    @SuppressLint("WrongConstant")
    public void onBackPressed() {
        if (this.rl_color.getVisibility() == 0) {
            this.rl_color.setVisibility(8);
        } else {
            finish();
        }
    }

    private class addFontThumbToHs extends AsyncTask<Void, Void, Bitmap> {
        public addFontThumbToHs() {
            StickerText.this.f200pd = new ProgressDialog(StickerText.this);
            StickerText.this.f200pd.setMessage("Loading...");
            StickerText.this.f200pd.setCancelable(false);
            StickerText.this.f200pd.show();
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            StickerText.this.fontLayout.removeAllViewsInLayout();
            StickerText.this.fontLayout.refreshDrawableState();
            StickerText.this.ll_font.clear();
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            StickerText.this.pos = 0;
            for (int i = 0; i < StickerText.this.fontlist.size(); i++) {
                StickerText.this.runOnUiThread(new Runnable() {
                    public void run() {
                        RelativeLayout relativeLayout = new RelativeLayout(StickerText.this.getApplicationContext());
                        int i = StickerText.this.screenWidth > 720 ? ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION : 100;
                        int i2 = i + 3;
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i2);
                        layoutParams.setMargins(2, 2, 2, 2);
                        relativeLayout.setLayoutParams(layoutParams);
                        relativeLayout.setGravity(17);
                        relativeLayout.setPadding(1, 1, 1, 1);
                        ImageView imageView = new ImageView(StickerText.this.getApplicationContext());
                        imageView.setLayoutParams(new FrameLayout.LayoutParams(i, i));
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        TextView textView = new TextView(StickerText.this.getApplicationContext());
                        textView.setLayoutParams(new FrameLayout.LayoutParams(i, i));
                        textView.setGravity(17);
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setTextSize(20.0f);
                        textView.setText("font");
                        textView.setTypeface(StickerText.this.getTextTypeface(StickerText.this.fontlist.get(StickerText.this.pos)));
                        imageView.setImageResource( R.drawable.sticker_bg);
                        imageView.setTag(Integer.valueOf(StickerText.this.pos));
                        StickerText.this.pos++;
                        relativeLayout.addView(imageView);
                        relativeLayout.addView(textView);
                        imageView.setOnClickListener(StickerText.this.ThumbFontClick);
                        StickerText.this.fontLayout.addView(relativeLayout);
                        StickerText.this.ll_font.add(relativeLayout);
                    }
                });
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            StickerText.this.f200pd.dismiss();
        }
    }
}
