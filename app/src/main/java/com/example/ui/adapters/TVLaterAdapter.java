package com.example.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listeners.TVShowListener;
import com.example.listeners.WatchLaterListener;
import com.example.model.TVShow;
import com.example.tvshows.R;
import com.example.tvshows.databinding.ItemContianerBinding;
import com.example.ui.WatchLaterActivity;

import java.util.List;

public class TVLaterAdapter extends RecyclerView.Adapter<TVLaterAdapter.TVShowAdapter> {

    private List<TVShow> showList;
    private LayoutInflater layoutInflater;
    private WatchLaterListener tvShowListener;

    public TVLaterAdapter(List<TVShow> showList , WatchLaterListener tvShowListener) {
        this.showList = showList;
        this.tvShowListener = tvShowListener;
    }

    @NonNull
    @Override
    public TVShowAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemContianerBinding binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_contianer,parent,false
        );

        return new TVShowAdapter(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowAdapter holder, int position) {
        holder.bindTVShow(showList.get(position));
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

     class TVShowAdapter extends RecyclerView.ViewHolder {
        private ItemContianerBinding binding;

        public TVShowAdapter(ItemContianerBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTVShow (TVShow tvShow){
        binding.setTv(tvShow);
        binding.executePendingBindings();
        binding.getRoot().setOnClickListener(v -> tvShowListener.onTVSHowClick(tvShow));
        binding.delete.setOnClickListener(v -> tvShowListener.removeTVShowWatchLater(tvShow ,getAdapterPosition()));
        binding.delete.setVisibility(View.VISIBLE);
        }

    }
}
