package com.example.projectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class FragmentDisponibles extends Fragment {

    private RecyclerView recyclerView;
    private MatchAdapter adapter;
    private List<Match> matchList;
    private Socket socket = SocketManager.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disponibles, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        matchList = new ArrayList<>();
        adapter = new MatchAdapter(matchList);
        recyclerView.setAdapter(adapter);


        socket.emit("getMatches","");
        socket.connect();
        socket.on("matches", onMatches);
        return view;
    }

    private Emitter.Listener onMatches = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
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




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (socket != null) {
            socket.disconnect();
            socket.off("matches", onMatches);
        }
    }
}
