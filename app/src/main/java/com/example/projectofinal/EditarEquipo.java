package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarEquipo extends AppCompatActivity {
    private String URL = "http://192.168.1.17:3001/";
    int userId;
    ConstraintLayout firstLayout,secondLayout;
    EditText editTextNE,editTextAbreviacion;
    ImageButton imageButtonClearNE,imageButtonClearAbreviacion;
    Button buttonAñadirGente,buttonEditarEquipo;
    ImageView imageViewUsuario;
    RecyclerView recyclerView;
    private UsersTeamAdapter adapter;
    private List<Usuario> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipo);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getInt("userId", -1);
        firstLayout = findViewById(R.id.firstLayout);
        secondLayout = findViewById(R.id.secondLayout);
        editTextNE = findViewById(R.id.editTextNE);
        editTextAbreviacion = findViewById(R.id.editTextAbreviacion);
        imageButtonClearNE = findViewById(R.id.imageButtonClear1);
        imageButtonClearAbreviacion = findViewById(R.id.imageButtonClear2);
        buttonAñadirGente = findViewById(R.id.buttonAñadirGente);
        buttonEditarEquipo = findViewById(R.id.buttonEditarEquipo);
        imageViewUsuario = findViewById(R.id.imageViewUsuario);
        userList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewUsTe);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsersTeamAdapter(EditarEquipo.this, userList);
        recyclerView.setAdapter(adapter);

        setupEditTextFocusChange(editTextNE, firstLayout, imageButtonClearNE);
        setupEditTextFocusChange(editTextAbreviacion, secondLayout, imageButtonClearAbreviacion);
        setupImageButtonClear(editTextNE, imageButtonClearNE);
        setupImageButtonClear(editTextAbreviacion, imageButtonClearAbreviacion);

        buttonAñadirGente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPeopleFragment dialogFragment = new AddPeopleFragment();
                dialogFragment.show(getSupportFragmentManager(), "AddPeopleFragment");
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        UserIdRequest userIdRequest = new UserIdRequest(userId);
        Call<List<TeamData>> call = apiService.getTeam(userIdRequest);
        call.enqueue(new Callback<List<TeamData>>() {
            @Override
            public void onResponse(Call<List<TeamData>> call, Response<List<TeamData>> response) {
                if (response.isSuccessful()) {
                    List<TeamData> teams = response.body();
                    if (!teams.isEmpty()) {
                        TeamData team = teams.get(0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("teamId", team.getId());
                        editor.apply();
                        editTextNE.setText(team.getTeamName());
                        editTextAbreviacion.setText(team.getShortName());
                        String imageUrl = team.getLogoPic();
                        Picasso.get().load(imageUrl).into(imageViewUsuario);

                        TeamIdRequest teamIdRequest = new TeamIdRequest(team.getId());
                        Call<List<Usuario>> call1 = apiService.getTeamUsers(teamIdRequest);
                        call1.enqueue(new Callback<List<Usuario>>() {
                            @Override
                            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                                if (response.isSuccessful()) {
                                    userList = response.body();
                                    if (userList != null) {
                                        adapter = new UsersTeamAdapter(EditarEquipo.this, userList);
                                        recyclerView.setAdapter(adapter);
                                    }
                                } else {
                                    Log.e("UserListResponse", "Error en la respuesta del servidor: " + response.message());
                                }
                            }
                            @Override
                            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                                Log.e("UserListRequest", "Error en la solicitud HTTP: " + t.getMessage());
                                Toast.makeText(EditarEquipo.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Log.e("TeamResponse", "Error en la respuesta del servidor: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<TeamData>> call, Throwable t) {
                Log.e("TeamRequest", "Error en la solicitud HTTP: " + t.getMessage());
            }
        });

        ImageButton imageButtonBEE = findViewById(R.id.imageButtonBEE);
        imageButtonBEE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}