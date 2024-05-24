package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private ImageButton imageButtonMostrarContraseña;
    private boolean contraseñaVisible = false;
    private String URL = "http://192.168.206.176:3001/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        imageButtonMostrarContraseña = findViewById(R.id.imageButtonMostrarPassword);

        imageButtonMostrarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contraseñaVisible) {
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageButtonMostrarContraseña.setImageResource(R.drawable.eye_closed_white);
                } else {
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageButtonMostrarContraseña.setImageResource(R.drawable.eye_white);
                }
                contraseñaVisible = !contraseñaVisible;
            }
        });
    }
    public void launchMain(View view){
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }
    public void launchInici(View view) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (!email.isEmpty() && !password.isEmpty()) {
            LoginRequestBody loginRequestBody = new LoginRequestBody(email, password);
            sendLoginRequest(loginRequestBody);
        } else {
            editTextEmail.setHint("¡Email Obligatorio!");
            editTextEmail.setHintTextColor(getColor(R.color.rojo));
            editTextPassword.setHint("¡Contraseña Obligatoria!");
            editTextPassword.setHintTextColor(getColor(R.color.rojo));
        }
    }
    private void sendLoginRequest(LoginRequestBody loginRequestBody) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<LoginResponse> call = apiService.login(loginRequestBody);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.isAuthorization()) {

                        getDataUser(loginResponse.getUserData().getId());
                        Intent intent = new Intent(Login.this, Inici.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("FALLA", "onFailure: " + t.getMessage());
            }
        });
    }

    private void getDataUser(int userId){
        String URL = "http://192.168.206.176:3001/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Map<String, Integer> userIdMap = new HashMap<>();
        userIdMap.put("id", userId);

        Call<GlobalDataUser> call = apiService.getUserForAndroid(userIdMap); // Cambiar el tipo de respuesta a GlobalDataUser

        call.enqueue(new Callback<GlobalDataUser>() {
            @Override
            public void onResponse(Call<GlobalDataUser> call, Response<GlobalDataUser> response) {
                if (response.isSuccessful()) {
                    GlobalDataUser userData = response.body();
                    Log.d("EMAIIIIIIIIIIIIIIIIIIIIIIIL", "onResponse: " + userData.getEmail());
                    saveUserData(userData);
                    // ... y así sucesivamente para todos los campos
                } else {
                    // Manejar errores
                    Log.e("FragmentPerfil", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GlobalDataUser> call, Throwable t) {
                Log.e("FragmentPerfil", "Failure: " + t.getMessage());
            }
        });
    }


    private void saveUserData(GlobalDataUser userData) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userData.getIdUser());
        editor.putString("userName", userData.getFirstname() + " " + userData.getSurname());
        editor.putString("userEmail", userData.getEmail());
        editor.putInt("userPhone", userData.getPhone());
        editor.putInt("totalGames", userData.getTotalGames() != null ? userData.getTotalGames() : 0);
        editor.putInt("dominantHand", userData.getDominantHand() != null ? userData.getDominantHand() : 0);
        editor.putString("position", userData.getPosition());
        editor.putInt("height", userData.getHeight());
        editor.putInt("verticalJump", userData.getVerticalJump());
        editor.putString("location", userData.getLocation());
        editor.putString("gender", userData.getGender());
        editor.putString("birthDate", userData.getBirthDate());
        editor.putString("bio", userData.getBio());
        editor.putString("availability", userData.getAvailability());
        editor.putString("country", userData.getCountry());
        editor.putString("profilePic", userData.getProfilePic());
        editor.putInt("userIdTeam", userData.getUserIdTeam() != null ? userData.getUserIdTeam() : 0);
        editor.putInt("spikePointsTotal", userData.getSpikePointsTotal());
        editor.putInt("spikeErrorsTotal", userData.getSpikeErrorsTotal());
        editor.putInt("spikeAttemptsTotal", userData.getSpikeAttemptsTotal());
        editor.putInt("blockPointsTotal", userData.getBlockPointsTotal());
        editor.putInt("blockErrorsTotal", userData.getBlockErrorsTotal());
        editor.putInt("blockReboundsTotal", userData.getBlockReboundsTotal());
        editor.putInt("servePointsTotal", userData.getServePointsTotal());
        editor.putInt("serveErrorsTotal", userData.getServeErrorsTotal());
        editor.putInt("serveAttemptsTotal", userData.getServeAttemptsTotal());
        editor.putInt("setSuccessfulTotal", userData.getSetSuccessfulTotal() != null ? userData.getSetSuccessfulTotal() : 0);
        editor.putInt("setErrorsTotal", userData.getSetErrorsTotal() != null ? userData.getSetErrorsTotal() : 0);
        editor.putInt("setAttemptsTotal", userData.getSetAttemptsTotal() != null ? userData.getSetAttemptsTotal() : 0);
        editor.putInt("receiveSuccessfulTotal", userData.getReceiveSuccessfulTotal() != null ? userData.getReceiveSuccessfulTotal() : 0);
        editor.putInt("receiveErrorsTotal", userData.getReceiveErrorsTotal() != null ? userData.getReceiveErrorsTotal() : 0);
        editor.putInt("receiveAttemptsTotal", userData.getReceiveAttemptsTotal() != null ? userData.getReceiveAttemptsTotal() : 0);
        editor.putString("rol", userData.getRol());
        editor.putInt("teamId", userData.getTeamId() != null ? userData.getTeamId() : 0);
        editor.putString("teamName", userData.getTeamName());
        editor.putInt("nPlayers", userData.getnPlayers() != null ? userData.getnPlayers() : 0);
        editor.putString("teamLogoPic", userData.getTeamLogoPic());
        editor.putString("shortName", userData.getShortName());
        editor.putInt("teamTotalGames", userData.getTeamTotalGames() != null ? userData.getTeamTotalGames() : 0);
        editor.putInt("wonGames", userData.getWonGames() != null ? userData.getWonGames() : 0);
        editor.putInt("lostGames", userData.getLostGames() != null ? userData.getLostGames() : 0);
        editor.putInt("totalPoints", userData.getTotalPoints() != null ? userData.getTotalPoints() : 0);
        editor.putInt("idGame", userData.getIdGame() != null ? userData.getIdGame() : 0);

        editor.apply();
    }


}