package com.example.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.Database.TVShowDatabase;
import com.example.model.TVShow;
import com.example.repository.MostPopularTVShowsDetailsRepository;
import com.example.response.TVShowDetailsResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private MostPopularTVShowsDetailsRepository mostPopularTVShowsDetailsRepository;
    private TVShowDatabase tvShowDatabase;

    public TVShowDetailsViewModel(@NonNull Application application){
        super(application);
        mostPopularTVShowsDetailsRepository = new MostPopularTVShowsDetailsRepository();
        tvShowDatabase = TVShowDatabase.tvShowDatabase(application);
    }

    public LiveData<TVShowDetailsResponse> getMostPopularTVShowsDetailsRepository (String tvShowId){
        return mostPopularTVShowsDetailsRepository.getDetailsResponseLiveData(tvShowId);
    }

    public Flowable<TVShow> getTVShowFromWatchLater (String tvId){
        return tvShowDatabase.tvShowDao().getWatchLater(tvId);
    }

    public Completable removeTVShowWatchLater (TVShow tvShow){
        return tvShowDatabase.tvShowDao().deleteTVShow(tvShow);
    }

    public Completable insertTVShow (TVShow tvShow){
        return tvShowDatabase.tvShowDao().addWatchLater(tvShow);
    }

}
