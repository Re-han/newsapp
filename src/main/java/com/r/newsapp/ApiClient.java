package com.r.newsapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public newsapi newsapi = retrofit.create(com.r.newsapp.newsapi.class);
    public ApiClient(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
}
