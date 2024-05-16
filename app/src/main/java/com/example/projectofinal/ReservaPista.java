package com.example.projectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservaPista extends AppCompatActivity implements ScrollListener, ZoomListener {
    ImageButton imageButtonBackReserva;
    MapView map;
    private Marker marker;
    private GeoPoint markerPosition;
    private String URL = "http://192.168.206.176:3001/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_pista);

        imageButtonBackReserva = findViewById(R.id.imageButtonBackReserva);

        imageButtonBackReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        map = (MapView) findViewById(R.id.map);
        map.getTileProvider().clearTileCache();
        Configuration.getInstance().setCacheMapTileCount((short)12);
        Configuration.getInstance().setCacheMapTileOvershoot((short)12);
        // Create a custom tile source
        map.setTileSource(new OnlineTileSourceBase("", 1, 20, 512, ".png",
                new String[] { "https://a.tile.openstreetmap.org/" }) {
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
        GeoPoint startPoint;
        startPoint = new GeoPoint(41.39279906, 2.118284722);
        mapController.setZoom(15.0);
        mapController.setCenter(startPoint);
        map.invalidate();
        getSpacesFromServer("Barcelona");
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

                        createMarker(spaceLatitud, spaceLongitud, space.getTipus_d_espai());
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

    private void createMarker(double latitude, double longitude, String title) {
        if (map == null) {
            return;
        }

        markerPosition = new GeoPoint(latitude, longitude);
        marker = new Marker(map);
        marker.setPosition(markerPosition);
        marker.setTitle(title);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setPanToView(true);
        map.getOverlays().add(marker);
        map.invalidate();

    }

    @Override
    public boolean onScroll(ScrollEvent event) {
        // Actualizar la posición del marcador durante el desplazamiento del mapa
        if (marker != null && markerPosition != null) {
            marker.setPosition(markerPosition);
            map.invalidate();
        }
        return false; // O true, según sea necesario
    }

    @Override
    public boolean onZoom(ZoomEvent event) {
        // Actualizar la posición del marcador durante el zoom del mapa
        if (marker != null && markerPosition != null) {
            marker.setPosition(markerPosition);
            map.invalidate();
        }
        return false; // O true, según sea necesario
    }
}