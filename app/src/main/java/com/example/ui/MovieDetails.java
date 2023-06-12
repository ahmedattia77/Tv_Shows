package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tvshows.R;
import com.example.tvshows.databinding.ActivityMovieDetailsBinding;
import com.example.ui.adapters.ImageSliderAdapter;
import com.example.ui.viewModel.MostPopularTVShowDetailsViewModel;

import retrofit2.http.Url;

public class MovieDetails extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    private MostPopularTVShowDetailsViewModel detailsViewModel;


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
        String tvShowSentId = String.valueOf(getIntent().getIntExtra("id",-1));
        String tvShowSentName = getIntent().getStringExtra("name");
        String tvShowSentStartDate = String.valueOf(getIntent().getIntExtra("startDate",-1));
        String tvShowSentEndDate = String.valueOf(getIntent().getIntExtra("endDate",-1));
        String tvShowSentThumbnailPath = String.valueOf(getIntent().getIntExtra("thumbnailPath",-1));

        binding.setTvShowImage(tvShowSentThumbnailPath);

        detailsViewModel.getMostPopularTVShowsDetailsRepository(tvShowSentId).observe(this
        , tvShowDetailsResponse -> {
            if (tvShowDetailsResponse.getTvShowDetails() != null){
                if(tvShowDetailsResponse.getTvShowDetails().getPictures() != null){
                    binding.setIsLoading(false);
                    initialSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
                }
                binding.setTvShowImage(tvShowDetailsResponse.getTvShowDetails().getImagePath());
                binding.setTvShowName(tvShowSentName);
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
}