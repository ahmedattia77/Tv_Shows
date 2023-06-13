package com.example.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.listeners.TVShowListener;
import com.example.model.TVShow;
import com.example.tvshows.R;
import com.example.tvshows.databinding.ActivityMainBinding;
import com.example.ui.adapters.TVAdapter;
import com.example.ui.viewModel.MostPopularTVShowViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowListener{

    private ActivityMainBinding binding;
    private MostPopularTVShowViewModel viewModel;
    private TVAdapter tvShowAdapter;
    private int currentPage = 1;
    private int allPage = 1;
    private List<TVShow> tvShowList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        initialRecycle();
    }



    private void initialRecycle(){
        binding.mainRecycle.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowViewModel.class);
        tvShowAdapter = new TVAdapter(tvShowList, this);

        binding.mainRecycle.setAdapter(tvShowAdapter);
        binding.mainRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!binding.mainRecycle.canScrollVertically(1)){
                    if (currentPage <= allPage){
                        currentPage++;
                        showMostPopularTvShow();
                    }
                }
            }
        });
        showMostPopularTvShow();
    }

    private void showMostPopularTvShow(){
        isCurrentPageLoaded();

        viewModel.getMostPopularTVShowsRepository(currentPage).observe(this , mostMovies -> {
            isCurrentPageLoaded();
            if (mostMovies!=null){
                if(mostMovies.getTvShows()!=null){
                    int previous = tvShowList.size();
                    allPage = mostMovies.getPages();
                    tvShowList.addAll(mostMovies.getTvShows());
                    tvShowAdapter.notifyItemRangeInserted(previous,tvShowList.size());
                }
            }
        });
    }

    private void isCurrentPageLoaded () {
        if (currentPage == 1){

            if (binding.getIsLoading()!= null && binding.getIsLoading()){binding.setIsLoading(false);}
            else{binding.setIsLoading(true);}

        }else{

            if (binding.getIsLoadingMorePages() != null && binding.getIsLoadingMorePages()){binding.setIsLoadingMorePages(false);}
            else {binding.setIsLoadingMorePages(true);}
        }
    }

    @Override
    public void onMovieClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext() , MovieDetails.class );
        intent.putExtra("tv_show" , tvShow);
        startActivity(intent);
    }

}