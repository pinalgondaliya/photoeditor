package com.example.photoeditor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ZoomControls;

import androidx.core.content.FileProvider;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class EditorActivity extends Activity implements View.OnClickListener {
    public static final int BACK_FROM_ACTIVITY = 98;
    public static final int FROM_CROWN_EDIT = 456;
    public static final int RESULT_FROM_BACK_GRID = 7;
    public static final int RESULT_FROM_CAMERA = 1;
    public static final int RESULT_FROM_CATEGORY = 555;
    public static final int RESULT_FROM_GALLERY = 2;
    public static final int RESULT_FROM_STICKER = 4;
    static String destPath = "";
    public static Uri mImageSavedUri;
    public static Bitmap selectedImg;
    public static int selectedPos;
     public static ArrayList<SingleFingerView> viewsList = new ArrayList<>();
    Animation Anim;
    int BitHeight;
    int BitHeight2;
    int BitWidth;
    int BitWidth2;
    CustomZoomableImageView CustomZoomView;

    /* renamed from: Dm */
    DisplayMetrics f145Dm;
    String FOLDER_NAME;
    LinearLayout Include_LL_Seekbar;
    SeekBar Opacity_seekBar;
    String[] StickerFolder;
    String Tag = EditorActivity.class.getSimpleName ();
    ZoomControls ZoomControls;
    int alpha;
    String applicationName;
    ImageView background;
    ImageView btnCategory;
    ImageView btnColor;
    ImageView btnEdit;
    ImageView btnEditGallery;
    ImageView btnEditSticker;
    ImageView btnEditStickerOpacity;
    Button btnMore;
    ImageView btnSave;
    ImageView btnShare;
    ImageView btnback;
    int clickCount = 0;
    int col;
    String[] colorArr = {"#ffffffff", "#0b0603", "#3c3230", "#4a3427", "#774631", "#7d4c29",
            "#673b18", "#7a4a1a", "#986337", "#753e10", "#d29668", "#b99172", "#b28d5c", "#c8a880",
            "#c8986f", "#c09566", "#ce9d75", "#b37c54", "#a15c47", "#bd6d54", "#772310", "#c9b79f",
            "#e0b693", "#99273b", "#0a2d62", "#c60d32", "#8cbd26", "#0f7e6c", "#e51843", "#6e03a7",
            "#cba695", "#660200", "#6b0131"};
    LinearLayout colorLayout;
    List<String> colorList;
    List<String> colorlist;
    private int counter;
    Bitmap croppedEditImage;
    Bitmap croppedImage;
    File file;
    FrameLayout flEditor;

    private static final String TAG = "EditorActivity";

    /* renamed from: h */
    int f146h;
    ImageButton ib_seekbar_Close;
    String imagName;
    private boolean isPause = false;
    ArrayList<FrameLayout> ivId = new ArrayList<> ();
    ImageView ivPhotoImages;
    LinearLayout ll_btn_container;
    LinearLayout ll_container;
    LinearLayout llseekbar;
    File mFileTemp;
    File mFileTemp2;
    File mGalleryFolder;
    Uri mImageUri;
    private ArrayList<View> mViews;
    Matrix matrix = new Matrix ();
    public DisplayImageOptions options;
    FrameLayout.LayoutParams params;
    String path;
    /* access modifiers changed from: private */

    /* renamed from: pd */
    public ProgressDialog f147pd;
    ProgressBar progress;
    LinearLayout rl_color;
    Uri screenshotUri;
    Uri selectedImageUri;
    Uri selectedImageUri2;
    // SingleFingerView sfv;
    ClipSticker stick1 = null;
    TextView txt_editor;

    /* renamed from: w */
    int f148w;

    public static Bitmap TrimBitmap(Bitmap bitmap) {
        int height = bitmap.getHeight ();
        int width = bitmap.getWidth ();
        int i = 0;
        for (int i2 = 0; i2 < width && i == 0; i2++) {
            int i3 = 0;
            while (true) {
                if (i3 >= height) {
                    break;
                } else if (bitmap.getPixel ( i2, i3 ) != 0) {
                    i = i2;
                    break;
                } else {
                    i3++;
                }
            }
        }
        int i4 = 0;
        for (int i5 = width - 1; i5 >= 0 && i4 == 0; i5--) {
            int i6 = 0;
            while (true) {
                if (i6 >= height) {
                    break;
                } else if (bitmap.getPixel ( i5, i6 ) != 0) {
                    i4 = i5;
                    break;
                } else {
                    i6++;
                }
            }
        }
        int i7 = 0;
        for (int i8 = 0; i8 < height && i7 == 0; i8++) {
            int i9 = 0;
            while (true) {
                if (i9 >= width) {
                    break;
                } else if (bitmap.getPixel ( i9, i8 ) != 0) {
                    i7 = i8;
                    break;
                } else {
                    i9++;
                }
            }
        }
        int i10 = 0;
        for (int i11 = height - 1; i11 >= 0 && i10 == 0; i11--) {
            int i12 = 0;
            while (true) {
                if (i12 >= width) {
                    break;
                } else if (bitmap.getPixel ( i12, i11 ) != 0) {
                    i10 = i11;
                    break;
                } else {
                    i12++;
                }
            }
        }
        return Bitmap.createBitmap ( bitmap, i, i7, i4 - i, i10 - i7 );
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        String[] strArr;
        super.onCreate ( bundle );
        getWindow ().addFlags ( 128 );
        requestWindowFeature ( 1 );
        getWindow ().setFlags ( 1024, 1024 );
        setContentView ( R.layout.activity_editor );
        String str = this.Tag;
        Log.d ( str, "Name Of Tag is : " + this.Tag );
        this.f145Dm = new DisplayMetrics ();
        getWindowManager ().getDefaultDisplay ().getMetrics ( this.f145Dm );
        this.f146h = this.f145Dm.heightPixels;
        this.f148w = this.f145Dm.widthPixels;
        this.mViews = new ArrayList<> ();
        this.applicationName = getResources ().getString ( R.string.app_name );
        this.mGalleryFolder = createFolders ();
        try {
            strArr = getAssets ().list ( "color" );
        } catch (IOException e) {
            e.printStackTrace ();
            strArr = null;
        }
        this.colorList = Arrays.asList ( strArr );
        if ("mounted".equals ( Environment.getExternalStorageState () )) {
            this.mFileTemp2 = new File ( Environment.getExternalStorageDirectory (), Utils.TEMP_FILE_NAME2 );
        } else {
            this.mFileTemp2 = new File ( getFilesDir (), Utils.TEMP_FILE_NAME2 );
        }
        if ("mounted".equals ( Environment.getExternalStorageState () )) {
            this.mFileTemp = new File ( Environment.getExternalStorageDirectory (), Utils.TEMP_FILE_NAME );
        } else {
            this.mFileTemp = new File ( getFilesDir (), Utils.TEMP_FILE_NAME );
        }
        if (Utils.selectedImageUri != null) {
            this.selectedImageUri = Utils.selectedImageUri;
            try {
                this.croppedImage = BitmapCompression.getCorrectlyOrientedImage ( getApplicationContext (), this.selectedImageUri, this.f146h );
                this.BitWidth = this.croppedImage.getWidth ();
                this.BitHeight = this.croppedImage.getHeight ();
                String str2 = this.Tag;
                Log.d ( str2, "bit width" + this.BitWidth );
                String str3 = this.Tag;
                Log.d ( str3, "bit height" + this.BitHeight );
                this.croppedImage = Bitmap.createScaledBitmap ( this.croppedImage, this.BitWidth, this.BitHeight, false );
                this.croppedImage = this.croppedImage.copy ( Bitmap.Config.ARGB_8888, true );
            } catch (IOException e2) {
                e2.printStackTrace ();
            }
        } else {
            this.croppedImage = BitmapCompression.decodeFile ( this.mFileTemp, this.f146h, this.f148w );
            File file2 = this.mFileTemp;
            if (file2 != null) {
                this.croppedImage = BitmapCompression.adjustImageOrientation ( file2, this.croppedImage );
            }
            this.BitWidth = this.croppedImage.getWidth ();
            this.BitHeight = this.croppedImage.getHeight ();
            this.croppedImage = Bitmap.createScaledBitmap ( this.croppedImage, this.BitWidth, this.BitHeight, false );
            this.croppedImage = this.croppedImage.copy ( Bitmap.Config.ARGB_8888, true );
        }
        try {
            this.StickerFolder = getAssets ().list ( "stickers" );
        } catch (IOException unused) {
        }
        FIND_VIEW_BY_ID ();
    }

    @SuppressLint("WrongConstant")
    private void FIND_VIEW_BY_ID() {
        this.progress = (ProgressBar) findViewById ( R.id.pbHeaderProgress );
        this.progress.setVisibility ( 0 );
        Typeface.createFromAsset ( getAssets (), "fonts/Roboto-Regular_0.ttf" );
        this.txt_editor = (TextView) findViewById ( R.id.txt_editor );
        this.ll_container = (LinearLayout) findViewById ( R.id.ll_container );
        this.colorLayout = (LinearLayout) findViewById ( R.id.color_layout );
        this.flEditor = (FrameLayout) findViewById ( R.id.flEditor );
        this.btnSave = (ImageView) findViewById ( R.id.btnSave );
        this.ivPhotoImages = (ImageView) findViewById ( R.id.ivPhotoImage );
        this.ivPhotoImages.setOnTouchListener ( new View.OnTouchListener () {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                EditorActivity.this.DisableAll ();
                return false;
            }
        } );
        new Handler ().postDelayed ( new Runnable () {
            @SuppressLint("WrongConstant")
            public void run() {
                Bitmap bitmapResize = EditorActivity.this.bitmapResize ();
                Utils.imageHolder = bitmapResize;
                Utils.originalBitmap = bitmapResize;
                Utils.imgHeight = bitmapResize.getHeight ();
                Utils.imgWidth = bitmapResize.getWidth ();
                EditorActivity.this.flEditor.setLayoutParams ( new LinearLayout.LayoutParams ( bitmapResize.getWidth (), bitmapResize.getHeight () ) );
                EditorActivity.this.ivPhotoImages.setLayoutParams ( new FrameLayout.LayoutParams ( bitmapResize.getWidth (), bitmapResize.getHeight () ) );
                EditorActivity.this.ivPhotoImages.setImageBitmap ( bitmapResize );
                EditorActivity.this.progress.setVisibility ( 8 );
                EditorActivity.this.ivPhotoImages.setAlpha ( 0.0f );
                EditorActivity.this.ivPhotoImages.setVisibility ( 0 );
                EditorActivity.this.ivPhotoImages.animate ().alpha ( 1.0f ).setDuration ( 1000 ).start ();
                EditorActivity editorActivity = EditorActivity.this;
                editorActivity.btnSave = (ImageView) editorActivity.findViewById ( R.id.btnSave );
                EditorActivity.this.btnSave.setOnClickListener ( EditorActivity.this );
            }
        }, 2000 );
        String str = this.Tag;
        Log.d ( str, "ivPhotoImages width" + this.ivPhotoImages.getWidth () );
        String str2 = this.Tag;
        Log.d ( str2, "ivPhotoImages height" + this.ivPhotoImages.getHeight () );
        this.rl_color = (LinearLayout) findViewById ( R.id.rl_color );
        this.rl_color.setVisibility ( 8 );
        this.btnEdit = (ImageView) findViewById ( R.id.btnEdit );
        this.btnEdit.setOnClickListener ( this );
        this.btnColor = (ImageView) findViewById ( R.id.btnColor );
        this.btnColor.setOnClickListener ( this );
        this.btnCategory = (ImageView) findViewById ( R.id.btnCategory );
        this.btnCategory.setOnClickListener ( this );
        this.btnShare = (ImageView) findViewById ( R.id.btnShare );
        this.btnShare.setOnClickListener ( this );
        this.btnback = (ImageView) findViewById ( R.id.btnBack );
        this.btnback.setOnClickListener ( new View.OnClickListener () {
            public void onClick(View view) {
                EditorActivity.this.onBackPressed ();
            }
        } );
        this.btnMore = (Button) findViewById ( R.id.btnMore );
        this.btnMore.setOnClickListener ( this );
        this.btnSave = (ImageView) findViewById ( R.id.btnSave );
        this.btnSave.setOnClickListener ( this );
        this.btnEditSticker = (ImageView) findViewById ( R.id.btnEditSticker );
        this.btnEditSticker.setOnClickListener ( this );
        this.btnEditStickerOpacity = (ImageView) findViewById ( R.id.btnEditStickerOpacity );
        this.btnEditStickerOpacity.setOnClickListener ( this );
        this.btnEditGallery = (ImageView) findViewById ( R.id.btnEditGallery );
        this.btnEditGallery.setOnClickListener ( this );
        this.CustomZoomView = (CustomZoomableImageView) findViewById ( R.id.ZoomableImageView );
        this.params = new FrameLayout.LayoutParams ( this.f148w, this.f146h );
        FrameLayout.LayoutParams layoutParams = this.params;
        layoutParams.leftMargin = 0;
        layoutParams.topMargin = 0;
        layoutParams.gravity = 17;
        this.ib_seekbar_Close = (ImageButton) findViewById ( R.id.ib_seekbar_Close );
        this.ib_seekbar_Close.setOnClickListener ( this );
        this.Include_LL_Seekbar = (LinearLayout) findViewById ( R.id.rl_opacity );
        this.ll_btn_container = (LinearLayout) findViewById ( R.id.ll_btn_container );
        this.ll_btn_container.setVisibility ( 0 );
        this.llseekbar = (LinearLayout) findViewById ( R.id.ll_slider_view );
        this.Opacity_seekBar = (SeekBar) findViewById ( R.id.slider_view_seek_bar );
        this.ivId.clear ();


        for (int i = 0; i < this.colorList.size (); i++) {
            Bitmap bitmap = null;
            View inflate = LayoutInflater.from ( this ).inflate ( R.layout.thumb_hs, (ViewGroup) null, false );
            FrameLayout frameLayout = (FrameLayout) inflate.findViewById ( R.id.frame );
            final int applyDimension = (int) TypedValue.applyDimension ( 1, 50.0f, getResources ().getDisplayMetrics () );
            ImageView imageView = (ImageView) inflate.findViewById ( R.id.back );
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams ( applyDimension, applyDimension );
            layoutParams2.setMargins ( 5, 5, 5, 5 );
            frameLayout.setTag ( Integer.valueOf ( i ) );
            try {
                bitmap = getBitmapFromAsset ( "color", this.colorList.get ( i ) );
            } catch (IOException e) {
                e.printStackTrace ();
            }


            Bitmap createBitmap = Bitmap.createBitmap ( bitmap.getWidth (), bitmap.getHeight (), bitmap.getConfig () );
            Canvas canvas = new Canvas ( createBitmap );
            Paint paint = new Paint ();
            paint.setAntiAlias ( true );
            paint.setShader ( new BitmapShader ( bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP ) );
            canvas.drawRoundRect ( new RectF ( 0.0f, 0.0f, (float) bitmap.getWidth (), (float) bitmap.getHeight () ), 30.0f, 30.0f, paint );
            imageView.setBackground ( new BitmapDrawable ( getResources (), createBitmap ) );
            frameLayout.setLayoutParams ( layoutParams2 );
            frameLayout.setOnClickListener ( new View.OnClickListener () {
                public void onClick(View view) {
                    EditorActivity.this.clickCount++;
                    if (EditorActivity.this.clickCount == 3) {
                        EditorActivity.this.clickCount = 0;
                    }
                    int intValue = ((Integer) view.getTag ()).intValue ();
                    for (int i = 0; i < EditorActivity.this.ivId.size (); i++) {
                        try {
                            EditorActivity.this.stick1.setColor ( Color.parseColor ( EditorActivity.this.colorArr[intValue] ) );
                        } catch (Exception unused) {
                        }
                        if (EditorActivity.this.ivId.get ( i ) == view) {
                            ImageView imageView = (ImageView) EditorActivity.this.ivId.get ( i ).getChildAt ( 1 );
                            int i2 = applyDimension;
                            imageView.setLayoutParams ( new FrameLayout.LayoutParams ( i2, i2 ) );
                            imageView.setImageResource ( R.drawable.thumb_select );
                        } else {
                            ImageView imageView2 = (ImageView) EditorActivity.this.ivId.get ( i ).getChildAt ( 1 );
                            int i3 = applyDimension;
                            imageView2.setLayoutParams ( new FrameLayout.LayoutParams ( i3, i3 ) );
                            imageView2.setImageResource ( R.drawable.color_thumb );
                        }
                    }
                }
            } );
            this.ivId.add ( frameLayout );
            for (int i2 = 0; i2 < this.ivId.size (); i2++) {
                ImageView imageView2 = (ImageView) this.ivId.get ( i2 ).getChildAt ( 1 );
                imageView2.setLayoutParams ( new FrameLayout.LayoutParams ( applyDimension, applyDimension ) );
                imageView2.setImageResource ( R.drawable.color_thumb );
            }
            this.colorLayout.addView ( inflate );
        }
        this.Opacity_seekBar.setMax ( 100 );
        int i3 = this.alpha;
        if (i3 > 0) {
            this.Opacity_seekBar.setProgress ( i3 );
        } else {
            this.Opacity_seekBar.setProgress ( 100 );
        }
        this.Opacity_seekBar.setOnSeekBarChangeListener ( new SeekBar.OnSeekBarChangeListener () {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EditorActivity editorActivity = EditorActivity.this;
                editorActivity.alpha = i;
                float f = ((float) i) / 100.0f;
                if (editorActivity.stick1 != null) {
                    EditorActivity.this.stick1.adjustOpacity ( f );
                }
            }
        } );
    }

    private Bitmap getBitmapFromAsset(String str, String str2) throws IOException {
        InputStream inputStream;
        AssetManager assets = getAssets ();
        try {
            inputStream = assets.open ( str + "/" + str2 );
        } catch (IOException e) {
            e.printStackTrace ();
            inputStream = null;
        }
        return BitmapFactory.decodeStream ( inputStream );
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        switch (view.getId ()) {
            case R.id.btnColor /*2131296373*/:
                this.clickCount = 0;
                if (this.rl_color.getVisibility () == 0) {
                    this.rl_color.setVisibility ( 8 );
                    this.Anim = AnimationUtils.loadAnimation ( this, R.anim.close_menu );
                    this.Anim.setDuration ( 200 );
                    this.rl_color.startAnimation ( this.Anim );
                    return;
                }
                if (this.Include_LL_Seekbar.getVisibility () == 0) {
                    this.Include_LL_Seekbar.setVisibility ( 8 );
                }
                this.rl_color.setVisibility ( 0 );
                this.Anim = AnimationUtils.loadAnimation ( this, R.anim.open_menu );
                this.Anim.setDuration ( 200 );
                this.rl_color.startAnimation ( this.Anim );
                return;
            case R.id.btnEdit /*2131296376*/:
                Utils.imageHolder = this.croppedImage;
                startActivityForResult ( new Intent ( this, CrownEditor.class ), FROM_CROWN_EDIT );
                return;
            case R.id.btnSave:
                saveImage ();
                return;
            case R.id.btnEditGallery /*2131296377*/:
                selectPipPhoto ();
                return;
            case R.id.btnEditSticker /*2131296378*/:
                Intent intent = new Intent ( this, beard.class );
                intent.putExtra ( "folderName", this.StickerFolder[0] );
                intent.setFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION );
                startActivityForResult ( intent, 4 );
                return;
            case R.id.btnEditStickerOpacity /*2131296379*/:
                Opacity_seekbar_open_close ();
                return;
            case R.id.btnShare /*2131296385*/:
                new shareTask ().execute ( new Void[0] );
                return;
            case R.id.ib_seekbar_Close /*2131296503*/:
                if (this.Include_LL_Seekbar.getVisibility () == 8) {
                    this.Include_LL_Seekbar.setVisibility ( 0 );
                    this.Anim = AnimationUtils.loadAnimation ( this, R.anim.open_menu );
                    this.Anim.setDuration ( 200 );
                    this.Include_LL_Seekbar.startAnimation ( this.Anim );
                }
                if (this.Include_LL_Seekbar.getVisibility () == 0) {
                    this.Include_LL_Seekbar.setVisibility ( 8 );
                    this.Anim = AnimationUtils.loadAnimation ( this, R.anim.close_menu );
                    this.Anim.setDuration ( 200 );
                    this.Include_LL_Seekbar.startAnimation ( this.Anim );
                    return;
                }
                return;
            default:
                return;
        }
    }

    @SuppressLint("WrongConstant")
    private void Opacity_seekbar_open_close() {
        if (this.Include_LL_Seekbar.getVisibility () == 8) {
            if (this.rl_color.getVisibility () == 0) {
                this.rl_color.setVisibility ( 8 );
            }
            this.Include_LL_Seekbar.setVisibility ( 0 );
            this.Anim = AnimationUtils.loadAnimation ( this, R.anim.open_menu );
            this.Anim.setDuration ( 200 );
            this.Include_LL_Seekbar.startAnimation ( this.Anim );
            return;
        }
        this.Include_LL_Seekbar.setVisibility ( 8 );
        this.Anim = AnimationUtils.loadAnimation ( this, R.anim.close_menu );
        this.Anim.setDuration ( 200 );
        this.Include_LL_Seekbar.startAnimation ( this.Anim );
    }

    private File createFolders() {
        File file2;
        if (Build.VERSION.SDK_INT < 8) {
            file2 = Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_DOWNLOADS );
        } else {
            file2 = Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_DOWNLOADS );
        }
        if (file2 == null) {
            return Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_DOWNLOADS );
        }
        File file3 = new File ( file2, this.applicationName );
        if (!file3.exists () && !file3.mkdirs ()) {
            return Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_DOWNLOADS );
        }
        return file3;
    }

    public Bitmap getFrameBitmap() {
        this.flEditor.postInvalidate ();
        this.flEditor.setDrawingCacheEnabled ( true );
        this.flEditor.buildDrawingCache ();
        Bitmap createBitmap = Bitmap.createBitmap ( this.flEditor.getDrawingCache () );
        this.flEditor.setDrawingCacheEnabled ( false );
        this.flEditor.destroyDrawingCache ();
        return createBitmap;
    }

    /* access modifiers changed from: private */
    public boolean saveImage() {
        this.path = getOutPutPath ();
        this.file = new File ( this.path );

        if (this.file.exists ()) {
            this.file.delete ();
        }

        try {
            selectedImg = getFrameBitmap ();
            mImageSavedUri = Uri.parse ( "file://" + this.file.getPath () );
            FileOutputStream fileOutputStream = new FileOutputStream ( this.file );
            selectedImg = TrimBitmap ( selectedImg );
            selectedImg.compress ( Bitmap.CompressFormat.JPEG, 80, fileOutputStream );
            fileOutputStream.flush ();
            fileOutputStream.close ();
            MediaScannerConnection.scanFile ( this, new String[]{this.file.getAbsolutePath ()}, new String[]{"image/jpg"}, (MediaScannerConnection.OnScanCompletedListener) null );
            refreshGallery ( this.file );
            return true;
        } catch (Exception e) {
            e.printStackTrace ();
            return false;
        } finally {
            Intent intent = new Intent ( EditorActivity.this.getApplicationContext (), CrownFinalScreen.class );
            startActivity ( intent );
        }

    }

    private void refreshGallery(File file2) {
        Intent intent = new Intent ( "android.intent.action.MEDIA_SCANNER_SCAN_FILE" );
        intent.setData ( FileProvider.getUriForFile ( this, getApplicationContext ().getPackageName () + ".provider", file2 ) );
        intent.setFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION );
        sendBroadcast ( intent );
    }

    public String getOutPutPath() {

        File parent = new File ( Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_DOWNLOADS ), applicationName );

        if (!parent.exists ()) {
            boolean s = parent.mkdir ();
            Log.i ( TAG, "getOutPutPath: "+ s );
        }
        return new File ( parent, "IPLSnap_" + System.currentTimeMillis () + ".jpg" ).getAbsolutePath ();


