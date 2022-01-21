package com.example.photoeditor;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class TextAdd extends Activity implements View.OnClickListener {
    public static final int RESULT_FROM_TAG = 333;
    public static final int RESULT_FROM_ADDTEXT = 999;

    Bitmap bit;
    ImageView btnBack;
    ImageView btnNext;
    FrameLayout fl_edit;
    ImageView image_edit;
    EditText snap_text;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_text_add);
        findviewByID();
        String str = Utils.textFrom.toString();
        if (!str.matches("")) {
            this.snap_text.setText(str);
            Selection.setSelection(this.snap_text.getText(), this.snap_text.getText().length());
        }
        this.bit = Utils.originalBitmap;
    }

    public void findviewByID() {
        this.fl_edit =  findViewById(R.id.fl_edit);
        this.image_edit =  findViewById(R.id.image_edit);
        this.snap_text =  findViewById(R.id.snap_text);
        this.btnNext = findViewById(R.id.btnNext);
        this.btnBack =  findViewById(R.id.btnBack);
        this.btnNext.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
        getWindow().setSoftInputMode(5);
    }
    public void next() {
        Intent intent = new Intent();
        Utils.add_text = TextAdd.this.snap_text.getText().toString();
        TextAdd.this.setResult(-1, intent);
        TextAdd.this.finish();
        Intent intent1 = new Intent();
        Utils.add_text = this.snap_text.getText().toString();
        setResult(-1, intent1);
        finish();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnBack) {
            Utils.textFrom = "";
            onBackPressed();
        } else if (id == R.id.btnNext) {
            next();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            finish();
        } else if (i == RESULT_FROM_TAG) {
            setResult(-1, new Intent());
            finish();
        }
    }

    public void onStop() {
        getWindow().setSoftInputMode(3);
        super.onStop();
    }

    public void onDestroy() {
        getWindow().setSoftInputMode(3);
        super.onDestroy();
    }

    public void onResume() {
        getWindow().setSoftInputMode(5);
        super.onResume();
    }

    public void onStart() {
        getWindow().setSoftInputMode(5);
        super.onStart();
    }
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}

