package com.example.projectofinal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.UsuarioViewHolder> {
    private Context context;
    private List<Usuario> usuarios;
    private String URL = "http://192.168.206.176:3001/";

    public NotificacionesAdapter(Context context, List<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listanotis, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        if (usuario.getProfilePic() != null && !usuario.getProfilePic().isEmpty()) {
            Picasso.get().load(usuario.getProfilePic()).into(holder.imageViewUsuario);
        } else {
            holder.imageViewUsuario.setImageResource(R.drawable.perfil);
        }
        holder.textViewNombreUsuario.setText(usuario.getFirstname() + " " + usuario.getSurname());
        holder.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int requestId =usuario.getIdRequest();
                String status = "ACCEPT";
                ResponseFriendRequest response = new ResponseFriendRequest(requestId, status);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService = retrofit.create(ApiService.class);
                Call<Void> call = apiService.responseFriendRequest(response);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            int position = holder.getAdapterPosition();
                            usuarios.remove(position);
                            notifyDataSetChanged();
                        } else {
                            // Acción en caso de respuesta no exitosa
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Acción en caso de fallo en la conexión
                    }
                });
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int requestId =usuario.getIdRequest();
                String status = "REJECT";
                ResponseFriendRequest response = new ResponseFriendRequest(requestId, status);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                Call<Void> call = apiService.responseFriendRequest(response);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            int position = holder.getAdapterPosition();
                            usuarios.remove(position);
                            notifyDataSetChanged();
                        } else {
                            // Acción en caso de respuesta no exitosa
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Acción en caso de fallo en la conexión
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUsuario;
        TextView textViewNombreUsuario;
        Button buttonConfirm;
        Button buttonDelete;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUsuario = itemView.findViewById(R.id.imageViewUsuario);
            textViewNombreUsuario = itemView.findViewById(R.id.textViewNombreUsuario);
            buttonConfirm = itemView.findViewById(R.id.buttonConfirm);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}

