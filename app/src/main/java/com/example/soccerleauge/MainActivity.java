package com.example.soccerleauge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements TeamAdapter.ItemClickListener {

    private RecyclerView team_RecyclerView;
    public RecyclerView fixture_RecyclerView;
    public  List<Team> teamList= new ArrayList<>();
    public List<Match> fixture;
    public TeamAdapter teamAdapter;
    public MatchAdapter matchAdapter;
    private MatchViewModel matchViewModel;
    private Switch themeSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        themeSwitch = (Switch) findViewById(R.id.themeswitch);
        setTheme();
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                themeSwitch.setChecked(true);
        }
            themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        reset();
                    }else{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        reset();
                    }
                }
            });



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);


        team_RecyclerView = findViewById(R.id.recycerlview_team);
        team_RecyclerView.setHasFixedSize(true);
        team_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(team_RecyclerView.getContext(),
                1);
        team_RecyclerView.addItemDecoration(dividerItemDecoration);



        Button drawFixturebtn = findViewById(R.id.drawfixture);
        drawFixturebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Match>> callMatch = api.getMatches();
                assert fixture != null;
                callMatch.enqueue(new Callback<List<Match>>() {
                    @Override
                    public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                        if(!response.isSuccessful()) {
                            return;
                        }
                        fixture = response.body();
                        for(Match M: fixture) {
                            Match match = new Match(M.getAway_name(),M.getHome_name(),"NULL","NULL","NULL","NULL",M.getWeek()+21);
                            matchViewModel.insert(M );
                            matchViewModel.insert(match);
                            Log.d("1.half",String.valueOf(M.getWeek()));
                            Log.d("2.half",String.valueOf(match.getWeek()));
                            Log.d("2. half",String.valueOf(match.getHome_name()));
                            Log.d("1 half",String.valueOf(M.getAway_name()));

                        }
                        matchAdapter = new MatchAdapter();
                        fixture_RecyclerView.setAdapter(matchAdapter);
                        matchViewModel = new ViewModelProvider(MainActivity.this, ViewModelProvider.AndroidViewModelFactory.getInstance(MainActivity.this.getApplication())).get(MatchViewModel.class);

                        matchViewModel.getAllMatches().observe(MainActivity.this, new Observer<List<Match>>() {
                            @Override
                            public void onChanged(List<Match> matches) {
                               int week=FixtureDatabase.getInstance(getApplicationContext()).matchDao().week(1);
                                Log.d("week",String.valueOf(week));
                                //matchAdapter.setMatches(matches);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Match>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //team name get request
        Call<List<Team>> callteam = api.getTeams();
        callteam.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                 teamList =response.body();
                teamAdapter = new TeamAdapter(getApplicationContext(),teamList, MainActivity.this::onItemClick);
                team_RecyclerView.setAdapter(teamAdapter);
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void reset() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getApplicationContext(), teamList.get(position).getName().toString(),Toast.LENGTH_SHORT).show();
    }
    public void setTheme(){
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);

        }
        else{
            setTheme(R.style.AppTheme);
        }
    }

}
