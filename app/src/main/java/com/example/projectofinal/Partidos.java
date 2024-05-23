package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Partidos extends AppCompatActivity {
    ImageButton imageButtonBackPartidos;
    Button buttonaaa,buttonbbb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidos);

        imageButtonBackPartidos = findViewById(R.id.imageButtonBackPartidos);
        Button buttonDisponibles = findViewById(R.id.buttonDisponibles);
        Button buttonTusPartidos = findViewById(R.id.buttonTusPartidos);
        imageButtonBackPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonDisponibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new FragmentDisponibles());
            }
        });

        buttonTusPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new FragmentTusPartidos());
            }
        });

        // Load the default fragment
        loadFragment(new FragmentDisponibles());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}