package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubirResultado extends AppCompatActivity {
    private String URL = "http://192.168.206.176:3001/";
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_resultado);

        EditText editTextPointsSpike = findViewById(R.id.editTextPointsSpike);
        EditText editTextErrorsSpike = findViewById(R.id.editTextErrorsSpike);
        EditText editTextAttemptsSpike = findViewById(R.id.editTextAttemptsSpike);
        EditText editTextPointsBlock = findViewById(R.id.editTextPointsBlock);
        EditText editTextErrorsBlock = findViewById(R.id.editTextErrorsBlock);
        EditText editTextAttemptsBlock = findViewById(R.id.editTextAttemptsBlock);
        EditText editTextPointsServe = findViewById(R.id.editTextPointsServe);
        EditText editTextErrorsServe = findViewById(R.id.editTextErrorsServe);
        EditText editTextAttemptsServe = findViewById(R.id.editTextAttemptsServe);
        EditText editTextSuccessfulSet = findViewById(R.id.editTextSuccessfulSet);
        EditText editTextErrorsSet = findViewById(R.id.editTextErrorsSet);
        EditText editTextAttemptsSet = findViewById(R.id.editTextAttemptsSet);
        EditText editTextSuccessfulReceive = findViewById(R.id.editTextSuccessfulsReceive);
        EditText editTextErrorsReceive = findViewById(R.id.editTextErrorsReceive);
        EditText editTextAttemptsReceive = findViewById(R.id.editTextAttemptsReceive);

        Button buttonSubirStats = findViewById(R.id.buttonSubirStats);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
    }
}