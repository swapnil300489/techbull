package com.example.techbull.netwokcall;

import com.example.techbull.pojo.Movie;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {

    @GET("/")
    Call<Movie> MOVIE_CALL(@Query("s") String s,
                           @Query("apikey") String apikey);


}
