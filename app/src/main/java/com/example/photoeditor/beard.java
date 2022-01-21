package com.example.photoeditor;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class beard extends Activity implements AdapterView.OnItemClickListener {
    public static Bitmap bmp;
    ImageAdapter adapter;
    Button apply;
    ImageView back;
    ImageView download_crown;
    String folderName;
    String[] folders;

    /* renamed from: gv */
    GridView gridView;

    /* renamed from: h */
    int f208h;
    /* access modifiers changed from: private */
    public ImageLoader imageLoader;
    ArrayList<HsItem> list1 = new ArrayList<>();
    ArrayList<HsItem> list10 = new ArrayList<>();
    ArrayList<HsItem> list11 = new ArrayList<>();
    ArrayList<HsItem> list12 = new ArrayList<>();
    ArrayList<HsItem> list13 = new ArrayList<>();
    ArrayList<HsItem> list14 = new ArrayList<>();
    ArrayList<HsItem> list15 = new ArrayList<>();
    ArrayList<HsItem> list16 = new ArrayList<>();
    ArrayList<HsItem> list17 = new ArrayList<>();
    ArrayList<HsItem> list18 = new ArrayList<>();
    ArrayList<HsItem> list2 = new ArrayList<>();
    ArrayList<HsItem> list3 = new ArrayList<>();
    ArrayList<HsItem> list4 = new ArrayList<>();
    ArrayList<HsItem> list5 = new ArrayList<>();
    ArrayList<HsItem> list6 = new ArrayList<>();
    ArrayList<HsItem> list7 = new ArrayList<>();
    ArrayList<HsItem> list8 = new ArrayList<>();
    ArrayList<HsItem> list9 = new ArrayList<>();
    String[] names;

    /* renamed from: om */
    DisplayMetrics f209om;
    DisplayImageOptions optsFull;
    DisplayImageOptions optsThumb;
    int pos = 0;
    TextView txt_gallery;

    /* renamed from: w */
    int f210w;
    int width;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_beard);
        this.f209om = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(this.f209om);
        this.f210w = this.f209om.widthPixels;
        this.f208h = this.f209om.heightPixels;
        try {
            this.folders = getAssets().list("stickers");
        } catch (IOException unused) {
        }
        this.folderName = getIntent().getStringExtra("folderName");
        this.back = (ImageView) findViewById(R.id.btnBack);
        Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular_0.ttf");
        this.txt_gallery = (TextView) findViewById(R.id.txt_gallery);
        this.apply = (Button) findViewById(R.id.btnApply);
        this.back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                beard.this.onBackPressed();
            }
        });
        this.list1.clear();
        StringBuilder sb = new StringBuilder();
        sb.append("stickers/");
        sb.append(this.folders[0]);
        this.names = getNames(sb.toString());
        for (String str : this.names) {
            this.list1.add(new HsItem("assets://" + str, true));
        }
        File file = new File(getFilesDir() + "/Stickers/" + this.folderName);
        if (!file.exists()) {
            file.mkdirs();
        }
        for (File file2 : file.listFiles()) {
            this.list1.add(new HsItem("file://" + file2.getAbsolutePath(), true));
        }
        initImageLoader();
        this.imageLoader = ImageLoader.getInstance();
        this.gridView = (GridView) findViewById(R.id.gridView1);
        this.adapter = new ImageAdapter(this);
        this.gridView.setAdapter(this.adapter);
        this.gridView.setOnItemClickListener(this);
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

    private class ImageAdapter extends BaseAdapter {
        public Activity activity;
        private LayoutInflater inflater = null;
        int width;

        public long getItemId(int i) {
            return (long) i;
        }

        public ImageAdapter(Activity activity2) {
            this.activity = activity2;
            this.inflater = LayoutInflater.from(this.activity);
            this.inflater = this.activity.getLayoutInflater();
            this.width = this.activity.getResources().getDisplayMetrics().widthPixels / 3;
        }

        public int getCount() {
            return beard.this.list1.size();
        }

        public Object getItem(int i) {
            return beard.this.list1.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2;
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view2 = this.inflater.inflate(R.layout.sub_sticker_imge_raw, (ViewGroup) null);
                int i2 = this.width;
                view2.setLayoutParams(new AbsListView.LayoutParams(i2, i2));
                viewHolder.ll_border = (LinearLayout) view2.findViewById(R.id.ll_border);
                viewHolder.f211iv = (ImageView) view2.findViewById(R.id.ivpip_tiny);
                viewHolder.f211iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                view2.setTag(viewHolder);
            }

            else {
                view2 = view;
                viewHolder = (ViewHolder) view.getTag();
            }


            HsItem hsItem = beard.this.list1.get(i);
            if (hsItem.isAvailable) {

                beard.this.imageLoader.displayImage(hsItem.path, viewHolder.f211iv, beard.this.optsFull);
            } else {

                beard.this.imageLoader.displayImage(hsItem.path, viewHolder.f211iv, beard.this.optsThumb);
            }

            viewHolder.f211iv.setTag(hsItem.path);
            return view2;
        }

        public class ViewHolder {
            ImageView f211iv;
            LinearLayout ll_border;

            public ViewHolder() {

            }
        }
    }
    private void initImageLoader() {
        this.optsFull = new DisplayImageOptions.Builder().cacheOnDisk(true).imageScaleType(ImageScaleType.NONE).showImageOnLoading(0).bitmapConfig(Bitmap.Config.ARGB_4444).build();
        this.optsThumb = new DisplayImageOptions.Builder().cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.ARGB_4444).build();
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(this.optsThumb).memoryCache(new WeakMemoryCache()).build());
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        HsItem hsItem = this.list1.get(i);
        new File(hsItem.path).getName();
        this.pos = i;
        this.adapter.notifyDataSetChanged();
        if (hsItem.isAvailable) {
            try {
                bmp = BitmapFactory.decodeStream(getAssets().open(this.names[i]));
            } catch (Exception unused) {
                bmp = BitmapFactory.decodeFile(hsItem.path.replace("file://", ""));
            }
            ((ImageView) ((LinearLayout) view).findViewById( R.id.ivpip_tiny)).getTag().toString();
            Log.d("path123", hsItem.path);
            Intent intent = new Intent();
            intent.putExtra("nam", hsItem.path);
            setResult(-1, intent);
            finish();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 101) {
            String stringExtra = intent.getStringExtra("dirPath");
            intent.getStringExtra("imagName");
            String str = stringExtra.replace("/file:", "") + "/" + intent.getStringExtra("imagName");
            bmp = BitmapFactory.decodeFile(str);
            Intent intent2 = new Intent();
            intent2.putExtra("nam", "file://" + str);
            setResult(-1, intent2);
            finish();
        }

        super.onActivityResult(i, i2, intent);
    }
}
