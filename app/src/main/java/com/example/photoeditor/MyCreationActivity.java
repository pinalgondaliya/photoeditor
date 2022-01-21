package com.example.photoeditor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MyCreationActivity extends AppCompatActivity {
    public ArrayList<File> fileArrayList = new ArrayList<>();
    RecyclerView myCreationRecyclerView;
    public MyCreationsAdapter myCreationsAdapter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_my_creation);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MyCreationActivity.this.onBackPressed();
            }
        });
        this.myCreationRecyclerView = (RecyclerView) findViewById(R.id.myCreationRecyclerView);
    }

    public void onResume() {
        super.onResume();
        new dataloader().execute(new Void[0]);
        MyCreationsAdapter myCreationsAdapter2 = this.myCreationsAdapter;
        if (myCreationsAdapter2 != null) {
            myCreationsAdapter2.notifyDataSetChanged();
        }
    }
    public void getVideo() {
        File[] listFiles;
        this.fileArrayList.clear();
        Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_DOWNLOADS );
        File file = new File(Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_DOWNLOADS ) + "/" + getString(R.string.app_name));
        if (file.exists() && (listFiles = file.listFiles()) != null) {
            this.fileArrayList.addAll ( Arrays.asList ( listFiles ) );
        }
    }

    public class dataloader extends AsyncTask<Void, Void, Void> {
        public dataloader() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);

            Log.i ( "TAG414", "onPostExecute: "+fileArrayList );
            MyCreationActivity myCreationActivity = MyCreationActivity.this;
            myCreationActivity.myCreationsAdapter = new MyCreationsAdapter(myCreationActivity.fileArrayList, MyCreationActivity.this);
            MyCreationActivity.this.myCreationRecyclerView.setLayoutManager(new GridLayoutManager(MyCreationActivity.this, 2));
            MyCreationActivity.this.myCreationRecyclerView.setAdapter(MyCreationActivity.this.myCreationsAdapter);
        }
        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            MyCreationActivity.this.getVideo();
            Collections.sort(MyCreationActivity.this.fileArrayList, new Comparator<File>() {
                public int compare(File file, File file2) {
                    return Long.valueOf(file2.lastModified()).compareTo(Long.valueOf(file.lastModified()));
                }
            });
            return null;
        }
    }

}
