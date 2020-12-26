package com.example.teamunited.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.teamunited.CheckInternetConnection;
import com.example.teamunited.MainActivity;
import com.example.teamunited.R;
import com.irozon.sneaker.Sneaker;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Otp extends AppCompatActivity {
    Toolbar toolbar;
    MaterialEditText ed_otp;
    Progress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initView();

        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.Otp));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/

    }

    private void initView() {
        progress=new Progress();
        ed_otp=findViewById(R.id.ed_otp);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void Onclick_Submit(View view)
    {
        OTP_verified();
    }

         public  void OTP_verified()
         {

             progress.createdialog(this);

             progress.showpDialog();
             String otp=null,mobile_no = null,Did_id = null,ed_otpp;
             ed_otpp=ed_otp.getText().toString().trim();

             Bundle bundle = getIntent().getExtras();
             if (bundle != null)
             {
                 otp = bundle.getString("otp");
                 mobile_no=bundle.getString("mobile_no");
                 Did_id=bundle.getString("Did_id");
                // Toast.makeText(this, otp+"", Toast.LENGTH_SHORT).show();
                 progress.hidepDialog();
             }
             if (ed_otpp.equals(otp))
             {
                 Sneaker.with(Otp.this)
                         .setTitle("otp verified")
                         .setMessage("")
                         .sneakSuccess();
                 Bundle bundle1 = new Bundle();
                 // bundle.putString("otp",otp);
                 bundle1.putString("mobile_no",mobile_no);
                 bundle1.putString("Did_id",Did_id);
                 Intent intent = new Intent(Otp.this,Password_Activity.class);
                 intent.putExtras(bundle1);
                 startActivity(intent);
                 progress.hidepDialog();
             }else
             {
                 Sneaker.with(Otp.this)
                         .setTitle("invalid otp ")
                         .setMessage("")
                         .sneakError();
                 progress.hidepDialog();
             }

             progress.hidepDialog();


         }
    @Override
    protected void onResume() {
        super.onResume();
        //check Internet Connection

        new CheckInternetConnection(this).checkConnection();
    }


}