package com.example.teamunited;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamunited.Activity.Guest_Login;
import com.example.teamunited.Activity.News;
import com.example.teamunited.Activity.Progress;
import com.example.teamunited.Activity.Reading_tools;
import com.example.teamunited.Activity.Video_tools;
import com.example.teamunited.Fragment.Home;
import com.example.teamunited.Modal.User_login;
import com.example.teamunited.storage.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.irozon.sneaker.Sneaker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    DrawerLayout drawer;
    NavigationView navigationView;
    AppBarConfiguration appBarConfiguration;
    BottomNavigationView bottomNavigationView;
    ImageView share;
    Progress progress;
    Toolbar toolbar;
    TextView did,number,reading_tools;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        util.blackiteamstatusbar(MainActivity.this,R.color.tab_indicator_gray);

        //changeStatusBarColor();
        init();
       /*
*/


        //  toolbar.setNavigationIcon(R.drawable.your_drawable_name);


        // NavigationView();
    }

    private void init() {



        share=findViewById(R.id.share);
        drawer = findViewById(R.id.drawer);
        progress=new Progress();
        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        //reading_tools=findViewById(R.id.reading);

        /*reading_tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MainActivity.this, Reading_tools.class);
                startActivity(in);

            }
        });
*/
       Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_support, getApplicationContext().getTheme());

        navController = Navigation.findNavController(this,R.id.main);

        appBarConfiguration = new AppBarConfiguration.Builder(new int[]{
                R.id.home,R.id.news,R.id.gallery,R.id.myWallet,R.id.reading_tools,R.id.video_tools,R.id.my_account,R.id.persnol_note,R.id.audio_tools,})
                .setDrawerLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
       // navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        MenuItem sharee= menu.findItem(R.id.share);

        sharee.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Share();
                return true;
            }
        });
        MenuItem logout=menu.findItem(R.id.logout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AlertDialogBox();

                return true;
            }
        });



        View header=navigationView.getHeaderView(0);

        did=(TextView)header.findViewById(R.id.did);
        number=(TextView)header.findViewById(R.id.number);
        did.setText(SharedPrefManager.getInstans(getApplicationContext()).getDid());
        number.setText(SharedPrefManager.getInstans(getApplicationContext()).getNumber());


        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Share();

            }
        });


    }

    private void Share() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String sAux = "Hey,\n Its amazing install Team United App " +"\n Download "+ getResources().getString(R.string.app_name) + "\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }







    public void change_menu_icon(){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_support);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,appBarConfiguration );
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

  /*  private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }*/



        


    public void logout()
    {

        String token="1";

        String id = SharedPrefManager.getInstans(getApplicationContext()).getUserId();
       // Toast.makeText(MainActivity.this, id+"", Toast.LENGTH_SHORT).show();


       progress.createdialog(this);
        progress.showpDialog();

        Call<ResponseBody> call= RetrofitClient
                .getInstance()
                .getApi().logout(token,id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s=null;
                try {
                    if (response.code()==200)
                    {
                        s=response.body().string();
                        JSONObject jsonObject=new JSONObject(s);
                        String msg=jsonObject.getString("message");

                        Toast.makeText(MainActivity.this, msg+"", Toast.LENGTH_SHORT).show();
                        SharedPrefManager.getInstance(MainActivity.this).clear();
                        Intent intent = new Intent(MainActivity.this,Guest_Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                        progress.hidepDialog();
                    }
                    else {
                        s=response.errorBody().string();
                        JSONObject jsonObject=new JSONObject(s);
                        String error=jsonObject.getString("error");
                        progress.hidepDialog();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Sneaker.with(MainActivity.this)
                        .setTitle("Server Error")
                        .setMessage("")
                        .sneakError();
                progress.hidepDialog();

            }
        });



/*
       */
    }

    @Override
    protected void onResume() {
        super.onResume();
        //check Internet Connection

        new CheckInternetConnection(this).checkConnection();
    }
    public void AlertDialogBox(){

        //Logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        // set title
        alertDialogBuilder.setTitle("TEAM UNITED");

        // set dialog message
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher_round);
        alertDialogBuilder
                .setMessage("Are you sure to Logout !!!!!")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logout();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //Fragment fragment = null;

        int id= menuItem.getItemId();
        if(id == R.id.home)
        {
            *//*FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();


            transaction.addToBackStack(null);
            transaction.commit();*//*
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.gallery)
        {

            Toast.makeText(this, "gallery", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.news)
        {

            Toast.makeText(this, "news", Toast.LENGTH_SHORT).show();
            Intent in=new Intent(MainActivity.this, News.class);
            startActivity(in);
        } else if(id == R.id.self_analysis)
        {

            Toast.makeText(this, "self_analysis", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.pro_tarining_module)
        {

            Toast.makeText(this, "pro_tarining_module", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.audio_tools)
        {

            Toast.makeText(this, "audio_tools", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.video_tools)
        {

            Intent in=new Intent(MainActivity.this, Video_tools.class);
            startActivity(in);


            Toast.makeText(this, "video_tools", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.reading_tools)
        {

            Intent in=new Intent(MainActivity.this, Reading_tools.class);
            startActivity(in);


            Toast.makeText(this, "reading_tools", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.my_account)
        {

            Toast.makeText(this, "my_account", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.my_subcriber)
        {

            Toast.makeText(this, "my_subcriber", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.event)
        {

            Toast.makeText(this, "event", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.persnol_note)
        {

            Toast.makeText(this, "persnol_note", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.feedback)
        {

            Toast.makeText(this, "feedback", Toast.LENGTH_SHORT).show();
        }
        else  if(id == R.id.testinomials)
        {
            Toast.makeText(this, "testinomials", Toast.LENGTH_SHORT).show();

        }  else  if(id == R.id.atm)
        {
            Toast.makeText(this, "atm", Toast.LENGTH_SHORT).show();

        }  else  if(id == R.id.share)
        {
            Share();

            Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();

        }
        else if(id == R.id.logout)
        {
            logout();
            //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
}