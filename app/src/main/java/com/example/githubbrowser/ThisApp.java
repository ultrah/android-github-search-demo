package com.example.githubbrowser;

import android.app.Application;

import timber.log.Timber;

public class ThisApp extends Application {

    private static ThisApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        // TODO not on prod
        Timber.plant(new Timber.DebugTree());
    }

    public static ThisApp getInstance() {
        return sInstance;
    }
}
