package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubirResultado extends AppCompatActivity {
    private String URL = "http://volleyadmin.dam.inspedralbes.cat:3005/";
    int userId;
    ImageButton imageButtonBackResultado;
    private static Socket socket = SocketManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_resultado);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getInt("userId", -1);

        Intent intent = getIntent();
        int gameId = intent.getIntExtra("gameId", 0);
        int team1Id = intent.getIntExtra("team1Id", 0);
        int team2Id = intent.getIntExtra("team2Id", 0);

        EditText editTextPointsSpike = findViewById(R.id.editTextPointsSpike);
        EditText editTextErrorsSpike = findViewById(R.id.editTextErrorsSpike);
        EditText editTextAttemptsSpike = findViewById(R.id.editTextAttemptsSpike);
        EditText editTextPointsBlock = findViewById(R.id.editTextPointsBlock);
        EditText editTextErrorsBlock = findViewById(R.id.editTextErrorsBlock);
        EditText editTextAttemptsBlock = findViewById(R.id.editTextAttemptsBlock);
        EditText editTextPointsServe = findViewById(R.id.editTextPointsServe);
        EditText editTextErrorsServe = findViewById(R.id.editTextErrorsServe);
        EditText editTextAttemptsServe = findViewById(R.id.editTextAttemptsServe);
        EditText editTextSuccessfulSet = findViewById(R.id.editTextSuccessfulSet);
        EditText editTextErrorsSet = findViewById(R.id.editTextErrorsSet);
        EditText editTextAttemptsSet = findViewById(R.id.editTextAttemptsSet);
        EditText editTextSuccessfulReceive = findViewById(R.id.editTextSuccessfulsReceive);
        EditText editTextErrorsReceive = findViewById(R.id.editTextErrorsReceive);
        EditText editTextAttemptsReceive = findViewById(R.id.editTextAttemptsReceive);

        imageButtonBackResultado = findViewById(R.id.imageButtonBackResultado);

        Button buttonSubirStats = findViewById(R.id.buttonSubirStats);

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

                int pointsSpike = Integer.parseInt(editTextPointsSpike.getText().toString());
                int errorsSpike = Integer.parseInt(editTextErrorsSpike.getText().toString());
                int attemptsSpike = Integer.parseInt(editTextAttemptsSpike.getText().toString());
                int pointsBlock = Integer.parseInt(editTextPointsBlock.getText().toString());
                int errorsBlock = Integer.parseInt(editTextErrorsBlock.getText().toString());
                int attemptsBlock = Integer.parseInt(editTextAttemptsBlock.getText().toString());
                int pointsServe = Integer.parseInt(editTextPointsServe.getText().toString());
                int errorsServe = Integer.parseInt(editTextErrorsServe.getText().toString());
                int attemptsServe = Integer.parseInt(editTextAttemptsServe.getText().toString());
                int successfulSet = Integer.parseInt(editTextSuccessfulSet.getText().toString());
                int errorsSet = Integer.parseInt(editTextErrorsSet.getText().toString());
                int attemptsSet = Integer.parseInt(editTextAttemptsSet.getText().toString());
                int successfulReceive = Integer.parseInt(editTextSuccessfulReceive.getText().toString());
                int errorsReceive = Integer.parseInt(editTextErrorsReceive.getText().toString());
                int attemptsReceive = Integer.parseInt(editTextAttemptsReceive.getText().toString());

                GameUserStats gameUserStats = new GameUserStats(gameId, userId, pointsSpike, errorsSpike, attemptsSpike, pointsBlock,
                        errorsBlock, attemptsBlock, pointsServe, errorsServe, attemptsServe, successfulSet, errorsSet, attemptsSet,
                        successfulReceive, errorsReceive, attemptsReceive
                );

                Call<Void> call = apiService.insertGameUserStats(gameUserStats);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // La solicitud fue exitosa
                            Log.d("SubirResultado", "Estadísticas subidas exitosamente");
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int userIdTeam = sharedPreferences.getInt("userIdTeam", 0);
                            int userId = sharedPreferences.getInt("userId", 0);
                            String rol = sharedPreferences.getString("rol", "");
                            JSONObject matchData = new JSONObject();

                            if(!rol.equals("soloPlayer")){
                                try {
                                    matchData.put("idTeam1", team1Id);
                                    matchData.put("idTeam2", team2Id);
                                    matchData.put("id", gameId);
                                    matchData.put("status","FINALIZADA");
                                    matchData.put("userId", userId);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                socket.emit("joinMatch", matchData);
                            }else{
                                try {
                                    matchData.put("idTeam1", team1Id);
                                    matchData.put("idTeam2", team2Id);
                                    matchData.put("matchGameId", gameId);
                                    matchData.put("userId", userId);
                                    matchData.put("crearPartida", "FINALIZADA");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                socket.emit("joinMatchSolo", matchData);
                            }
                            Intent intent1 = new Intent(SubirResultado.this, Inici.class);
                            startActivity(intent1);
                        } else {
                            // La solicitud no fue exitosa
                            Log.e("SubirResultado", "Error al subir estadísticas: " + response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Error de red o error en la solicitud
                        Log.e("SubirResultado", "Error al subir estadísticas", t);
                    }
                });
            }
        });
    }
}