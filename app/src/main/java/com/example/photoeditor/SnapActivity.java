package com.example.photoeditor;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

;

public class SnapActivity extends Activity implements View.OnClickListener {
    public final int RESULT_FROM_SNAP = 111;
    public final int RESULT_FROM_TAP = CrownEditor.RESULT_FROM_EFFECTS;
    ImageView add_text;
    Bitmap bit;
    LinearLayout bottom;
    ImageView btnBack;
    ImageView btnDone;
    ArrayList<ImageButton> btnList = new ArrayList<>();
    ImageView btnNext;
    int count;

    /* renamed from: dX */
    float f180dX;

    /* renamed from: dY */
    float f181dY;
    EditText edText;
    FrameLayout fl_edit;
    FrameLayout frame;
    ArrayList<FrameLayout> frmList = new ArrayList<>();
    /* access modifiers changed from: private */
    public GestureDetector gestureDetector;
    ImageView hide_show;
    ImageView image_edit;
    InputMethodManager imm;
    boolean isMoving;
    int lastY = 0;
    int moveY = 0;
    int number;
//    public View.OnClickListener onclickbtn = new View.OnClickListener() {
//        public void onClick(final View view) {
//             alert.setPositiveButton ("r0" new DialogInterface.OnClickListener()) {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    if (i == -1) {
//                        SnapActivity.this.fl_edit.removeView(SnapActivity.this.textList.get(Integer.parseInt(view.getTag().toString())));
//                    }
//                }
//            };
//            final AlertDialog show = new AlertDialog.Builder ( SnapActivity.this ).setMessage ( "Delete this TAG?" ).setPositiveButton ( "Yes", r0 ).setNegativeButton ( "No", r0 ).show ();
//        }
//    };
    PopupWindow popupEditText;
    View rootView;

