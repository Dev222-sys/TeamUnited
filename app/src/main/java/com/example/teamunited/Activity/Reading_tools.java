package com.example.teamunited.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamunited.Adapter.NewsAdapter;
import com.example.teamunited.Adapter.PDFAdapter;
import com.example.teamunited.MainActivity;
import com.example.teamunited.Modal.PDFModel;
import com.example.teamunited.R;
import com.example.teamunited.callback.ItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Reading_tools extends AppCompatActivity {

    RecyclerView recyclerView;
    public static List<PDFModel> list;
    EditText searchInput;
    CharSequence search = "";
    FloatingActionButton fabSwitcher;
    PDFAdapter adapter;

    ConstraintLayout rootLayout;

    boolean isDark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_reading_tools);
        recyclerView = findViewById(R.id.RV);
        searchInput = findViewById(R.id.search_input);
        fabSwitcher = findViewById(R.id.fab_switcher);
        rootLayout = findViewById(R.id.root_layout);

        list = new ArrayList<>();
        list.add(new PDFModel("Team united One", "https://www.cs.cmu.edu/afs/cs.cmu.edu/user/gchen/www/download/java/LearnJava.pdf"));
        list.add(new PDFModel("Team united Two", "http://enos.itcollege.ee/~jpoial/allalaadimised/reading/Android-Programming-Cookbook.pdf"));
        list.add(new PDFModel("Team united Three", "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"));
        list.add(new PDFModel("Team united Four", "http://www.africau.edu/images/default/sample.pdf"));
        list.add(new PDFModel("Team united Five", "http://www.pdf995.com/samples/pdf.pdf"));
        list.add(new PDFModel("Team united Six", "https://www.cs.cmu.edu/afs/cs.cmu.edu/user/gchen/www/download/java/LearnJava.pdf"));
        list.add(new PDFModel("Team united Seven", "https://www.cs.cmu.edu/afs/cs.cmu.edu/user/gchen/www/download/java/LearnJava.pdf"));
        list.add(new PDFModel("Team united Eight", "https://www.cs.cmu.edu/afs/cs.cmu.edu/user/gchen/www/download/java/LearnJava.pdf"));
        list.add(new PDFModel("Team united Nine", "http://enos.itcollege.ee/~jpoial/allalaadimised/reading/Android-Programming-Cookbook.pdf"));
        list.add(new PDFModel("Team united  Ten", "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"));
        list.add(new PDFModel("Team united Eleven", "http://www.africau.edu/images/default/sample.pdf"));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(Reading_tools.this, PDFActivity.class);
                //intent.putExtra("url",list.get(position).getPdfUrl());
                intent.putExtra("position", position);
                startActivity(intent);
            }
        };

       adapter = new PDFAdapter(list, this, itemClickListener,isDark);
        recyclerView.setAdapter(adapter);
        fabSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDark = !isDark ;
                if (isDark) {

                    rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                    searchInput.setBackgroundResource(R.drawable.search_input_dark_style);

                }
                else {
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    searchInput.setBackgroundResource(R.drawable.search_input_style);
                }
                adapter = new PDFAdapter(list, getApplicationContext(), itemClickListener,isDark);

                if (!search.toString().isEmpty()){

                    adapter.getFilter().filter(search);

                }
                recyclerView.setAdapter(adapter);
                saveThemeStatePref(isDark);




            }
        });
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                adapter.getFilter().filter(s);
                search = s;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    private void saveThemeStatePref(boolean isDark) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDark",isDark);
        editor.commit();
    }

    private boolean getThemeStatePref () {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        boolean isDark = pref.getBoolean("isDark",false) ;
        return isDark;

    }


}