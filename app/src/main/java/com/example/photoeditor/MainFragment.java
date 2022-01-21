package com.example.photoeditor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.isseiaoki.simplecropview.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainFragment extends Fragment {
    private static final String PROGRESS_DIALOG = "ProgressDialog";
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;
    private File ImageTemp;
    Uri selectedImage;
    ImageView back;
    private final View.OnClickListener btnListener = new View.OnClickListener () {
        public void onClick(View view) {
            switch (view.getId ()) {
                case R.id.button16_9 /*2131296398*/:
                    MainFragment.this.mCropView.setCropMode ( CropImageView.CropMode.RATIO_16_9 );
                    return;
                case R.id.button1_1 /*2131296399*/:
                    MainFragment.this.mCropView.setCropMode ( CropImageView.CropMode.SQUARE );
                    return;
                case R.id.button3_4 /*2131296401*/:
                    MainFragment.this.mCropView.setCropMode ( CropImageView.CropMode.RATIO_3_4 );
                    return;
                case R.id.button4_3 /*2131296402*/:
                    MainFragment.this.mCropView.setCropMode ( CropImageView.CropMode.RATIO_4_3 );
                    return;
                case R.id.button9_16 /*2131296403*/:
                    MainFragment.this.mCropView.setCropMode ( CropImageView.CropMode.RATIO_9_16 );
                    return;
                case R.id.buttonCircle /*2131296404*/:
                    MainFragment.this.mCropView.setCropMode ( CropImageView.CropMode.CIRCLE );
                    return;
                case R.id.buttonCustom /*2131296405*/:
                    MainFragment.this.mCropView.setCustomRatio ( 7, 5 );
                    return;
                case R.id.buttonDone /*2131296406*/:
                    MainFragment.this.cropImage ();
                    return;
                case R.id.buttonFitImage /*2131296407*/:
                    MainFragment.this.mCropView.setCropMode ( CropImageView.CropMode.FIT_IMAGE );
                    return;
                case R.id.buttonFree /*2131296408*/:
                    MainFragment.this.mCropView.setCropMode ( CropImageView.CropMode.FREE );
                    return;
                case R.id.buttonRotateLeft /*2131296410*/:
                    MainFragment.this.mCropView.rotateImage ( CropImageView.RotateDegrees.ROTATE_M90D );
                    return;
                case R.id.buttonRotateRight /*2131296411*/:
                    MainFragment.this.mCropView.rotateImage ( CropImageView.RotateDegrees.ROTATE_90D );
                    return;
                default:
                    return;
            }
        }
    };
    Bitmap cropImage;
    ImageView image;
    /* access modifiers changed from: private */
    public final CropCallback mCropCallback = new CropCallback () {
        public void onError() {
        }

        public void onSuccess(Bitmap bitmap) {
            mCropView.save ( bitmap )
                    .compressFormat ( mCompressFormat )
                    .execute ( createSaveUri (), mSaveCallback );

            try {
                File file = File.createTempFile ( "cropped", ".jpg", requireActivity ().getCacheDir () );
                if (saveBitmapToFile ( bitmap, file ))
                {
                    String path = file.getAbsolutePath ();
                    Intent i = new Intent (requireContext (),CrownEditor.class);
                    i.putExtra("mfile", path);
                     startActivity ( i );
                    Log.i ( "TAG123", "onSuccess: " );
                } else {
                    Log.i ( "TAG123", "onSuccess: failed" );
                }

            } catch (IOException e) {
                e.printStackTrace ();
            }


        }

        @Override
        public void onError(Throwable e) {
        }
    };

    /**
     * Saves the bitmap to the given file location
     *
     * @param bitmap bitmap you want to save
     * @param file   destination file where bitmap need to be saved
     */
    public boolean saveBitmapToFile(@NonNull Bitmap bitmap, @NonNull File file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream ( file )) {
            return bitmap.compress ( Bitmap.CompressFormat.JPEG, 80, fileOutputStream );
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return false;
    }

    /* access modifiers changed from: private */
    public CropImageView mCropView;
    private final LoadCallback mLoadCallback = new LoadCallback () {
        public void onSuccess() {
            MainFragment.this.dismissProgress ();
        }

        @Override
        public void onError(Throwable e) {

        }

        public void onError() {
            MainFragment.this.dismissProgress ();
        }
    };
    private LinearLayout mRootLayout;
    public final SaveCallback mSaveCallback = new SaveCallback () {
        public void onSuccess(Uri uri) {
            MainFragment.this.dismissProgress ();
            ((MainCroper) MainFragment.this.getActivity ()).startResultActivity ( uri );
        }

        @Override
        public void onError(Throwable e) {
            Log.i ( "TAG1", "onError: " + e );
        }

        public void onError() {
            MainFragment.this.dismissProgress ();
        }
    };


//    public void showRationaleForCrop(PermissionRequest permissionRequest) {
//    }
//
//    public void showRationaleForPick(PermissionRequest permissionRequest) {
//    }

    public static MainFragment getInstance() {
        MainFragment mainFragment = new MainFragment ();
        mainFragment.setArguments ( new Bundle () );
        return mainFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate ( bundle );
        setRetainInstance ( true );
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate ( R.layout.fragment_main, (ViewGroup) null, false );
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated ( view, bundle );
        bindViews ( view );
        FontUtils.setFont ( (ViewGroup) this.mRootLayout );
        if (this.mCropView.getImageBitmap () == null) {
            this.mCropView.setImageBitmap ( Utils.imageHolder );
        }
        Uri mSourceUri = null;
        RectF mFrameRect = null;
        mCropView.load ( mSourceUri )
                .initialFrameRect ( mFrameRect )
                .useThumbnail ( true )
                .execute ( mLoadCallback );
    }

    public String getPath(Uri uri) {
        Cursor query = getActivity ().getContentResolver ().query ( uri, new String[]{"_data"}, (String) null, (String[]) null, (String) null );
        int columnIndexOrThrow = query.getColumnIndexOrThrow ( "_data" );
        query.moveToFirst ();
        String string = query.getString ( columnIndexOrThrow );
        query.close ();
        return string;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult ( i, strArr, iArr );
    }

    private void bindViews(View view) {
        this.mCropView = (CropImageView) view.findViewById ( R.id.cropImageView );
        view.findViewById ( R.id.buttonDone ).setOnClickListener ( this.btnListener );
        view.findViewById ( R.id.buttonFitImage ).setOnClickListener ( this.btnListener );
        view.findViewById ( R.id.button1_1 ).setOnClickListener ( this.btnListener );
        view.findViewById ( R.id.button3_4 ).setOnClickListener ( this.btnListener );
        view.findViewById ( R.id.button4_3 ).setOnClickListener ( this.btnListener );
        view.findViewById ( R.id.button9_16 ).setOnClickListener ( this.btnListener );
        view.findViewById ( R.id.button16_9 ).setOnClickListener ( this.btnListener );
        view.findViewById ( R.id.buttonFree ).setOnClickListener ( this.btnListener );
        view.findViewById ( R.id.buttonRotateLeft ).setOnClickListener ( this.btnListener );
        view.findViewById ( R.id.buttonRotateRight ).setOnClickListener ( this.btnListener );
        view.findViewById ( R.id.buttonCustom ).setOnClickListener ( this.btnListener );
        view.findViewById ( R.id.buttonCircle ).setOnClickListener ( this.btnListener );
        this.mRootLayout = (LinearLayout) view.findViewById ( R.id.layout_root );
    }

    public void cropImage() {
       // showProgress ();
        this.mCropView.startCrop ( createSaveUri (), this.mCropCallback, null/*this.mSaveCallback*/ );
    }

//    public void showProgress() {
//        getFragmentManager ().beginTransaction ().add ( (Fragment) ProgressDialogFragment.getInstance (), PROGRESS_DIALOG ).commitAllowingStateLoss ();
//    }

    public void dismissProgress() {
        FragmentManager fragmentManager;
        ProgressDialogFragment progressDialogFragment;
        if (isAdded () && (fragmentManager = getFragmentManager ()) != null && (progressDialogFragment = (ProgressDialogFragment) fragmentManager.findFragmentByTag ( PROGRESS_DIALOG )) != null) {
            getFragmentManager ().beginTransaction ().remove ( progressDialogFragment ).commitAllowingStateLoss ();
        }
    }

    public Uri createSaveUri() {
        File file = new File ( Environment.getExternalStorageDirectory ().getAbsolutePath () + "/cropped" );
        file.mkdirs ();
        File file2 = new File ( file.getAbsolutePath () + "/temp.jpg" );
        Context applicationContext = getActivity ().getApplicationContext ();
        return FileProvider.getUriForFile ( applicationContext, getActivity ().getApplicationContext ().getPackageName () + ".provider", file2 );
    }

    public Bitmap getResizedBitmapp(Bitmap bitmap, int i) {
        int i2;
        float width = ((float) bitmap.getWidth ()) / ((float) bitmap.getHeight ());
        if (width > 1.0f) {
            i2 = (int) (((float) i) / width);
        } else {
            int i3 = (int) (((float) i) * width);
            i2 = i;
            i = i3;
        }
        return Bitmap.createScaledBitmap ( bitmap, i, i2, true );
    }

}
