package com.example.photoeditor;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.ItemTouchHelper;

public class Exit extends Activity {
    private boolean isFirstDone = false;
    LinearLayout lay;
    ImageView yes;
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(1024);
        setContentView(R.layout.exit_dialog);
        this.yes = (ImageView) findViewById(R.id.ivok);
        this.lay = (LinearLayout) findViewById(R.id.lay);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 480) / 1080, (getResources().getDisplayMetrics().heightPixels * 104) / 1920);
        layoutParams.gravity = 17;
        this.yes.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 480) / 1080, (getResources().getDisplayMetrics().heightPixels * ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION) / 1920);
        layoutParams2.addRule(14);
        layoutParams2.addRule(12);
        this.lay.setLayoutParams(layoutParams2);
        this.yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Exit.this.finishAffinity ();

            }
        });
    }

}
