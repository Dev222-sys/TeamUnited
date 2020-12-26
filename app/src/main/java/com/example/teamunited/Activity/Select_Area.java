package com.example.teamunited.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.hardware.Camera;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.example.teamunited.CheckInternetConnection;
import com.example.teamunited.R;

public class Select_Area extends AppCompatActivity {
    View image;
    Progress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__area);
        image = findViewById(R.id.image);
        progress = new Progress();
        progress.createdialog(Select_Area.this);
        progress.showpDialog();
        progress.hidepDialog();

    }


    public void Onclick_Guestarea(View view) {


        startActivity(new Intent(Select_Area.this, Guest_Login.class));


    }

    public void Onclick_Distributorarea(View view) {


        startActivity(new Intent(Select_Area.this, Distributor_Login.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //check Internet Connection

        new CheckInternetConnection(this).checkConnection();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

}