package com.example.projectofinal;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TWO_TEAMS = 0;
    private static final int TYPE_FIVE_VS_FIVE = 1;
    private List<Match> matchList;

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
        private TextView textViewNivelMatch;
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
            textViewDateMatch.setText(match.getMatchDate());
            textViewTimeMatch.setText(match.getMatchTime());
            textViewLocalitationMatch.setText(match.getMatchLocation());
            // Asignar imágenes de equipos (si tienes la URL de la imagen)
            // Glide.with(itemView.getContext()).load(match.getTeam1().getLogoPic()).into(imageViewTeam1);
            // Glide.with(itemView.getContext()).load(match.getTeam2().getLogoPic()).into(imageViewTeam2);
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
            buttonEntrarMatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acción al hacer clic en el botón
                }
            });
        }
    }


    static class FiveVsFiveViewHolder extends RecyclerView.ViewHolder {

        public FiveVsFiveViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Match match) {
        }
    }
}

