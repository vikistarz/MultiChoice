package com.company.mulitchoice.retrofitApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BillersApi {

    public static final String BILLERS_BASE_URL = "https://run.mocky.io/";

    public static Retrofit myRetrofit;

    public static Retrofit getMyRetrofit(){

        HttpLoggingInterceptor myLoggingInterceptor = new HttpLoggingInterceptor();
        myLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(myLoggingInterceptor).build();

        if(myRetrofit == null){

            myRetrofit = new Retrofit.Builder().baseUrl(BILLERS_BASE_URL).client(myClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return myRetrofit;
    }
}
