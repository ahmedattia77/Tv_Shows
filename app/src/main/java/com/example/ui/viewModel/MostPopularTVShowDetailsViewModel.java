package com.example.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.repository.MostPopularTVShowsDetailsRepository;
import com.example.response.TVShowDetailsResponse;

public class MostPopularTVShowDetailsViewModel extends ViewModel {

    MostPopularTVShowsDetailsRepository mostPopularTVShowsDetailsRepository;

    public MostPopularTVShowDetailsViewModel(){
        mostPopularTVShowsDetailsRepository = new MostPopularTVShowsDetailsRepository();
    }

    public LiveData<TVShowDetailsResponse> getMostPopularTVShowsDetailsRepository (String tvShowId){
        return mostPopularTVShowsDetailsRepository.getDetailsResponseLiveData(tvShowId);
    }

}
