package com.example.projectetf2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import com.example.projectofinal.DatePickerFragment;
import com.example.projectofinal.R;

import java.util.Locale;

public class EditarPerfil extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        Locale locale = new Locale("es");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        TextView textViewCambiosGuardados = findViewById(R.id.textViewCambiosGuardados);
        ConstraintLayout firstLayout = findViewById(R.id.firstLayout);
        ConstraintLayout secondLayout = findViewById(R.id.secondLayout);
        ConstraintLayout fourLayout = findViewById(R.id.fourLayout);
        EditText editTextNC = findViewById(R.id.editTextNC);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        ImageButton imageButtonClearNC = findViewById(R.id.imageButtonClear1);
        ImageButton imageButtonClearEmail = findViewById(R.id.imageButtonClear2);
        ImageButton imageButtonClearPhone = findViewById(R.id.imageButtonClear3);
        TextView textViewfecha = findViewById(R.id.textViewFecha);
        // Habilitar el TextView y cambiar su color a naranja cuando se detecten cambios
        textViewCambiosGuardados.setEnabled(true);
        textViewCambiosGuardados.setTextColor(getResources().getColor(R.color.naranja1));
        // Deshabilitar el TextView y cambiar su color a gris cuando los cambios se guarden
        textViewCambiosGuardados.setEnabled(false);
        textViewCambiosGuardados.setTextColor(getResources().getColor(R.color.gris));


        setupEditTextFocusChange(editTextNC, firstLayout, imageButtonClearNC);
        setupEditTextFocusChange(editTextEmail, secondLayout, imageButtonClearEmail);
        setupEditTextFocusChange(editTextPhone, fourLayout, imageButtonClearPhone);

        setupImageButtonClear(editTextNC, imageButtonClearNC);
        setupImageButtonClear(editTextEmail, imageButtonClearEmail);
        setupImageButtonClear(editTextPhone, imageButtonClearPhone);


        textViewfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
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

    private void showDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        datePickerFragment.show(fragmentManager, "DatePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String dateMessage="";
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        dateMessage = "Fecha de Nacimiento \n"+(day_string + "/"
                + month_string + "/" + year_string);
        TextView selectedDateTextView = findViewById(R.id.textViewFecha);
        selectedDateTextView.setText(getString(R.string.selected_date, dateMessage));
    }

}