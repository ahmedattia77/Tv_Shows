package com.example.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Episode;
import com.example.tvshows.R;
import com.example.tvshows.databinding.BottonSheetContianerBinding;
import com.example.tvshows.databinding.EpisoesBottonSheetBinding;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeHolder>{

    private List<Episode> episodes;
    private LayoutInflater layoutInflater;

    public EpisodesAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        BottonSheetContianerBinding binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.botton_sheet_contianer , parent ,false
        );

        return new EpisodeHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeHolder holder, int position) {
        holder.bindEpisode(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    static class EpisodeHolder extends RecyclerView.ViewHolder {
        BottonSheetContianerBinding binding;

        public EpisodeHolder(BottonSheetContianerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindEpisode (Episode episode){

            String title = "S";
            String season = episode.getSeason();
            if (season.length() == 1){
                season = "0".concat(season);
            }

            String episodeNumber = episode.getEpisode();
            if (episodeNumber.length() == 1){
                episodeNumber = "0".concat(episodeNumber);
            }

            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(season).concat(episodeNumber);

            binding.setTitle(title);
            binding.setName(episode.getName());
            binding.setAirDate(episode.getAir_date());

        }
    }
}
