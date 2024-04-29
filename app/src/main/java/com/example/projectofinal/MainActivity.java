package com.example.projectofinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.fondovideo;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0, 0);
            videoView.start();
        });
    }

    public void launchLogin(View view){
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }

    public void launchRegister(View view){
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }
}
