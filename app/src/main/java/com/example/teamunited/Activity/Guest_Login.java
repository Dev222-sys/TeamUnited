package com.example.teamunited.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamunited.CheckInternetConnection;
import com.example.teamunited.MainActivity;
import com.example.teamunited.Modal.User_login;
import com.example.teamunited.R;
import com.example.teamunited.RetrofitClient;
import com.example.teamunited.storage.SharedPrefManager;
import com.irozon.sneaker.Sneaker;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Guest_Login extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
//dveeb
    Toolbar toolbar;
    String[] bankNames={"India","UAE"};
LinearLayout sign_in,sign_up,forget;
    Progress progress;
    TextView title,tv_sign_up,tv_forget,tv_Did_id,tv_phone_no;
    Button Send_otp,Sign_in,forget_otp;
    String mobile_no,Did_id;
    MaterialEditText ed_sign_in_distributor_id,ed_sign_in_password,ed_forget_number;
    String token="1";
    KProgressHUD pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest__login);
       /* toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    */ /*   getSupportActionBar().setTitle(getResources().getString(R.string.guest_login));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/
        initView();







         }

    private void initView() {
        progress=new Progress();
        title=findViewById(R.id.title);
        Send_otp=findViewById(R.id.Send_otp);
        Sign_in=findViewById(R.id.Sign_in);
        tv_sign_up=findViewById(R.id.tv_sign_up);
        tv_forget=findViewById(R.id.tv_forget);
        sign_in=findViewById(R.id.sign_in);
        sign_up=findViewById(R.id.sign_up);
        forget=findViewById(R.id.forget);
        tv_Did_id=findViewById(R.id.Did_id);
        tv_phone_no=findViewById(R.id.phone_no);
        forget_otp=findViewById(R.id.forget_otp);
        ed_forget_number=findViewById(R.id.forget_number);



        ed_sign_in_distributor_id=findViewById(R.id.sign_in_distributor_id);
        ed_sign_in_password=findViewById(R.id.sign_in_password);


        forget_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Forget_validation();

            }
        });
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_in.setVisibility(View.GONE);
                //Toast.makeText(progress, "tv_sign_in", Toast.LENGTH_SHORT).show();
             forget.setVisibility(View.VISIBLE);

                //  Toast.makeText(progress, " tv_forget", Toast.LENGTH_SHORT).show();

            }
        });
        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_in.setVisibility(View.GONE);
               //Toast.makeText(progress, "tv_sign_in", Toast.LENGTH_SHORT).show();
                sign_up.setVisibility(View.VISIBLE);
            }
        });

        Send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sign_up_validation();



            }
        });
        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sign_validation();
                /*
                Intent in=new Intent(Guest_Login.this, MainActivity.class);
                startActivity(in);
           */ }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedin())
        {
            Intent intent = new Intent(Guest_Login.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }

    public  void Sign_validation()
    {


        String password,Did_id;

        Did_id=ed_sign_in_distributor_id.getText().toString().trim();
        password=ed_sign_in_password.getText().toString().trim();

        if (TextUtils.isEmpty( Did_id)) {

            Sneaker.with(Guest_Login.this)
                    .setTitle("Please Enter Distributor ID!")
                    .setMessage("")
                    .sneakError();


        }
        else if (password.isEmpty() || password.length() < 6) {


            Sneaker.with(Guest_Login.this)
                    .setTitle("Please enter Password !")
                    .setMessage("")
                    .sneakError();

        }
        else {

            progress.createdialog(this);
            progress.showpDialog();

            Call<ResponseBody> call= RetrofitClient
                    .getInstance()
                    .getApi().login(token,Did_id,password);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String s=null;
                    try {
                        if (response.code()==200)
                        {
                            s=response.body().string();
                            JSONObject jsonObject=new JSONObject(s);
                            String user_id=jsonObject.getString("id");
                            String did=jsonObject.getString("distributed id");
                            String number=jsonObject.getString("phone");

                           // Toast.makeText(Guest_Login.this, user_id+"", Toast.LENGTH_SHORT).show();
                            User_login user_login=new User_login(1,did,number,user_id);

                            SharedPrefManager.getInstance(Guest_Login.this)
                                    .saveuser(user_login);


                            Intent intent = new Intent(Guest_Login.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                            progress.hidepDialog();
                        }
                        else {
                            s=response.errorBody().string();
                            JSONObject jsonObject=new JSONObject(s);
                            String error=jsonObject.getString("error");
                            Sneaker.with(Guest_Login.this)
                                    .setTitle(error)
                                    .setMessage("")
                                    .sneakError();
                            progress.hidepDialog();
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    Sneaker.with(Guest_Login.this)
                            .setTitle("Server Error")
                            .setMessage("")
                            .sneakError();
                    progress.hidepDialog();

                }
            });


        }

    }

    public  void Sign_up_validation()
    {
        progress.createdialog(this);

        progress.showpDialog();

        mobile_no=tv_phone_no.getText().toString().trim();
        Did_id=tv_Did_id.getText().toString().trim();
        if (TextUtils.isEmpty( Did_id)) {
            Sneaker.with(Guest_Login.this)
                    .setTitle("Please Enter Distributor ID!")
                    .setMessage("")
                    .sneakError();

            progress.hidepDialog();
            /*
            tv_Did_id.setError("Please Enter Distributor ID");
            tv_Did_id.requestFocus();
            Toast.makeText(this, "Please Enter Distributor ID", Toast.LENGTH_SHORT).show();
        */}
       else if (mobile_no.isEmpty() || mobile_no.length() < 10) {
            Sneaker.with(Guest_Login.this)
                    .setTitle("Please enter Mobile Number!")
                    .setMessage("")
                    .sneakError();

            progress.hidepDialog();
        }
        else {
                Call<ResponseBody> call= RetrofitClient
                    .getInstance()
                    .getApi().registration(token,Did_id,mobile_no);
                     call.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              String s=null;
              try {
              if (response.code()==200)
              {
                       s=response.body().string();

                  JSONObject jsonObject=new JSONObject(s);
                  String otp=jsonObject.getString("otp");
                  String status=jsonObject.getString("status");
                  Sneaker.with(Guest_Login.this)
                          .setTitle(status)
                          .setMessage("")
                          .sneakSuccess();
                  //Toast.makeText(Guest_Login.this, otp, Toast.LENGTH_SHORT).show();

                  Bundle bundle = new Bundle();
                  bundle.putString("otp",otp);
                  bundle.putString("mobile_no",mobile_no);
                  bundle.putString("Did_id",Did_id);


                  Intent intent = new Intent(Guest_Login.this,Otp.class);
                  intent.putExtras(bundle);
                  startActivity(intent);

                  progress.hidepDialog();
              }
              else {
                  s=response.errorBody().string();
                  JSONObject jsonObject=new JSONObject(s);
                  String error=jsonObject.getString("error");
                  Sneaker.with(Guest_Login.this)
                          .setTitle(error)
                          .setMessage("")
                          .sneakError();
                  progress.hidepDialog();
              }
              } catch (IOException | JSONException e) {
                  e.printStackTrace();
              }

             /* if (s!=null)
              {
                  try {

                      JSONObject jsonObject=new JSONObject(s);

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

              }*/

          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {

              Sneaker.with(Guest_Login.this)
                      .setTitle("Server Error")
                      .setMessage("")
                      .sneakError();
              progress.hidepDialog();

          }
      });

           /*
            Intent in=new Intent(Guest_Login.this,Password_Activity.class);
            startActivity(in);
*/
        }


