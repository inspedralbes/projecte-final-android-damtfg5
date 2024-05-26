package com.example.projectofinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
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

import com.example.projectofinal.databinding.ActivitySubirResultadoBinding;
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

    // Constructor que recibe el contexto
    public MatchAdapter(Context context, List<Match> matchList) {
        this.context = context;
        this.matchList = matchList;
    }

    @Override
    public int getItemViewType(int position) {
        Match match = matchList.get(position);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int userIdTeam = sharedPreferences.getInt("userIdTeam", 0);
        int userId = sharedPreferences.getInt("userId", 0);
        String rol = sharedPreferences.getString("rol", "");
        if (!rol.equals("soloPlayer")) {
            return TYPE_TWO_TEAMS;
        } else {
            return TYPE_FIVE_VS_FIVE;
        }
        /*if (!match.getTeam1().getId().equals("No disponible") || !match.getTeam2().getId().equals("No disponible")) {
            return TYPE_TWO_TEAMS;
        } else {
            return TYPE_FIVE_VS_FIVE;
        }*/
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
            String rol = sharedPreferences.getString("rol", "");
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
                if(!isNotIn && rol.equals("captain")){
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
                    buttonEntrarMatch.setText("YA ESTAS DENTRO");
                    buttonEntrarMatch.setBackgroundColor(ContextCompat.getColor(context, R.color.negro1));
                    buttonEntrarMatch.setTextColor(ContextCompat.getColor(context, R.color.white));

                }

            }else if(match.getStatus().equals("CERRADA")){
                buttonEntrarMatch.setText("ESPERANDO");
                buttonEntrarMatch.setBackgroundColor(ContextCompat.getColor(context, R.color.negro1));
                buttonEntrarMatch.setTextColor(ContextCompat.getColor(context, R.color.white));
                buttonEntrarMatch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(rol.equals("captain")){
                            Intent intent = new Intent(context, ResultadoPartida.class);
                            intent.putExtra("team1Id", Integer.parseInt(match.getTeam1().getId()));
                            intent.putExtra("team2Id", Integer.parseInt(match.getTeam2().getId()));
                            intent.putExtra("team1Logo", match.getTeam1().getLogoPic());
                            intent.putExtra("team2Logo", match.getTeam2().getLogoPic());
                            intent.putExtra("team1Name", match.getTeam1().getTeamName());
                            intent.putExtra("team2Name", match.getTeam2().getTeamName());
                            intent.putExtra("userId", userId);
                            intent.putExtra("gameId", match.getMatchId());
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, SubirResultado.class);
                            intent.putExtra("team1Id", Integer.parseInt(match.getTeam1().getId()));
                            intent.putExtra("team2Id", Integer.parseInt(match.getTeam2().getId()));
                            intent.putExtra("userId", userId);
                            intent.putExtra("gameId", match.getMatchId());
                            context.startActivity(intent);
                        }
                    }
                });
            } else if (match.getStatus().equals("FINALIZADA")) {
                buttonEntrarMatch.setText("FINALIZADA");
                buttonEntrarMatch.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
                buttonEntrarMatch.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        }
    }
    static class FiveVsFiveViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDateMatch;
        private TextView textViewTimeMatch;
        private TextView textViewLocalitationMatch;
        private ImageView imageViewTeam1User1;
        private ImageView imageViewTeam1User2;
        private ImageView imageViewTeam1User3;
        private ImageView imageViewTeam1User4;
        private ImageView imageViewTeam1User5;
        private ImageView imageViewTeam2User1;
        private ImageView imageViewTeam2User2;
        private ImageView imageViewTeam2User3;
        private ImageView imageViewTeam2User4;
        private ImageView imageViewTeam2User5;
        private TextView texViewNom1Team1, texViewNom2Team1, texViewNom3Team1, texViewNom4Team1, texViewNom5Team1, texViewNom1Team2, texViewNom2Team2, texViewNom3Team2, texViewNom4Team2, texViewNom5Team2;
        private Button buttonEntrarMatch;
        public FiveVsFiveViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDateMatch = itemView.findViewById(R.id.textViewDateMatch);
            textViewTimeMatch = itemView.findViewById(R.id.textViewTimeMatch);
            textViewLocalitationMatch = itemView.findViewById(R.id.textViewLocalitationMatch);
            imageViewTeam1User1 = itemView.findViewById(R.id.imageViewTeam1Jugador1);
            imageViewTeam1User2 = itemView.findViewById(R.id.imageViewTeam1Jugador2);
            imageViewTeam1User3 = itemView.findViewById(R.id.imageViewTeam1Jugador3);
            imageViewTeam1User4 = itemView.findViewById(R.id.imageViewTeam1Jugador4);
            imageViewTeam1User5 = itemView.findViewById(R.id.imageViewTeam1Jugador5);

            texViewNom1Team1 = itemView.findViewById(R.id.texViewNomTeam1J1);
            texViewNom2Team1 = itemView.findViewById(R.id.texViewNomTeam1J2);
            texViewNom3Team1 = itemView.findViewById(R.id.texViewNomTeam1J3);
            texViewNom4Team1 = itemView.findViewById(R.id.texViewNomTeam1J4);
            texViewNom5Team1 = itemView.findViewById(R.id.texViewNomTeam1J5);

            imageViewTeam2User1 = itemView.findViewById(R.id.imageViewTeam2Jugador1);
            imageViewTeam2User2 = itemView.findViewById(R.id.imageViewTeam2Jugador2);
            imageViewTeam2User3 = itemView.findViewById(R.id.imageViewTeam2Jugador3);
            imageViewTeam2User4 = itemView.findViewById(R.id.imageViewTeam2Jugador4);
            imageViewTeam2User5 = itemView.findViewById(R.id.imageViewTeam2Jugador5);

            texViewNom1Team2 = itemView.findViewById(R.id.texViewNomTeam2J1);
            texViewNom2Team2 = itemView.findViewById(R.id.texViewNomTeam2J2);
            texViewNom3Team2 = itemView.findViewById(R.id.texViewNomTeam2J3);
            texViewNom4Team2 = itemView.findViewById(R.id.texViewNomTeam2J4);
            texViewNom5Team2 = itemView.findViewById(R.id.texViewNomTeam2J5);

            buttonEntrarMatch = itemView.findViewById(R.id.buttonEntrarMatch);


        }

        public void bind(Match match) {
            // Aquí puedes implementar la lógica para el diseño FiveVsFive
            textViewDateMatch.setText(match.getMatchDate());
            textViewTimeMatch.setText(match.getMatchTime());
            textViewLocalitationMatch.setText(match.getMatchLocation());

            // Team 1
            List<Usuario> playersTeam1 = match.getTeam1().getPlayers();
            if (playersTeam1 != null && playersTeam1.size() > 0) {
                loadPlayerImage(playersTeam1.get(0), imageViewTeam1User1, texViewNom1Team1);
                loadPlayerImage(playersTeam1.get(1), imageViewTeam1User2, texViewNom2Team1);
                loadPlayerImage(playersTeam1.get(2), imageViewTeam1User3, texViewNom3Team1);
                loadPlayerImage(playersTeam1.get(3), imageViewTeam1User4, texViewNom4Team1);
                loadPlayerImage(playersTeam1.get(4), imageViewTeam1User5, texViewNom5Team1);
            }

            // Team 2
            List<Usuario> playersTeam2 = match.getTeam2().getPlayers();
            if (playersTeam2 != null && playersTeam2.size() > 0) {
                loadPlayerImage(playersTeam2.get(0), imageViewTeam2User1, texViewNom1Team2);
                loadPlayerImage(playersTeam2.get(1), imageViewTeam2User2, texViewNom2Team2);
                loadPlayerImage(playersTeam2.get(1), imageViewTeam2User3, texViewNom3Team2);
                loadPlayerImage(playersTeam2.get(1), imageViewTeam2User4, texViewNom4Team2);
                loadPlayerImage(playersTeam2.get(1), imageViewTeam2User5, texViewNom5Team2);
            }
        }

        private void loadPlayerImage(Usuario player, ImageView imageView, TextView textView) {
            String playerName = TextUtils.isEmpty(player.getFirstname()) ? "Disponible" : player.getFirstname();
            textView.setText(playerName);

            if (!TextUtils.isEmpty(player.getProfilePic())) {
                Picasso.get().load(player.getProfilePic()).into(imageView);
            } else {
                imageView.setImageResource(R.drawable.add_black);
            }
        }



    }
}
