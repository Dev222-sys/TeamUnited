package com.example.teamunited.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.teamunited.CheckInternetConnection;
import com.example.teamunited.R;
import com.example.teamunited.RetrofitClient;
import com.irozon.sneaker.Sneaker;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Password_Activity extends AppCompatActivity {
Button Submit;
    MaterialEditText password, confirm_password;

    String otp=null,mobile_no = null,Did_id = null;
    String passwordd,confirm_passwordd;
    Progress progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_);
        initview();
    }

    private void initview() {
        progress=new Progress();

        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
        Submit=findViewById(R.id.Submit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            mobile_no=bundle.getString("mobile_no");
            Did_id=bundle.getString("Did_id");
            //Toast.makeText(this, mobile_no+Did_id+"", Toast.LENGTH_SHORT).show();
            }

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPasswordvalidation();

            }
        });
    }
    public  void setPasswordvalidation()
    {



        passwordd=password.getText().toString().trim();
        confirm_passwordd=confirm_password.getText().toString().trim();



         if (passwordd.isEmpty() || passwordd.length() < 6) {

            Sneaker.with(Password_Activity.this)
                    .setTitle("Please Enter Password More Then 5 Value!")
                    .setMessage("")
                    .sneakError();
        }
         else if (confirm_passwordd.isEmpty() || !confirm_passwordd .equals(passwordd)) {
             Sneaker.with(Password_Activity.this)
                     .setTitle("Please Enter Confirm Password !")
                     .setMessage("")
                     .sneakError();

    }
        else {


             final_registration();
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

    private void final_registration() {
        progress.createdialog(this);
        progress.showpDialog();
        Call<ResponseBody> call= RetrofitClient
                .getInstance()
                .getApi().final_registration("1",Did_id,mobile_no,passwordd);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s=null;
                try {
                    if (response.code()==200)
                    {
                        s=response.body().string();


                        Sneaker.with(Password_Activity.this)
                                .setTitle("success")
                                .setMessage("")
                                .sneakSuccess();

                        Intent intent = new Intent(Password_Activity.this,Guest_Login.class);
                        startActivity(intent);

                        progress.hidepDialog();
                    }
                    else {
                        s=response.errorBody().string();
                        JSONObject jsonObject=new JSONObject(s);
                        String error=jsonObject.getString("error");
                        Sneaker.with(Password_Activity.this)
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

                Sneaker.with(Password_Activity.this)
                        .setTitle("Server Error")
                        .setMessage("")
                        .sneakError();

                progress.hidepDialog();

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in =new Intent(Password_Activity.this,Guest_Login.class);
        startActivity(in);

        //finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //check Internet Connection

        new CheckInternetConnection(this).checkConnection();
    }

}