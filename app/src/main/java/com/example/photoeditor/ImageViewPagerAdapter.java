package com.example.photoeditor;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.io.File;
import java.util.ArrayList;

public class ImageViewPagerAdapter extends PagerAdapter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private ArrayList<File> fileArrayList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public ImageViewPagerAdapter(ArrayList<File> arrayList, Context context) {
        this.fileArrayList = arrayList;
        this.mContext = context;
    }

    public int getCount() {
        return this.fileArrayList.size();
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        this.layoutInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        View inflate = this.layoutInflater.inflate(R.layout.custom_layout, (ViewGroup) null);
        ((ImageView) inflate.findViewById(R.id.imageView)).setImageURI(Uri.parse(this.fileArrayList.get(i).getAbsolutePath()));
        ((ViewPager) viewGroup).addView(inflate, 0);
        return inflate;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        ((ViewPager) viewGroup).removeView((View) obj);
    }
}

