package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectofinal.databinding.ActivityIniciBinding;

public class Inici extends AppCompatActivity {
    ActivityIniciBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIniciBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FragmentJugar());

        binding.bottomNavigationView.setOnItemSelectedListener(item ->{
            String itemId = getResources().getResourceEntryName(item.getItemId());
            switch (itemId){
                case "jugar":
                    replaceFragment(new FragmentJugar());
                    break;
                case "descubrir":
                    replaceFragment(new FragmentDescubrir());
                    break;
                case "comunidad":
                    replaceFragment(new FragmentComunidad());
                    break;
                case "perfil":
                    replaceFragment(new FragmentPerfil());
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

    public void launchEditarPerfil(View view){
        Intent intent = new Intent(Inici.this,EditarPerfil.class);
        startActivity(intent);
    }
}