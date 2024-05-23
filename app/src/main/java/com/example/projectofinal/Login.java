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
import android.widget.Toast;

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
                        saveUserData(loginResponse.getUserData());
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

    private void saveUserData(Usuario userData) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userData.getId());
        editor.putString("userName", userData.getFirstname() + " " + userData.getSurname());
        editor.putString("userProfilePic", userData.getProfilePic());
        editor.apply();
    }

}