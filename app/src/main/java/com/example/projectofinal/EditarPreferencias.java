package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarPreferencias extends AppCompatActivity {

    private Button buttonSoloPlayer, buttonTeamPlayer, buttonCaptain;
    private Button buttonDerecha, buttonIzquierda, buttonAmbas;
    private AutoCompleteTextView editTextLocalitation;
    private EditText editTextPos; // Declarar editTextPos a nivel de clase
    private EditText editTextHeight;
    private EditText editTextVJ;
    private String URL = "http://192.168.1.17:3001/";
    private List<String> municipiNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_preferencias);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String position = sharedPreferences.getString("position", "");
        int height = sharedPreferences.getInt("height", 0);
        String heightString = String.valueOf(height);
        int verticalJump = sharedPreferences.getInt("verticalJump", 0);
        String verticalJumpString = String.valueOf(verticalJump);
        String rol = sharedPreferences.getString("rol", "");
        String dominantHand = sharedPreferences.getString("dominantHand", "");
        String location = sharedPreferences.getString("location", "");

        ConstraintLayout firstLayout = findViewById(R.id.firstLayout);
        ConstraintLayout secondLayout = findViewById(R.id.secondLayout);
        ConstraintLayout thirdLayout = findViewById(R.id.thirdLayout);
        ConstraintLayout sixLayout = findViewById(R.id.sixLayout);
        editTextPos = findViewById(R.id.editTextPos);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextVJ = findViewById(R.id.textViewHand);
        ImageButton imageButtonClearPos = findViewById(R.id.imageButtonClearPos);
        ImageButton imageButtonClearHeight = findViewById(R.id.imageButtonClearHeight);
        ImageButton imageButtonClearVJ = findViewById(R.id.imageButtonClearVJ);
        ImageButton imageButtonClearLocalitation = findViewById(R.id.imageButtonClearLocalitation);
        ImageButton imageButtonBEP = findViewById(R.id.imageButtonBEP);
        Button textViewGuardarCambios = findViewById(R.id.textViewCambiosGuardados);
        editTextLocalitation = findViewById(R.id.editTextLocalitation);
        municipiNames = new ArrayList<>();

        editTextPos.setText(position);
        editTextHeight.setText(heightString);
        editTextVJ.setText(verticalJumpString);
        editTextLocalitation.setText(location);

        setupEditTextFocusChange(editTextPos, firstLayout, imageButtonClearPos);
        setupImageButtonClear(editTextPos, imageButtonClearPos);
        setupEditTextFocusChange(editTextHeight, secondLayout, imageButtonClearHeight);
        setupImageButtonClear(editTextHeight, imageButtonClearHeight);
        setupEditTextFocusChange(editTextVJ, thirdLayout, imageButtonClearVJ);
        setupImageButtonClear(editTextVJ, imageButtonClearVJ);
        setupEditTextFocusChange(editTextLocalitation, sixLayout, imageButtonClearLocalitation);
        setupImageButtonClear(editTextLocalitation, imageButtonClearLocalitation);

        buttonSoloPlayer = findViewById(R.id.buttonSoloPlayer);
        buttonTeamPlayer = findViewById(R.id.buttonTeamPlayer);
        buttonCaptain = findViewById(R.id.buttonCaptain);

        buttonDerecha = findViewById(R.id.buttonDerecha);
        buttonIzquierda = findViewById(R.id.buttonIzquierda);
        buttonAmbas = findViewById(R.id.buttonAmbas);

        // Seleccionar el botón correspondiente al rol
        selectRoleBasedOnPreferences(rol);
        // Seleccionar el botón correspondiente a la mano dominante
        selectHandBasedOnPreferences(dominantHand);

        // Configurar el OnClickListener para textViewCambiosGuardados
        textViewGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });

        imageButtonBEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<String>> call = apiService.getMunicipis();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> municipis = response.body();
                    if (municipis != null) {
                        setupAutoCompleteTextView(municipis);
                    }
                } else {
                    Toast.makeText(EditarPreferencias.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(EditarPreferencias.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarCambios() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String newPosition = editTextPos.getText().toString();
        int newHeight = Integer.parseInt(editTextHeight.getText().toString());
        String newDominantHand = editTextVJ.getText().toString();
        int newVerticalJump = Integer.parseInt(editTextVJ.getText().toString());
        String newLocation = editTextLocalitation.getText().toString();

        editor.putString("position", newPosition);
        editor.putInt("height", newHeight);
        editor.putString("dominantHand", newDominantHand);
        editor.putInt("verticalJump", newVerticalJump);
        editor.putString("location", newLocation);
        editor.apply();

        int userId = sharedPreferences.getInt("userId", -1);
        Toast.makeText(EditarPreferencias.this, "Guardando cambios...", Toast.LENGTH_SHORT).show();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("position", newPosition);
        requestBody.put("height", newHeight);
        requestBody.put("dominantHand", newDominantHand);
        requestBody.put("vj", newVerticalJump);
        requestBody.put("location", newLocation);
        requestBody.put("userId", userId);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<Void> call = apiService.updateUserPreferencesForAndroid(requestBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarPreferencias.this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
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

    public void selectRole(View view) {
        buttonSoloPlayer.setSelected(false);
        buttonTeamPlayer.setSelected(false);
        buttonCaptain.setSelected(false);

        // Marcar el botón seleccionado
        view.setSelected(true);

        String buttonId = getResources().getResourceEntryName(view.getId());

        // Realizar acciones basadas en el nombre del recurso del botón
        switch (buttonId) {
            case "buttonSoloPlayer":
                // Lógica para el rol de soloPlayer
                break;
            case "buttonTeamPlayer":
                // Lógica para el rol de teamPlayer
                break;
            case "buttonCaptain":
                // Lógica para el rol de captain
                break;
        }
    }

    public void selectHand(View view) {
        buttonDerecha.setSelected(false);
        buttonIzquierda.setSelected(false);
        buttonAmbas.setSelected(false);

        // Marcar el botón seleccionado
        view.setSelected(true);

        String buttonId = getResources().getResourceEntryName(view.getId());

        // Realizar acciones basadas en el nombre del recurso del botón
        switch (buttonId) {
            case "buttonDerecha":
                // Lógica para el rol de soloPlayer
                break;
            case "buttonIzquierda":
                // Lógica para el rol de teamPlayer
                break;
            case "buttonAmbas":
                // Lógica para el rol de captain
                break;
        }
    }

    private void selectRoleBasedOnPreferences(String rol) {
        switch (rol) {
            case "soloPlayer":
                buttonSoloPlayer.setSelected(true);
                break;
            case "teamPlayer":
                buttonTeamPlayer.setSelected(true);
                break;
            case "captain":
                buttonCaptain.setSelected(true);
                break;
        }
    }

    private void selectHandBasedOnPreferences(String dominantHand) {
        switch (dominantHand) {
            case "Derecha":
                buttonDerecha.setSelected(true);
                break;
            case "Izquierda":
                buttonIzquierda.setSelected(true);
                break;
            case "Ambas":
                buttonAmbas.setSelected(true);
                break;
        }
    }

    private void setupAutoCompleteTextView(List<String> municipis) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, municipis);
        editTextLocalitation.setAdapter(adapter);
        editTextLocalitation.setThreshold(1);
    }
}
