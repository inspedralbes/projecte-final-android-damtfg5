package com.example.projectofinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class FullScreenDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_screen_dialog, container, false);

        ImageButton imageButton = view.findViewById(R.id.imageButtonClose);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Cierra el fragmento
            }
        });

        ImageButton imageButtonUpload = view.findViewById(R.id.imageButtonUpload);
        imageButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad CrearEquipo
                Intent intent = new Intent(getActivity(), CrearEquipo.class);
                startActivity(intent);
            }
        });

        ImageButton imageButtonTeam = view.findViewById(R.id.imageButtonTeam);
        imageButtonTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad CrearEquipo
                Intent intent = new Intent(getActivity(), EditarEquipo.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }
}
