package com.example.projectofinal;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;

public class FragmentJugar extends Fragment {

    public FragmentJugar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jugar, container, false);
        CardView cardViewPartido = rootView.findViewById(R.id.cardViewPartido);
        CardView cardViewReserva = rootView.findViewById(R.id.cardViewReservaPista);
        cardViewPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Partidos.class);
                startActivity(intent);
            }
        });
        cardViewReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReservaPista.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
