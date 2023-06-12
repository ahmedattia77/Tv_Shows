package com.example.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.network.ApiClint;
import com.example.network.ApiTVShowService;
import com.example.response.TVShowDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsDetailsRepository {

    private ApiTVShowService apiService;

    public MostPopularTVShowsDetailsRepository() {
        this.apiService = ApiClint.getRetrofit().create(ApiTVShowService.class);
    }

    public LiveData<TVShowDetailsResponse> getDetailsResponseLiveData (String tvShowId){
            MutableLiveData<TVShowDetailsResponse> mutableLiveData = new MutableLiveData<>();

            apiService.getMostPopularTVShowsDetails(tvShowId).enqueue(new Callback<TVShowDetailsResponse>() {
                @Override
                public void onResponse(@NonNull Call<TVShowDetailsResponse> call,@NonNull Response<TVShowDetailsResponse> response) {
                    mutableLiveData.setValue(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<TVShowDetailsResponse> call,@NonNull Throwable t) {
                    mutableLiveData.setValue(null);
                }
            });
            return mutableLiveData;
    }
}
