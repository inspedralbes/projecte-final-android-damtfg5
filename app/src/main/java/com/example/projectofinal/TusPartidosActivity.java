package com.example.projectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class TusPartidosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MatchAdapter adapter;
    private List<Match> matchList;
    private int userId;
    private Socket socket = SocketManager.getInstance();
    ImageButton imageButtonBackPartidos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tus_partidos); // Cambia esto al layout correspondiente

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        matchList = new ArrayList<>();
        adapter = new MatchAdapter(getApplicationContext(), matchList);
        recyclerView.setAdapter(adapter);

        imageButtonBackPartidos = findViewById(R.id.imageButtonBackPartidos);
        Button buttonDisponibles = findViewById(R.id.buttonDisponibles);
        Button buttonTusPartidos = findViewById(R.id.buttonTusPartidos);
        imageButtonBackPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TusPartidosActivity.this, Inici.class);
                startActivity(intent);
            }
        });

        buttonDisponibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TusPartidosActivity.this, DisponiblesActivity.class);
                startActivity(intent);
            }
        });

        buttonTusPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getInt("userId", -1);


        int userIdTeam = sharedPreferences.getInt("userIdTeam", 0);
        int userId = sharedPreferences.getInt("userId", 0);
        String rol = sharedPreferences.getString("rol", "");
        socket.emit("getMyMatches", userId);
        if(!rol.equals("soloPlayer")){
            socket.on("myMatches", onMyMatches);
        }else{
            socket.on("myMatches", onMySoloMatches);
        }
    }

    private Emitter.Listener onMyMatches = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    matchList.clear();
                    for (Object arg : args) {
                        try {
                            JSONArray jsonArray = (JSONArray) arg;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                int matchId = data.getInt("match_id");
                                String matchDate = data.getString("matchDate");
                                String matchTime = data.getString("matchTime");
                                String matchLocation = data.getString("matchLocation");
                                String status = data.getString("status");
                                Team team1 = new Team(
                                        data.getString("team1_id"),
                                        data.getString("team1_name"),
                                        data.getInt("team1_nPlayers"),
                                        data.getString("team1_logoPic"),
                                        data.getString("team1_shortName"),
                                        data.getInt("team1_totalGames"),
                                        data.getInt("team1_wonGames"),
                                        data.getInt("team1_lostGames"),
                                        data.getInt("team1_totalPoints"),
                                        data.getString("team1_idGame")
                                );
                                Team team2 = new Team(
                                        data.getString("team2_id"),
                                        data.getString("team2_name"),
                                        data.getInt("team2_nPlayers"),
                                        data.getString("team2_logoPic"),
                                        data.getString("team2_shortName"),
                                        data.getInt("team2_totalGames"),
                                        data.getInt("team2_wonGames"),
                                        data.getInt("team2_lostGames"),
                                        data.getInt("team2_totalPoints"),
                                        data.getString("team2_idGame")
                                );

                                Match match = new Match(matchId, matchDate, matchTime, matchLocation, status, team1, team2);
                                matchList.add(match);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };

    private Emitter.Listener onMySoloMatches = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    matchList.clear();
                    for (Object arg : args) {
                        try {
                            JSONArray jsonArray = (JSONArray) arg;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                int matchId = data.getInt("match_id");
                                String matchDate = data.getString("matchDate");
                                String matchTime = data.getString("matchTime");
                                String matchLocation = data.getString("matchLocation");
                                String status = data.getString("status");
                                Team team1 = new Team(
                                        data.getString("team1_id"),
                                        data.getString("team1_name"),
                                        data.getInt("team1_nPlayers"),
                                        data.getString("team1_logoPic"),
                                        data.getString("team1_shortName"),
                                        data.getInt("team1_totalGames"),
                                        data.getInt("team1_wonGames"),
                                        data.getInt("team1_lostGames"),
                                        data.getInt("team1_totalPoints"),
                                        data.getString("team1_idGame")
                                );
                                // Obtén el array de jugadores y asignalo al equipo
                                JSONArray playersArray1 = data.getJSONArray("playersTeam1");
                                team1.setPlayersFromJsonArray(playersArray1);

                                Team team2 = new Team(
                                        data.getString("team2_id"),
                                        data.getString("team2_name"),
                                        data.getInt("team2_nPlayers"),
                                        data.getString("team2_logoPic"),
                                        data.getString("team2_shortName"),
                                        data.getInt("team2_totalGames"),
                                        data.getInt("team2_wonGames"),
                                        data.getInt("team2_lostGames"),
                                        data.getInt("team2_totalPoints"),
                                        data.getString("team2_idGame")
                                );
                                // Verificar si playersTeam2 existe en los datos
                                if (data.has("playersTeam2")) {
                                    // Obtén el array de jugadores del equipo 2 y asígnalo al equipo
                                    JSONArray playersArray2 = data.getJSONArray("playersTeam2");
                                    team2.setPlayersFromJsonArray(playersArray2);
                                }

                                Match match = new Match(matchId, matchDate, matchTime, matchLocation, status, team1, team2);
                                matchList.add(match);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socket != null) {
            socket.off("myMatches", onMyMatches);
        }
    }
}
