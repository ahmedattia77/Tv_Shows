package com.example.Doa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.model.TVShow;
import com.google.android.material.color.utilities.QuantizerWsmeans;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TVShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addWatchLater(TVShow tvShow);

    @Delete
    Completable deleteTVShow (TVShow tvShow);

    @Query("SELECT * FROM tv_show")
    Flowable<List<TVShow>> getWatchLater ();

    @Query("SELECT * FROM tv_show WHERE id = :tvId")
    Flowable<TVShow> getWatchLater (String tvId);

}
