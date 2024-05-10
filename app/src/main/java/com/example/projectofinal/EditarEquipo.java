package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarEquipo extends AppCompatActivity {
    private String URL = "http://192.168.206.176:3001/";
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipo);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getInt("userId", -1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear instancia de ApiService
        ApiService apiService = retrofit.create(ApiService.class);

        // Hacer la solicitud HTTP
        UserIdRequest userIdRequest = new UserIdRequest(userId);
        Call<TeamResponse> call = apiService.getTeam(userIdRequest);
        call.enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(Call<TeamResponse> call, Response<TeamResponse> response) {
                if (response.isSuccessful()) {
                    TeamResponse teamResponse = response.body();
                    Team team = teamResponse.getData();
                } else {
                    Log.e("TeamResponse", "Error en la respuesta del servidor: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TeamResponse> call, Throwable t) {
                Log.e("TeamRequest", "Error en la solicitud HTTP: " + t.getMessage());
            }
        });
    }
}