package com.example.teamunited.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.teamunited.Adapter.GalleryAdapter;
import com.example.teamunited.Adapter.GalleryAdapterr;
import com.example.teamunited.Fragment.Gallery;
import com.example.teamunited.R;

public class Full_Image extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__image);
        imageView=findViewById(R.id.full_image);
        Intent i=getIntent();
        int position=i.getExtras().getInt("id");
        imageView.setImageResource(position);/*
        GalleryAdapterr galleryAdapter =new GalleryAdapterr(this);
        imageView.setImageResource(galleryAdapter.imageArray[position]);
*/

    }
}