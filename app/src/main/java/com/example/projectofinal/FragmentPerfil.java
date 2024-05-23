package com.example.projectofinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPerfil extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragmentPerfil() {
        // Required empty public constructor
    }

    public static FragmentPerfil newInstance(String param1, String param2) {
        FragmentPerfil fragment = new FragmentPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int userId = sharedPreferences.getInt("userId", -1);
        getDataUser(userId);
        return view;
    }

    private void getDataUser(int userId){
        String URL = "http://192.168.1.17:3001/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Map<String, Integer> userIdMap = new HashMap<>();
        userIdMap.put("id", userId);

        Call<Usuario> call = apiService.getUser(userIdMap);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario usuario = response.body();
                    // Manejar la respuesta exitosa
                    Log.d("FragmentPerfil", "User: " + usuario.getFirstname() + " " + usuario.getSurname());
                } else {
                    // Manejar errores
                    Log.e("FragmentPerfil", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("FragmentPerfil", "Failure: " + t.getMessage());
            }
        });
    }
}
