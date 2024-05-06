package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_amigo);
        editTextBuscar = findViewById(R.id.editTextBuscador);
        usuarioList = new ArrayList<>();
        recyclerViewListaUsers = findViewById(R.id.recyclerViewListaUsers);
        usuarioAdapter = new UsuarioAdapter(usuarioList);
        recyclerViewListaUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListaUsers.setAdapter(usuarioAdapter);

        // Agregar usuarios a la lista
        agregarUsuariosALaLista();

        // Configurar el TextWatcher para el EditText
        /*editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filtrar la lista de usuarios
                //filtrarUsuarios(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/

    }

    private void agregarUsuariosALaLista() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL) // Reemplaza con la URL de tu servidor
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Realizar la solicitud para obtener usuarios aleatorios
        Call<List<Usuario>> call = apiService.getUsersRandom();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                Log.d("TAG", "onResponse: ");
                if (response.isSuccessful()) {
                    List<Usuario> usuarios = response.body();
                    if (usuarios != null) {
                        usuarioList.addAll(usuarios);
                        usuarioAdapter.notifyDataSetChanged();
                    }
                } else {
                    // Manejar respuesta de error
                    Toast.makeText(AnadirAmigo.this, "Error al obtener usuarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                // Manejar error de conexión
                Toast.makeText(AnadirAmigo.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.d("ERROR", "onFailure: "+t.getMessage());
            }
        });
    }

    private void filtrarUsuarios(String texto) {
        List<Usuario> listaFiltrada = new ArrayList<>();
        for (Usuario usuario : usuarioList) {
            if (usuario.getFirstname().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(usuario);
            }
        }
        usuarioAdapter.setUsuario(listaFiltrada);
    }
}