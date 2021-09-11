package com.example.newsjunction;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {
    @GET
    Call<NewsModal> getNewsByCategory(@Url String url);
}
