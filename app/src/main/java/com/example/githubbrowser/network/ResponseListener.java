package com.example.githubbrowser.network;

import android.support.annotation.Nullable;

public interface ResponseListener<T> {

    @Nullable
    void onResponse(T result);
}
