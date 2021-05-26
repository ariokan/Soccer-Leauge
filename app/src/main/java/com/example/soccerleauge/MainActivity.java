package com.example.soccerleauge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements TeamAdapter.ItemClickListener {

    public  List<Team> teamList= new ArrayList<>();
    public List<Match> fixture;
    public TeamAdapter teamAdapter;
    public MatchAdapter matchAdapter;
    private MatchViewModel matchViewModel;
    private Switch themeSwitch;
    public Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        themeSwitch = (Switch) findViewById(R.id.themeswitch);

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                themeSwitch.setChecked(true);
        }
            themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked){
                    setTheme(R.style.DarkTheme);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    setTheme(R.style.AppTheme);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            });


        matchViewModel = new ViewModelProvider(MainActivity.this, ViewModelProvider.AndroidViewModelFactory.getInstance(MainActivity.this.getApplication())).get(MatchViewModel.class);
        matchViewModel.getAllMatches().observe(MainActivity.this, new Observer<List<Match>>() {
            @Override
            public void onChanged(List<Match> matches) {
                /*ViewPager viewPager_fixture = findViewById(R.id.viewpager_fixture);
                final MatchAdapter adapter = new MatchAdapter(getApplicationContext(),fixture);
                viewPager_fixture.setAdapter(adapter);*/
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);

        RecyclerView team_RecyclerView = findViewById(R.id.recyclerview_team);
        team_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        team_RecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(team_RecyclerView.getContext(),
                1);
        team_RecyclerView.addItemDecoration(dividerItemDecoration);

        Button drawFixturebtn = findViewById(R.id.drawfixture);
        Map<Integer,Match> weekMatches;

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
                        List<Match> temp = new ArrayList<>();
                        for(Match M: fixture) {
                            match = new Match( M.getAway_name(),M.getHome_name(),"NULL","NULL",M.getWeek()+21);
                            temp.add(match);
                          //  matchViewModel.insert(match);
                        }

                        for(int i=0;i<temp.size();i++)
                        {
                            fixture.add(temp.get(i));
                        }

                        int week = 1;
                        List weekList = new ArrayList();
                        List<List<Match>> mapWeek= new ArrayList<>();
                        for(int i =0;i<fixture.size();i++) {

                          if(week == fixture.get(i).getWeek())
                          {
                              weekList.add(fixture.get(i));

                          }
                          else
                          {
                              mapWeek.add(weekList);
                              weekList = new ArrayList();
                              weekList.add(fixture.get(i));
                              week = fixture.get(i).getWeek();

                          }
                        }
                        mapWeek.add(weekList);
                        ViewPager viewPager_fixture = findViewById(R.id.viewpager_fixture);
                        MatchAdapter adapter = new MatchAdapter(getApplicationContext(),mapWeek);
                        viewPager_fixture.setAdapter(adapter);


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
                    ProgressDialog dialog = ProgressDialog.show(getApplicationContext(), "",
                            "Loading. Please wait...", true);
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


}
