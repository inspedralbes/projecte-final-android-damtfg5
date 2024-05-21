package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
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
    private CardView selectedCardView = null;
    int teamId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_info);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        teamId = sharedPreferences.getInt("teamId", -1);

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
        String address = getIntent().getStringExtra("address");

        // Muestra los datos en los TextView
        textViewTitle.setText(address);

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

        List<String> hoursList = HourGenerator.generateHoursFromNowUntil22();
        for (String hour : hoursList) {
            addHourCard(gridLayout, hour);
        }
    }
    private void addHourCard(GridLayout gridLayout, String hour) {
        CardView cardView = new CardView(this);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = GridLayout.LayoutParams.WRAP_CONTENT;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.setMargins(20, 20, 20, 20);
        cardView.setLayoutParams(params);
        cardView.setRadius(16);
        cardView.setCardElevation(4);
        cardView.setBackground(ContextCompat.getDrawable(this, R.drawable.border_orange));

        TextView textViewHour = new TextView(this);
        textViewHour.setText(hour);
        textViewHour.setTextColor(Color.BLACK);
        textViewHour.setPadding(50, 50, 50, 50);
        cardView.addView(textViewHour); // Agrega el TextView al CardView

        cardView.setOnClickListener(v -> {
            if (selectedCardView != null) {
                selectedCardView.setBackground(ContextCompat.getDrawable(this, R.drawable.border_orange));
                ((TextView) selectedCardView.getChildAt(0)).setTextColor(Color.BLACK);
            }
            cardView.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_orange_cardview));
            textViewHour.setTextColor(Color.WHITE);
            selectedCardView = cardView;

            // Puedes agregar la lógica para marcar la hora como reservada aquí
        });

        gridLayout.addView(cardView); // Agrega el CardView al GridLayout
    }

    public void onItemClick(int position) {
        recyclerView.smoothScrollToPosition(position+3);
        updateScreen();
    }
    private void updateScreen() {
        List<String> hoursList = HourGenerator.generateHoursFromNowUntil22();
        gridLayout.removeAllViews(); // Borra todos los CardViews existentes
        for (String hour : hoursList) {
            addHourCard(gridLayout, hour); // Agrega los nuevos CardViews
        }
    }
}