package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class Login extends AppCompatActivity {

    private EditText editTextContraseña;
    private ImageButton imageButtonMostrarContraseña;
    private boolean contraseñaVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextContraseña = findViewById(R.id.editTextPassword);
        imageButtonMostrarContraseña = findViewById(R.id.imageButtonMostrarPassword);

        imageButtonMostrarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contraseñaVisible) {
                    // Ocultar la contraseña
                    editTextContraseña.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageButtonMostrarContraseña.setImageResource(R.drawable.eye_closed_white);
                } else {
                    // Mostrar la contraseña
                    editTextContraseña.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageButtonMostrarContraseña.setImageResource(R.drawable.eye_white);
                }
                // Cambiar el estado de la contraseña (visible o no)
                contraseñaVisible = !contraseñaVisible;
            }
        });
    }

    public void launchMain(View view){
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    public void launchInici(View view){
        Intent intent = new Intent(Login.this, Inici.class);
        startActivity(intent);
    }
}