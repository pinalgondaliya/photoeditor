package com.example.photoeditor;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;


public class StickerAdapter extends BaseAdapter {
    private Activity activity;
    ImageLoader imgLoader;
    private LayoutInflater inflater = null;
    DisplayImageOptions optsFull;
    DisplayImageOptions optsThumb;
    ArrayList<FrameModel> stickerList = new ArrayList<>();
    int width;

    public long getItemId(int i) {
        return (long) i;
    }

    public StickerAdapter(Activity activity2, ArrayList<FrameModel> arrayList, ImageLoader imageLoader) {
        this.activity = activity2;
        this.stickerList = arrayList;
        this.imgLoader = imageLoader;
        this.inflater = LayoutInflater.from(this.activity);
        this.inflater = this.activity.getLayoutInflater();
        this.width = this.activity.getResources().getDisplayMetrics().widthPixels / 3;
    }

    public int getCount() {
        return this.stickerList.size();
    }

    public Object getItem(int i) {
        return this.stickerList.get(i);
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
            viewHolder.f43iv = (ImageView) view2.findViewById(R.id.ivpip_tiny);
            viewHolder.f43iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        FrameModel frameModel = this.stickerList.get(i);
        if (frameModel.IsAvailable.booleanValue()) {
            this.imgLoader.displayImage(frameModel.FramePath, viewHolder.f43iv, this.optsFull);
        } else {
            this.imgLoader.displayImage(frameModel.FramePath, viewHolder.f43iv, this.optsThumb);
        }
        return view2;
    }

    public class ViewHolder {

        /* renamed from: iv */
        ImageView f43iv;
        LinearLayout ll_border;

        public ViewHolder() {
        }
    }
}

