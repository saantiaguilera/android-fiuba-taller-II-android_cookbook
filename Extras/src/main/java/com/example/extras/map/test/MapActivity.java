package com.example.extras.map.test;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * Knowing what you already know, you could:
 * - Implement your own inheritance of MapView that implements a certain contract (add pin / remove
 * pin / centerMyself / ...?)
 * - Implement a presenter that will manage the MapContract.View ( onCreate / onResume / onDestroy /
 * onLowMemory / ...?)
 * - Put the view inside an XML and let it be decoupled from everything.
 *
 * Some points to know:
 * - The presenter contract should define the onCreate/onResume/onDestroy/onLowMemory per defined
 * in the gms api
 */
public class MapActivity extends AppCompatActivity {

    @NonNull
    private MapView mapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(mapView = new MapView(this));

        // Dont forget to call also onCreate's!
        mapView.onCreate(savedInstanceState);

        // Check if we have the access fine location permission.
        // Some permissions in android (in later versions) are asked at runtime
        // Since they arent implicitly accepted (like internet). This permissions
        // Should be validated with this (that we already have it, else ask for it)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            updateMap();
        }
    }

    /**
     * Since we do only one activity for result, and I will personally accept it
     * I wont validate request code / grant results
     * @param requestCode code of the permission request
     * @param permissions requested
     * @param grantResults results granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        updateMap();
    }

    /**
     * Updates the map, this is done either when the permission is accepted, or if it already is
     * in the onCreate();
     */
    private void updateMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Set our location enabled, and the button to center it
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.setMyLocationEnabled(true);

                // Updates location and zoom in some random location
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(-34.5, -58.4), 10);
                googleMap.animateCamera(cameraUpdate);
            }
        });
    }

    /**
     * The following overrided methods are because gms api demands it
     */

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
