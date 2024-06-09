package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadoPartida extends AppCompatActivity {
    private String URL = "http://192.168.1.17:3005/";
    private TextView editTextTeam1Set1, editTextTeam2Set1, editTextTeam1Set2, editTextTeam2Set2, editTextTeam1Set3, editTextTeam2Set3;
    private EditText editTextPointsTeam1Set1,editTextPointsTeam2Set1,editTextPointsTeam1Set2,editTextPointsTeam2Set2, editTextPointsTeam1Set3, editTextPointsTeam2Set3;
    private ImageView imageViewTeam1set1,imageViewTeam2set1, imageViewTeam1set2,imageViewTeam2set2, imageViewTeam1set3,imageViewTeam2set3;
    ImageButton imageButtonBackResultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int team1Id = intent.getIntExtra("team1Id", 0);
        int team2Id = intent.getIntExtra("team2Id", 0);
        String team1LogoUrl = intent.getStringExtra("team1Logo");
        String team2LogoUrl = intent.getStringExtra("team2Logo");
        String team1Name = intent.getStringExtra("team1Name");
        String team2Name = intent.getStringExtra("team2Name");
        int userId = intent.getIntExtra("userId", 0);
        int gameId = intent.getIntExtra("gameId", 0);

        setContentView(R.layout.activity_resultado_partida);
        Button buttonSubirStats = findViewById(R.id.buttonSubirStats);
        editTextTeam1Set1 = findViewById(R.id.editTextTeam1Set1);
        editTextTeam1Set2 = findViewById(R.id.editTextTeam1Set2);
        editTextTeam1Set3 = findViewById(R.id.editTextTeam1Set3);
        editTextTeam2Set1 = findViewById(R.id.editTextTeam2Set1);
        editTextTeam2Set2 = findViewById(R.id.editTextTeam2Set2);
        editTextTeam2Set3 = findViewById(R.id.editTextTeam2Set3);

        editTextPointsTeam1Set1 = findViewById(R.id.editTextPointsTeam1Set1);
        editTextPointsTeam2Set1 = findViewById(R.id.editTextPointsTeam2Set1);
        editTextPointsTeam1Set2 = findViewById(R.id.editTextPointsTeam1Set2);
        editTextPointsTeam2Set2 = findViewById(R.id.editTextPointsTeam2Set2);
        editTextPointsTeam1Set3 = findViewById(R.id.editTextPointsTeam1Set3);
        editTextPointsTeam2Set3 = findViewById(R.id.editTextPointsTeam2Set3);

        imageViewTeam1set1 = findViewById(R.id.imageViewTeam1set1);
        imageViewTeam2set1 = findViewById(R.id.imageViewTeam2Set1);
        imageViewTeam1set2 = findViewById(R.id.imageViewTeam1set2);
        imageViewTeam2set2 = findViewById(R.id.imageViewTeam2Set2);
        imageViewTeam1set3 = findViewById(R.id.imageViewTeam1set3);
        imageViewTeam2set3 = findViewById(R.id.imageViewTeam2Set3);

        Picasso.get().load(team1LogoUrl).into(imageViewTeam1set1);
        Picasso.get().load(team2LogoUrl).into(imageViewTeam2set1);
        Picasso.get().load(team1LogoUrl).into(imageViewTeam1set2);
        Picasso.get().load(team2LogoUrl).into(imageViewTeam2set2);
        Picasso.get().load(team1LogoUrl).into(imageViewTeam1set3);
        Picasso.get().load(team2LogoUrl).into(imageViewTeam2set3);

        editTextTeam1Set1.setText(team1Name);
        editTextTeam2Set1.setText(team2Name);
        editTextTeam1Set2.setText(team1Name);
        editTextTeam2Set2.setText(team2Name);
        editTextTeam1Set3.setText(team1Name);
        editTextTeam2Set3.setText(team2Name);


        imageButtonBackResultado = findViewById(R.id.imageButtonBackResultado);

        imageButtonBackResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonSubirStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService = retrofit.create(ApiService.class);
                int idSQL = gameId;
                int scoreTeam1Set1 = Integer.parseInt(editTextPointsTeam1Set1.getText().toString());
                int scoreTeam2Set1 = Integer.parseInt(editTextPointsTeam2Set1.getText().toString());
                int scoreTeam1Set2 = Integer.parseInt(editTextPointsTeam1Set2.getText().toString());
                int scoreTeam2Set2 = Integer.parseInt(editTextPointsTeam2Set2.getText().toString());
                int scoreTeam1Set3 = Integer.parseInt(editTextPointsTeam1Set3.getText().toString());
                int scoreTeam2Set3 = Integer.parseInt(editTextPointsTeam2Set3.getText().toString());
                List<SetData> sets = new ArrayList<>();
                Map<String, Integer> set1Team1 = new HashMap<>();
                set1Team1.put("idequipo1", team1Id);
                set1Team1.put("score", scoreTeam1Set1);
                Map<String, Integer> set1Team2 = new HashMap<>();
                set1Team2.put("idequipo2", team2Id);
                set1Team2.put("score", scoreTeam2Set1);
                List<Map<String, Integer>> set1 = new ArrayList<>();
                set1.add(set1Team1);
                set1.add(set1Team2);
                SetData set1Object = new SetData(set1);

                Map<String, Integer> set2Team1 = new HashMap<>();
                set2Team1.put("idequipo1", team1Id);
                set2Team1.put("score", scoreTeam1Set2);
                Map<String, Integer> set2Team2 = new HashMap<>();
                set2Team2.put("idequipo2", team2Id);
                set2Team2.put("score", scoreTeam2Set2);
                List<Map<String, Integer>> set2 = new ArrayList<>();
                set2.add(set2Team1);
                set2.add(set2Team2);
                SetData set2Object = new SetData(set2);

                Map<String, Integer> set3Team1 = new HashMap<>();
                set3Team1.put("idequipo1", team1Id);
                set3Team1.put("score", scoreTeam1Set3);
                Map<String, Integer> set3Team2 = new HashMap<>();
                set3Team2.put("idequipo2", team2Id);
                set3Team2.put("score", scoreTeam2Set3);
                List<Map<String, Integer>> set3 = new ArrayList<>();
                set3.add(set3Team1);
                set3.add(set3Team2);
                SetData set3Object = new SetData(set3);

                sets.add(set1Object);
                sets.add(set2Object);
                sets.add(set3Object);

                GameData gameData = new GameData(idSQL, sets);
                Call<Void> call = apiService.addGame(gameData);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ResultadoPartida.this, "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ResultadoPartida.this, SubirResultado.class);
                            intent.putExtra("team1Id", team1Id);
                            intent.putExtra("team2Id", team2Id);
                            intent.putExtra("userId", userId);
                            intent.putExtra("gameId", gameId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ResultadoPartida.this, "Error al enviar datos", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ResultadoPartida.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}