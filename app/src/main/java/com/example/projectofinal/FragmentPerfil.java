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
import android.widget.TextView;

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
    private View view; // Variable para la vista del fragmento

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar la vista del fragmento
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int userId = sharedPreferences.getInt("userId", -1);
        String spikePointsTotalString = String.valueOf(sharedPreferences.getInt("spikePointsTotal", 0));
        String spikeErrorsTotalString = String.valueOf(sharedPreferences.getInt("spikeErrorsTotal", 0));
        String spikeAttemptsTotalString = String.valueOf(sharedPreferences.getInt("spikeAttemptsTotal", 0));
        String blockPointsTotalString = String.valueOf(sharedPreferences.getInt("blockPointsTotal", 0));
        String blockErrorsTotalString = String.valueOf(sharedPreferences.getInt("blockErrorsTotal", 0));
        String blockReboundsTotalString = String.valueOf(sharedPreferences.getInt("blockReboundsTotal", 0));
        String servePointsTotalString = String.valueOf(sharedPreferences.getInt("servePointsTotal", 0));
        String serveErrorsTotalString = String.valueOf(sharedPreferences.getInt("serveErrorsTotal", 0));
        String serveAttemptsTotalString = String.valueOf(sharedPreferences.getInt("serveAttemptsTotal", 0));
        String setSuccessfulTotalString = String.valueOf(sharedPreferences.getInt("setSuccessfulTotal", 0));
        String setErrorsTotalString = String.valueOf(sharedPreferences.getInt("setErrorsTotal", 0));
        String setAttemptsTotalString = String.valueOf(sharedPreferences.getInt("setAttemptsTotal", 0));
        String receiveSuccessfulTotalString = String.valueOf(sharedPreferences.getInt("receiveSuccessfulTotal", 0));
        String receiveErrorsTotalString = String.valueOf(sharedPreferences.getInt("receiveErrorsTotal", 0));
        String receiveAttemptsTotalString = String.valueOf(sharedPreferences.getInt("receiveAttemptsTotal", 0));
        String dominantHand = sharedPreferences.getString("dominantHand", "");
        String position = sharedPreferences.getString("position", "");
        String height = String.valueOf(sharedPreferences.getInt("height", 0));
        String verticalJump = String.valueOf(sharedPreferences.getInt("verticalJump", 0));
        String location = sharedPreferences.getString("location", "");

        TextView textViewtPointsSpike = view.findViewById(R.id.textViewtPointsSpike);
        TextView textViewtErrorsSpike = view.findViewById(R.id.textViewtErrorsSpike);
        TextView textViewtAttemptsSpike = view.findViewById(R.id.textViewtAttemptsSpike);
        TextView textViewtPointsBlock = view.findViewById(R.id.textViewtPointsBlock);
        TextView textViewtErrorsBlock = view.findViewById(R.id.textViewtErrorsBlock);
        TextView textViewtAttemptsBlock = view.findViewById(R.id.textViewtAttemptsBlock);
        TextView textViewtPointsServe = view.findViewById(R.id.textViewtPointsServe);
        TextView textViewtErrorsServe = view.findViewById(R.id.textViewtErrorsServe);
        TextView textViewtAttemptsServe = view.findViewById(R.id.textViewtAttemptsServe);
        TextView textViewtSuccessfulSet = view.findViewById(R.id.textViewtSuccessfulSet);
        TextView textViewtErrorsSet = view.findViewById(R.id.textViewtErrorsSet);
        TextView textViewtAttemptsSet = view.findViewById(R.id.textViewtAttemptsSet);
        TextView textViewtSuccessfulReceive = view.findViewById(R.id.textViewtSuccessfulReceive);
        TextView textViewtErrorsReceive = view.findViewById(R.id.textViewtErrorsReceive);
        TextView textViewtAttemptsReceive = view.findViewById(R.id.textViewtAttemptsReceive);

        TextView editTextPos = view.findViewById(R.id.editTextPos);
        TextView editTextHeight = view.findViewById(R.id.editTextHeight);
        TextView editTextViewVJ = view.findViewById(R.id.editTextViewVJ);
        TextView editTextViewChooseHand = view.findViewById(R.id.editTextViewChooseHand);
        TextView editTextLocalitation = view.findViewById(R.id.editTextLocalitation);


        editTextHeight.setText(String.valueOf(height));
        editTextPos.setText(position);
        editTextLocalitation.setText(location);
        editTextViewVJ.setText(verticalJump);
        editTextViewChooseHand.setText(dominantHand);

        textViewtPointsSpike.setText(spikePointsTotalString);
        textViewtErrorsSpike.setText(spikeErrorsTotalString);
        textViewtAttemptsSpike.setText(spikeAttemptsTotalString);

        textViewtPointsBlock.setText(blockPointsTotalString);
        textViewtErrorsBlock.setText(blockErrorsTotalString);
        textViewtAttemptsBlock.setText(blockReboundsTotalString);

        textViewtPointsServe.setText(servePointsTotalString);
        textViewtErrorsServe.setText(serveErrorsTotalString);
        textViewtAttemptsServe.setText(serveAttemptsTotalString);

        textViewtSuccessfulSet.setText(setSuccessfulTotalString);
        textViewtErrorsSet.setText(setErrorsTotalString);
        textViewtAttemptsSet.setText(setAttemptsTotalString);

        textViewtSuccessfulReceive.setText(receiveSuccessfulTotalString);
        textViewtErrorsReceive.setText(receiveErrorsTotalString);
        textViewtAttemptsReceive.setText(receiveAttemptsTotalString);


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
                    Log.d("TAG usuario", "onResponse: " + usuario.getTotalGames());
                    // Actualizar el nombre de usuario en la vista
                    TextView textViewNombreApellido = view.findViewById(R.id.textViewNombreApellido);
                    textViewNombreApellido.setText(usuario.getFirstname() + " " + usuario.getSurname());

                    // Actualizar el número de partidos en la vista
                    TextView textViewContPart = view.findViewById(R.id.textViewContPart);
                    textViewContPart.setText(String.valueOf(usuario.getTotalGames()));
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
