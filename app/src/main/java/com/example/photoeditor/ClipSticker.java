package com.example.photoeditor;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.app.NotificationManagerCompat;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class ClipSticker extends FrameLayout {
    int baseh;
    int basew;
    int basex;
    int basey;
    Button bntflp;
    Button bottom;
    Button btndel;
    Button btnrot;
    Button btnscl;
    Activity cntx;
    DoubleTapListener doubleTaplistener;
    boolean freeze = false;
    int height;
    ImageView image;
    String imageUri;
    Button imgring;
    boolean isShadow;
    FrameLayout layBg;
    FrameLayout layGroup;
    public LayoutInflater mInflater;
    int margb;
    int margl;
    int margr;
    int margt;
    float opacity = 1.0f;
    DisplayImageOptions options;
    Bitmap originalBitmap;
    int pivx;
    int pivy;
    Button right;
    Bitmap shadowBitmap;
    int[] size;
    float startDegree;
    int width;

    public interface DoubleTapListener {
        void onDoubleTap();
    }

    @SuppressLint("WrongConstant")
    public ClipSticker(Activity activity, DisplayImageOptions displayImageOptions, String str) {
        super(activity);
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        this.width = displayMetrics.widthPixels;
        this.height = displayMetrics.heightPixels;
        this.cntx = activity;
        this.size = new StickerCommon(this.cntx).getScreenSizeInPixels();
        this.layGroup = this;
        this.options = displayImageOptions;
        this.basex = 0;
        this.basey = 0;
        this.pivx = 0;
        this.pivy = 0;
        this.mInflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        this.mInflater.inflate(R.layout.clipsticker, this, true);
        FrameLayout frameLayout = this.layGroup;
        int[] iArr = this.size;
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(iArr[0] / 2, iArr[0] / 2));
        this.image = (ImageView) findViewById(R.id.stikerimage);
        this.image.setTag(0);
        this.btndel = (Button) findViewById(R.id.close);
        this.btnrot = (Button) findViewById(R.id.rotate);
        this.btnscl = (Button) findViewById(R.id.zoom);
        this.right = (Button) findViewById(R.id.right);
        this.bottom = (Button) findViewById(R.id.bottom);
        this.bntflp = (Button) findViewById(R.id.flip);
        this.imgring = (Button) findViewById(R.id.outring);
        ImageLoader.getInstance().displayImage(str, this.image, displayImageOptions);
        this.image.setOnTouchListener(new ImageTouch());
        this.btnscl.setOnTouchListener(new ImageScale());
        this.right.setOnTouchListener(new RightScale());
        this.bottom.setOnTouchListener(new BottomScale());
        this.btnrot.setOnTouchListener(new ImageRotate());
        this.btndel.setOnClickListener(new ImageDelete());
        this.bntflp.setOnClickListener(new ImageFlip());
        setLocation();
    }

    public void setLocation() {
        this.layBg = (FrameLayout) getParent();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.layGroup.getLayoutParams();
        layoutParams.topMargin = (this.height / 2) - 400;
        layoutParams.leftMargin = (this.width / 2) - 400;
        this.layGroup.setLayoutParams(layoutParams);
    }

    @SuppressLint("ResourceType")
    public void setImageId() {
        this.image.setId(this.layGroup.getId() + 10000);
    }

    @SuppressLint("WrongConstant")
    public void disableAll() {
        this.btndel.setVisibility(8);
        this.btnrot.setVisibility(8);
        this.btnscl.setVisibility(8);
        this.bntflp.setVisibility(8);
        this.right.setVisibility(8);
        this.bottom.setVisibility(8);
        this.imgring.setVisibility(8);
    }

    @SuppressLint("WrongConstant")
    public void Visibilityshow() {
        this.image.setVisibility(0);
    }

    @SuppressLint("WrongConstant")
    public void Visibilityhide() {
        this.image.setVisibility(8);
    }

    public void setColor(int i) {
        this.image.getDrawable().setColorFilter((ColorFilter) null);
        this.image.getDrawable().setColorFilter(new LightingColorFilter(i, 0));
        this.image.setTag(Integer.valueOf(i));
        this.layGroup.performLongClick();
    }

    public void setImage(Bitmap bitmap) {
        if (this.originalBitmap == null) {
            this.originalBitmap = bitmap;
            this.shadowBitmap = shadowImage(this.originalBitmap);
        }
        if (this.isShadow) {
            this.image.setImageBitmap(this.originalBitmap);
        } else {
            this.image.setImageBitmap(this.originalBitmap);
            this.image.setBackgroundResource(0);
        }
        this.image.setAlpha(this.opacity);
        setColor(((Integer) this.image.getTag()).intValue());
        this.layGroup.performLongClick();
    }

    public void setFreeze(boolean z) {
        this.freeze = z;
    }

    public void showShadow(boolean z) {
        this.isShadow = z;
        setImage(this.originalBitmap);
        this.layGroup.performLongClick();
    }

    public Bitmap shadowImage(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        int[] iArr = new int[2];
        Bitmap extractAlpha = bitmap.extractAlpha(new Paint(), iArr);
        Paint paint = new Paint();
        paint.setColor(-11184811);
        canvas.drawBitmap(Bitmap.createScaledBitmap(extractAlpha, extractAlpha.getWidth(), extractAlpha.getHeight(), false), (float) iArr[0], (float) iArr[1], paint);
        extractAlpha.recycle();
        return createBitmap;
    }

    public void adjustOpacity(float f) {
        this.opacity = f;
        this.image.setAlpha(f);
    }
    public void resetImage() {
        this.originalBitmap = null;
        this.layGroup.performLongClick();
    }

    public void changeImageView(String str) {
        ImageLoader.getInstance().displayImage(str, this.image, this.options);
    }

    public void setOnDoubleTapListener(DoubleTapListener doubleTapListener) {
        this.doubleTaplistener = doubleTapListener;
    }

    public float getOpacity() {
        return this.image.getAlpha();
    }

    class ImageTouch implements View.OnTouchListener {
        final GestureDetector gestureDetector;

        ImageTouch() {
            this.gestureDetector = new GestureDetector(ClipSticker.this.cntx, new SingleTap());
        }

        @SuppressLint("WrongConstant")
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (ClipSticker.this.freeze) {
                return ClipSticker.this.freeze;
            }
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ClipSticker.this.layGroup.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == 0) {
                ClipSticker.this.layGroup.performClick();
                ClipSticker.this.btndel.setVisibility(0);
                ClipSticker.this.btnrot.setVisibility(0);
                ClipSticker.this.btnscl.setVisibility(0);
                ClipSticker.this.bntflp.setVisibility(0);
                ClipSticker.this.imgring.setVisibility(0);
                ClipSticker.this.right.setVisibility(0);
                ClipSticker.this.bottom.setVisibility(0);
                ClipSticker.this.basex = (int) (motionEvent.getRawX() - ((float) layoutParams.leftMargin));
                ClipSticker.this.basey = (int) (motionEvent.getRawY() - ((float) layoutParams.topMargin));
            } else if (action == 2) {
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                ClipSticker clipSticker = ClipSticker.this;
                clipSticker.layBg = (FrameLayout) clipSticker.getParent();
                if (rawX - ClipSticker.this.basex > (-((ClipSticker.this.layGroup.getWidth() * 1) / 2)) && rawX - ClipSticker.this.basex < ClipSticker.this.layBg.getWidth() - (ClipSticker.this.layGroup.getWidth() / 2)) {
                    layoutParams.leftMargin = rawX - ClipSticker.this.basex;
                }
                if (rawY - ClipSticker.this.basey > (-((ClipSticker.this.layGroup.getHeight() * 1) / 2)) && rawY - ClipSticker.this.basey < ClipSticker.this.layBg.getHeight() - (ClipSticker.this.layGroup.getHeight() / 2)) {
                    layoutParams.topMargin = rawY - ClipSticker.this.basey;
                }
                layoutParams.rightMargin = NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
                layoutParams.bottomMargin = NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
                ClipSticker.this.layGroup.setLayoutParams(layoutParams);
            }
            ClipSticker.this.layGroup.invalidate();
            this.gestureDetector.onTouchEvent(motionEvent);
            return true;
        }

        class SingleTap extends GestureDetector.SimpleOnGestureListener {
            public boolean onDoubleTap(MotionEvent motionEvent) {
                return false;
            }

            SingleTap() {
            }
        }
    }

    class RightScale implements View.OnTouchListener {
        RightScale() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (ClipSticker.this.freeze) {
                return ClipSticker.this.freeze;
            }
            int rawX = (int) motionEvent.getRawX();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ClipSticker.this.layGroup.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == 0) {
                ClipSticker clipSticker = ClipSticker.this;
                clipSticker.basex = rawX;
                clipSticker.basew = clipSticker.layGroup.getWidth();
                ClipSticker clipSticker2 = ClipSticker.this;
                clipSticker2.baseh = clipSticker2.layGroup.getHeight();
                ClipSticker.this.layGroup.getLocationOnScreen(new int[2]);
                ClipSticker.this.margl = layoutParams.leftMargin;
                ClipSticker.this.margr = layoutParams.rightMargin;
            } else if (action == 2) {
                int i = rawX - ClipSticker.this.basex;
                int i2 = (i * 2) + ClipSticker.this.basew;
                if (i2 > ClipSticker.this.size[0] / 6 && i2 < (ClipSticker.this.size[0] / 2) + ClipSticker.this.size[0]) {
                    layoutParams.width = i2;
                    layoutParams.leftMargin = ClipSticker.this.margl - i;
                }
                ClipSticker.this.layGroup.setLayoutParams(layoutParams);
            }
            ClipSticker.this.layGroup.invalidate();
            return true;
        }
    }

    class BottomScale implements View.OnTouchListener {
        BottomScale() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (ClipSticker.this.freeze) {
                return ClipSticker.this.freeze;
            }
            int rawY = (int) motionEvent.getRawY();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ClipSticker.this.layGroup.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == 0) {
                ClipSticker clipSticker = ClipSticker.this;
                clipSticker.basey = rawY;
                clipSticker.baseh = clipSticker.layGroup.getHeight();
                ClipSticker.this.layGroup.getLocationOnScreen(new int[2]);
                ClipSticker.this.margt = layoutParams.topMargin;
            } else if (action == 2) {
                int i = rawY - ClipSticker.this.basey;
                int i2 = (i * 2) + ClipSticker.this.baseh;
                if (i2 > ClipSticker.this.size[0] / 6 && i2 < (ClipSticker.this.size[0] / 2) + ClipSticker.this.size[0]) {
                    layoutParams.height = i2;
                    layoutParams.topMargin = ClipSticker.this.margt - i;
                }
                ClipSticker.this.layGroup.setLayoutParams(layoutParams);
            }
            ClipSticker.this.layGroup.invalidate();
            return true;
        }
    }

    class ImageScale implements View.OnTouchListener {
        ImageScale() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (ClipSticker.this.freeze) {
                return ClipSticker.this.freeze;
            }
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ClipSticker.this.layGroup.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == 0) {
                ClipSticker clipSticker = ClipSticker.this;
                clipSticker.basex = rawX;
                clipSticker.basey = rawY;
                clipSticker.basew = clipSticker.layGroup.getWidth();
                ClipSticker clipSticker2 = ClipSticker.this;
                clipSticker2.baseh = clipSticker2.layGroup.getHeight();
                ClipSticker.this.layGroup.getLocationOnScreen(new int[2]);
                ClipSticker.this.margl = layoutParams.leftMargin;
                ClipSticker.this.margt = layoutParams.topMargin;
            } else if (action == 2) {
                float degrees = (float) Math.toDegrees(Math.atan2((double) (rawY - ClipSticker.this.basey), (double) (rawX - ClipSticker.this.basex)));
                if (degrees < 0.0f) {
                    degrees += 360.0f;
                }
                int i = rawX - ClipSticker.this.basex;
                int i2 = rawY - ClipSticker.this.basey;
                int i3 = i2 * i2;
                int sqrt = (int) (Math.sqrt((double) ((i * i) + i3)) * Math.cos(Math.toRadians((double) (degrees - ClipSticker.this.layGroup.getRotation()))));
                int i4 = (sqrt * 2) + ClipSticker.this.basew;
                int sqrt2 = (((int) (Math.sqrt((double) ((sqrt * sqrt) + i3)) * Math.sin(Math.toRadians((double) (degrees - ClipSticker.this.layGroup.getRotation()))))) * 2) + ClipSticker.this.baseh;
                if (i4 > ClipSticker.this.size[0] / 6 && i4 < (ClipSticker.this.size[0] / 2) + ClipSticker.this.size[0] && sqrt2 > ClipSticker.this.size[0] / 6 && sqrt2 < (ClipSticker.this.size[0] / 2) + ClipSticker.this.size[0]) {
                    layoutParams.width = i4;
                    layoutParams.leftMargin = ClipSticker.this.margl;
                    layoutParams.height = i4;
                    layoutParams.topMargin = ClipSticker.this.margt;
                }
                ClipSticker.this.layGroup.setLayoutParams(layoutParams);
            }
            ClipSticker.this.layGroup.invalidate();
            return true;
        }
    }

    class ImageRotate implements View.OnTouchListener {
        ImageRotate() {
        }

        @SuppressLint("WrongConstant")
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (ClipSticker.this.freeze) {
                return ClipSticker.this.freeze;
            }
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ClipSticker.this.layGroup.getLayoutParams();
            ClipSticker clipSticker = ClipSticker.this;
            clipSticker.layBg = (FrameLayout) clipSticker.getParent();
            int[] iArr = new int[2];
            ClipSticker.this.layBg.getLocationOnScreen(iArr);
            int rawX = ((int) motionEvent.getRawX()) - iArr[0];
            int rawY = ((int) motionEvent.getRawY()) - iArr[1];
            int action = motionEvent.getAction();
            if (action == 0) {
                ClipSticker clipSticker2 = ClipSticker.this;
                clipSticker2.startDegree = clipSticker2.layGroup.getRotation();
                ClipSticker.this.pivx = layoutParams.leftMargin + (ClipSticker.this.getWidth() / 2);
                ClipSticker.this.pivy = layoutParams.topMargin + (ClipSticker.this.getHeight() / 2);
                ClipSticker clipSticker3 = ClipSticker.this;
                clipSticker3.basex = rawX - clipSticker3.pivx;
                ClipSticker clipSticker4 = ClipSticker.this;
                clipSticker4.basey = clipSticker4.pivy - rawY;
            } else if (action == 2) {
                int degrees = (int) (Math.toDegrees(Math.atan2((double) ClipSticker.this.basey, (double) ClipSticker.this.basex)) - Math.toDegrees(Math.atan2((double) (ClipSticker.this.pivy - rawY), (double) (rawX - ClipSticker.this.pivx))));
                if (degrees < 0) {
                    degrees += 360;
                }
                ClipSticker.this.layGroup.setLayerType(2, (Paint) null);
                ClipSticker.this.layGroup.setRotation((ClipSticker.this.startDegree + ((float) degrees)) % 360.0f);
            }
            ClipSticker.this.layGroup.invalidate();
            return true;
        }
    }

    class ImageDelete implements View.OnClickListener {
        ImageDelete() {
        }

        public void onClick(View view) {
            if (!ClipSticker.this.freeze) {
                ClipSticker clipSticker = ClipSticker.this;
                clipSticker.layBg = (FrameLayout) clipSticker.getParent();
                ClipSticker.this.layBg.performClick();
                ClipSticker.this.layBg.removeView(ClipSticker.this.layGroup);
            }
        }
    }

    class ImageFlip implements View.OnClickListener {
        public int counter = 0;

        ImageFlip() {
        }

        public void onClick(View view) {
            this.counter++;
            if (!ClipSticker.this.freeze) {
                ClipSticker clipSticker = ClipSticker.this;
                clipSticker.layBg = (FrameLayout) clipSticker.getParent();
                ClipSticker.this.layBg.performClick();
                if (this.counter == 1) {
                    ClipSticker.this.image.setScaleX(-1.0f);
                }
                if (this.counter == 2) {
                    ClipSticker.this.image.setScaleX(1.0f);
                    this.counter = 0;
                }
            }
        }
    }
}

