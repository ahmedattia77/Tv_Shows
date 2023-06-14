package com.example.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshows.R;
import com.example.tvshows.databinding.ContianerSliderImageBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {
    private String []images;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter( String[] images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ContianerSliderImageBinding binding = DataBindingUtil.inflate(
                layoutInflater , R.layout.contianer_slider_image , parent , false
        );
        return new ImageSliderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.binding.setImageURL(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    static class ImageSliderViewHolder extends RecyclerView.ViewHolder{
        ContianerSliderImageBinding binding;
        public ImageSliderViewHolder(ContianerSliderImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void sliderImageView (String imageURL){
            binding.setImageURL(imageURL);
        }

    }
}
