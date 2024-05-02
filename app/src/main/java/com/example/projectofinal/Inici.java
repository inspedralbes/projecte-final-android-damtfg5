package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.projectofinal.databinding.ActivityIniciBinding;

public class Inici extends AppCompatActivity {
    ActivityIniciBinding binding;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIniciBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FragmentJugar());
        imageButton = findViewById(R.id.imageButton);
        binding.bottomNavigationView.setOnItemSelectedListener(item ->{
            String itemId = getResources().getResourceEntryName(item.getItemId());
            switch (itemId) {
                case "jugar":
                    replaceFragment(new FragmentJugar());
                    showImageButton();
                    break;
                case "descubrir":
                    replaceFragment(new FragmentDescubrir());
                    showImageButton();
                    break;
                case "comunidad":
                    replaceFragment(new FragmentComunidad());
                    showImageButton();
                    break;
                case "perfil":
                    replaceFragment(new FragmentPerfil());
                    hideImageButton();
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    private void showImageButton() {
        imageButton.setVisibility(View.VISIBLE);
    }

    private void hideImageButton() {
        imageButton.setVisibility(View.GONE);
    }


    public void launchEditarPerfil(View view){
        Intent intent = new Intent(Inici.this,EditarPerfil.class);
        startActivity(intent);
    }

    public void launchAñadirAmigo(View view){
        Intent intent = new Intent(Inici.this,AnadirAmigo.class);
        startActivity(intent);
    }
}