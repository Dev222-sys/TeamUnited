package com.example.teamunited.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamunited.Adapter.IntroViewPagerAdapter;
import com.example.teamunited.MainActivity;
import com.example.teamunited.Modal.ScreenItem;
import com.example.teamunited.R;
import com.example.teamunited.util;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IntroActivity extends AppCompatActivity {
    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;
    Button btnGetStarted;
    Animation btnAnim;
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util.blackiteamstatusbar(IntroActivity.this, R.color.light_background);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (restorePrefData()) {
            Intent mainActivity = new Intent(getApplicationContext(), Select_Area.class);
            startActivity(mainActivity);
            finish();
        }

        loadlocale();

        setContentView(R.layout.activity_intro);
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);





        // fill list screen

        final List<ScreenItem> mList = new ArrayList<>();

        mList.add(new ScreenItem(getResources().getString(R.string.title1), getResources().getString(R.string.title1_1), getResources().getString(R.string.Description1), R.drawable.traning));
        mList.add(new ScreenItem(getResources().getString(R.string.title2), getResources().getString(R.string.title2_1), getResources().getString(R.string.Description2), R.drawable.achievement));
        mList.add(new ScreenItem(getResources().getString(R.string.title3), getResources().getString(R.string.title3_1), getResources().getString(R.string.Description3), R.drawable.league));


        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tablayout with viewpager

        tabIndicator.setupWithViewPager(screenPager);

        // next button click Listner

        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size() - 1) { // when we rech to the last screen
                    // TODO : show the GETSTARTED Button and hide the indicator and the next butto
                    loaddLastScreen();
                }
            }
        });
        // tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1) {
                    loaddLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // Get Started button click listener

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open main activity
                Intent mainActivity = new Intent(getApplicationContext(), Select_Area.class);
                startActivity(mainActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();


            }
        });

        // skip button click listener

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend", false);
        return isIntroActivityOpnendBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend", true);
        editor.commit();
    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private void loaddLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);


    }
    public void changelanguage()
    {
        String[]list_item={"English",
                "हिंदी"};



        // set dialog message


        AlertDialog.Builder builder = new AlertDialog.Builder(IntroActivity.this);
        builder.setTitle("CHOOSE  LANGUAGE");
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setCancelable(false);

        builder.setSingleChoiceItems(list_item, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0)
                {
                    setLocale("en");
                    recreate();

                }else if(i==1)
                {
                    setLocale("hi");
                    recreate();


                }
                dialogInterface.dismiss();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }


    public void setLocale(String lan) {
        Locale locale=new Locale(lan);
        locale.setDefault(locale);

        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor=getSharedPreferences("setting",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lan);
        editor.apply();

    }
    public void loadlocale()
    {
        SharedPreferences pref=getSharedPreferences("setting", Activity.MODE_PRIVATE);
        String language=pref.getString("My_Lang","");

        setLocale(language);
        if (language.isEmpty())
        {
            changelanguage();
        }


    }
}
