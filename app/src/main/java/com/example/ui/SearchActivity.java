package com.example.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.listeners.TVShowListener;
import com.example.model.TVShow;
import com.example.tvshows.R;
import com.example.tvshows.databinding.ActivitySearchBinding;
import com.example.ui.adapters.TVAdapter;
import com.example.ui.viewModel.SearchTVShowViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TVShowListener {

    private ActivitySearchBinding binding;
    private List<TVShow> tvShowList;
    private SearchTVShowViewModel tvShowViewModel;
    private TVAdapter tvAdapter;
    private int currentPage = 1;
    private int total = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_search);

        initialData();

    }

    private void initialData() {
        tvShowViewModel = new ViewModelProvider(this).get(SearchTVShowViewModel.class);
        binding.recycleView.setVisibility(View.VISIBLE);
        binding.recycleView.setHasFixedSize(true);
        tvShowList = new ArrayList<>();
        tvAdapter = new TVAdapter(tvShowList , this);
        binding.recycleView.setAdapter(tvAdapter);

        binding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()){
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                currentPage =1;
                                total = 1;
                                getData(s.toString());
                            });
                        }
                    }, 800);
                }
                else {
                     tvShowList.clear();
                     tvAdapter.notifyDataSetChanged();
                }
            }
        });

        binding.recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.recycleView.canScrollVertically(1)) {
                    if (currentPage <= total) {
                        currentPage++;
                        getData(binding.searchInput.getText().toString());
                    }
                }
            }
        });

        binding.searchInput.requestFocus();

        binding.backImage.setOnClickListener(v -> onBackPressed());
    }

    private void getData(String query) {
        isCurrentPageLoaded();
        tvShowViewModel.getSearchTVShowsRepository(query,currentPage).observe(this, mostMovies -> {
            isCurrentPageLoaded();
            if (mostMovies != null) {
                if (mostMovies.getTvShows() != null) {
                    int previous = tvShowList.size();
                    total = mostMovies.getPages();
                    tvShowList.addAll(mostMovies.getTvShows());
                    tvAdapter.notifyItemRangeInserted(previous, tvShowList.size());
                }
            }
        });
    }

    private void isCurrentPageLoaded() {
        if (currentPage == 1) {

            if (binding.getIsLoading() != null && binding.getIsLoading())
                binding.setIsLoading(false);
            else
                binding.setIsLoading(true);
        }
        else {

            if (binding.getIsLoadingMore() != null && binding.getIsLoadingMore())
                binding.setIsLoadingMore(false);
            else
                binding.setIsLoadingMore(true);
        }
    }

    @Override
    public void onMovieClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), MovieDetails.class);
        intent.putExtra("tv_show", tvShow);
        startActivity(intent);
    }
}