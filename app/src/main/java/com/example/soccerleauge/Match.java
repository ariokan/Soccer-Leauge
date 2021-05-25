package com.example.soccerleauge;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "matches")
public class Match {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private  int id;
    @ColumnInfo(name = "home_name")
    @NonNull
    private String home_name;
    @ColumnInfo(name = "away_name")
    @NonNull
    private String away_name;
    @ColumnInfo(name = "score")
    @NonNull
    private String score;
    @ColumnInfo(name = "location")
    @NonNull
    private String location;

    @NonNull
    private int week;

    public Match( String home_name, String away_name, String location,String score, int week) {

        this.home_name = home_name;
        this.away_name = away_name;
        this.score = score;
        this.location = location;
        this.week = week;
    }

    @NonNull
    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(@NonNull String home_name) {
        this.home_name = home_name;
    }

    @NonNull
    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(@NonNull String away_name) {
        this.away_name = away_name;
    }

    @NonNull
    public String getScore() {
        return score;
    }

    public void setScore(@NonNull String score) {
        this.score = score;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
