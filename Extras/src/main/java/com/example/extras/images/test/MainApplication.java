package com.example.extras.images.test;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

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

        Fresco.initialize(this);
    }
}
