package com.example.ui.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.Database.TVShowDatabase;
import com.example.model.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchListViewModel extends AndroidViewModel {

    private TVShowDatabase tvShowDatabase;

    public WatchListViewModel (Application application){
        super(application);
        tvShowDatabase = TVShowDatabase.tvShowDatabase(application);
    }

    public Flowable<List<TVShow>> getTVShows (){
        return tvShowDatabase.tvShowDao().getWatchLater();
    }

    public Completable insertTVShow (TVShow tvShow){
        return tvShowDatabase.tvShowDao().addWatchLater(tvShow);
    }

    public Completable removeTVShowWatchLater (TVShow tvShow){
        return tvShowDatabase.tvShowDao().deleteTVShow(tvShow);
    }

}
