package com.example.projectofinal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarioList;
    private String URL = "http://192.168.206.176:3001/";
    int userId;

    public UsuarioAdapter(List<Usuario> usuarioList,int userId) {
        this.usuarioList = usuarioList;
        this.userId = userId;
    }

    public void setUsuario(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listausers, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarioList.get(position);
        if (usuario.getProfilePic() != null && !usuario.getProfilePic().isEmpty()) {
            // Cargar la imagen desde la URL utilizando Picasso
            Picasso.get().load(usuario.getProfilePic()).into(holder.imageViewUsuario);
        } else {
            // Si el campo profilePic no contiene una URL válida, puedes cargar una imagen de placeholder o dejar el ImageView vacío
            holder.imageViewUsuario.setImageResource(R.drawable.perfil); // Cambia placeholder_image por el ID de tu imagen de placeholder
        }
        holder.textViewNombreUsuario.setText(usuario.getFirstname() + " " + usuario.getSurname());
        holder.buttonSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendRequestBody friendRequestBody = new FriendRequestBody(userId, usuario.getId()); // Asegúrate de tener senderId definido en tu actividad
                Log.d("ID", userId+", "+usuario.getId());
                // Enviar la solicitud de amistad al servidor
                sendFriendRequestToServer(friendRequestBody);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarioList != null ? usuarioList.size() : 0;
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUsuario;
        TextView textViewNombreUsuario;
        Button buttonSeguir;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUsuario = itemView.findViewById(R.id.imageViewUsuario);
            textViewNombreUsuario = itemView.findViewById(R.id.textViewNombreUsuario);
            buttonSeguir = itemView.findViewById(R.id.buttonSeguir);
        }
    }

    private void sendFriendRequestToServer(FriendRequestBody friendRequestBody) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<Void> call = apiService.sendFriendRequest(friendRequestBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Manejar la respuesta exitosa del servidor (opcional)
                    //Toast.makeText(context, "Solicitud de amistad enviada", Toast.LENGTH_SHORT).show();
                    Log.d("BEIN", "Solicitud de amistad enviada ");
                } else {
                    // Manejar respuesta de error del servidor
                    //Toast.makeText(context, "Error al enviar solicitud de amistad", Toast.LENGTH_SHORT).show();
                    Log.d("MAL", "Error al enviar solicitud de amistad");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Manejar error de conexión
                //Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("falla", "onFailure: " + t.getMessage());
            }
        });
    }
}
