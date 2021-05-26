package com.example.soccerleauge;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities ={Match.class},version = 1)
public  abstract class FixtureDatabase extends RoomDatabase {

    private static volatile FixtureDatabase fixtureInstance;
    public abstract MatchDao matchDao();

    static FixtureDatabase getInstance(final Context context){
        if(fixtureInstance==null){
            fixtureInstance= Room.databaseBuilder(context.getApplicationContext(),FixtureDatabase.class, "fixture_database").fallbackToDestructiveMigration().addCallback(roomCallback)
                    .build();
        }
        return fixtureInstance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new FillMockDataAsyncTask(fixtureInstance).execute();
        }
    };
    private static class FillMockDataAsyncTask extends AsyncTask<Void, Void,Void>{
        private MatchDao matchDao;

        private FillMockDataAsyncTask(FixtureDatabase db){
            matchDao=db.matchDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
