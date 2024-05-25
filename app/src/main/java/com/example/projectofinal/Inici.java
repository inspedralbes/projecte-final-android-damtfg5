package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.projectofinal.databinding.ActivityIniciBinding;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;

public class Inici extends AppCompatActivity {
    ActivityIniciBinding binding;
    CardView cardView;
    ImageButton imageButton;
    boolean isCardOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIniciBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FragmentJugar());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        int userId = sharedPreferences.getInt("userId", -1);
        String userName = sharedPreferences.getString("userName", "");
        String userEmail = sharedPreferences.getString("userEmail", "");
        int userPhone = sharedPreferences.getInt("userPhone", 0);
        int totalGames = sharedPreferences.getInt("totalGames", 0);
        String dominantHand = sharedPreferences.getString("dominantHand", "");
        String position = sharedPreferences.getString("position", "");
        int height = sharedPreferences.getInt("height", 0);
        int verticalJump = sharedPreferences.getInt("verticalJump", 0);
        String location = sharedPreferences.getString("location", "");
        String gender = sharedPreferences.getString("gender", "");
        String birthDate = sharedPreferences.getString("birthDate", "");
        String bio = sharedPreferences.getString("bio", "");
        String availability = sharedPreferences.getString("availability", "");
        String country = sharedPreferences.getString("country", "");
        String profilePic = sharedPreferences.getString("profilePic", "");
        int userIdTeam = sharedPreferences.getInt("userIdTeam", 0);
        int spikePointsTotal = sharedPreferences.getInt("spikePointsTotal", 0);
        int spikeErrorsTotal = sharedPreferences.getInt("spikeErrorsTotal", 0);
        int spikeAttemptsTotal = sharedPreferences.getInt("spikeAttemptsTotal", 0);
        int blockPointsTotal = sharedPreferences.getInt("blockPointsTotal", 0);
        int blockErrorsTotal = sharedPreferences.getInt("blockErrorsTotal", 0);
        int blockReboundsTotal = sharedPreferences.getInt("blockReboundsTotal", 0);
        int servePointsTotal = sharedPreferences.getInt("servePointsTotal", 0);
        int serveErrorsTotal = sharedPreferences.getInt("serveErrorsTotal", 0);
        int serveAttemptsTotal = sharedPreferences.getInt("serveAttemptsTotal", 0);
        int setSuccessfulTotal = sharedPreferences.getInt("setSuccessfulTotal", 0);
        int setErrorsTotal = sharedPreferences.getInt("setErrorsTotal", 0);
        int setAttemptsTotal = sharedPreferences.getInt("setAttemptsTotal", 0);
        int receiveSuccessfulTotal = sharedPreferences.getInt("receiveSuccessfulTotal", 0);
        int receiveErrorsTotal = sharedPreferences.getInt("receiveErrorsTotal", 0);
        int receiveAttemptsTotal = sharedPreferences.getInt("receiveAttemptsTotal", 0);
        String rol = sharedPreferences.getString("rol", "");
        int teamId = sharedPreferences.getInt("teamId", 0);
        String teamName = sharedPreferences.getString("teamName", "");
        int nPlayers = sharedPreferences.getInt("nPlayers", 0);
        String teamLogoPic = sharedPreferences.getString("teamLogoPic", "");
        String shortName = sharedPreferences.getString("shortName", "");
        int teamTotalGames = sharedPreferences.getInt("teamTotalGames", 0);
        int wonGames = sharedPreferences.getInt("wonGames", 0);
        int lostGames = sharedPreferences.getInt("lostGames", 0);
        int totalPoints = sharedPreferences.getInt("totalPoints", 0);
        int idGame = sharedPreferences.getInt("idGame", 0);
        int admin = sharedPreferences.getInt("admin", 0);

        /*Log.d("SharedPreferences", "userId: " + userId);
        Log.d("SharedPreferences", "userName: " + userName);
        Log.d("SharedPreferences", "userEmail: " + userEmail);
        Log.d("SharedPreferences", "userPhone: " + userPhone);
        Log.d("SharedPreferences", "totalGames: " + totalGames);
        Log.d("SharedPreferences", "dominantHand: " + dominantHand);
        Log.d("SharedPreferences", "position: " + position);
        Log.d("SharedPreferences", "height: " + height);
        Log.d("SharedPreferences", "verticalJump: " + verticalJump);
        Log.d("SharedPreferences", "location: " + location);
        Log.d("SharedPreferences", "gender: " + gender);
        Log.d("SharedPreferences", "birthDate: " + birthDate);
        Log.d("SharedPreferences", "bio: " + bio);
        Log.d("SharedPreferences", "availability: " + availability);
        Log.d("SharedPreferences", "country: " + country);
        Log.d("SharedPreferences", "profilePic: " + profilePic);
        Log.d("SharedPreferences", "userIdTeam: " + userIdTeam);
        Log.d("SharedPreferences", "spikePointsTotal: " + spikePointsTotal);
        Log.d("SharedPreferences", "spikeErrorsTotal: " + spikeErrorsTotal);
        Log.d("SharedPreferences", "spikeAttemptsTotal: " + spikeAttemptsTotal);
        Log.d("SharedPreferences", "blockPointsTotal: " + blockPointsTotal);
        Log.d("SharedPreferences", "blockErrorsTotal: " + blockErrorsTotal);
        Log.d("SharedPreferences", "blockReboundsTotal: " + blockReboundsTotal);
        Log.d("SharedPreferences", "servePointsTotal: " + servePointsTotal);
        Log.d("SharedPreferences", "serveErrorsTotal: " + serveErrorsTotal);
        Log.d("SharedPreferences", "serveAttemptsTotal: " + serveAttemptsTotal);
        Log.d("SharedPreferences", "setSuccessfulTotal: " + setSuccessfulTotal);
        Log.d("SharedPreferences", "setErrorsTotal: " + setErrorsTotal);
        Log.d("SharedPreferences", "setAttemptsTotal: " + setAttemptsTotal);
        Log.d("SharedPreferences", "receiveSuccessfulTotal: " + receiveSuccessfulTotal);
        Log.d("SharedPreferences", "receiveErrorsTotal: " + receiveErrorsTotal);
        Log.d("SharedPreferences", "receiveAttemptsTotal: " + receiveAttemptsTotal);
        Log.d("SharedPreferences", "rol: " + rol);
        Log.d("SharedPreferences", "teamId: " + teamId);
        Log.d("SharedPreferences", "teamName: " + teamName);
        Log.d("SharedPreferences", "nPlayers: " + nPlayers);
        Log.d("SharedPreferences", "teamLogoPic"+ teamLogoPic);
        Log.d("SharedPreferences", "admin: "+ admin);*/

        cardView = findViewById(R.id.cardView);
        imageButton = findViewById(R.id.imageButtonMas);
        binding.bottomNavigationView.setOnItemSelectedListener(item ->{
            String itemId = getResources().getResourceEntryName(item.getItemId());
            switch (itemId) {
                case "jugar":
                    replaceFragment(new FragmentJugar());
                    showImageButton();
                    break;
                case "perfil":
                    replaceFragment(new FragmentPerfil());
                    hideImageButton();
                    break;
            }
            return true;
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FullScreenDialogFragment dialogFragment = new FullScreenDialogFragment();
                    dialogFragment.show(getSupportFragmentManager(), "FullScreenDialogFragment");
            }
        });


    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    private void showImageButton() {
        cardView.setVisibility(View.VISIBLE);
    }

    private void hideImageButton() {
        cardView.setVisibility(View.GONE);
    }


    public void launchEditarPerfil(View view){
        Intent intent = new Intent(Inici.this,EditarPerfil.class);
        startActivity(intent);
    }

    public void launchAñadirAmigo(View view){
        Intent intent = new Intent(Inici.this,AnadirAmigo.class);
        startActivity(intent);
    }

    public void launchNotifications(View view){
        Intent intent = new Intent(Inici.this,Notifications.class);
        startActivity(intent);
    }
}