package com.example.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.repository.MostPopularTVShowsRepository;
import com.example.repository.SearchTVShowsRepository;
import com.example.response.TVShowResponse;

public class SearchTVShowViewModel extends ViewModel {

    private SearchTVShowsRepository searchTVShowsRepository;

    public SearchTVShowViewModel(){
        searchTVShowsRepository = new SearchTVShowsRepository();
    }

    public LiveData<TVShowResponse> getSearchTVShowsRepository (String query ,int page){
        return searchTVShowsRepository.SearchMostPopularMovies(query,page);
    }
}
