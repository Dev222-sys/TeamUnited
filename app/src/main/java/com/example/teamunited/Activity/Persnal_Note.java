package com.example.teamunited.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.teamunited.Adapter.Adapter;
import com.example.teamunited.Modal.Note;
import com.example.teamunited.R;
import com.example.teamunited.storage.SimpleDatabase;

import java.util.List;

public class Persnal_Note extends AppCompatActivity {

    ConstraintLayout constraintLayout;

    Toolbar toolbar;
    RecyclerView recyclerView;
    Adapter adapter;
    TextView noItemText;
    SimpleDatabase simpleDatabase;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnal__note);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Persnal Note");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        constraintLayout=findViewById(R.id.constraintLayout);
        constraintLayout.setBackgroundColor(R.color.white);

        noItemText = findViewById(R.id.noItemText);
        simpleDatabase = new SimpleDatabase(this);
        List<Note> allNotes = simpleDatabase.getAllNotes();
        recyclerView = findViewById(R.id.allNotesList);
        constraintLayout.setBackgroundColor(R.color.white);

        if(allNotes.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);

          /*  noItemText.setTextColor(Color.parseColor("#000000"));
            constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));*/

           /* noItemText.setTextColor(R.color.black);
            constraintLayout.setBackgroundColor(R.color.white);*/

        }else {
            noItemText.setVisibility(View.GONE);

            /*
            noItemText.setTextColor(Color.parseColor("#000000"));
            constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));*/
            displayList(allNotes);
        }


    }
    private void displayList(List<Note> allNotes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,allNotes);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add){
            Toast.makeText(this, "Add New Note", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,AddNote.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Note> getAllNotes = simpleDatabase.getAllNotes();
        if(getAllNotes.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
/*
            noItemText.setTextColor(Color.parseColor("#000000"));
            constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));*/
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(getAllNotes);
/*
            noItemText.setTextColor(Color.parseColor("#000000"));
            constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));*/
        }


    }

}