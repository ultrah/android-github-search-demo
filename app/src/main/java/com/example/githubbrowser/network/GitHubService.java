package com.example.githubbrowser.network;

import com.example.githubbrowser.model.network.github.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("search/repositories")
    Call<SearchResult> search(@Query("q") String keywords);
}