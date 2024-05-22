package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;

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
        buttonaaa=findViewById(R.id.buttonaaaaa);
        buttonbbb=findViewById(R.id.buttonbbbbb);
        imageButtonBackPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonaaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Partidos.this, SubirResultado.class);
                startActivity(intent);
            }
        });

        buttonbbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Partidos.this, ResultadoPartida.class);
                startActivity(intent);
            }
        });
    }
}