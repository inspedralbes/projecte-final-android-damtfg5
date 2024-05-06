package com.example.projectofinal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/createTeam")
    Call<Void> createTeam(@Body TeamData teamData);

}