    /* renamed from: st */
    String f182st;
    String text;
    ArrayList<View> textList = new ArrayList<>();
    public View.OnTouchListener textTouch = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            SnapActivity.this.number = Integer.parseInt(view.getTag().toString());
            SnapActivity snapActivity = SnapActivity.this;
            snapActivity.f182st = snapActivity.txt.getText().toString();
            if (SnapActivity.this.gestureDetector.onTouchEvent(motionEvent)) {
                boolean z = SnapActivity.this.isMoving;
                return true;
            }
            SnapActivity.this.drag(motionEvent, view);
            return true;
        }
    };
    ArrayList<TextView> textnum = new ArrayList<>();
    TextView txt;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_snap);
        findviewByID();
        this.bit = Utils.imageHolder;
        this.text = Utils.add_text;
        new Handler().postDelayed(new Runnable() {
            @SuppressLint("WrongConstant")
            public void run() {
                Bitmap bitmapResize = SnapActivity.this.bitmapResize();
                SnapActivity.this.fl_edit.setLayoutParams(new RelativeLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                SnapActivity.this.image_edit.setLayoutParams(new FrameLayout.LayoutParams(bitmapResize.getWidth(), bitmapResize.getHeight()));
                SnapActivity.this.image_edit.setImageBitmap(bitmapResize);
                SnapActivity.this.image_edit.setVisibility(0);
                SnapActivity.this.snap();
                Toast.makeText(SnapActivity.this.getApplicationContext(), "Double tap to edit !", 0).show();
            }
        }, 500);
        this.hide_show.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint({"WrongConstant", "ClickableViewAccessibility"})
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    Iterator<FrameLayout> it = SnapActivity.this.frmList.iterator();
                    while (it.hasNext()) {
                        it.next().setVisibility(0);
                    }
                }
                if (motionEvent.getAction() == 0) {
                    Iterator<FrameLayout> it2 = SnapActivity.this.frmList.iterator();
                    while (it2.hasNext()) {
                        it2.next().setVisibility(8);
                    }
                }
                return true;
            }
        });
    }

    public void findviewByID() {
        this.bottom = (LinearLayout) findViewById(R.id.bottom);
        this.hide_show = (ImageView) findViewById(R.id.hide_show);
        this.add_text = (ImageView) findViewById(R.id.add_text);
        this.fl_edit = (FrameLayout) findViewById(R.id.fl_edit);
        this.image_edit = (ImageView) findViewById(R.id.image_edit);
        this.btnNext = (ImageView) findViewById(R.id.btnNext);
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        this.btnNext.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
        this.add_text.setOnClickListener(this);
    }

    public void next() {
        new Handler().postDelayed(new Runnable() {
            @SuppressLint("WrongConstant")
            public void run() {
                Iterator<ImageButton> it = SnapActivity.this.btnList.iterator();
                while (it.hasNext()) {
                    it.next().setVisibility(8);
                }
                Utils.saveBitmap = SnapActivity.this.getFrameBitmap();
                SnapActivity.this.setResult(-1, new Intent());
                SnapActivity.this.finish();
            }
        }, 500);
    }

    public Bitmap getFrameBitmap() {
        this.fl_edit.postInvalidate();
        this.fl_edit.setDrawingCacheEnabled(true);
        this.fl_edit.buildDrawingCache();
        Bitmap createBitmap = Bitmap.createBitmap(this.fl_edit.getDrawingCache());
        this.fl_edit.destroyDrawingCache();
        return createBitmap;
    }

    public void snap() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        @SuppressLint("WrongConstant") View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.add_tag_text, (ViewGroup) null);
        this.frame = (FrameLayout) inflate.findViewById(R.id.frame);
        this.txt = (TextView) inflate.findViewById(R.id.add_text);
        ImageButton imageButton = (ImageButton) inflate.findViewById(R.id.btn_del);
        this.txt.setText(this.text);
        this.txt.setTextSize(18.0f);
        this.txt.setTag("" + this.count);
        imageButton.setTag("" + this.count);
        inflate.setTag("" + this.count);
        inflate.setOnTouchListener(this.textTouch);
        //imageButton.setOnClickListener(this.onclickbtn);
        this.textList.add(inflate);
        this.btnList.add(imageButton);
        this.textnum.add(this.txt);
        this.frmList.add(this.frame);
        this.fl_edit.addView(inflate, layoutParams);
        this.count++;
    }

    public void drag(MotionEvent motionEvent, View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.moveY = (int) motionEvent.getY();
            this.lastY = this.moveY;
        } else if (action == 2) {
            this.moveY = (int) motionEvent.getY();
            layoutParams.topMargin += this.moveY;
            layoutParams.leftMargin = 0;
            view.setLayoutParams(layoutParams);
        }
    }

    public Bitmap bitmapResize() {
        int width = this.fl_edit.getWidth();
        int height = this.fl_edit.getHeight();
        int width2 = this.bit.getWidth();
        int height2 = this.bit.getHeight();
        if (width2 >= height2) {
            int i = (height2 * width) / width2;
            if (i > height) {
                width = (width * height) / i;
            } else {
                height = i;
            }
        } else {
            int i2 = (width2 * height) / height2;
            if (i2 > width) {
                height = (height * width) / i2;
            } else {
                width = i2;
            }
        }
        return Bitmap.createScaledBitmap(this.bit, width, height, true);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.add_text) {
            Utils.textFrom = "";
            startActivityForResult(new Intent(this, TextAdd.class), 111);
            overridePendingTransition(R.anim.slide_up, R.anim.stay);
        } else if (id == R.id.btnBack) {
            onBackPressed();
        } else if (id == R.id.btnNext) {
            next();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            return;
        }
        if (i == 111) {
            this.text = Utils.add_text;
            snap();
        } else if (i == 222) {
            this.text = Utils.add_text;
            this.textnum.get(this.number).setText(this.text);
        }
    }

    public void onBackPressed() {
        Utils.textFrom = "";
        finish();
        super.onBackPressed();
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
        private SingleTapConfirm() {
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            Utils.textFrom = SnapActivity.this.textnum.get(SnapActivity.this.number).getText().toString();
            SnapActivity.this.startActivityForResult(new Intent(SnapActivity.this, TextAdd.class), CrownEditor.RESULT_FROM_EFFECTS);
            SnapActivity.this.overridePendingTransition(R.anim.slide_up, R.anim.stay);
            return super.onDoubleTap(motionEvent);
        }
    }
}

