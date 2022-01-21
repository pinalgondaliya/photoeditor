package com.example.photoeditor;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import java.util.ArrayList;
import java.util.Iterator;

public class StickerActivity extends Activity implements View.OnClickListener {
    public final int RESULT_FROM_STICKER_LIB = 111;
    public String Sticker_path;
    ImageView add_new;
    Bitmap bit;
    ImageView btnBack;
    ImageView btnNext;
    boolean check;
    ArrayList<ClipSticker> clipList = new ArrayList<>();
    private int counter;
    FrameLayout fl_edit;
    ImageView hide_show;
    ImageView image_edit;
    private ArrayList<View> mViews;
    public DisplayImageOptions options;
    ClipSticker stick1 = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_sticker);
        findviewByID();
        this.bit = Utils.imageHolder;
        new Handler().postDelayed(new Runnable() {
            @SuppressLint("WrongConstant")
            public void run() {
                Bitmap bitmapResize = StickerActivity.this.bitmapResize();
                StickerActivity.this.fl_edit.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                StickerActivity.this.image_edit.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                StickerActivity.this.image_edit.setImageBitmap(bitmapResize);
                StickerActivity.this.image_edit.setVisibility(0);
                StickerActivity.this.ClipSticker(Utils.urSticker);
            }
        }, 500);
        this.image_edit.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                StickerActivity.this.DisableAll();
                return false;
            }
        });
        this.hide_show.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    Iterator<ClipSticker> it = StickerActivity.this.clipList.iterator();
                    while (it.hasNext()) {
                        it.next().Visibilityshow();
                    }
                }
                if (motionEvent.getAction() == 0) {
                    Iterator<ClipSticker> it2 = StickerActivity.this.clipList.iterator();
                    while (it2.hasNext()) {
                        it2.next().Visibilityhide();
                        StickerActivity.this.DisableAll();
                    }
                }
                return true;
            }
        });
    }

    public void findviewByID() {
        this.fl_edit = (FrameLayout) findViewById(R.id.fl_edit);
        this.add_new = (ImageView) findViewById(R.id.add_new);
        this.hide_show = (ImageView) findViewById(R.id.hide_show);
        this.image_edit = (ImageView) findViewById(R.id.image_edit);
        this.btnNext = (ImageView) findViewById(R.id.btnNext);
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.mViews = new ArrayList<>();
        this.add_new.setOnClickListener(this);
        this.btnNext.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
    }

    public void next() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                StickerActivity.this.DisableAll();
                Utils.saveBitmap = StickerActivity.this.getFrameBitmap();
                StickerActivity.this.setResult(-1, new Intent());
                StickerActivity.this.finish();
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

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.add_new) {
            startActivityForResult(new Intent(this, StickerLibrary.class), 111);
            overridePendingTransition(R.anim.slide_up, R.anim.stay);
        } else if (id == R.id.btnBack) {
            onBackPressed();
        } else if (id == R.id.btnNext) {
            DisableAll();
            next();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 111) {
            ClipSticker(Utils.urSticker);
        }
    }

    public void ClipSticker(String str) {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(this);
        builder.threadPriority(3);
        builder.denyCacheImageMultipleSizesInMemory();
        builder.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        builder.diskCacheSize(52428800);
        builder.tasksProcessingOrder(QueueProcessingType.LIFO);
        builder.writeDebugLogs();
        ImageLoader.getInstance().init(builder.build());
        this.options = new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading((int)
                R.mipmap.ic_launcher).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();
        String str2 = str.toString();
        DisableAll();
        this.counter++;
        ClipSticker clipSticker = new ClipSticker(this, this.options, str2);
        clipSticker.adjustOpacity(255.0f);
        clipSticker.setId(this.counter);
        this.fl_edit.addView(clipSticker);
        this.clipList.add(clipSticker);
        clipSticker.setOnClickListener(new ClipClick(clipSticker));
    }

    class ClipClick implements View.OnClickListener {
        final ClipSticker clip_val;

        ClipClick(ClipSticker clipSticker) {
            this.clip_val = clipSticker;
        }

        public void onClick(View view) {
            StickerActivity stickerActivity = StickerActivity.this;
            ClipSticker clipSticker = this.clip_val;
            stickerActivity.stick1 = clipSticker;
            clipSticker.bringToFront();
            StickerActivity.this.DisableAll();
        }
    }

    /* access modifiers changed from: private */
    public void DisableAll() {
        for (int i = 0; i < this.fl_edit.getChildCount(); i++) {
            if (this.fl_edit.getChildAt(i) instanceof ClipSticker) {
                ((ClipSticker) findViewById(this.fl_edit.getChildAt(i).getId())).disableAll();
            }
        }
    }
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
