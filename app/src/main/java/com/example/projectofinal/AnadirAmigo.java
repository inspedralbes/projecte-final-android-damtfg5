package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class AnadirAmigo extends AppCompatActivity {

    private RecyclerView recyclerViewListaUsers;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarioList;
    private EditText editTextBuscar;
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

        Usuario user = new Usuario("Daniel",R.drawable.perfil);
        Usuario user2 = new Usuario("Abuelo abuelo",R.drawable.viejo);
        Usuario user3 = new Usuario("Abuelo abuelo",R.drawable.viejo);
        Usuario user4 = new Usuario("Abuelo abuelo",R.drawable.viejo);
        Usuario user5 = new Usuario("Abuelo abuelo",R.drawable.viejo);
        Usuario user6 = new Usuario("Abuelo abuelo",R.drawable.viejo);
        Usuario user7 = new Usuario("Abuelo abuelo",R.drawable.viejo);
        Usuario user8 = new Usuario("Abuelo abuelo",R.drawable.viejo);
        usuarioList.add(user);
        usuarioList.add(user2);
        usuarioList.add(user3);
        usuarioList.add(user4);
        usuarioList.add(user5);
        usuarioList.add(user6);
        usuarioList.add(user7);
        usuarioList.add(user8);

        usuarioAdapter.setUsuario(usuarioList);







    }
}