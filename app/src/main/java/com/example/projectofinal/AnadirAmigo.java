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

        usuarioList = new ArrayList<>();
        recyclerViewListaUsers = findViewById(R.id.recyclerViewListaUsers);
        usuarioAdapter = new UsuarioAdapter(usuarioList);
        recyclerViewListaUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListaUsers.setAdapter(usuarioAdapter);

        Usuario user = new Usuario("Daniel",R.drawable.perfil);
        Usuario user2 = new Usuario("Abuelo abuelo",R.drawable.viejo);
        usuarioList.add(user);
        usuarioList.add(user2);

        usuarioAdapter.setUsuario(usuarioList);




    }
}