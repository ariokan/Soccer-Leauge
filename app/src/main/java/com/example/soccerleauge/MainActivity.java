package com.example.soccerleauge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements TeamAdapter.ItemClickListener {

    private  List<Team> teamList= new ArrayList<>();
    private List<Match> fixture;
    private TeamAdapter teamAdapter;
    private MatchAdapter matchAdapter;
    private MatchViewModel matchViewModel;
    private Switch themeSwitch;
    private Match match;
    private ViewPager viewPager_fixture;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView team_RecyclerView;
    private Api api;
    private  Button drawFixture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        checkTheme();
        getTeams();
        drawFixtures();
        checkDatabase();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getApplicationContext(), teamList.get(position).getName().toString(),Toast.LENGTH_SHORT).show();
    }

    public void init (){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         api =retrofit.create(Api.class);


        team_RecyclerView = findViewById(R.id.recyclerview_team);
        dividerItemDecoration = new DividerItemDecoration(team_RecyclerView.getContext(),
                1);
        themeSwitch = (Switch) findViewById(R.id.themeswitch);
        viewPager_fixture = findViewById(R.id.viewpager_fixture);
        drawFixture = findViewById(R.id.drawfixture);
    }
    public void checkTheme(){
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
    }
    public void getTeams(){
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
        team_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        team_RecyclerView.setHasFixedSize(true);
        team_RecyclerView.addItemDecoration(dividerItemDecoration);
    }
    public void drawFixtures(){
        drawFixture.setOnClickListener(new View.OnClickListener() {
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
                            matchViewModel.insert(match);
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
                        matchAdapter = new MatchAdapter(getApplicationContext(),mapWeek);
                        viewPager_fixture.setAdapter(matchAdapter);
                    }
                    @Override
                    public void onFailure(Call<List<Match>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
    public void checkDatabase(){
        matchViewModel = new ViewModelProvider(MainActivity.this, ViewModelProvider.AndroidViewModelFactory.getInstance(MainActivity.this.getApplication())).get(MatchViewModel.class);
        matchViewModel.getAllMatches().observe(MainActivity.this, new Observer<List<Match>>() {
            @Override
            public void onChanged(List<Match> matches) {
                Log.d("Dbstat","updated");
            }
        });

    }

}
