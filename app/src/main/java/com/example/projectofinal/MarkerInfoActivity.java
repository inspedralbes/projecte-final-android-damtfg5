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
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MarkerInfoActivity extends AppCompatActivity implements DayAdapter.OnItemClickListener {
    private TextView textViewTitle, textViewMostrarHoras;
    private ImageButton imageButtonBackMarkerInfo;
    private Button reservarPista;
    private RecyclerView recyclerView;
    private DayAdapter dayAdapter;
    private Switch switchShowAvailableHours;
    GridLayout gridLayout;
    private CardView selectedCardView = null;
    private Socket mSocket;
    int teamId,userId;
    private String URL = "http://192.168.206.176:3001/";
    private String selectedDay = "";
    private String selectedHour = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_info);

        try {
            mSocket = IO.socket(URL);
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        teamId = sharedPreferences.getInt("teamId", -1);
        userId = sharedPreferences.getInt("userId", -1);

        textViewTitle = findViewById(R.id.textViewtitleRP);
        imageButtonBackMarkerInfo = findViewById(R.id.imageButtonBackMarkerInfo);
        switchShowAvailableHours = findViewById(R.id.switchShowAvailableHours);
        textViewMostrarHoras = findViewById(R.id.textViewMostrarHoras);
        gridLayout = findViewById(R.id.gridLayout);
        reservarPista = findViewById(R.id.reservarPista);

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

        reservarPista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDay.isEmpty() || selectedHour.isEmpty()) {
                    Toast.makeText(MarkerInfoActivity.this, "Seleccione un día y una hora", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear el objeto de reserva
                JSONObject bookingObject = new JSONObject();
                try {
                    bookingObject.put("teamId", teamId);
                    bookingObject.put("date", selectedDay);
                    bookingObject.put("time", selectedHour);
                    bookingObject.put("location", municipi);
                    bookingObject.put("userId",userId);

                    // Enviar el objeto de reserva al servidor
                    mSocket.emit("createMatch", bookingObject);
                    Toast.makeText(MarkerInfoActivity.this, "Reserva enviada", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
        cardView.addView(textViewHour);

        cardView.setOnClickListener(v -> {
            if (selectedCardView != null) {
                selectedCardView.setBackground(ContextCompat.getDrawable(this, R.drawable.border_orange));
                ((TextView) selectedCardView.getChildAt(0)).setTextColor(Color.BLACK);
            }
            cardView.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_orange_cardview));
            textViewHour.setTextColor(Color.WHITE);
            selectedCardView = cardView;
            selectedHour = hour;
        });

        gridLayout.addView(cardView);
    }

    @Override
    public void onItemClick(int position) {
        recyclerView.smoothScrollToPosition(position + 3);
        DayItem selectedDayItem = dayAdapter.getItem(position);
        selectedDay = selectedDayItem.getDate();
        updateScreen();
    }

    private void updateScreen() {
        List<String> hoursList = HourGenerator.generateHoursFromNowUntil22();
        gridLayout.removeAllViews();
        for (String hour : hoursList) {
            addHourCard(gridLayout, hour);
        }
    }
}
