package com.example.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.network.ApiClint;
import com.example.network.ApiTVShowService;
import com.example.response.TVShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTVShowsRepository {

    private ApiTVShowService apiService;

    public SearchTVShowsRepository() {
        this.apiService = ApiClint.getRetrofit().create(ApiTVShowService.class);
    }

    public LiveData<TVShowResponse> SearchMostPopularMovies (String query,int page){
        MutableLiveData<TVShowResponse> mutableLiveData = new MutableLiveData<>();

        apiService.searchTVShows(query,page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call,@NonNull Response<TVShowResponse> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call,@NonNull Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }
}
