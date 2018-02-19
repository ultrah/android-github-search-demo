package com.example.githubbrowser;

import android.app.Application;

import timber.log.Timber;

public class AppApp extends Application {

    private static AppApp sInstance;

    private Injector mInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        // TODO not on prod
        Timber.plant(new Timber.DebugTree());

        mInjector = new Injector();
    }

    public static AppApp getInstance() {
        return sInstance;
    }

    public static Injector getInjector() {
        return getInstance().mInjector;
    }
}
