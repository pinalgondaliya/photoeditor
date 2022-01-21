package com.example.photoeditor;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public class MainCroper extends FragmentActivity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_main_croper);
        if (bundle == null) {
            getSupportFragmentManager().beginTransaction().add((int) R.id.container, MainFragment.getInstance() ).commit();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void startResultActivity(Uri uri) {
        if (!isFinishing()) {
            Intent intent = new Intent();
            Utils.uriHolder = uri;
            setResult(-1, intent);
            finish();
        }
    }
}

