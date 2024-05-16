package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Partidos extends AppCompatActivity {
    ImageButton imageButtonBackPartidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidos);

        imageButtonBackPartidos = findViewById(R.id.imageButtonBackPartidos);
        imageButtonBackPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}