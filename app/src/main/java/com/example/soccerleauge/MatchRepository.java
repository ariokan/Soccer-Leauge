package com.example.soccerleauge;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MatchRepository {
    private  MatchDao matchDao;
    private LiveData<List<Match>> allMatches;

    public MatchRepository(Application application){
        FixtureDatabase database =FixtureDatabase.getInstance(application);
        matchDao = database.matchDao();
        allMatches= matchDao.getAllMatches();
    }
    public void insert(Match match){
        new InsertMatchAsyncTask(matchDao).execute(match);
}
public LiveData<List<Match>> getAllMatches(){
        return allMatches;
}
private static class InsertMatchAsyncTask extends AsyncTask<Match, Void,Void>{
        private MatchDao matchDao;

        private  InsertMatchAsyncTask(MatchDao matchDao){
            this.matchDao=matchDao;
        }
    @Override
    protected Void doInBackground(Match... matches) {
        matchDao.insert(matches[0]);
            return null;
    }
}
}
