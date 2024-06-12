package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.projectofinal.databinding.ActivityIniciBinding;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Inici extends AppCompatActivity {
    ActivityIniciBinding binding;
    private static final int REQUEST_NOTIFICATION_PERMISSION = 100;
    CardView cardView;
    ImageButton imageButton;
    private Socket socket = SocketManager.getInstance();
    boolean isCardOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIniciBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FragmentJugar());
        // Request notification permission for Android 13+
        requestNotificationPermission();
        // Create notification channel and show notification
        createNotificationChannel();
        socket.on("sendNotification", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("TAG", "call: se escucha");

                
                showNotification("Te han enviando una nueva solicitud de amistad", "Nueva solicitud de amistad");

            }
        });



        cardView = findViewById(R.id.cardView);
        imageButton = findViewById(R.id.imageButtonMas);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            String itemId = getResources().getResourceEntryName(item.getItemId());
            switch (itemId) {
                case "jugar":
                    replaceFragment(new FragmentJugar());
                    showImageButton();
                    break;
                case "perfil":
                    replaceFragment(new FragmentPerfil());
                    hideImageButton();
                    break;
            }
            return true;
        });

        imageButton.setOnClickListener(v -> {
            FullScreenDialogFragment dialogFragment = new FullScreenDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "FullScreenDialogFragment");
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showImageButton() {
        cardView.setVisibility(View.VISIBLE);
    }

    private void hideImageButton() {
        cardView.setVisibility(View.GONE);
    }

    public void launchEditarPerfil(View view) {
        Intent intent = new Intent(Inici.this, EditarPerfil.class);
        startActivity(intent);
    }

    public void launchAñadirAmigo(View view) {
        Intent intent = new Intent(Inici.this, AnadirAmigo.class);
        startActivity(intent);
    }

    public void launchNotifications(View view) {
        Intent intent = new Intent(Inici.this, Notifications.class);
        startActivity(intent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel serviceChannel = new NotificationChannel(
                    "channelID",
                    "Channel Name",
                    importance
            );
            serviceChannel.setDescription("Channel Description");
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
                Log.d("Notification", "Notification channel created");
            } else {
                Log.e("Notification", "NotificationManager is null");
            }
        } else {
            Log.d("Notification", "Notification channel creation skipped for pre-Oreo devices");
        }
    }

    private void showNotification(String msg, String title) {

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelID")
                .setSmallIcon(R.drawable.logovolleypal) // Ensure logovolleypal exists
                .setContentTitle("Solicitud de amistad")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true); // Auto cancel notification when clicked

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(3, builder.build());
            Log.d("Notification", "Notification displayed");
        } else {
            Log.e("Notification", "NotificationManager is null, cannot display notification");
        }
    }


    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_NOTIFICATION_PERMISSION);
            } else {
                Log.d("Notification", "Notification permission already granted");
            }
        } else {
            Log.d("Notification", "Notification permission not required for this OS version");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Notification", "Notification permission granted");
                showNotification("Has aceptado las notificaciones, asi se te enviaran." , "Aceptado la solicitud de notificaciones."); // Show notification after permission is granted
            } else {
                Log.d("Notification", "Notification permission denied");
            }
        }
    }
}