//        return Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_DOWNLOADS ) +
//                "/" + this.applicationName + "/IPLSnap_" + System.currentTimeMillis () + ".jpg";
    }

    private void selectPipPhoto() {
        @SuppressLint("WrongConstant") View inflate = ((LayoutInflater) getSystemService ( "layout_inflater" )).inflate ( R.layout.gallery_dialog, (ViewGroup) null );
        final Dialog dialog = new Dialog ( this, R.style.Theme_Transparent );
        dialog.setContentView ( inflate );
        ImageView imageView = (ImageView) inflate.findViewById ( R.id.ivgallery );
        ImageView imageView2 = (ImageView) inflate.findViewById ( R.id.ivcamera );
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams ( (getResources ().getDisplayMetrics ().widthPixels * 920) / 1080, (getResources ().getDisplayMetrics ().heightPixels * 660) / 1920 );
        layoutParams.addRule ( 13 );
        ((LinearLayout) inflate.findViewById ( R.id.ldialog )).setLayoutParams ( layoutParams );
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams ( (getResources ().getDisplayMetrics ().widthPixels * 263) / 1080, (getResources ().getDisplayMetrics ().widthPixels * 251) / 1080 );
        imageView.setLayoutParams ( layoutParams2 );
        imageView2.setLayoutParams ( layoutParams2 );
        imageView.setOnClickListener ( new View.OnClickListener () {
            public void onClick(View view) {
                Intent intent = new Intent ( "android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                intent.setType ( "image/*" );
                intent.setFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION );
                EditorActivity.this.startActivityForResult ( intent, 2 );
                Log.d ( EditorActivity.this.Tag, "RESULT_FROM_GALLERY" );
                final Dialog dialog = new Dialog ( EditorActivity.this );
                if (dialog != null) {
                    dialog.dismiss ();
                }
            }
        } );
        imageView2.setOnClickListener ( new View.OnClickListener () {
            public void onClick(View view) {
                StrictMode.setVmPolicy ( new StrictMode.VmPolicy.Builder ().build () );
                Intent intent = new Intent ( "android.media.action.IMAGE_CAPTURE" );
                intent.putExtra ( "output", Uri.fromFile ( EditorActivity.this.mFileTemp ) );
                intent.setFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION );
                EditorActivity.this.startActivityForResult ( intent, 1 );
                Log.d ( EditorActivity.this.Tag, "RESULT_FROM_CAMERA" );
                dialog.dismiss ();
            }
        } );
        dialog.show ();
    }
    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            return;
        }
        if (i == 1)
        {
            Utils.selectedImageUri = Uri.fromFile ( this.mFileTemp );
            Intent intent2 = new Intent ( getApplicationContext (), crop.class );
            intent2.putExtra ( "isFromMain", true );
            startActivityForResult ( intent2, 98 );
        }

        else if (i == 2)
        {
            this.selectedImageUri2 = intent.getData ();
            Utils.selectedImageUri = this.selectedImageUri2;
            Intent intent3 = new Intent ( getApplicationContext (), crop.class );
            intent3.putExtra ( "isFromMain2", true );
            startActivityForResult ( intent3, 98 );
            Log.d ( this.Tag, "BACK_FROM_ACTIVITY" );


        }
        else if (i == 4) {

            ClipSticker ( intent );
        }
        else if (i == 98)
        {
            Log.d ( this.Tag, "In BACK_FROM_ACTIVITY Yes*****" );
            if (Utils.selectedImageUri != null) {
                String str = this.Tag;
                Log.d ( str, "Utils.selectedImageUri2 " + Utils.selectedImageUri2 );
                this.selectedImageUri2 = Utils.selectedImageUri;
                String str2 = this.Tag;
                Log.d ( str2, "selectedImageUri2 " + this.selectedImageUri2 );

                try {
                    this.croppedEditImage = BitmapCompression.getCorrectlyOrientedImage ( getApplicationContext (), this.selectedImageUri2, this.f146h );
                    this.BitWidth2 = this.croppedEditImage.getWidth ();
                    this.BitHeight2 = this.croppedEditImage.getHeight ();
                    this.croppedEditImage = Bitmap.createScaledBitmap ( this.croppedEditImage, this.BitWidth2, this.BitHeight2, false );
                    this.croppedEditImage = this.croppedEditImage.copy ( Bitmap.Config.ARGB_8888, true );
                    this.croppedImage = this.croppedEditImage;
                    String str3 = this.Tag;
                    Log.d ( str3, "croppedEditImage " + this.croppedEditImage );
                    this.ivPhotoImages.setImageBitmap ( (Bitmap) null );
                    this.ivPhotoImages.setImageBitmap ( this.croppedImage );
                } catch (Exception unused) {

                }

            }
            else
                {
                this.croppedEditImage = BitmapCompression.decodeFile ( this.mFileTemp2, this.f146h, this.f148w );
                this.croppedEditImage = BitmapCompression.adjustImageOrientation ( this.mFileTemp2, this.croppedEditImage );
                this.BitWidth2 = this.croppedEditImage.getWidth ();
                this.BitHeight2 = this.croppedEditImage.getHeight ();
                this.croppedEditImage = Bitmap.createScaledBitmap ( this.croppedEditImage, this.BitWidth2, this.BitHeight2, false );
                this.croppedEditImage = this.croppedEditImage.copy ( Bitmap.Config.ARGB_8888, true );
                this.croppedImage = this.croppedEditImage;
                this.ivPhotoImages.setImageBitmap ( this.croppedImage );
            }

        }
        else if (i == 456) {
            Bitmap bitmap = Utils.saveBitmap;
            this.croppedImage = bitmap;
            this.flEditor.setLayoutParams ( new LinearLayout.LayoutParams ( bitmap.getWidth (), bitmap.getHeight () ) );
            this.ivPhotoImages.setLayoutParams ( new FrameLayout.LayoutParams ( bitmap.getWidth (), bitmap.getHeight () ) );
            this.ivPhotoImages.setImageBitmap ( bitmap );
        }
        else if (i == 555) {
            ClipSticker ( intent );
        }
    }

    public void ClipSticker(Intent intent) {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(this);
        builder.threadPriority(3);
        builder.denyCacheImageMultipleSizesInMemory();
        builder.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        builder.diskCacheSize(52428800);
        builder.tasksProcessingOrder(QueueProcessingType.LIFO);
        builder.writeDebugLogs();
        ImageLoader.getInstance().init(builder.build());
        this.options = new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading((int) R.mipmap.ic_launcher).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.ARGB_8888).build();
        String stringExtra = intent.getStringExtra("nam");
        DisableAll();
        this.counter++;
        ClipSticker clipSticker = new ClipSticker(this, this.options, stringExtra);
        clipSticker.adjustOpacity(255.0f);
        clipSticker.setId(this.counter);
        this.flEditor.addView(clipSticker);
        clipSticker.setOnClickListener(new ClipClick(clipSticker));
    }

    /* access modifiers changed from: private */
    public void DisableAll() {
        for (int i = 0; i < this.flEditor.getChildCount (); i++) {
            if (this.flEditor.getChildAt ( i ) instanceof ClipSticker) {
                ((ClipSticker) findViewById ( this.flEditor.getChildAt ( i ).getId () )).disableAll ();
            }
        }
    }

    public Bitmap scaleCenterCrop(Bitmap bitmap, int i, int i2) {
        float f = (float) i2;
        float width = (float) bitmap.getWidth ();
        float f2 = (float) i;
        float height = (float) bitmap.getHeight ();
        float max = Math.max ( f / width, f2 / height );
        float f3 = width * max;
        float f4 = max * height;
        float f5 = (f - f3) / 2.0f;
        float f6 = (f2 - f4) / 2.0f;
        RectF rectF = new RectF ( f5, f6, f3 + f5, f4 + f6 );
        Bitmap createBitmap = Bitmap.createBitmap ( i2, i, bitmap.getConfig () );
        new Canvas ( createBitmap ).drawBitmap ( bitmap, (Rect) null, rectF, (Paint) null );
        return createBitmap;
    }

    @SuppressLint("WrongConstant")
    public void onBackPressed() {
        MyUtils.isFromScreen = 0;
        if (this.rl_color.getVisibility () == 0) {
            this.rl_color.setVisibility ( 8 );
            return;
        }
        Intent intent = new Intent ( this, start.class );
        intent.addFlags ( 335544320 );
        startActivity ( intent );
        finish ();
    }

    public void showExitDialog() {
        @SuppressLint("WrongConstant") View inflate = ((LayoutInflater) getSystemService ( "layout_inflater" )).inflate ( R.layout.exit_dialog, (ViewGroup) null );
        final Dialog dialog = new Dialog ( this, R.style.Theme_Transparent );
        dialog.setContentView ( R.layout.custom );
        ImageView imageView = (ImageView) dialog.findViewById ( R.id.ivok );
        ImageView imageView2 = (ImageView) dialog.findViewById ( R.id.yes );
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams ( (getResources ().getDisplayMetrics ().widthPixels * 238) / 1080, (getResources ().getDisplayMetrics ().heightPixels * 90) / 1920 );
        imageView.setLayoutParams ( layoutParams );
        imageView2.setLayoutParams ( layoutParams );
        imageView.setOnClickListener ( new View.OnClickListener () {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                Intent intent = new Intent ( EditorActivity.this, start.class );
                intent.addFlags ( 335544320 );
                EditorActivity.this.startActivity ( intent );
                dialog.dismiss ();
                EditorActivity.this.finish ();
            }
        } );
        imageView2.setOnClickListener ( new View.OnClickListener () {
            public void onClick(View view) {
                dialog.dismiss ();
            }
        } );
        dialog.show ();
    }

    public Bitmap bitmapResize() {
        try {
            int width = this.flEditor.getWidth ();
            int height = this.flEditor.getHeight ();
            int i = this.BitWidth;
            int i2 = this.BitHeight;
            if (i >= i2) {
                int i3 = (i2 * width) / i;
                if (i3 > height) {
                    width = (width * height) / i3;
                } else {
                    height = i3;
                }
            } else {
                int i4 = (i * height) / i2;
                if (i4 > width) {
                    height = (height * width) / i4;
                } else {
                    width = i4;
                }
            }
            return Bitmap.createScaledBitmap ( this.croppedImage, width, height, true );
        } catch (Exception unused) {
            return this.croppedImage;
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        getWindow ().clearFlags ( 128 );
        super.onStop ();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        getWindow ().clearFlags ( 128 );
        super.onDestroy ();
    }

    private class saveTask extends AsyncTask<Void, Void, Bitmap> {
        private saveTask() {
        }


        public void onPreExecute() {
            EditorActivity editorActivity = EditorActivity.this;
            ProgressDialog unused = editorActivity.f147pd = new ProgressDialog ( editorActivity );
            EditorActivity.this.f147pd.setIndeterminateDrawable ( EditorActivity.this.getResources ().getDrawable ( R.drawable.progress_dialogue ) );
            EditorActivity.this.f147pd.setInverseBackgroundForced ( true );
            EditorActivity.this.f147pd.setMessage ( "Progress......" );
            EditorActivity.this.f147pd.setCanceledOnTouchOutside ( false );
            EditorActivity.this.f147pd.setCancelable ( false );
            EditorActivity.this.f147pd.show ();
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            EditorActivity.this.saveImage ();
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            if (Build.VERSION.SDK_INT >= 19) {
                Intent intent = new Intent ( "android.intent.action.MEDIA_SCANNER_SCAN_FILE" );
                EditorActivity editorActivity = EditorActivity.this;
                intent.setData ( FileProvider.getUriForFile ( editorActivity, getApplicationContext ().getPackageName () + ".provider", editorActivity.file ) );
                EditorActivity.this.sendBroadcast ( intent );
            } else {
                EditorActivity editorActivity2 = EditorActivity.this;
                editorActivity2.sendBroadcast ( new Intent ( "android.intent.action.MEDIA_MOUNTED", Uri.parse ( "file://" + Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_DOWNLOADS ) ) ) );
            }
            if (EditorActivity.this.path != null) {
                Intent intent2 = new Intent ( EditorActivity.this.getApplicationContext (), CrownFinalScreen.class );
                intent2.putExtra ( "position", 0 );
                intent2.putExtra ( "just_check", false );
                EditorActivity.this.startActivity ( intent2 );
                EditorActivity.this.finish ();
            }
            EditorActivity.this.f147pd.dismiss ();
        }
    }

    class ClipClick implements View.OnClickListener {
        final ClipSticker clip_val;

        ClipClick(ClipSticker clipSticker) {
            this.clip_val = clipSticker;
        }

        public void onClick(View view) {
            EditorActivity editorActivity = EditorActivity.this;
            ClipSticker clipSticker = this.clip_val;
            editorActivity.stick1 = clipSticker;
            clipSticker.bringToFront ();
            EditorActivity.this.DisableAll ();
        }
    }

    private class shareTask extends AsyncTask<Void, Void, Bitmap> {
        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private shareTask() {
        }

        public void onPostExecute(Bitmap bitmap) {
            Intent intent = new Intent ( "android.intent.action.SEND" );
            EditorActivity editorActivity = EditorActivity.this;
            editorActivity.screenshotUri = FileProvider.getUriForFile ( editorActivity, getApplicationContext ().getPackageName () + ".provider", new File ( editorActivity.mImageUri.getPath () ) );
            intent.setType ( "image/jpg" );
            intent.putExtra ( "android.intent.extra.STREAM", EditorActivity.this.screenshotUri );
            EditorActivity.this.startActivity ( Intent.createChooser ( intent, "Share Image" ) );
        }
    }
}
