package com.example.finalmobiletest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {

    @Headers({
            "X-RapidAPI-Key: 031d95d0eemsh998024cd637426ep1e0e22jsnc9861488e575",
            "X-RapidAPI-Host: famous-quotes4.p.rapidapi.com"
    })
    @GET("/random")
    Call<List<Quote>> getQuotes(@Query("category") String category, @Query("count") int count);
}
