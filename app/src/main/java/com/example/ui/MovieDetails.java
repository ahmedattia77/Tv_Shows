package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.Database.TVShowDatabase;
import com.example.Database.TVShowDatabase_Impl;
import com.example.model.Episode;
import com.example.model.TVShow;
import com.example.tvshows.R;
import com.example.tvshows.databinding.ActivityMovieDetailsBinding;
import com.example.tvshows.databinding.BottonSheetContianerBinding;
import com.example.tvshows.databinding.EpisoesBottonSheetBinding;
import com.example.ui.adapters.EpisodesAdapter;
import com.example.ui.adapters.ImageSliderAdapter;
import com.example.ui.viewModel.MostPopularTVShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Url;

public class MovieDetails extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    private MostPopularTVShowDetailsViewModel detailsViewModel;
    private EpisoesBottonSheetBinding bindingSheet;
    private EpisodesAdapter episodesAdapter;
    BottomSheetDialog bottomSheetDialog;
    TVShow tvShow ;

    TVShowDatabase tvShowDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_details);

        initialData();

    }

    private void initialData (){
        detailsViewModel = new ViewModelProvider(this).get(MostPopularTVShowDetailsViewModel.class);
        getDataMovieDetails();
        binding.backSpace.setOnClickListener( v -> onBackPressed());
    }

    private void getDataMovieDetails (){
        binding.setIsLoading(true);
        tvShow = (TVShow) getIntent().getSerializableExtra("tv_show");

        detailsViewModel.getMostPopularTVShowsDetailsRepository(String.valueOf(tvShow.getId())).observe(this
        , tvShowDetailsResponse -> {
            if (tvShowDetailsResponse.getTvShowDetails() != null){
                if(tvShowDetailsResponse.getTvShowDetails().getPictures() != null){
                    binding.setIsLoading(false);
                    initialSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
                }
                binding.setTvShowImage(tvShowDetailsResponse.getTvShowDetails().getImagePath());
                binding.thumbnailPath.setVisibility(View.VISIBLE);
                getMovieDetails(
                 tvShowDetailsResponse.getTvShowDetails().getDescription()
                ,tvShowDetailsResponse.getTvShowDetails().getRating()
                ,tvShowDetailsResponse.getTvShowDetails().getGenres()[0]
                ,tvShowDetailsResponse.getTvShowDetails().getRuntime()
                ,tvShowDetailsResponse.getTvShowDetails().getUrl()
                ,tvShowDetailsResponse.getTvShowDetails().getEpisodes()
                );

                binding.watchLater.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new CompositeDisposable().add(detailsViewModel.insertTVShow(tvShow)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    binding.watchLater.setImageResource(R.drawable.baseline_check_);
                                    Toast.makeText(MovieDetails.this, "added To Watch later list", Toast.LENGTH_SHORT).show();
                                })
                        );
                    }
                });

            }
        });
    }

    private void initialSlider(String[] images){
        binding.sliderPages.setOffscreenPageLimit(1);
        binding.sliderPages.setAdapter(new ImageSliderAdapter(images));
        binding.sliderPages.setVisibility(ViewPager.VISIBLE);
        initialSliderIndicator(images.length);
        binding.sliderPages.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                highLight_position_slider(position);
            }
        });
    }

    private void initialSliderIndicator(int count){
        ImageView[] views = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.leftMargin = 8;
        layoutParams.rightMargin = 8;

        for (int index = 0 ; index < views.length ; index++){
            views[index] = new ImageView(getApplicationContext());
            views[index].setImageDrawable(ContextCompat.getDrawable(getApplicationContext()
                    ,R.drawable.backgroung_slider_indicator));
            views[index].setLayoutParams(layoutParams);
            binding.layoutIndicator.addView(views[index]);
        }
        binding.layoutIndicator.setVisibility(View.VISIBLE);
        highLight_position_slider(0);
    }

    private void highLight_position_slider(int position){
        int count = binding.layoutIndicator.getChildCount();

        for (int index = 0; index < count ; index++){
            ImageView imageView = (ImageView) binding.layoutIndicator.getChildAt(index);
            if (index == position){
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),R.drawable.backgroung_slider_indicator_active));
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),R.drawable.backgroung_slider_indicator));
            }
        }
    }

    private void getMovieDetails(String description , String rate , String genre , int runtime
     , String URI , List<Episode> episodes){

          tvShow = (TVShow) getIntent().getSerializableExtra("tv_show");

        binding.setTvShowName(tvShow.getName());
        binding.setStatus(tvShow.getStatus());
        binding.setStartDate(tvShow.getStart_date());
        binding.setNetworkCountry(tvShow.getNetwork() +"(" + tvShow.getCountry() +")");
        binding.setDescription(String.valueOf(
                HtmlCompat.fromHtml(description ,HtmlCompat.FROM_HTML_MODE_LEGACY)
        ));

        binding.readMore.setOnClickListener(v -> {
            if (binding.readMore.getText().toString().equals("Read More")){
                binding.description.setMaxLines(Integer.MAX_VALUE);
                binding.description.setEllipsize(null);
                binding.readMore.setText(R.string.read_less);
            }
            else{
                binding.readMore.setText(R.string.read_more);
                binding.description.setMaxLines(4);
                binding.description.setEllipsize(TextUtils.TruncateAt.END);
            }
        });

        binding.setRate(String.format(
                Locale.getDefault(),
                "%.1f",
                Double.parseDouble(rate)
        ));

        if(genre != null)
            binding.setGenre(genre);
        else
            binding.genre.setText("N/A");

        binding.setRuntime(runtime + "Min");


        binding.websiteButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(URI));
            startActivity(intent);
        });

        binding.episodesButton.setOnClickListener(v -> {
            if (bottomSheetDialog == null){
                bottomSheetDialog = new BottomSheetDialog(MovieDetails.this);
                bindingSheet = DataBindingUtil.inflate(
                        LayoutInflater.from(MovieDetails.this),
                        R.layout.episoes_botton_sheet,
                        findViewById(R.id.contianer_sheet),
                        false
                );
                bottomSheetDialog.setContentView(bindingSheet.getRoot());
                bindingSheet.recycle.setAdapter(new EpisodesAdapter(episodes));
                bindingSheet.title.setText(String.format("Episodes | %s" , tvShow.getName()));

                bindingSheet.cancel.setOnClickListener(v1 -> bottomSheetDialog.dismiss());
            }
            bottomSheetDialog.show();
        });

        binding.name.setVisibility(View.VISIBLE);
        binding.status.setVisibility(View.VISIBLE);
        binding.startDate.setVisibility(View.VISIBLE);
        binding.country.setVisibility(View.VISIBLE);
        binding.description.setVisibility(View.VISIBLE);
        binding.readMore.setVisibility(View.VISIBLE);
        binding.layMisc.setVisibility(View.VISIBLE);
        binding.divi.setVisibility(View.VISIBLE);
        binding.divi2.setVisibility(View.VISIBLE);
        binding.websiteButton.setVisibility(View.VISIBLE);
        binding.episodesButton.setVisibility(View.VISIBLE);
        binding.sliderPages.setVisibility(View.VISIBLE);
        binding.watchLater.setVisibility(View.VISIBLE);

    }
}