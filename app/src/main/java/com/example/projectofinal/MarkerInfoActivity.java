package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class MarkerInfoActivity extends AppCompatActivity implements DayAdapter.OnItemClickListener {
    private TextView textViewTitle,textViewMostrarHoras;
    private ImageButton imageButtonBackMarkerInfo;
    private RecyclerView recyclerView;
    private DayAdapter dayAdapter;
    private Switch switchShowAvailableHours;
    GridLayout gridLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_info);

        textViewTitle = findViewById(R.id.textViewtitleRP);
        imageButtonBackMarkerInfo = findViewById(R.id.imageButtonBackMarkerInfo);
        switchShowAvailableHours = findViewById(R.id.switchShowAvailableHours);
        textViewMostrarHoras = findViewById(R.id.textViewMostrarHoras);
        gridLayout = findViewById(R.id.gridLayout);

        recyclerView = findViewById(R.id.recyclerViewDays);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        List<DayItem> dayList = DayGenerator.generateDays();
        dayAdapter = new DayAdapter(dayList);
        recyclerView.setAdapter(dayAdapter);

        // Obtén los datos del Intent
        String title = getIntent().getStringExtra("title");
        String municipi = getIntent().getStringExtra("municipi");
        String paviment = getIntent().getStringExtra("paviment");

        // Muestra los datos en los TextView
        textViewTitle.setText(title+" "+municipi);

        dayAdapter.setOnItemClickListener(this);
        imageButtonBackMarkerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchShowAvailableHours.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Si el Switch está activado
                textViewMostrarHoras.setText("Mostrando solo horas disponibles");
                // Aquí puedes agregar lógica para mostrar solo las horas disponibles
            } else {
                // Si el Switch está desactivado
                textViewMostrarHoras.setText("Mostrando todas las horas");
                // Aquí puedes agregar lógica para mostrar todas las horas
            }
        });

        for (int hour = 0; hour <= 22; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                String formattedHour = String.format("%02d:%02d", hour, minute);
                addHourCard(gridLayout, formattedHour);
            }
        }
    }
    private void addHourCard(GridLayout gridLayout, String hour) {
        CardView cardView = new CardView(this);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = GridLayout.LayoutParams.WRAP_CONTENT;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        cardView.setLayoutParams(params);
        cardView.setRadius(8); // Redondea las esquinas del CardView
        cardView.setCardElevation(4); // Elevación del CardView

        TextView textViewHour = new TextView(this);
        textViewHour.setText(hour);
        textViewHour.setPadding(16, 16, 16, 16);
        cardView.addView(textViewHour); // Agrega el TextView al CardView

        gridLayout.addView(cardView); // Agrega el CardView al GridLayout
    }

    public void onItemClick(int position) {
        recyclerView.smoothScrollToPosition(position+3);

    }
}