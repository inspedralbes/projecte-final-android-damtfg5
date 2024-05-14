package com.example.projectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPeopleFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private AddPeopleAdapter adapter;
    private List<Usuario> usuarioList;
    private EditText editTextBuscador;
    private String URL = "http://192.168.206.176:3001/";
    int userId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_people, container, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        userId = sharedPreferences.getInt("userId", -1);

        ImageButton imageButton = view.findViewById(R.id.imageButtonCloseAP);
        editTextBuscador = view.findViewById(R.id.editTextBuscador);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Cierra el fragmento
            }
        });

        recyclerView = view.findViewById(R.id.recycler_viewIP);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        usuarioList = new ArrayList<>();
        adapter = new AddPeopleAdapter(getContext(), usuarioList);
        recyclerView.setAdapter(adapter);

        editTextBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarUsuarios(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle2);
    }

    private void filtrarUsuarios(String texto) {
        if (texto.isEmpty()) {
            adapter.setUsuario(new ArrayList<>());
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);
            UserIdRequest userIdRequest = new UserIdRequest(userId);
            Call<List<Usuario>> call = apiService.getUsers(userIdRequest);
            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    if (response.isSuccessful()) {
                        List<Usuario> listaCompletaUsuarios = response.body();
                        List<Usuario> listaUsuariosFiltrada = new ArrayList<>();

                        for (Usuario usuario : listaCompletaUsuarios) {
                            if (usuario.getFirstname().toLowerCase().contains(texto.toLowerCase()) || usuario.getSurname().toLowerCase().contains(texto.toLowerCase())) {
                                listaUsuariosFiltrada.add(usuario);
                            }
                        }

                        adapter.setUsuario(listaUsuariosFiltrada);
                    } else {
                        // Manejar respuesta no exitosa
                    }
                }

                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {
                    // Manejar fallos en la solicitud
                }
            });
        }
    }



}