package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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

        ApiService apiService = retrofit.create(ApiService.class);
        UserIdRequest userIdRequest = new UserIdRequest(userId);
        Call<List<TeamData>> call = apiService.getTeam(userIdRequest);
        call.enqueue(new Callback<List<TeamData>>() {
            @Override
            public void onResponse(Call<List<TeamData>> call, Response<List<TeamData>> response) {
                if (response.isSuccessful()) {
                    List<TeamData> teams = response.body();
                    if (!teams.isEmpty()) {
                        TeamData team = teams.get(0);
                        EditText editTextNE = findViewById(R.id.editTextNE);
                        EditText editTextAbreviacion = findViewById(R.id.editTextAbreviacion);
                        ImageView imageViewUsuario = findViewById(R.id.imageViewUsuario);

                        editTextNE.setText(team.getTeamName());
                        editTextAbreviacion.setText(team.getShortName());
                        String imageUrl = team.getLogoPic();
                        Picasso.get().load(imageUrl).into(imageViewUsuario);
                    } else {

                    }
                } else {
                    Log.e("TeamResponse", "Error en la respuesta del servidor: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<TeamData>> call, Throwable t) {
                Log.e("TeamRequest", "Error en la solicitud HTTP: " + t.getMessage());
            }
        });

        ImageButton imageButtonBEE = findViewById(R.id.imageButtonBEE);
        imageButtonBEE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}