package com.example.network;

import com.example.response.TVShowDetailsResponse;
import com.example.response.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiTVShowService {
    @GET("most-popular")
    Call <TVShowResponse> getMostPopularTVShows(@Query("page") int page);

    @GET("show-details")
    Call <TVShowDetailsResponse> getMostPopularTVShowsDetails(@Query("q") String tvShowId);

    @GET("search")
    Call<TVShowResponse> searchTVShows (@Query("q") String query , @Query("page") int page);

}
