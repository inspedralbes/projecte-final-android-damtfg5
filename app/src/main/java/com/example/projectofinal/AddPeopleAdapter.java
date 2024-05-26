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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPeopleAdapter extends RecyclerView.Adapter<AddPeopleAdapter.ViewHolder> {

    private List<Usuario> usuarioList;
    private Context context;
    private String URL = "http://192.168.1.17:3001/";
    int teamId;

    public AddPeopleAdapter(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
    }

    public void setUsuario(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listainviteteam, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Usuario usuario = usuarioList.get(position);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        teamId = sharedPreferences.getInt("teamId", -1);
        holder.textViewNombreUsuario.setText(usuario.getFirstname() + " " + usuario.getSurname());
        if (usuario.getProfilePic() != null && !usuario.getProfilePic().isEmpty()) {
            Picasso.get().load(usuario.getProfilePic()).into(holder.imageViewUsuario);
        } else {
            holder.imageViewUsuario.setImageResource(R.drawable.perfil);
        }

        if(usuario.getIdTeam()==teamId){
            holder.buttonInvitar.setText("EN EL EQUIPO");
            holder.buttonInvitar.setBackgroundResource(R.drawable.button_amigobg);
            Context context = holder.buttonInvitar.getContext();
            holder.buttonInvitar.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.buttonInvitar.setClickable(false);
        }else{
            holder.buttonInvitar.setBackgroundResource(R.drawable.button_selectorbg2);
            holder.buttonInvitar.setText("INVITAR");
            Context context = holder.buttonInvitar.getContext();
            holder.buttonInvitar.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.buttonInvitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inviteUserToTeam(usuario.getId());
                }
            });
        }
        /*holder.buttonInvitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteUserToTeam(usuario.getId());
            }
        });*/
    }

    private void inviteUserToTeam(int userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        InviteBody inviteBody = new InviteBody(teamId, userId); // Ajusta esto según tus necesidades
        Call<Void> call = apiService.inviteUserToTeam(inviteBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Manejar la respuesta exitosa
                } else {
                    // Manejar respuesta no exitosa
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Manejar fallos en la solicitud
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUsuario;
        TextView textViewNombreUsuario;
        Button buttonInvitar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUsuario = itemView.findViewById(R.id.imageViewUsuario);
            textViewNombreUsuario = itemView.findViewById(R.id.textViewNombreUsuario);
            buttonInvitar = itemView.findViewById(R.id.buttonSeguir);
        }
    }
}

