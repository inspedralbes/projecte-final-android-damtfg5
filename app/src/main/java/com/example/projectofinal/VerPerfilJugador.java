package com.example.projectofinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class VerPerfilJugador extends AppCompatActivity {

    TextView textViewTituloPerfil,textViewNombreApellido,textViewContPart;
    ImageView imageViewFotoPerfil;
    ImageButton imageButtonBVP;
    Button buttonReport;
    Socket socket = SocketManager.getInstance();
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil_jugador);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);

        imageViewFotoPerfil = findViewById(R.id.imageViewFotoPerfil);
        textViewTituloPerfil = findViewById(R.id.textViewTituloPerfil);
        textViewNombreApellido = findViewById(R.id.textViewNombreApellido);
        textViewContPart = findViewById(R.id.textViewContPart);
        TextView textViewtPointsSpike = findViewById(R.id.textViewtPointsSpike);
        TextView textViewtErrorsSpike = findViewById(R.id.textViewtErrorsSpike);
        TextView textViewtAttemptsSpike = findViewById(R.id.textViewtAttemptsSpike);
        TextView textViewtPointsBlock = findViewById(R.id.textViewtPointsBlock);
        TextView textViewtErrorsBlock = findViewById(R.id.textViewtErrorsBlock);
        TextView textViewtAttemptsBlock = findViewById(R.id.textViewtAttemptsBlock);
        TextView textViewtPointsServe = findViewById(R.id.textViewtPointsServe);
        TextView textViewtErrorsServe = findViewById(R.id.textViewtErrorsServe);
        TextView textViewtAttemptsServe = findViewById(R.id.textViewtAttemptsServe);
        TextView textViewtSuccessfulSet = findViewById(R.id.textViewtSuccessfulSet);
        TextView textViewtErrorsSet = findViewById(R.id.textViewtErrorsSet);
        TextView textViewtAttemptsSet = findViewById(R.id.textViewtAttemptsSet);
        TextView textViewtSuccessfulReceive = findViewById(R.id.textViewtSuccessfulReceive);
        TextView textViewtErrorsReceive = findViewById(R.id.textViewtErrorsReceive);
        TextView textViewtAttemptsReceive = findViewById(R.id.textViewtAttemptsReceive);
        imageButtonBVP = findViewById(R.id.imageButtonBVP);
        buttonReport = findViewById(R.id.buttonReport);

        imageButtonBVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCuadroDialogo();
            }
        });

        String URL = "http://192.168.1.17:3001/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Map<String, Integer> userIdMap = new HashMap<>();
        userIdMap.put("id", userId);

        Call<GlobalDataUser> call = apiService.getUserForAndroid(userIdMap);

        call.enqueue(new Callback<GlobalDataUser>() {
            @Override
            public void onResponse(Call<GlobalDataUser> call, Response<GlobalDataUser> response) {
                if (response.isSuccessful()) {
                    GlobalDataUser userData = response.body();

                    Picasso.get().load(userData.getProfilePic()).into(imageViewFotoPerfil);


                    String totalGames = String.valueOf(userData.getTotalGames());
                    String spikePointsTotal = String.valueOf(userData.getSpikePointsTotal());
                    String spikeErrorsTotal = String.valueOf(userData.getSpikeErrorsTotal());
                    String spikeAttemptsTotal = String.valueOf(userData.getSpikeAttemptsTotal());
                    String blockPointsTotal = String.valueOf(userData.getBlockPointsTotal());
                    String blockErrorsTotal = String.valueOf(userData.getBlockErrorsTotal());
                    String blockReboundsTotal = String.valueOf(userData.getBlockReboundsTotal());
                    String servePointsTotal = String.valueOf(userData.getServePointsTotal());
                    String serveErrorsTotal = String.valueOf(userData.getServeErrorsTotal());
                    String serveAttemptsTotal = String.valueOf(userData.getServeAttemptsTotal());

                    String setSuccessfulTotal = String.valueOf(userData.getSetSuccessfulTotal());
                    String setErrorsTotal = String.valueOf(userData.getSetErrorsTotal());
                    String setAttemptsTotal = String.valueOf(userData.getSetAttemptsTotal());

                    String receiveSuccessfulTotal = String.valueOf(userData.getReceiveSuccessfulTotal());
                    String receiveErrorsTotal = String.valueOf(userData.getReceiveErrorsTotal());
                    String receiveAttemptsTotal = String.valueOf(userData.getReceiveAttemptsTotal());

                    textViewTituloPerfil.setText(userData.getFirstname());
                    textViewNombreApellido.setText(userData.getFirstname()+" "+userData.getSurname());
                    textViewContPart.setText(totalGames);
                    textViewtPointsSpike.setText(spikePointsTotal);
                    textViewtErrorsSpike.setText(spikeErrorsTotal);
                    textViewtAttemptsSpike.setText(spikeAttemptsTotal);
                    textViewtPointsBlock.setText(blockPointsTotal);
                    textViewtErrorsBlock.setText(blockErrorsTotal);
                    textViewtAttemptsBlock.setText(blockReboundsTotal);
                    textViewtPointsServe.setText(servePointsTotal);
                    textViewtErrorsServe.setText(serveErrorsTotal);
                    textViewtAttemptsServe.setText(serveAttemptsTotal);

                    textViewtSuccessfulSet.setText(setSuccessfulTotal);
                    textViewtErrorsSet.setText(setErrorsTotal);
                    textViewtAttemptsSet.setText(setAttemptsTotal);

                    textViewtSuccessfulReceive.setText(receiveSuccessfulTotal);
                    textViewtErrorsReceive.setText(receiveErrorsTotal);
                    textViewtAttemptsReceive.setText(receiveAttemptsTotal);


                } else {
                    Log.e("VerPerfilJugador", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GlobalDataUser> call, Throwable t) {
                Log.e("VerPerfilJugador", "Failure: " + t.getMessage());
            }
        });
    }
    private void mostrarCuadroDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reportar Usuario");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String reporte = input.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int userIdReport = sharedPreferences.getInt("userId", 0);
                Log.d("IDUSERS REPORTED", "onClick: " + userIdReport);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String fechaActual = sdf.format(new Date());

                JSONObject reportData = new JSONObject();
                try {
                    reportData.put("idUserReport", userIdReport);
                    reportData.put("idUserReported", userId);
                    reportData.put("date", fechaActual);
                    reportData.put("msg", reporte);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("reportUser",reportData);
                Toast.makeText(VerPerfilJugador.this, "Reporte enviado: " + reporte, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}