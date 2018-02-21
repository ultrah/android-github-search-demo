package com.example.githubbrowser.model.network;

import com.example.githubbrowser.model.network.pojo.SearchResult;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("search/repositories")
    Single<SearchResult> search(@Query("q") String keywords);
}

