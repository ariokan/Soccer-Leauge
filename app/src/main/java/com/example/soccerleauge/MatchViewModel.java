package com.example.soccerleauge;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.app.Application;

import java.util.List;

public class MatchViewModel extends AndroidViewModel {
    private  MatchRepository repository;
    private LiveData<List<Match>> allMatches;
    public MatchViewModel(@NonNull Application application) {
        super(application);
        repository = new MatchRepository(application);
        allMatches = repository.getAllMatches();
    }
    public void insert (Match match){
        repository.insert(match);
    }
    public LiveData<List<Match>> getAllMatches(){
        return allMatches;
    }
}
