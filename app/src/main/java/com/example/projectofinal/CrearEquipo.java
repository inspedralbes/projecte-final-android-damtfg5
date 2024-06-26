package com.example.projectofinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearEquipo extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageButtonUsuario;
    private Uri imageUri;
    private String URL = "http://volleyadmin.dam.inspedralbes.cat:3005/";
    private int userId;
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
        Button buttonCrearEquipo = findViewById(R.id.buttonCrearEquipo);

        setupEditTextFocusChange(editTextNE, firstLayout, imageButtonClearNE);
        setupEditTextFocusChange(editTextAbreviacion, secondLayout, imageButtonClearAbreviacion);
        setupImageButtonClear(editTextNE, imageButtonClearNE);
        setupImageButtonClear(editTextAbreviacion, imageButtonClearAbreviacion);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getInt("userId", -1);
        imageButtonUsuario = findViewById(R.id.imageViewUsuario);
        imageButtonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        ImageButton imageButtonBCE = findViewById(R.id.imageButtonBCE);
        imageButtonBCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonCrearEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teamName = editTextNE.getText().toString();
                String logoPic = imageUri != null ? imageUri.toString() : "sin foto";
                String shortName = editTextAbreviacion.getText().toString();

                // Crea un objeto TeamData con los datos del equipo
                TeamData teamData = new TeamData(userId, teamName, logoPic, shortName);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL) // Reemplaza esto con la URL base de tu servidor
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Crea una instancia de la interfaz ApiService
                ApiService apiService = retrofit.create(ApiService.class);

                // Realiza la solicitud POST al servidor
                Call<TeamData> call = apiService.createTeam(teamData);
                call.enqueue(new Callback<TeamData>() {
                    @Override
                    public void onResponse(Call<TeamData> call, Response<TeamData> response) {
                        if (response.isSuccessful()) {
                            // Manejar la respuesta exitosa
                            Toast.makeText(CrearEquipo.this, "Equipo creado exitosamente", Toast.LENGTH_SHORT).show();
                            // Actualizar los valores en SharedPreferences
                            // Obtener el id del equipo de la respuesta
                            int idTeam = response.body().getId();
                            Log.d("iddddd", "onResponse: " + idTeam);
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("idTeam", idTeam);
                            editor.putString("rol", "captain");

                            editor.apply();
                            // Puedes manejar cualquier otra acción aquí, como regresar a la actividad anterior
                        } else {
                            // Manejar el error
                            Toast.makeText(CrearEquipo.this, "Error al crear el equipo", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TeamData> call, Throwable t) {
                        Toast.makeText(CrearEquipo.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageButtonUsuario.setImageURI(imageUri);
        }
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
}
