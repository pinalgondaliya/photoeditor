package com.example.photoeditor;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StickerLibrary extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static Bitmap bmp;
    public final int RESULT_FROM_STICKER = CrownEditor.RESULT_FROM_TAG;
    String Sticker_name;
    StickerAdapter adapter;
    String folderName;
    String[] folders;
    ArrayList<FrameModel> frameList = new ArrayList<>();
    ImageLoader imgLoader;

    /* renamed from: l1 */
    LinearLayout f195l1;
    LinearLayout l10;

    /* renamed from: l3 */
    LinearLayout f196l3;

    /* renamed from: l6 */
    LinearLayout f197l6;

    /* renamed from: l7 */
    LinearLayout f198l7;

    /* renamed from: l8 */
    LinearLayout f199l8;
    String[] names;
    int pos;
    GridView stickerGrid;
    ImageView sticker_back;
    ImageView sticker_camera;
    ImageView sticker_doggy;
    ImageView sticker_fall;
    ImageView sticker_flower;
    ImageView sticker_food;
    ImageView sticker_love;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_sticker_library);
        findViewById();
        initImageLoader();
        MySticker("DogyFaces");
        this.sticker_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                StickerLibrary.this.onBackPressed();
            }
        });
    }

    public void findViewById() {
        this.sticker_back = (ImageView) findViewById(R.id.sticker_back);
        this.stickerGrid = (GridView) findViewById(R.id.sticker_grid);
        this.sticker_doggy = (ImageView) findViewById(R.id.sticker_doggy);
        this.sticker_camera = (ImageView) findViewById(R.id.sticker_camera);
        this.sticker_fall = (ImageView) findViewById(R.id.sticker_fall);
        this.sticker_flower = (ImageView) findViewById(R.id.sticker_flower);
        this.sticker_food = (ImageView) findViewById(R.id.sticker_food);
        this.sticker_love = (ImageView) findViewById(R.id.sticker_love);
        this.sticker_doggy.setOnClickListener(this);
        this.sticker_camera.setOnClickListener(this);
        this.sticker_fall.setOnClickListener(this);
        this.sticker_flower.setOnClickListener(this);
        this.sticker_food.setOnClickListener(this);
        this.sticker_love.setOnClickListener(this);
        this.f195l1 = (LinearLayout) findViewById(R.id.l1);
        this.f196l3 = (LinearLayout) findViewById(R.id.l3);
        this.f197l6 = (LinearLayout) findViewById(R.id.l6);
        this.f198l7 = (LinearLayout) findViewById(R.id.l7);
        this.f199l8 = (LinearLayout) findViewById(R.id.l8);
        this.l10 = (LinearLayout) findViewById(R.id.l10);
        setBackSticker();
        this.f195l1.setBackgroundColor(Color.parseColor("#bf000000"));
    }
    private void initImageLoader() {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        this.imgLoader = ImageLoader.getInstance();
        this.imgLoader.init(build);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sticker_camera:
                setBackSticker();
                this.f196l3.setBackgroundColor(Color.parseColor("#bf000000"));
                this.Sticker_name = "Camera";
                MySticker(this.Sticker_name);
                return;
            case R.id.sticker_doggy:
                setBackSticker();
                this.f195l1.setBackgroundColor(Color.parseColor("#bf000000"));
                this.Sticker_name = "DogyFaces";
                MySticker(this.Sticker_name);
                return;
            case R.id.sticker_fall:
                setBackSticker();
                this.f197l6.setBackgroundColor(Color.parseColor("#bf000000"));
                this.Sticker_name = "Fall";
                MySticker(this.Sticker_name);
                return;
            case R.id.sticker_flower:
                setBackSticker();
                this.f198l7.setBackgroundColor(Color.parseColor("#bf000000"));
                this.Sticker_name = "Flower";
                MySticker(this.Sticker_name);
                return;
            case R.id.sticker_food:
                setBackSticker();
                this.f199l8.setBackgroundColor(Color.parseColor("#bf000000"));
                this.Sticker_name = "Food";
                MySticker(this.Sticker_name);
                return;
            case R.id.sticker_love:
                setBackSticker();
                this.l10.setBackgroundColor(Color.parseColor("#bf000000"));
                this.Sticker_name = "Love";
                MySticker(this.Sticker_name);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void setBackSticker() {
        this.f195l1.setBackgroundColor(Color.parseColor("#000000"));
        this.f196l3.setBackgroundColor(Color.parseColor("#000000"));
        this.f197l6.setBackgroundColor(Color.parseColor("#000000"));
        this.f198l7.setBackgroundColor(Color.parseColor("#000000"));
        this.f199l8.setBackgroundColor(Color.parseColor("#000000"));
        this.l10.setBackgroundColor(Color.parseColor("#000000"));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        getWindow().clearFlags(128);
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        System.gc();
        getWindow().clearFlags(128);
        ImageLoader imageLoader = this.imgLoader;
        if (imageLoader != null) {
            imageLoader.clearDiscCache();
            this.imgLoader.clearMemoryCache();
        }
        super.onDestroy();
    }

    private String[] getNames(String str) {
        String[] strArr;
        try {
            strArr = getAssets().list(str);
        } catch (IOException e) {
            e.printStackTrace();
            strArr = null;
        }
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = str + "/" + strArr[i];
        }
        return strArr;
    }

    public void MySticker(String str) {
        this.folderName = str;
        try {
            this.folders = getAssets().list("sticker");
        } catch (IOException unused) {
        }
        this.frameList.clear();
        this.names = getNames("sticker/" + this.folderName);
        for (String str2 : this.names) {
            this.frameList.add(new FrameModel("assets://" + str2, true));
        }
        File file = new File(getFilesDir() + "/sticker/" + this.folderName);
        if (!file.exists()) {
            file.mkdirs();
        }
        for (File file2 : file.listFiles()) {
            this.frameList.add(new FrameModel("file://" + file2.getAbsolutePath(), true));
        }
        this.adapter = new StickerAdapter(this, this.frameList, this.imgLoader);
        this.stickerGrid.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
        this.stickerGrid.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        FrameModel frameModel = this.frameList.get(i);
        new File(frameModel.FramePath).getName();
        this.pos = i;
        this.adapter.notifyDataSetChanged();
        if (frameModel.IsAvailable.booleanValue()) {
            try {
                bmp = BitmapFactory.decodeStream(getAssets().open(this.names[i]));
            } catch (Exception unused) {
                bmp = BitmapFactory.decodeFile(frameModel.FramePath.replace("file://", ""));
            }
            Intent intent = new Intent();
            Utils.urSticker = frameModel.FramePath.replace("file://", "");
            Utils.stickHolder = bmp;
            setResult(-1, intent);
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            finish();
        } else if (i == 333) {
            setResult(-1, new Intent());
            finish();
        }
    }
}

