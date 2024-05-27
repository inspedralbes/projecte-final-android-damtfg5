package com.example.projectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.View;
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

import com.example.projectofinal.DatePickerFragment;
import com.example.projectofinal.R;
import com.squareup.picasso.Picasso;

import com.bumptech.glide.Glide;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditarPerfil extends AppCompatActivity implements GenderPickerBottomSheet.GenderPickerListener {
    private TextView textViewGendrePick,textViewGendre;
    private boolean cambiosPendientes = false;
    private String URL = "http://192.168.1.17:3001/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        Locale locale = new Locale("es");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = sharedPreferences.getInt("userId", -1);
        String profilePic = sharedPreferences.getString("profilePic", "");
        String userName = sharedPreferences.getString("userName", "");
        String userEmail = sharedPreferences.getString("userEmail", "");
        int userPhone = sharedPreferences.getInt("userPhone", 0);
        String phone = String.valueOf(userPhone);
        String birthDate = sharedPreferences.getString("birthDate", "");
        String birthDateFirstTenChars = "";
        if (birthDate != null && birthDate.length() >= 10) {
            birthDateFirstTenChars = birthDate.substring(0, 10);
        } else {
            birthDateFirstTenChars = birthDate;
        }
        String country = sharedPreferences.getString("country", "");
        String gender = sharedPreferences.getString("gender", "");
        String bio = sharedPreferences.getString("bio", "");

        TextView textViewCambiosGuardados = findViewById(R.id.textViewCambiosGuardados);
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
        // Habilitar el TextView y cambiar su color a naranja cuando se detecten cambios
        textViewCambiosGuardados.setEnabled(true);
        textViewCambiosGuardados.setTextColor(getResources().getColor(R.color.naranja1));
        // Deshabilitar el TextView y cambiar su color a gris cuando los cambios se guarden
        textViewCambiosGuardados.setEnabled(false);
        textViewCambiosGuardados.setTextColor(getResources().getColor(R.color.negro1));


        editTextNC.setText(userName);
        editTextEmail.setText(userEmail);
        editTextPhone.setText(phone);
        textViewfecha.setText("Fecha de Nacimiento \n"+birthDateFirstTenChars);
        editTextPais.setText(country);
        editTextDescription.setText(bio);
        if(gender.isEmpty()){
            textViewGendrePick.setText("Sin definir");
        }
        else{
            textViewGendrePick.setText(gender);
        }

        if (!profilePic.isEmpty()) {
            Glide.with(this).load(profilePic).into(imageViewFotoPerfil);
        } else {
            // Si no hay una imagen de perfil, puedes cargar una imagen de recurso predeterminada
            Glide.with(this).load(R.drawable.perfil).into(imageViewFotoPerfil);
        }

        /*if (usuario.getProfilePic() != null && !usuario.getProfilePic().isEmpty()) {
            Picasso.get().load(usuario.getProfilePic()).into(holder.imageViewUsuario);
        } else {
            holder.imageViewUsuario.setImageResource(R.drawable.perfil);
        }*/

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

        if(cambiosPendientes){
            textViewCambiosGuardados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    int userId = sharedPreferences.getInt("userId", -1);
                    // Obtener los nuevos valores de los EditText y otros componentes de la interfaz de usuario
                    String nuevoNombre = editTextNC.getText().toString();
                    String nuevoEmail = editTextEmail.getText().toString();
                    int nuevoTelefono = Integer.parseInt(editTextPhone.getText().toString());
                    String nuevoPais = editTextPais.getText().toString();
                    String nuevaDescripcion = editTextDescription.getText().toString();
                    String nuevaFechaNacimiento = textViewfecha.getText().toString();

                    // Crear un objeto con los nuevos datos del usuario
                    UpdateUserRequest updateUserRequest = new UpdateUserRequest(nuevoEmail, nuevoNombre, nuevoTelefono, nuevaFechaNacimiento, nuevoPais, gender, nuevaDescripcion, userId);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiService apiService = retrofit.create(ApiService.class);
                    // Realizar la llamada a la API para actualizar el usuario con los nuevos datos
                    Call<Void> call= apiService.updateUserForAndroid(updateUserRequest);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            // Manejar la respuesta de la API aquí
                            if (response.isSuccessful()) {
                                // Actualizar cambiosPendientes a false después de que se hayan guardado los cambios
                                cambiosPendientes = false;
                                // Actualizar la disponibilidad del botón de guardar cambios
                                actualizarDisponibilidadBotonGuardarCambios();
                                // Mostrar mensaje de éxito o realizar otras acciones necesarias
                                Toast.makeText(EditarPerfil.this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                // Mostrar mensaje de error si la solicitud no fue exitosa
                                Toast.makeText(EditarPerfil.this, "Error al guardar los cambios", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // Manejar el fallo en la llamada a la API
                            Toast.makeText(EditarPerfil.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }


        cardViewPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarPerfil.this,EditarPreferencias.class);
                startActivity(intent);
            }
        });
    }

    private void setupEditTextFocusChange(EditText editText, ConstraintLayout layout,ImageButton button) {
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

                cambiosPendientes = true;
                // Luego, actualiza la disponibilidad del botón de guardar cambios
                actualizarDisponibilidadBotonGuardarCambios();
            }
        });
    }

    private void setupImageButtonClear(EditText editText, ImageButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar el texto del EditText cuando se hace clic en el ImageButton
                editText.setText("");

                cambiosPendientes = true;
                // Luego, actualiza la disponibilidad del botón de guardar cambios
                actualizarDisponibilidadBotonGuardarCambios();
            }
        });
    }

    private void actualizarDisponibilidadBotonGuardarCambios() {
        TextView textViewGuardarCambios = findViewById(R.id.textViewCambiosGuardados);
        // Si hay cambios pendientes, habilitar el botón y cambiar su color a naranja
        if (cambiosPendientes) {
            textViewGuardarCambios.setEnabled(true);
            textViewGuardarCambios.setTextColor(getResources().getColor(R.color.naranja1));
        } else { // Si no hay cambios pendientes, deshabilitar el botón y cambiar su color a gris
            textViewGuardarCambios.setEnabled(false);
            textViewGuardarCambios.setTextColor(getResources().getColor(R.color.negro1));
        }
    }

    private void showDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        datePickerFragment.show(fragmentManager, "DatePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String dateMessage="";
        String month_string = String.format("%02d", month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        dateMessage = "Fecha de Nacimiento \n"+(year_string + "-"
                + month_string + "-" + day_string);
        TextView selectedDateTextView = findViewById(R.id.textViewFecha);
        selectedDateTextView.setText(getString(R.string.selected_date, dateMessage));
    }

    @Override
    public void onGenderSelected(String gender) {
        textViewGendrePick.setText(gender);
    }



}