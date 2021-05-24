package com.example.soccerleauge;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Match.class,version = 1)
public  abstract class FixtureDatabase extends RoomDatabase {

    public abstract MatchDao matchDao();

    private static volatile FixtureDatabase fixtureInstance;

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
            matchDao.insert(new Match("mock","mock","mock","mock","mock","mock",1));
            return null;
        }
    }
}
