package com.flaviumircia.aquatrouble.restdata.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private static Retrofit retrofitInstance;

    public static Retrofit getInstance()
    {
        if (retrofitInstance==null)
        {
            retrofitInstance=new Retrofit.Builder()
                    .baseUrl("http://52.57.0.62:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitInstance;

    }
    public RetrofitClient(){}
}
