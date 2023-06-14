package com.example.listeners;

import com.example.model.TVShow;

public interface WatchLaterListener {

    void onTVSHowClick (TVShow tvShow);
    void removeTVShowWatchLater (TVShow tvShow , int position);
}
