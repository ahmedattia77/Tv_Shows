package com.example.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.repository.MostPopularTVShowsRepository;
import com.example.response.TVShowResponse;

public class MostPopularTVShowViewModel extends ViewModel {

    MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTVShowViewModel (){
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowResponse> getMostPopularTVShowsRepository (int page){
        return mostPopularTVShowsRepository.getMostPopularMovies(page);
    }
}
