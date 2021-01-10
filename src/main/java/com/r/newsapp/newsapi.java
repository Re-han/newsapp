package com.r.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface newsapi {
    @GET("top-headlines")
    Call<Details> getHeadlines(@Query("country") String country,
                               @Query("apiKey") String api_key);

    @GET("top-headlines")
    Call<Details> getCategory(@Query("country") String country,
                              @Query("category") String category,
                              @Query("pageSize") int pageSize, @Query("apiKey") String api_key);

    @GET("everything")
    Call<Details> getEverything(@Query("q") String query,
                                @Query("apiKey") String api_key);
}
