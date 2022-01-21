package com.example.photoeditor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

public class start extends AppCompatActivity implements View.OnClickListener {
public static final int RESULT_FROM_CAMERA = 1;
public static final int RESULT_FROM_GALLERY = 2;
public static SaveGallaryAlbumAdapter imageadapter = null;
public static boolean isDownloadComplete = false;
public static boolean isDownloadStarted = false;
public static Activity mActivity;
public static File mFileTemp;
public static TextView percentage;
public static Dialog progressDialog;
/* access modifiers changed from: private */
public String PACKAGE_ACITIVITY;
/* access modifiers changed from: private */
public String PACKAGE_NAME;
private String PREFS_NAME = "autostartpref";
private String TITLE;
        String Tag = start.class.getSimpleName();
/* access modifiers changed from: private */
        ImageView btnCamera;
        ImageView btnMyCreation;
        Context ctx;
        ArrayList<String> imgList;
        ImageLoader imgLoader;
        ImageView ivTitale;
        GridView lvImageList;
        String progress1;
        Uri selectedImageUri;
public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_start);
        MyUtils.width = getResources().getDisplayMetrics().widthPixels;
        MyUtils.height = getResources().getDisplayMetrics().heightPixels;
    ActivityCompat.requestPermissions ( this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},123 );
        new RelativeLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 893) / 1152, (getResources().getDisplayMetrics().heightPixels * 543) / 2048).addRule(14);
        String str = this.Tag;
        if ("mounted".equals(Environment.getExternalStorageState())) {
        mFileTemp = new File(Environment.getExternalStorageDirectory(), Utils.TEMP_FILE_NAME);
        } else {
        mFileTemp = new File(getFilesDir(), Utils.TEMP_FILE_NAME);
        }
        FIND_VIEW_BY_ID();
        }

private void FIND_VIEW_BY_ID() {
        this.btnCamera = (ImageView) findViewById(R.id.btnStartCamera);
        this.btnCamera.setOnClickListener(this);
        this.btnMyCreation = (ImageView) findViewById(R.id.btnMyCreation);
        this.btnMyCreation.setOnClickListener(this);
        this.lvImageList = (GridView) findViewById(R.id.lvImageList);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 271) / 1080, (getResources().getDisplayMetrics().heightPixels * 175) / 1920);
        layoutParams.weight = 1.0f;
        layoutParams.setMargins(20, 0, 20, 0);
        }

    @SuppressLint("ResourceType")
    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.btnMyCreation) {
            if (id == R.id.btnStartCamera) {
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.pick_image_dialog);
                dialog.getWindow().setBackgroundDrawableResource(17170445);
                dialog.show();
                ((ImageView) dialog.findViewById(R.id.camera)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (MyUtils.isFromScreen != 0) {
                            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra("output", Uri.fromFile(start.mFileTemp));
                            start.this.startActivityForResult(intent,RESULT_FROM_CAMERA);
                            dialog.dismiss();
                        }  else {
                            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
                            Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent2.putExtra("output", Uri.fromFile(start.mFileTemp));
                            start.this.startActivityForResult(intent2, 1);
                            dialog.dismiss();
                        }
                    }
                });
                ((ImageView) dialog.findViewById(R.id.gallery)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (MyUtils.isFromScreen != 0) {
                            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            start.this.startActivityForResult(intent,RESULT_FROM_GALLERY);
                            dialog.dismiss();
                        }  else {
                            Intent intent2 = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent2.setType("image/*");
                            start.this.startActivityForResult(intent2, 2);
                            dialog.dismiss();
                        }
                    }
                });
            }
        } else if (MyUtils.isFromScreen != 0) {
            startActivity(new Intent(getApplicationContext(), MyCreationActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), MyCreationActivity.class));
        }

    }
/* access modifiers changed from: protected */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (resultCode != -1) {
            return;
        }
        if (requestCode == 1) {
            File file = mFileTemp;
            if (file != null) {
                Utils.selectedImageUri = Uri.fromFile ( file );
            }
            Intent intent2 = new Intent ( this, crop.class );
            intent2.putExtra ( "isFromMain", true );
            intent2.setFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION );
            startActivityForResult ( intent2 ,1 );
            finish ();
        }
        else if (requestCode == 2) {
            this.selectedImageUri = data.getData ();
            Utils.selectedImageUri = this.selectedImageUri;
            Intent intent3 = new Intent ( this, crop.class );
            intent3.putExtra ( "isFromMain", true );
            intent3.setFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION );
            startActivityForResult ( intent3,2 );
            finish ();
        }
    }

    private void initImageLoader() {
        Log.d("test", "init image loader");
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).displayer(new FadeInBitmapDisplayer(100)).build()).build();
        this.imgLoader = ImageLoader.getInstance();
        this.imgLoader.init(build);
        }

public void showExitDialog() {
        @SuppressLint("WrongConstant") View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.exit_dialog, (ViewGroup) null);
final Dialog dialog = new Dialog(this, R.style.Theme_Transparent);
        dialog.setContentView(inflate);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.no);
        ImageView imageView2 = (ImageView) inflate.findViewById(R.id.yes);
        imageView.setOnClickListener(new View.OnClickListener() {
@SuppressLint("WrongConstant")
public void onClick(View view) {
        dialog.dismiss();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(67108864);
        start.this.startActivity(intent);
        start.this.finish();
        }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
public void onClick(View view) {
        dialog.dismiss();
        }
        });
        dialog.show();
        }

public void onActionModeFinished(ActionMode actionMode) {
    super.onActionModeFinished ( actionMode );
    finish ();
}

public void onBackPressed() {
       startActivity(new Intent(this, Exit.class));

        }

/* access modifiers changed from: protected */
public void onStop() {
        super.onStop();
        }

/* access modifiers changed from: protected */
public void onDestroy() {
        super.onDestroy();
        ImageLoader imageLoader = this.imgLoader;
        if (imageLoader != null) {
        imageLoader.clearDiscCache();
        this.imgLoader.clearMemoryCache();
        }
        }

public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        }

public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_settings) {
        return true;
        }
        return super.onOptionsItemSelected(menuItem);
        }
public class InitObject extends AsyncTask<Object, Object, Void> {
    Context context = start.this;
    int flag = 2;

    public InitObject() {
    }
    @Override
    protected Void doInBackground(Object... objects) {
        return null;
    }

    public void onPreExecute() {
        super.onPreExecute();
    }

    public void onPostExecute(Void voidR) {
        int i = this.flag;
    }
}

}







