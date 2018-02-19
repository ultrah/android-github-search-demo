package com.example.githubbrowser.network;

import android.support.annotation.Nullable;

import com.example.githubbrowser.model.network.github.SearchResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubNetworkRepository {

    public static final String BASE_URL = "https://api.github.com/";
    private GitHubService mService;

    //TODO set accept header
    public void search(String keywords, final ResponseListener<SearchResult> responseListener) {
        if(mService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();

            mService = retrofit.create(GitHubService.class);
        }

        mService.search(keywords).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                responseListener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                responseListener.onResponse(null);
            }
        });
    }

    public interface ResponseListener<T> {

        @Nullable
        void onResponse(T result);
    }

}
