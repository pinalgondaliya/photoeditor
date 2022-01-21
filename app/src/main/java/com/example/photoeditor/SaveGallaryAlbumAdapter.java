package com.example.photoeditor;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SaveGallaryAlbumAdapter extends BaseAdapter {
    String bucket;
    ArrayList<String> data = new ArrayList<>();
    int height;

    /* renamed from: id */
    String f176id;
    ImageLoader imageLoader;
    private LayoutInflater infalter;
    /* access modifiers changed from: private */
    public final Context mContext;
    int width = 0;
    public long getItemId(int i) {
        return (long) i;
    }
    @SuppressLint("WrongConstant")
    public SaveGallaryAlbumAdapter(Context context, ArrayList<String> arrayList, ImageLoader imageLoader2) {
        this.mContext = context;
        this.infalter = (LayoutInflater) context.getSystemService("layout_inflater");
        this.data.addAll(arrayList);
        this.imageLoader = imageLoader2;
        this.width = this.mContext.getResources().getDisplayMetrics().widthPixels / 3;
        this.height = this.width + 73;
    }

    public int getCount() {
        return this.data.size();
    }

    public Object getItem(int i) {
        return this.data.get(i);
    }

    /* access modifiers changed from: package-private */
    public void remove(int i) {
        this.data.remove(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = this.infalter.inflate(R.layout.main_row_image_listadapter, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.ivThumb = (ImageView) view.findViewById(R.id.ivImageThumb);
            ImageView imageView = viewHolder.ivThumb;
            int i2 = this.width;
            imageView.setLayoutParams(new FrameLayout.LayoutParams(i2, i2));
            viewHolder.btnDel = (Button) view.findViewById(R.id.btnDelete);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        new DisplayImageOptions.Builder().showStubImage(R.color.trans).showImageForEmptyUri((int) R.color.trans).cacheInMemory().cacheOnDisc().bitmapConfig(Bitmap.Config.ARGB_8888).delayBeforeLoading(100).displayer(new SimpleBitmapDisplayer()).build();
        ImageLoader imageLoader2 = this.imageLoader;
        imageLoader2.displayImage(Uri.parse("file://" + this.data.get(i).toString()).toString(), viewHolder.ivThumb);
        viewHolder.ivThumb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SaveGallaryAlbumAdapter.this.mContext, CrownFinalScreen.class);
                intent.putExtra("ImagePath", "" + SaveGallaryAlbumAdapter.this.data.get(i).toString());
                SaveGallaryAlbumAdapter.this.mContext.startActivity(intent);
            }
        });
//        viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                final Dialog dialog = new Dialog (this );
//                public void onClick(dialog, int i) {
//                        if (i == -1) {
//                            SaveGallaryAlbumAdapter.this.deleteTmpFile(i);
//                        }
//                    }
//                new AlertDialog.Builder(SaveGallaryAlbumAdapter.this.mContext).setMessage("Delete this Photo?").setPositiveButton("Yes", r3).setNegativeButton("No", r3).show();
//            }
//        });
        return view;
    }

    private class ViewHolder {
        Button btnDel;
        ImageView ivThumb;

        private ViewHolder() {
        }
    }

    public void deleteTmpFile(int i) {
        this.mContext.getResources().getString(R.string.app_name);
        File file = new File(this.data.get(i));
        if (file.exists()) {
            file.delete();
            deleteFileFromMediaStore(this.mContext.getContentResolver(), file);
            remove(i);
            notifyDataSetChanged();
        }
        Toast.makeText(this.mContext, "Delete Successfully..", Toast.LENGTH_LONG).show();
    }

    public static void deleteFileFromMediaStore(ContentResolver contentResolver, File file) {
        String str;
        try {
            str = file.getCanonicalPath();
        } catch (IOException unused) {
            str = file.getAbsolutePath();
        }
        Uri contentUri = MediaStore.Files.getContentUri("external");
        if (contentResolver.delete(contentUri, "_data=?", new String[]{str}) == 0) {
            String absolutePath = file.getAbsolutePath();
            if (!absolutePath.equals(str)) {
                contentResolver.delete(contentUri, "_data=?", new String[]{absolutePath});
            }
        }
    }
}

