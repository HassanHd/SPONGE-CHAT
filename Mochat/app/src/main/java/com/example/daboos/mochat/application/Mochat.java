package com.example.daboos.mochat.application;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class Mochat extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
