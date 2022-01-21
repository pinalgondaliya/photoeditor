package com.example.photoeditor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CrownFinalScreen extends AppCompatActivity implements View.OnClickListener {
    String ImagePath;
    int RESULT_FROM_SHARE = 111;
    Bitmap bmp;
    ImageView btnBack;
    ImageView btn_Final_Delete;
    ImageView btn_Final_Share;
    Dialog dialog1;
    public ArrayList<File> fileArrayList = new ArrayList<>();
    TextView final_title;
    ImageView iv_image;
    Context mContext;
    Dialog popup;
    int position;
    Uri selectedImageUri;
    ViewPager viewPager;
    ImageViewPagerAdapter viewPagerAdapter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_crown_final_screen);
        this.mContext = this;
        this.position = getIntent().getIntExtra("position", 0);
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CrownFinalScreen.this.onBackPressed();
            }
        });
        findById();
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageSelected(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
                CrownFinalScreen.this.position = i;
            }
        });
    }

    public void onResume() {
        super.onResume();
        new dataloader().execute(new Void[0]);
    }
    @SuppressLint("WrongConstant")
    public void onBackPressed() {
        MyUtils.isFromScreen = 0;
        boolean booleanExtra = CrownFinalScreen.this.getIntent().getBooleanExtra("just_check", false);
        boolean booleanExtra2 = CrownFinalScreen.this.getIntent().getBooleanExtra("just_check1", false);
        if (booleanExtra) {
           show_popup ();
        } else if (booleanExtra2) {
            CrownFinalScreen.this.finish();
        } else {
            Intent intent = new Intent(CrownFinalScreen.this, start.class);
            intent.addFlags(335544320);
            CrownFinalScreen.this.startActivity(intent);
            CrownFinalScreen.this.finish();
        }
        return;
    }
    @SuppressLint("ResourceType")
    public void show_popup() {
        ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.custom, (ViewGroup) null);
        this.popup = new Dialog(this);
        this.popup.getWindow().setBackgroundDrawableResource(17170445);
        this.popup.setContentView(R.layout.custom);
        this.popup.show();
        ImageView imageView = (ImageView) this.popup.findViewById(R.id.stars);
        LinearLayout mainLayout = (LinearLayout) this.popup.findViewById(R.id.mainLayout);
        ((ImageView) this.popup.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CrownFinalScreen.this.rate_us();
                CrownFinalScreen.this.popup.dismiss();
            }
        });
        ((TextView) this.popup.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (CrownFinalScreen.this.getIntent().getBooleanExtra("just_check", false)) {
                    CrownFinalScreen.this.popup.dismiss();
                    CrownFinalScreen.this.finish();
                    return;
                }
                Intent intent = new Intent(CrownFinalScreen.this, start.class);
                intent.addFlags(335544320);
                CrownFinalScreen.this.startActivity(intent);
                CrownFinalScreen.this.finish();
                CrownFinalScreen.this.popup.dismiss();
            }
        });
    }

    public void rate_us() {
        startActivityForResult(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())), this.RESULT_FROM_SHARE);
    }

    /* access modifiers changed from: package-private */
    public void findById() {
        Typeface.createFromAsset ( getAssets (), "fonts/Roboto-Regular_0.ttf" );
        this.iv_image = (ImageView) findViewById ( R.id.iv_image );
        this.final_title = (TextView) findViewById ( R.id.final_title );
        this.btn_Final_Delete = (ImageView) findViewById ( R.id.btn_Final_Delete );
        this.btn_Final_Delete.setOnClickListener ( this );
        this.btn_Final_Share = (ImageView) findViewById ( R.id.btn_Final_Share );
        this.btn_Final_Share.setOnClickListener ( this );
    }

    public void delete_popup() {
        this.dialog1 = new Dialog(this.mContext);
//        this.dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        this.dialog1.requestWindowFeature(1);
        this.dialog1.setContentView(R.layout.delete_popup);
     //   Helper.setSize((ConstraintLayout) this.dialog1.findViewById(R.id.mainlay), 890, 694, true);
        ImageView imageView = (ImageView) this.dialog1.findViewById(R.id.yes);
        ImageView imageView2 = (ImageView) this.dialog1.findViewById(R.id.no);
        //ImageView imageView3 = (ImageView) this.dialog1.findViewById(R.id.icon);
//        Helper.setSize(imageView3, 140, 140, true);
//        Helper.setSize(imageView, 348, 116, true);
//        Helper.setSize(imageView2, 348, 116, true);
//        Helper.setMargin(imageView3, 0, 80, 0, 0);
//        Helper.setMargin(imageView, 80, 30, 0, 0);
//        Helper.setMargin(imageView2, 0, 30, 80, 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CrownFinalScreen.this.deleteFile();
                CrownFinalScreen.this.dialog1.dismiss();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CrownFinalScreen.this.dialog1.dismiss();
            }
        });
        this.dialog1.show();
    }

    public void deleteFile() {
        File file = new File(this.fileArrayList.get(this.position).getAbsolutePath());
        if (file.exists() && file.delete()) {
            Toast.makeText(getApplicationContext(), "Delete Successfully..", Toast.LENGTH_LONG).show();
            finish();
        }
        if (start.imageadapter != null) {
            start.imageadapter.notifyDataSetChanged();
        }
        finish();
    }


    /* access modifiers changed from: protected */
    public void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
    }


    public void getVideo() {
        File[] listFiles;
        this.fileArrayList.clear();
        File file = new File( Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_DOWNLOADS ) + "/" + getString(R.string.app_name));
        if (file.exists() && (listFiles = file.listFiles()) != null) {
            for (File add : listFiles) {
                this.fileArrayList.add(add);
            }
        }
    }
    @Override
    public void onClick(View v) {
        findViewById(R.id.btn_Final_Share).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String string = CrownFinalScreen.this.getResources().getString(R.string.app_name);
                Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.SUBJECT", string);
                Context applicationContext = CrownFinalScreen.this.getApplicationContext();
                Uri uriForFile = FileProvider.getUriForFile(applicationContext, getApplicationContext ().getPackageName () + ".provider", new File(CrownFinalScreen.this.fileArrayList.get(CrownFinalScreen.this.position).getAbsolutePath()));
                intent.setType("image/jpg");
                intent.setFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION );
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
                CrownFinalScreen.this.startActivity(Intent.createChooser(intent, "Share Image"));

            }
        } );
        findViewById ( R.id.btn_Final_Delete ).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                CrownFinalScreen.this.delete_popup();
               CrownFinalScreen.this.dialog1.show();
            }
        } );
    }

    public class dataloader extends AsyncTask<Void, Void, Void> {
        public dataloader() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            CrownFinalScreen crownFinalScreen = CrownFinalScreen.this;
            crownFinalScreen.viewPagerAdapter = new ImageViewPagerAdapter(crownFinalScreen.fileArrayList, CrownFinalScreen.this);
            CrownFinalScreen.this.viewPager.setAdapter(CrownFinalScreen.this.viewPagerAdapter);
            CrownFinalScreen.this.viewPager.setCurrentItem(CrownFinalScreen.this.position);
        }
        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            CrownFinalScreen.this.getVideo();
            Collections.sort(CrownFinalScreen.this.fileArrayList, new Comparator<File>() {
                public int compare(File file, File file2) {
                    return Long.valueOf(file2.lastModified()).compareTo(Long.valueOf(file.lastModified()));
                }
            });
            return null;
        }
    }

}
