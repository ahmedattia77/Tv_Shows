package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.listeners.WatchLaterListener;
import com.example.model.TVShow;
import com.example.model.TVShowDetails;
import com.example.tvshows.R;
import com.example.tvshows.databinding.ActivityWatchLaterBinding;
import com.example.ui.adapters.TVLaterAdapter;
import com.example.ui.utilities.DataHolder;
import com.example.ui.viewModel.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchLaterActivity extends AppCompatActivity implements WatchLaterListener {

    private ActivityWatchLaterBinding binding;
    private WatchListViewModel watchListViewModel;
    private List<TVShow> showList;
    private TVLaterAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watch_later);
        InitialData();
    }

    private void InitialData() {
        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        binding.back.setOnClickListener(v -> onBackPressed());
        showList = new ArrayList<>();
        getData();
    }

    private void getData (){
        binding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add((watchListViewModel.getTVShows().subscribeOn(Schedulers.computation()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( (tvShows) -> {
                    binding.setIsLoading(false);

                    if (showList.size() > 0)
                        showList.clear();

                    showList.addAll(tvShows);
                    adapter= new TVLaterAdapter(tvShows , this);
                    binding.recycleView.setAdapter(adapter);
                    binding.recycleView.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(DataHolder.CHECK_DATA_CHANGE)
                getData();
    }

    @Override
    public void onTVSHowClick(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext() , MovieDetails.class);
        intent.putExtra("tv_show" , tvShow);
        startActivity(intent);
    }

    @Override
    public void removeTVShowWatchLater(TVShow tvShow, int position) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add((watchListViewModel.removeTVShowWatchLater(tvShow)
                .subscribeOn(Schedulers.computation()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( () -> {
                        showList.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                        compositeDisposable.dispose();
                }));
    }
}