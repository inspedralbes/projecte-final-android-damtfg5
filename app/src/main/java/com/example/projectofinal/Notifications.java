package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
    private List<TeamData> invitaciones;
    private String URL = "http://192.168.206.176:3001/";
    ImageButton imageButtonBackFR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        recyclerView = findViewById(R.id.recyclerViewListaNotis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificaciones = new ArrayList<>();
        invitaciones = new ArrayList<>();

        adapter = new NotificacionesAdapter(this, notificaciones);

        recyclerView.setAdapter(adapter);
        imageButtonBackFR = findViewById(R.id.imageButtonBackFR);

        // Obtener el userId de las SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = sharedPreferences.getInt("userId", -1);

        getPendingFriendRequests(userId);
        getPendingTeamInvitations(userId);


        imageButtonBackFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getPendingFriendRequests(int userId) {
        UserIdRequest userIdRequest = new UserIdRequest(userId);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Usuario>> call = apiService.getPendingFriendRequests(userIdRequest);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    List<Usuario> pendingRequests = response.body();
                    if (pendingRequests != null) {
                        for (Usuario request : pendingRequests) {
                            Usuario usuario = new Usuario(request.getId(),request.getFirstname(),request.getSurname(),request.getProfilePic(), request.getIdRequest());
                            notificaciones.add(usuario);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Notifications.this, "No hay solicitudes pendientes", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Notifications.this, "Error al obtener solicitudes pendientes", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(Notifications.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: " + t.getMessage());
            }
        });
    }
    private void getPendingTeamInvitations(int userId) {
        // Crear un objeto UserIdRequest con el userId
        UserIdRequest userIdRequest = new UserIdRequest(userId);

        // Iniciar Retrofit y llamar al método para obtener las solicitudes pendientes de amistad
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<TeamData>> call = apiService.getPendingTeamInvitations(userIdRequest);
        call.enqueue(new Callback<List<TeamData>>() {
            @Override
            public void onResponse(Call<List<TeamData>> call, Response<List<TeamData>> response) {
                if (response.isSuccessful()) {
                    List<TeamData> pendingRequests = response.body();
                    if (pendingRequests != null) {
                        for (TeamData request : pendingRequests) {
                            TeamData team = new TeamData(request.getId(),request.getTeamName(),request.getShortName(),request.getLogoPic(), request.getIdRequest());
                            Log.d("Team", "ID: "+team.getId());
                            Log.d("Team", "Name: "+team.getTeamName());
                            Log.d("Team", "ShortName: "+team.getShortName());
                            Log.d("Team", "IdRequest: "+team.getIdRequest());
                            invitaciones.add(team);
                        }
                    } else {
                        Toast.makeText(Notifications.this, "No hay solicitudes pendientes", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Notifications.this, "Error al obtener solicitudes pendientes", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<TeamData>> call, Throwable t) {
                Toast.makeText(Notifications.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}
