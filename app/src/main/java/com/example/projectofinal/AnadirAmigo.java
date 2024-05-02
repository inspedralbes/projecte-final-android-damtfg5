package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AnadirAmigo extends AppCompatActivity {

    private RecyclerView recyclerViewListaUsers;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarioList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_amigo);

        recyclerViewListaUsers = findViewById(R.id.recyclerViewListaUsers);
        // Configurar el RecyclerView con un LinearLayoutManager
        recyclerViewListaUsers.setLayoutManager(new LinearLayoutManager(this));
        // Inicializar el adaptador y configurarlo en el RecyclerView
        usuarioAdapter = new UsuarioAdapter(new ArrayList<>());
        usuarioList.add(new Usuario("NombreUsuario", R.drawable.perfil));
        usuarioAdapter.notifyDataSetChanged();
        recyclerViewListaUsers.setAdapter(usuarioAdapter);


    }
}