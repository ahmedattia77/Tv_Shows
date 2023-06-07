package com.example.network;

import com.example.response.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiTVShowService {
    @GET("most-popular")
    Call <TVShowResponse> getMostPopularTVShows(@Query("page") int page);

}
