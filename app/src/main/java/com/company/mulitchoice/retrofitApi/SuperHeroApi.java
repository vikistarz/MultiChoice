package com.company.mulitchoice.retrofitApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuperHeroApi {


    public static final String GAME_BASE_URL = "https://simplifiedcoding.net/demos/";


    public static Retrofit myRetrofit;

    public static Retrofit getMyRetrofit(){

        HttpLoggingInterceptor myLoggingInterceptor = new HttpLoggingInterceptor();
        myLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(myLoggingInterceptor).build();

        if(myRetrofit == null){

            myRetrofit = new Retrofit.Builder().baseUrl(GAME_BASE_URL).client(myClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return myRetrofit;
    }
}
