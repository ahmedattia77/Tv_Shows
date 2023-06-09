package com.example.ui.adapters;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listeners.TVShowListener;
import com.example.model.TVShow;
import com.example.tvshows.R;
import com.example.tvshows.databinding.ItemContianerBinding;

import java.util.List;

public class TVAdapter  extends RecyclerView.Adapter<TVAdapter.TVShowAdapter>  {

    private List<TVShow> showList;
    private LayoutInflater layoutInflater;
    private TVShowListener tvShowListener;

    public TVAdapter(List<TVShow> showList , TVShowListener tvShowListener) {
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
        binding.getRoot().setOnClickListener(v -> tvShowListener.onMovieClicked(tvShow));
        }
    }
}
