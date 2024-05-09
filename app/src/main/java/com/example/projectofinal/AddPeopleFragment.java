package com.example.projectofinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class AddPeopleFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private AddPeopleAdapter adapter;
    private List<Usuario> usuarioList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_people, container, false);

        ImageButton imageButton = view.findViewById(R.id.imageButtonCloseAP);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Cierra el fragmento
            }
        });

        recyclerView = view.findViewById(R.id.recycler_viewIP);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar la lista de usuarios
        usuarioList = new ArrayList<>();
        // Aquí puedes agregar elementos a usuarioList según tus necesidades
        usuarioList.add(new Usuario(1,"a","a",""));
        usuarioList.add(new Usuario(3,"b","b",""));
        usuarioList.add(new Usuario(2,"c","c",""));
        // Crear y configurar el adaptador
        adapter = new AddPeopleAdapter(getContext(), usuarioList);
        recyclerView.setAdapter(adapter);

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle2);
    }

}