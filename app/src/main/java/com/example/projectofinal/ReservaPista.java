package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservaPista extends AppCompatActivity {
    ImageButton imageButtonBackReserva;
    MapView map;
    ImageButton imageButtonZoomIn;
    ImageButton imageButtonZoomOut;
    AutoCompleteTextView editTextLocalitation;
    private String URL = "http://volleypal.dam.inspedralbes.cat:3001/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_pista);

        imageButtonBackReserva = findViewById(R.id.imageButtonBackReserva);
        imageButtonZoomIn = findViewById(R.id.imageButtonZoomIn);
        imageButtonZoomOut = findViewById(R.id.imageButtonZoomOut);
        editTextLocalitation = findViewById(R.id.editTextBuscador);

        imageButtonBackReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageButtonZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomIn();
            }
        });

        imageButtonZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomOut();
            }
        });

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        map = findViewById(R.id.map);
        map.getTileProvider().clearTileCache();
        Configuration.getInstance().setCacheMapTileCount((short) 12);
        Configuration.getInstance().setCacheMapTileOvershoot((short) 12);
        // Create a custom tile source
        map.setTileSource(new OnlineTileSourceBase("", 1, 20, 730, ".png",
                new String[]{"https://a.tile.openstreetmap.org/"}) {
            @Override
            public String getTileURLString(long pMapTileIndex) {
                return getBaseUrl()
                        + MapTileIndex.getZoom(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + "/" + MapTileIndex.getY(pMapTileIndex)
                        + mImageFilenameEnding;
            }
        });

        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        GeoPoint startPoint = new GeoPoint(41.39279906, 2.118284722);
        mapController.setZoom(14.0);
        mapController.setCenter(startPoint);
        map.invalidate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<String>> call = apiService.getMunicipis();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> municipis = response.body();
                    if (municipis != null) {
                        setupAutoCompleteTextView(municipis);
                    }
                } else {
                    Toast.makeText(ReservaPista.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(ReservaPista.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getSpacesFromServer(String municipi) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        MunicipiRequest municipiRequest = new MunicipiRequest(municipi);

        Call<List<Space>> call = apiService.getSpaces(municipiRequest);
        call.enqueue(new Callback<List<Space>>() {
            @Override
            public void onResponse(Call<List<Space>> call, Response<List<Space>> response) {
                if (response.isSuccessful()) {
                    List<Space> spaces = response.body();
                    for (Space space : spaces) {
                        double spaceLatitud = Double.parseDouble(space.getLatitud());
                        double spaceLongitud = Double.parseDouble(space.getLongitud());

                        createMarker(spaceLatitud, spaceLongitud, space.getActivitat_principal(), space.getRef_espai(), space.getPaviment());
                    }
                } else {
                    // Manejar respuesta no exitosa
                }
            }

            @Override
            public void onFailure(Call<List<Space>> call, Throwable t) {
                // Manejar error de conexión
            }
        });
    }

    private void createMarker(double latitude, double longitude, String title, String municipi, String paviment) {
        if (map == null) {
            return;
        }

        Marker marker = new Marker(map);
        marker.setPosition(new GeoPoint(latitude, longitude));

        // Obtener la dirección utilizando Geocoder
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String addressLine = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                addressLine = address.getAddressLine(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        marker.setTitle(title);
        marker.setPanToView(true);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        String finalAddressLine = addressLine; // Necesario para usar en el Listener
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Intent intent = new Intent(ReservaPista.this, MarkerInfoActivity.class);
                intent.putExtra("title", marker.getTitle());
                intent.putExtra("municipi", municipi);
                intent.putExtra("paviment", paviment);
                intent.putExtra("address", finalAddressLine);
                startActivity(intent);
                return true;
            }
        });
        map.getOverlays().add(marker);
        map.invalidate();
    }
    private void setupAutoCompleteTextView(List<String> municipis) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, municipis);
        editTextLocalitation.setAdapter(adapter);
        editTextLocalitation.setThreshold(1);
        editTextLocalitation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el texto seleccionado del adaptador y llamar a getSpacesFromServer
                String selectedText = (String) parent.getItemAtPosition(position);
                getSpacesFromServer(selectedText);
            }
        });
    }

    private void zoomIn() {
        map.getController().zoomIn();
    }

    private void zoomOut() {
        map.getController().zoomOut();
    }
}