/*

        progress.createdialog(Guest_Login.this);
        progress.showpDialog();
        Intent intent=new Intent(Guest_Login.this,Otp.class);
        startActivity(intent);
        Toast.makeText(Guest_Login.this, mobile_no+Did_id+"", Toast.LENGTH_SHORT).show();
        progress.hidepDialog();
*/

    }
    public  void Forget_validation()
    {
        progress.createdialog(this);

        progress.showpDialog();
        String forget_number;
        forget_number=ed_forget_number.getText().toString().trim();
         if ( forget_number.isEmpty() ||  forget_number.length() <10) {

            Sneaker.with(Guest_Login.this)
                    .setTitle("Please enter Mobile Number!")
                    .setMessage("")
                    .sneakError();
            progress.hidepDialog();
        }
         else
         {
             Call<ResponseBody> call= RetrofitClient
                     .getInstance()
                     .getApi().forget(token,forget_number);
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


                             Sneaker.with(Guest_Login.this)
                                     .setTitle(msg)
                                     .setMessage("")
                                     .sneakSuccess();
                             Toast.makeText(Guest_Login.this, msg+"", Toast.LENGTH_SHORT).show();
                             forget.setVisibility(View.GONE);
                             sign_in.setVisibility(View.VISIBLE);
                             //Toast.makeText(Guest_Login.this, otp, Toast.LENGTH_SHORT).show();
                         progress.hidepDialog();

                         }
                         else {
                             s=response.errorBody().string();
                             JSONObject jsonObject=new JSONObject(s);
                             String error=jsonObject.getString("error");
                             Sneaker.with(Guest_Login.this)
                                     .setTitle(error)
                                     .setMessage("")
                                     .sneakError();
                             progress.hidepDialog();
                         }
                     } catch (IOException | JSONException e) {
                         e.printStackTrace();
                     }
                 }
                 @Override
                 public void onFailure(Call<ResponseBody> call, Throwable t) {

                     Sneaker.with(Guest_Login.this)
                             .setTitle("Server Error")
                             .setMessage("")
                             .sneakError();
                     progress.hidepDialog();

                 }
             });





         }


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
        Intent in =new Intent(Guest_Login.this,Select_Area.class);
        startActivity(in);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getApplicationContext(), bankNames[position], Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();
    }

}