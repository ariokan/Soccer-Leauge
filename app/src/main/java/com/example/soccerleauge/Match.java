package com.example.soccerleauge;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "matches")
public class Match {
    @ColumnInfo(name = "home_name")
    @NonNull
    private String home_name;
    @ColumnInfo(name = "away_name")
    @NonNull
    private String away_name;
    @ColumnInfo(name = "score")
    @NonNull
    private String score;
    @ColumnInfo(name = "date")
    @NonNull
    private String date;
    @ColumnInfo(name = "location")
    @NonNull
    private String location;
    @ColumnInfo(name = "scheduled")
    @NonNull
    private String scheduled;
    @PrimaryKey
    @NonNull
    private int week;

    public Match(String home_name, String away_name, String score, String date, String location, String scheduled, int week) {
        this.home_name = home_name;
        this.away_name = away_name;
        this.score = score;
        this.date = date;
        this.location = location;
        this.scheduled = scheduled;
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
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    @NonNull
    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(@NonNull String scheduled) {
        this.scheduled = scheduled;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
