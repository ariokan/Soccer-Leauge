package com.example.soccerleauge;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MatchDao {
   @Insert
    void insert(Match match);

    @Query("SELECT * FROM matches ORDER BY week DESC")
    LiveData<List<Match>> getAllMatches();

}
