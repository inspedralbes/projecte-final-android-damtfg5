package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notifications extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificacionesAdapter adapter;
    private List<Usuario> notificaciones;
    private String URL = "http://192.168.206.176:3001/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        recyclerView = findViewById(R.id.recyclerViewListaNotis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificaciones = new ArrayList<>();

        adapter = new NotificacionesAdapter(this, notificaciones);

        recyclerView.setAdapter(adapter);

        obtenerSolicitudesAmistadPendientes(5);
    }

    private void obtenerSolicitudesAmistadPendientes(int userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<FriendRequestBody>> call = apiService.getPendingFriendRequests(userId);

        call.enqueue(new Callback<List<FriendRequestBody>>() {
            @Override
            public void onResponse(Call<List<FriendRequestBody>> call, Response<List<FriendRequestBody>> response) {
                if (response.isSuccessful()) {
                    List<FriendRequestBody> friendRequests = response.body();
                    // Convertir las solicitudes de amistad en objetos Usuario
                    for (FriendRequestBody friendRequest : friendRequests) {
                        Usuario usuario = new Usuario(friendRequest.getSenderId(), "", "", "");
                        notificaciones.add(usuario);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    // Manejar el caso en el que la respuesta del servidor no sea exitosa
                    Toast.makeText(Notifications.this, "Error al obtener las solicitudes de amistad", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FriendRequestBody>> call, Throwable t) {
                // Manejar el caso en el que la llamada al servidor falle
                Toast.makeText(Notifications.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}