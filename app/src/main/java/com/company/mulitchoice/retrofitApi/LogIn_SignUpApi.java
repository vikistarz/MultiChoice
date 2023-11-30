package com.company.mulitchoice.retrofitApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogIn_SignUpApi {

    public static final String USER_LOGIN_SIGNUP_BASE_URL = "https://642d-102-89-47-220.ngrok-free.app";

    public static Retrofit myRetrofit;

    public static Retrofit getMyRetrofit(){

        HttpLoggingInterceptor myLoggingInterceptor = new HttpLoggingInterceptor();
        myLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(myLoggingInterceptor).build();

        if(myRetrofit == null){

            myRetrofit = new Retrofit.Builder().baseUrl(USER_LOGIN_SIGNUP_BASE_URL).client(myClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return myRetrofit;
    }
}
