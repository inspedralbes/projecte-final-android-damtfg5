package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class VerPerfilJugador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil_jugador);

        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);

        Log.d("id", "onCreate: "+userId);
    }
}