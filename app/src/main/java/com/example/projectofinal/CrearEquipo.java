package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class CrearEquipo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_equipo);

        ConstraintLayout firstLayout = findViewById(R.id.firstLayout);
        ConstraintLayout secondLayout = findViewById(R.id.secondLayout);
        EditText editTextNE = findViewById(R.id.editTextNE);
        EditText editTextAbreviacion = findViewById(R.id.editTextAbreviacion);
        ImageButton imageButtonClearNE = findViewById(R.id.imageButtonClear1);
        ImageButton imageButtonClearAbreviacion = findViewById(R.id.imageButtonClear2);

        setupEditTextFocusChange(editTextNE, firstLayout, imageButtonClearNE);
        setupEditTextFocusChange(editTextAbreviacion, secondLayout, imageButtonClearAbreviacion);
        setupImageButtonClear(editTextNE, imageButtonClearNE);
        setupImageButtonClear(editTextAbreviacion, imageButtonClearAbreviacion);
    }

    private void setupEditTextFocusChange(EditText editText, ConstraintLayout layout, ImageButton button) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    layout.setBackgroundResource(R.drawable.layout_border_orange);
                    button.setVisibility(View.VISIBLE);
                } else {
                    layout.setBackgroundResource(R.drawable.layout_border);
                    button.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setupImageButtonClear(EditText editText, ImageButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar el texto del EditText cuando se hace clic en el ImageButton
                editText.setText("");
            }
        });
    }

    public void launchInici(View view){
        Intent intent = new Intent(CrearEquipo.this, Inici.class);
        startActivity(intent);
    }
}