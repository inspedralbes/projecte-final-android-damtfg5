package com.example.projectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentDisponibles extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disponibles, container, false);

        // Inicializa el RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Proveer una lista de datos al adaptador
        List<Match> matchList = new ArrayList<>();
        matchList.add(new Match(true)); // Ejemplo de un partido con dos equipos
        matchList.add(new Match(false)); // Ejemplo de un partido 5vs5

        // Inicializa el adaptador con la lista de datos
        MatchAdapter adapter = new MatchAdapter(matchList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
