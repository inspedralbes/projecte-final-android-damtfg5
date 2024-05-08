package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificacionesAdapter adapter;
    private List<Usuario> notificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        recyclerView = findViewById(R.id.recyclerViewListaNotis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificaciones = new ArrayList<>();

        adapter = new NotificacionesAdapter(this, notificaciones);

        recyclerView.setAdapter(adapter);
    }
}