package com.example.soccerleauge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements TeamAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    public  List<Team> teamList;
    public TeamAdapter teamAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycerlview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api =retrofit.create(Api.class);


        Call<List<Team>> call = api.getTeams();

        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                 teamList =response.body();
                Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        teamAdapter = new TeamAdapter(getApplicationContext(),teamList, this);
        recyclerView.setAdapter(teamAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getApplicationContext(), teamList.get(position).getName().toString(),Toast.LENGTH_SHORT).show();
    }
}
