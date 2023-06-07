package com.example.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.response.TVShowResponse;
import com.example.network.ApiClint;
import com.example.network.ApiTVShowService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsRepository {

    private ApiTVShowService apiService;

    public MostPopularTVShowsRepository() {
        this.apiService = ApiClint.getRetrofit().create(ApiTVShowService.class);
    }

    public LiveData<TVShowResponse> getMostPopularMovies (int page){
        MutableLiveData<TVShowResponse> mutableLiveData = new MutableLiveData<>();

        apiService.getMostPopularTVShows(page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }
}
