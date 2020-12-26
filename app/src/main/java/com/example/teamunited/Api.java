package com.example.teamunited;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("registration.php/")
    Call<ResponseBody>registration(
      @Field("token") String token,
      @Field("did") String did,
      @Field("phone") String phone);

    @FormUrlEncoded
    @POST("final_registration.php/")
    Call<ResponseBody>final_registration(
            @Field("token") String token,
            @Field("did") String did,
            @Field("phone") String phone,
            @Field("password") String pass
    );


    @FormUrlEncoded
    @POST("login.php/")
    Call<ResponseBody>login(
            @Field("token") String token,
            @Field("did") String did,
            @Field("password") String pass
    );
    @FormUrlEncoded

    @POST("forget.php/")
    Call<ResponseBody>forget(
            @Field("token") String token,
            @Field("phone") String did

    );

    @FormUrlEncoded
    @POST("logout.php/")
    Call<ResponseBody>logout(
            @Field("token") String token,
            @Field("id") String id

    );



}
