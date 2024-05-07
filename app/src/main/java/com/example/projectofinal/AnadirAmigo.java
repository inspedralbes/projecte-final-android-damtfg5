package com.example.projectofinal;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnadirAmigo extends AppCompatActivity {

    private RecyclerView recyclerViewListaUsers;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarioList;
    private EditText editTextBuscar;
    private String URL = "http://192.168.206.176:3001/";
    private List<Usuario> listaCompletaUsuarios = new ArrayList<>();
    private List<Usuario> listaUsuariosAleatorios = new ArrayList<>();
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_amigo);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getInt("userId", -1);

        editTextBuscar = findViewById(R.id.editTextBuscador);
        usuarioList = new ArrayList<>();
        recyclerViewListaUsers = findViewById(R.id.recyclerViewListaUsers);
        usuarioAdapter = new UsuarioAdapter(usuarioList, userId);
        recyclerViewListaUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListaUsers.setAdapter(usuarioAdapter);

        // Agregar usuarios a la lista
        agregarUsuariosALaLista();

        // Configurar el TextWatcher para el EditText
        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filtrar la lista de usuarios
                filtrarUsuarios(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void agregarUsuariosALaLista() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Usuario>> call = apiService.getUsers();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    List<Usuario> usuarios = response.body();
                    if (usuarios != null) {
                        listaCompletaUsuarios.addAll(usuarios);
                        // Filtrar el usuario actualmente autenticado
                        List<Usuario> listaFiltrada = new ArrayList<>();
                        for (Usuario usuario : listaCompletaUsuarios) {
                            if (usuario.getId() != userId) {
                                listaFiltrada.add(usuario);
                            }
                        }
                        // Selección aleatoria de 5 usuario
                        Collections.shuffle(listaFiltrada);
                        listaUsuariosAleatorios = listaFiltrada.subList(0, Math.min(listaFiltrada.size(), 5));
                        usuarioAdapter.setUsuario(listaUsuariosAleatorios);
                    }
                } else {
                    Toast.makeText(AnadirAmigo.this, "Error al obtener usuarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(AnadirAmigo.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.d("ERROR", "onFailure: " + t.getMessage());
            }
        });
    }

    private void filtrarUsuarios(String texto) {
        if (texto.isEmpty()) {
            // Si el texto de búsqueda está vacío, mostrar los 5 usuarios aleatorios
            usuarioAdapter.setUsuario(listaUsuariosAleatorios);
        } else {
            // Filtrar la lista completa de usuarios según el texto de búsqueda
            List<Usuario> listaFiltrada = new ArrayList<>();
            for (Usuario usuario : listaCompletaUsuarios) {
                if (usuario.getFirstname().toLowerCase().contains(texto.toLowerCase()) || usuario.getSurname().toLowerCase().contains(texto.toLowerCase()) ) {
                    listaFiltrada.add(usuario);
                }
            }
            usuarioAdapter.setUsuario(listaFiltrada);
        }
    }
}