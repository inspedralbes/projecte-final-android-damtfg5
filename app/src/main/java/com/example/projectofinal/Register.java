package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    private EditText editTextContraseña,editTextNomCognom,editTextEmail,editTextTelefono;
    private ImageButton imageButtonMostrarContraseña;
    private boolean contraseñaVisible = false;
    private String URL = "http://192.168.1.17:3001/";
    Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextContraseña = findViewById(R.id.editTextContraseña);
        editTextNomCognom = findViewById(R.id.editTextNomCognom);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        imageButtonMostrarContraseña = findViewById(R.id.imageButtonMostrarContraseña);
        buttonLogin = findViewById(R.id.buttonLogin);

        imageButtonMostrarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contraseñaVisible) {
                    // Ocultar la contraseña
                    editTextContraseña.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageButtonMostrarContraseña.setImageResource(R.drawable.eye_closed);
                } else {
                    // Mostrar la contraseña
                    editTextContraseña.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageButtonMostrarContraseña.setImageResource(R.drawable.eye);
                }
                // Cambiar el estado de la contraseña (visible o no)
                contraseñaVisible = !contraseñaVisible;
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = editTextNomCognom.getText().toString().trim();
                String[] names = fullName.split(" ");
                String firstname = "";
                String surname = "";
                if (names.length > 0) {
                    // El primer elemento del array es el nombre
                    firstname = names[0];
                }
                if (names.length > 1) {
                    // El segundo y siguientes elementos son el apellido
                    for (int i = 1; i < names.length; i++) {
                        surname += names[i];
                        if (i < names.length - 1) {
                            surname += " "; // Agrega un espacio entre cada parte del apellido
                        }
                    }
                }
                String email = editTextEmail.getText().toString().trim();
                String password = hashPassword(editTextContraseña.getText().toString().trim());
                String phone = editTextTelefono.getText().toString().trim();
                String country = "";
                String birthDate = "";

                // Crear un objeto RegisterRequest con los datos del registro
                RegisterRequest registerRequest = new RegisterRequest(firstname, surname, email, password, phone, country, birthDate);

                // Iniciar Retrofit y llamar al método para registrar al usuario
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService = retrofit.create(ApiService.class);

                Call<Void> call = apiService.registerUser(registerRequest);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Registro exitoso
                            Toast.makeText(Register.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            // Aquí puedes realizar acciones adicionales después del registro exitoso
                        } else {
                            // Registro fallido
                            Toast.makeText(Register.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Manejar errores de conexión
                        Toast.makeText(Register.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        Log.e("Error", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }
    public void launchMain(View view){
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
    }
    private String hashPassword(String password) {
        try {
            // Crear instancia de MessageDigest con algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("MD5");

            // Aplicar el algoritmo de hash a la contraseña
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convertir el arreglo de bytes a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public void launchInici(View view){
        Intent intent = new Intent(Register.this, Inici.class);
        startActivity(intent);
    }*/
}