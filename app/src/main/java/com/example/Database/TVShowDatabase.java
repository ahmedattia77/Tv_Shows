package com.example.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.Doa.TVShowDao;
import com.example.model.TVShow;

@Database(entities = TVShow.class , version = 1 , exportSchema = false)
public abstract class TVShowDatabase extends RoomDatabase {

    private static TVShowDatabase tvShowDatabase;

    public static synchronized TVShowDatabase tvShowDatabase (Context context){
        if (tvShowDatabase == null){

            tvShowDatabase = Room.databaseBuilder(
                    context,
                    TVShowDatabase.class,
                    "tv_database"
            ).build();

        }
        return tvShowDatabase;
    }

    public abstract TVShowDao tvShowDao ();

}
