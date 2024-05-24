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
import android.util.Log;
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
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    String municipi;

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
        municipi = getIntent().getStringExtra("municipi");
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

        //checkAvailability(selectedDay, municipi);


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

    private void addHourCard(GridLayout gridLayout, String timeSlot, boolean available) {
        CardView cardView = new CardView(this);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.setMargins(20, 20, 20, 20);
        cardView.setLayoutParams(params);
        cardView.setRadius(16);
        cardView.setCardElevation(4);

        // Cambiar el fondo y la clicabilidad de la tarjeta según la disponibilidad
        if (!available) {
            cardView.setBackground(ContextCompat.getDrawable(this, R.drawable.border_orange));
            cardView.setClickable(true);
        } else {
            cardView.setBackground(ContextCompat.getDrawable(this, R.drawable.disabled_background));
            cardView.setClickable(false);
        }

        TextView textViewHour = new TextView(this);
        textViewHour.setText(timeSlot);
        textViewHour.setTextColor(Color.BLACK);
        textViewHour.setPadding(50, 50, 50, 50);
        cardView.addView(textViewHour);

        // Manejar el clic en la tarjeta
        if (!available) {
            cardView.setOnClickListener(v -> {
                if (selectedCardView != null) {
                    selectedCardView.setBackground(ContextCompat.getDrawable(this, R.drawable.border_orange));
                    ((TextView) selectedCardView.getChildAt(0)).setTextColor(Color.BLACK);
                }
                cardView.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_orange_cardview));
                textViewHour.setTextColor(Color.WHITE);
                selectedCardView = cardView;
                selectedHour = timeSlot;
            });
        }

        gridLayout.addView(cardView);
    }

    private void checkAvailability(String selectedDay, String municipi) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        DayLocationRequest dayLocationRequest = new DayLocationRequest(selectedDay,municipi);
        Call<List<MatchTime>> call = apiService.getHoursByDay(dayLocationRequest);
        call.enqueue(new Callback<List<MatchTime>>() {
            @Override
            public void onResponse(Call<List<MatchTime>> call, Response<List<MatchTime>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MatchTime> availableHours = response.body();
                    updateHourCards(availableHours);
                    switchShowAvailableHours.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            // Si el Switch está activado
                            textViewMostrarHoras.setText("Mostrando solo horas disponibles");
                            updateHourCards(availableHours, false); // Mostrar solo las horas no disponibles
                        } else {
                            // Si el Switch está desactivado
                            textViewMostrarHoras.setText("Mostrando todas las horas");
                            // Aquí puedes agregar lógica para mostrar todas las horas
                            updateHourCards(availableHours, true); // Mostrar todas las horas
                        }
                    });
                } else {
                    Toast.makeText(MarkerInfoActivity.this, "Error al verificar la disponibilidad", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MatchTime>> call, Throwable t) {
                // Manejar el error de conexión
                Toast.makeText(MarkerInfoActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        recyclerView.smoothScrollToPosition(position + 3);
        DayItem selectedDayItem = dayAdapter.getItem(position);
        selectedDay = selectedDayItem.getDate();
        checkAvailability(selectedDay, municipi);
    }

    private void updateHourCards(List<MatchTime> availableHours) {
        gridLayout.removeAllViews();


        List<String> timeSlots1 = new ArrayList<>();
        timeSlots1.add("9:00 - 11:30");
        timeSlots1.add("12:00 - 14:30");

        List<String> timeSlots2 = new ArrayList<>();
        timeSlots2.add("15:00 - 17:30");
        timeSlots2.add("18:00 - 20:30");

        for (String timeSlot : timeSlots1) {
            boolean available = isHourAvailable(availableHours, timeSlot);
            addHourCard(gridLayout, timeSlot, available);
        }

        for (String timeSlot : timeSlots2) {
            boolean available = isHourAvailable(availableHours, timeSlot);
            addHourCard(gridLayout, timeSlot, available);
        }
    }

    private void updateHourCards(List<MatchTime> availableHours, boolean showUnavailable) {
        gridLayout.removeAllViews();

        List<String> timeSlots1 = new ArrayList<>();
        timeSlots1.add("9:00 - 11:30");
        timeSlots1.add("12:00 - 14:30");

        List<String> timeSlots2 = new ArrayList<>();
        timeSlots2.add("15:00 - 17:30");
        timeSlots2.add("18:00 - 20:30");

        for (String timeSlot : timeSlots1) {
            boolean available = isHourAvailable(availableHours, timeSlot);
            if (showUnavailable || !available) {
                addHourCard(gridLayout, timeSlot, available);
            }
        }

        for (String timeSlot : timeSlots2) {
            boolean available = isHourAvailable(availableHours, timeSlot);
            if (showUnavailable || !available) {
                addHourCard(gridLayout, timeSlot, available);
            }
        }
    }


    private boolean isHourAvailable(List<MatchTime> availableHours, String timeSlot) {
        for (MatchTime matchTime : availableHours) {
            if (matchTime.getMatchTime().equals(timeSlot)) {
                return true;
            }
        }
        return false;
    }


}
