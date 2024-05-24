package com.example.projectofinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TWO_TEAMS = 0;
    private static final int TYPE_FIVE_VS_FIVE = 1;
    private List<Match> matchList;
    private static Socket socket = SocketManager.getInstance();
    static Context context;

    public MatchAdapter(List<Match> matchList) {
        this.matchList = matchList;
    }

    @Override
    public int getItemViewType(int position) {
        Match match = matchList.get(position);
        if (!match.getTeam1().getId().equals("No disponible") || !match.getTeam2().getId().equals("No disponible")) {
            return TYPE_TWO_TEAMS;
        } else {
            return TYPE_FIVE_VS_FIVE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        if (viewType == TYPE_TWO_TEAMS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partido_teams, parent, false);
            return new TwoTeamsViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partido_jugadores, parent, false);
            return new FiveVsFiveViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Match match = matchList.get(position);
        if (holder.getItemViewType() == TYPE_TWO_TEAMS) {
            ((TwoTeamsViewHolder) holder).bind(match);
        } else {
            ((FiveVsFiveViewHolder) holder).bind(match);
        }
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    static class TwoTeamsViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDateMatch;
        private TextView textViewTimeMatch;
        private TextView textViewLocalitationMatch;
        private ImageView imageViewTeam1;
        private ImageView imageViewTeam2;
        private TextView texViewNomTeam1;
        private TextView texViewNomTeam2;
        private Button buttonEntrarMatch;

        public TwoTeamsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDateMatch = itemView.findViewById(R.id.textViewDateMatch);
            textViewTimeMatch = itemView.findViewById(R.id.textViewTimeMatch);
            textViewLocalitationMatch = itemView.findViewById(R.id.textViewLocalitationMatch);
            imageViewTeam1 = itemView.findViewById(R.id.imageViewTeam1);
            imageViewTeam2 = itemView.findViewById(R.id.imageViewTeam2);
            texViewNomTeam1 = itemView.findViewById(R.id.texViewNomTeam1);
            texViewNomTeam2 = itemView.findViewById(R.id.texViewNomTeam2);
            buttonEntrarMatch = itemView.findViewById(R.id.buttonEntrarMatch);

        }

        public void bind(Match match) {
            Log.d("TAG", "onClick:  dandoclick");
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            int userIdTeam = sharedPreferences.getInt("userIdTeam", 0);
            int userId = sharedPreferences.getInt("userId", 0);
            Log.d("userid", "onClick: " + userIdTeam);
            boolean isNotIn = false;
            if(userIdTeam == Integer.parseInt(match.getTeam1().getId())  && match.getTeam2().getId().equals("No disponible")) {
                isNotIn = true;
            }

            textViewDateMatch.setText(match.getMatchDate());
            textViewTimeMatch.setText(match.getMatchTime());
            textViewLocalitationMatch.setText(match.getMatchLocation());
            if(match.getTeam1().getLogoPic()!= null && !match.getTeam1().getLogoPic().isEmpty()){
                Picasso.get().load(match.getTeam1().getLogoPic()).into(imageViewTeam1);
            }
            else{
                imageViewTeam1.setImageResource(R.drawable.add_black);
            }

            if(match.getTeam2().getLogoPic()!= null && !match.getTeam2().getLogoPic().isEmpty()){
                Picasso.get().load(match.getTeam2().getLogoPic()).into(imageViewTeam2);
            }
            else{
                imageViewTeam2.setImageResource(R.drawable.add_black);
            }

            if (!match.getTeam1().getId().equals("No disponible")){
                texViewNomTeam1.setText(match.getTeam1().getTeamName());
            } else{
                texViewNomTeam1.setText("Libre");
            }
            if (!match.getTeam2().getId().equals("No disponible")){
                texViewNomTeam2.setText(match.getTeam2().getTeamName());
            } else{
                texViewNomTeam2.setText("Libre");
            }
            if(match.getStatus().equals("ABIERTA")){
                boolean finalIsNotIn = isNotIn;
                if(!isNotIn){
                    buttonEntrarMatch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!finalIsNotIn){
                                Log.d("IDTEAMMMMMMMMMMMM NO DENTRO", "onClick:ENTRANDO MI PANA");

                                JSONObject matchData = new JSONObject();
                                try {
                                    matchData.put("idTeam1", Integer.parseInt(match.getTeam1().getId()));
                                    matchData.put("idTeam2", userIdTeam);
                                    matchData.put("id", match.getMatchId());
                                    matchData.put("status","CERRADA");
                                    matchData.put("userId", userId);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                socket.emit("joinMatch", matchData);
                            }else{
                                Log.d("IDTEAMMMMMMMMMMMM DENTRO", "onClick: YA ESTA DENTRO MI PANA");
                            }
                        }
                    });
                }else{
                    buttonEntrarMatch.setText("Esperando");
                    buttonEntrarMatch.setBackgroundColor(ContextCompat.getColor(context, R.color.negro1));
                    buttonEntrarMatch.setTextColor(ContextCompat.getColor(context, R.color.white));

                }

            }
        }
    }
    static class FiveVsFiveViewHolder extends RecyclerView.ViewHolder {

        public FiveVsFiveViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Match match) {
            // Aquí puedes implementar la lógica para el diseño FiveVsFive
        }
    }
}
