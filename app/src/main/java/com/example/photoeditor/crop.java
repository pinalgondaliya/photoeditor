package com.example.photoeditor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class  crop extends Activity {

    String ImagePath;
    ImageView back;
    Bitmap bmp;
    ImageView btnDone;
    CropImageView cropImageView;
    Boolean isFromCropper = false;
    Boolean isFromMain = false;
    private File mFileTemp;
    int screenHeight;
    int screenWidth;
    Uri selectedImageUri;
    TextView txt_crop;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
      //  requestWindowFeature(1);
       // getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_crop);
        findById();

        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
        this.isFromMain = getIntent().getBooleanExtra("isFromMain", false);
        this.isFromCropper = getIntent().getBooleanExtra("isFromCropper", false);
        if (Utils.selectedImageUri != null) {
            this.selectedImageUri = Utils.selectedImageUri;
            try {
                this.bmp = useImage(this.selectedImageUri);
                Log.i("TAG", "onCreate: " + bmp);
                this.bmp = this.bmp.copy(Bitmap.Config.ARGB_8888, true);
                this.bmp = bitmapResize(this.bmp, this.screenHeight, this.screenWidth);
            } catch (IOException e) {
                e.printStackTrace();

            }
        } else {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                this.mFileTemp = new File(Environment.getExternalStorageDirectory(), Utils.TEMP_FILE_NAME);
            } else {
                this.mFileTemp = new File(getFilesDir(), Utils.TEMP_FILE_NAME);
            }
            this.bmp = BitmapCompression.decodeFile(this.mFileTemp, this.screenHeight, this.screenWidth);
            this.bmp = BitmapCompression.adjustImageOrientation(this.mFileTemp, this.bmp);
            this.bmp = this.bmp.copy(Bitmap.Config.ARGB_8888, true);
        }
        if (this.isFromCropper.booleanValue()) {
            this.bmp = Utils.imageHolder;

        }
        this.cropImageView.setImageBitmap(this.bmp);
        super.onCreate(bundle);
    }


    public Bitmap useImage(Uri uri) throws FileNotFoundException, IOException {
        return Utils.rotateImageIfRequired(getApplicationContext(),
                MediaStore.Images.Media.getBitmap(getContentResolver(), uri), uri);


    }
    @SuppressLint("WrongConstant")
    public void onBackPressed() {
        MyUtils.isFromScreen = 0;
        if (this.isFromMain) {
            finish();
            Intent intent = new Intent(this, crop.class);
           // intent.addFlags(335544320);
            startActivity(intent);
            return;
        }
        finish();
    }

    /* access modifiers changed from: package-private */
    public void findById() {
        Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular_0.ttf");
        this.txt_crop = (TextView) findViewById(R.id.txt_crop);
        this.cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        this.cropImageView.setFixedAspectRatio(false);
        new Handler().postDelayed(new Runnable() {
            public void run() {


                crop.this.cropImageView.setAspectRatio(crop.this.screenWidth, crop.this.screenHeight);
                crop crop = crop.this;
                crop.btnDone = (ImageView) crop.findViewById(R.id.btnDone);
                crop.this.btnDone.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Bitmap croppedImage = crop.this.cropImageView.getCroppedImage();
                        Log.i("TAG", "onClick: " + croppedImage);
                        Utils.imageHolder = croppedImage;
                        Utils.saveBitmapImage(croppedImage);
                        if (crop.this.isFromMain) {
                            Utils.selectedImageUri = null;
                            crop.this.finish();
                            Intent intent = new Intent(crop.this, EditorActivity.class);
                            intent.putExtra("isFromMain", true);
                            crop.this.startActivity(intent);
                            return;
                        }


                        crop.this.setResult(-1, new Intent());
                        crop.this.finish();
                    }
                });

                crop crop2 = crop.this;
                crop2.back = (ImageView) crop2.findViewById(R.id.back);
                crop.this.back.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        crop.this.onBackPressed();
                    }
                });
            }
        }, 1500);
    }

    public void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
    }
    public static Bitmap bitmapResize(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width >= height) {
            int i3 = (height * i) / width;
            if (i3 > i2) {
                i = (i * i2) / i3;
            } else {
                i2 = i3;
            }
        } else {
            int i4 = (width * i2) / height;
            if (i4 > i) {
                i2 = (i2 * i) / i4;
            } else {
                i = i4;
            }
        }
        return Bitmap.createScaledBitmap(bitmap, i, i2, true);
    }
}
