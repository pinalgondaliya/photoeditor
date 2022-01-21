package com.example.photoeditor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class MyCreationsAdapter extends RecyclerView.Adapter<MyCreationsAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<File> myList;

    public MyCreationsAdapter(ArrayList<File> arrayList, Context context) {
        this.myList = arrayList;
        this.mContext = context;
    }


    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder ( LayoutInflater.from ( this.mContext ).inflate ( R.layout.all_in_one_adapter_layout, viewGroup, false ) );
    }


    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        Glide.with(this.mContext).load(this.myList.get(i).getAbsolutePath()).into(myViewHolder.image);
        myViewHolder.image.setOnClickListener ( new View.OnClickListener () {
            public void onClick(View view) {
                Intent intent = new Intent ( MyCreationsAdapter.this.mContext, CrownFinalScreen.class );
                intent.putExtra ( "position", i );
                intent.putExtra ( "just_check", true );
                MyCreationsAdapter.this.mContext.startActivity ( intent );
            }
        } );

        Bitmap bitmap = BitmapFactory.decodeFile ( myList.get ( i ).getAbsolutePath () );
        myViewHolder.image.setImageBitmap ( bitmap );

    }


    public int getItemCount() {
        return this.myList.size ();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public MyViewHolder(View view) {
            super ( view );
            this.image = (ImageView) view.findViewById ( R.id.image );
        }
    }
}
