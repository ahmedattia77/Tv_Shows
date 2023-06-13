package com.example.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.Database.TVShowDatabase;
import com.example.Database.TVShowDatabase_Impl;
import com.example.model.TVShow;
import com.example.repository.MostPopularTVShowsDetailsRepository;
import com.example.response.TVShowDetailsResponse;

import io.reactivex.Completable;

public class MostPopularTVShowDetailsViewModel extends AndroidViewModel {

    private MostPopularTVShowsDetailsRepository mostPopularTVShowsDetailsRepository;
    private TVShowDatabase tvShowDatabase;

    public MostPopularTVShowDetailsViewModel(@NonNull Application application){
        super(application);
        mostPopularTVShowsDetailsRepository = new MostPopularTVShowsDetailsRepository();
        tvShowDatabase = TVShowDatabase.tvShowDatabase(application);
    }

    public LiveData<TVShowDetailsResponse> getMostPopularTVShowsDetailsRepository (String tvShowId){
        return mostPopularTVShowsDetailsRepository.getDetailsResponseLiveData(tvShowId);
    }

    public Completable insertTVShow (TVShow tvShow){
       return tvShowDatabase.tvShowDao().addWatchLater(tvShow);
    }

}
