package com.example.githubbrowser;

import android.app.Application;

import timber.log.Timber;

public class AppApp extends Application {

    private static AppApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        // TODO not on prod
        Timber.plant(new Timber.DebugTree());
    }

    public static AppApp getInstance() {
        return sInstance;
    }
}
