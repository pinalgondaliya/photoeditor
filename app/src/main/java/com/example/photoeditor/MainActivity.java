package com.example.photoeditor;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import androidx.core.app.ActivityCompat;
//public class MainActivity extends Activity {
//    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
//    private static final int REQUEST_PERMISSION_SETTING = 101;
//    private static int  SPLASH_SCREEN_TIME_OUT = 2000;
//
//
//    RelativeLayout ll_main;
//    Context mContext;
//    private SharedPreferences permissionStatus;
//    String[] permissionsRequired = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE"};
//    /* access modifiers changed from: private */
//    public boolean sentToSettings = false;
//
//    /* access modifiers changed from: protected */
//    public void onCreate(Bundle bundle) {
//        super.onCreate(bundle);
//        getWindow().addFlags(128);
//        requestWindowFeature(1);
//        getWindow().setFlags(1024, 1024);
//        setContentView(R.layout.activity_main);
//        this.mContext = this;
//        Helper.getHeightAndWidth(this.mContext);
//        this.permissionStatus = getSharedPreferences("permissionStatus", 0);
//        this.ll_main = (RelativeLayout) findViewById(R.id.ll_main);
//
//    }
//
//    /* access modifiers changed from: package-private */
//    public void checkPermition() {
//        if (ActivityCompat.checkSelfPermission(this, this.permissionsRequired[0]) == 0 && ActivityCompat.checkSelfPermission(this, this.permissionsRequired[1]) == 0 && ActivityCompat.checkSelfPermission(this, this.permissionsRequired[2]) == 0) {
//            proceedAfterPermission();
//            return;
//        }
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, this.permissionsRequired[0]) || ActivityCompat.shouldShowRequestPermissionRationale(this, this.permissionsRequired[1]) || ActivityCompat.shouldShowRequestPermissionRationale(this, this.permissionsRequired[2])) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle((CharSequence) "Need Multiple Permissions");
//            builder.setMessage((CharSequence) "This app needs Storage Write and Storage Read permissions.");
//            builder.setPositiveButton((CharSequence) "Grant", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.cancel();
//                    MainActivity splashScreenActivity = MainActivity.this;
//                    ActivityCompat.requestPermissions(splashScreenActivity, splashScreenActivity.permissionsRequired, 100);
//                }
//            });
//            builder.setNegativeButton((CharSequence) "Exit", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.cancel();
//                    MainActivity.this.finish();
//                }
//            });
//            builder.show();
//        } else if (this.permissionStatus.getBoolean(this.permissionsRequired[0], false)) {
//            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
//            builder2.setTitle((CharSequence) "Need Multiple Permissions");
//            builder2.setMessage((CharSequence) "This app needs Storage Write and Storage Read permissions.");
//            builder2.setPositiveButton((CharSequence) "Grant", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.cancel();
//                    boolean unused = MainActivity.this.sentToSettings = true;
//                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
//                    intent.setData( Uri.fromParts("package", MainActivity.this.getPackageName(), (String) null));
//                   MainActivity.this.startActivityForResult(intent, 101);
//                }
//            });
//            builder2.setNegativeButton((CharSequence) "Exit", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.cancel();
//                   MainActivity.this.finish();
//                }
//            });
//            builder2.show();
//        } else {
//            ActivityCompat.requestPermissions(this, this.permissionsRequired, 100);
//        }
//        SharedPreferences.Editor edit = this.permissionStatus.edit();
//        edit.putBoolean(this.permissionsRequired[0], true);
//        edit.commit();
//    }
//
//    /* access modifiers changed from: protected */
//    public void onActivityResult(int i, int i2, Intent intent) {
//        super.onActivityResult(i, i2, intent);
//        if (i == 101 && ActivityCompat.checkSelfPermission(this, this.permissionsRequired[0]) == 0) {
//            proceedAfterPermission();
//        }
//    }
//    private void proceedAfterPermission() {
//        allDone();
//    }
//
//    /* access modifiers changed from: protected */
//    public void onPostResume() {
//        super.onPostResume();
//        if (this.sentToSettings && ActivityCompat.checkSelfPermission(this, this.permissionsRequired[0]) == 0) {
//            proceedAfterPermission();
//        }
//    }
//
//    public void onStop() {
//        getWindow().clearFlags(128);
//        super.onStop();
//    }
//
//    public void onDestroy() {
//        System.gc();
//        getWindow().clearFlags(128);
//        super.onDestroy();
//    }
//
//    private void allDone() {
//        new Handler ().postDelayed( new Runnable() {
//            public void run() {
//                MainActivity.this.startActivity(new Intent(MainActivity.this, start.class));
//                MainActivity.this.finish();
//            }
//        },  SPLASH_SCREEN_TIME_OUT);
//    }
//
//    /* access modifiers changed from: private */
//    public void goToMain() {
//        allDone();
//
//    }
//    private void permissionDialog() {
//        final Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.permissionsdialog);
//        dialog.setTitle(getResources().getString(R.string.permission).toString());
//        dialog.setCancelable(false);
//        ((ImageView) dialog.findViewById(R.id.ok)).setOnClickListener( new View.OnClickListener() {
//            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    MainActivity.this.requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 922);
//                }
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//    @SuppressLint("WrongConstant")
//    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
//        if (i == 922) {
//            if (iArr[0] == 0) {
//                if (Build.VERSION.SDK_INT < 23 || (checkSelfPermission("android.permission.CAMERA") == 0 && checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0 && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
//                    allDone();
//                } else {
//                    permissionDialog();
//                }
//            } else if (Build.VERSION.SDK_INT < 23 || (checkSelfPermission("android.permission.CAMERA") == 0 && checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0 && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
//                allDone();
//            } else {
//                permissionDialog();
//            }
//        }
//    }
//
//    /* access modifiers changed from: package-private */
//    public void setPref() {
//        SharedPreferences.Editor edit = getSharedPreferences("consentpreff", 0).edit();
//        edit.putBoolean("isDone", true);
//        edit.apply();
//    }
//
//    /* access modifiers changed from: package-private */
//    public boolean isConsentDone() {
//        return getSharedPreferences("consentpreff", 0).getBoolean("isDone", false);
//    }
//
//}
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private static int SPLASH_SCREEN_TIME_OUT=2000;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this, start.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}


