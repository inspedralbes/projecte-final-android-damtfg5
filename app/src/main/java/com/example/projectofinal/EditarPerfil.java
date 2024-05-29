package com.example.projectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarPerfil extends AppCompatActivity implements GenderPickerBottomSheet.GenderPickerListener {
    private TextView textViewGendrePick, textViewGendre;
    private String URL = "http://192.168.1.17:3001/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = sharedPreferences.getInt("userId", -1);
        String profilePic = sharedPreferences.getString("profilePic", "");
        String userName = sharedPreferences.getString("userName", "");
        String userEmail = sharedPreferences.getString("userEmail", "");
        int userPhone = sharedPreferences.getInt("userPhone", 0);
        String phone = String.valueOf(userPhone);
        String birthDate = sharedPreferences.getString("birthDate", "");
        String birthDateFirstTenChars = birthDate != null && birthDate.length() >= 10 ? birthDate.substring(0, 10) : birthDate;
        String country = sharedPreferences.getString("country", "");
        String gender = sharedPreferences.getString("gender", "");
        String bio = sharedPreferences.getString("bio", "");

        Button textViewCambiosGuardados = findViewById(R.id.textViewCambiosGuardados);
        ConstraintLayout firstLayout = findViewById(R.id.firstLayout);
        ConstraintLayout secondLayout = findViewById(R.id.secondLayout);
        ConstraintLayout fourLayout = findViewById(R.id.fourLayout);
        ConstraintLayout midLayout = findViewById(R.id.midLayout);
        ConstraintLayout sevenLayout = findViewById(R.id.sevenLayout);
        CardView cardViewPrefs = findViewById(R.id.cardViewPrefs);
        EditText editTextNC = findViewById(R.id.editTextNC);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        EditText editTextPais = findViewById(R.id.editTextPais);
        EditText editTextDescription = findViewById(R.id.editTextDescription);
        ImageView imageViewFotoPerfil = findViewById(R.id.imageViewUsuario);
        ImageButton imageButtonClearNC = findViewById(R.id.imageButtonClear1);
        ImageButton imageButtonClearEmail = findViewById(R.id.imageButtonClear2);
        ImageButton imageButtonClearPhone = findViewById(R.id.imageButtonClear3);
        ImageButton imageButtonClearPais = findViewById(R.id.imageButtonClearPais);
        ImageButton imageButtonBackPerfil = findViewById(R.id.imageButtonBP);
        ImageButton imageButtonCleardesc = findViewById(R.id.imageButtonCleardesc);
        TextView textViewfecha = findViewById(R.id.textViewFecha);
        textViewGendre = findViewById(R.id.textViewGendre);
        textViewGendrePick = findViewById(R.id.textViewGendrePick);
        TextView logout = findViewById(R.id.textViewCerrarSesion);

        textViewCambiosGuardados.setEnabled(true);
        textViewCambiosGuardados.setTextColor(getResources().getColor(R.color.naranja1));

        editTextNC.setText(userName);
        editTextEmail.setText(userEmail);
        editTextPhone.setText(phone);
        textViewfecha.setText(birthDateFirstTenChars);
        editTextPais.setText(country);
        editTextDescription.setText(bio);
        textViewGendrePick.setText(gender.isEmpty() ? "Sin definir" : gender);

        if (!profilePic.isEmpty()) {
            Picasso.get()
                    .load(profilePic)
                    .resize(500, 500)
                    .centerCrop()
                    .config(Bitmap.Config.RGB_565)
                    .into(imageViewFotoPerfil);
        } else {
            Picasso.get()
                    .load(R.drawable.perfil)
                    .resize(500, 500)
                    .centerCrop()
                    .config(Bitmap.Config.RGB_565)
                    .into(imageViewFotoPerfil);
        }

        setupEditTextFocusChange(editTextNC, firstLayout, imageButtonClearNC);
        setupEditTextFocusChange(editTextEmail, secondLayout, imageButtonClearEmail);
        setupEditTextFocusChange(editTextPhone, fourLayout, imageButtonClearPhone);
        setupEditTextFocusChange(editTextDescription, sevenLayout, imageButtonCleardesc);
        setupEditTextFocusChange(editTextPais, midLayout, imageButtonClearPais);

        setupImageButtonClear(editTextNC, imageButtonClearNC);
        setupImageButtonClear(editTextEmail, imageButtonClearEmail);
        setupImageButtonClear(editTextPhone, imageButtonClearPhone);
        setupImageButtonClear(editTextDescription, imageButtonCleardesc);
        setupImageButtonClear(editTextPais, imageButtonClearPais);

        textViewfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        imageButtonBackPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewGendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderPickerBottomSheet bottomSheet = new GenderPickerBottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "GenderPickerBottomSheet");
            }
        });

        textViewCambiosGuardados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: Guardar cambios");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Obtener los nuevos valores de los EditText y otros componentes de la interfaz de usuario
                String nuevoNombre = editTextNC.getText().toString();
                String nuevoEmail = editTextEmail.getText().toString();
                int nuevoTelefono = Integer.parseInt(editTextPhone.getText().toString());
                String nuevoPais = editTextPais.getText().toString();
                String nuevaDescripcion = editTextDescription.getText().toString();
                String nuevaFechaNacimiento = textViewfecha.getText().toString();
                String nuevoGenero = textViewGendrePick.getText().toString();

                // Actualizar los valores en SharedPreferences
                editor.putString("userName", nuevoNombre);
                editor.putString("userEmail", nuevoEmail);
                editor.putInt("userPhone", nuevoTelefono);
                editor.putString("country", nuevoPais);
                editor.putString("bio", nuevaDescripcion);
                editor.putString("birthDate", nuevaFechaNacimiento);
                editor.putString("gender", nuevoGenero);
                editor.apply();

                // Crear un objeto con los nuevos datos del usuario
                UpdateUserRequest updateUserRequest = new UpdateUserRequest(nuevoEmail, nuevoNombre, nuevoTelefono, nuevaFechaNacimiento, nuevoPais, nuevoGenero, nuevaDescripcion, userId);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService = retrofit.create(ApiService.class);
                // Realizar la llamada a la API para actualizar el usuario con los nuevos datos
                Call<Void> call = apiService.updateUserForAndroid(updateUserRequest);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EditarPerfil.this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                });
            }
        });

        cardViewPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarPerfil.this, EditarPreferencias.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(EditarPerfil.this,MainActivity.class);
                startActivity(logout);
            }
        });
    }

    private void setupEditTextFocusChange(EditText editText, ConstraintLayout layout, ImageButton button) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                layout.setBackgroundResource(R.drawable.layout_border_orange);
                button.setVisibility(View.VISIBLE);
            } else {
                layout.setBackgroundResource(R.drawable.layout_border);
                button.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setupImageButtonClear(EditText editText, ImageButton button) {
        button.setOnClickListener(v -> editText.setText(""));
    }

    private void showDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        datePickerFragment.show(fragmentManager, "DatePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String dateMessage = "Fecha de Nacimiento \n" + year + "-" + String.format("%02d", month + 1) + "-" + day;
        TextView selectedDateTextView = findViewById(R.id.textViewFecha);
        selectedDateTextView.setText(getString(R.string.selected_date, dateMessage));
    }

    @Override
    public void onGenderSelected(String gender) {
        textViewGendrePick.setText(gender);
    }
}
