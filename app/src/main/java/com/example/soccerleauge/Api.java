package com.example.soccerleauge;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://api.mocki.io/v2/a70b75fb/";
    @GET("teams")
    Call<List<Team>> getTeams();
    @GET("fixture")
    Call<List<Match>> getMatches();
}
