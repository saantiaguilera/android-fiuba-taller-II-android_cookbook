package com.example.extras.test;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.maps.MapsInitializer;

/**
 * Main Application. This class simulates the application head.
 *
 * Check in the manifest that you should add the node 'name' to the application,
 * with this class as a reference.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Fresco
        Fresco.initialize(this);

        // Initialize google maps api
        MapsInitializer.initialize(this);
    }
}
