package com.example.githubbrowser.model.network;

import com.example.githubbrowser.model.network.pojo.SearchResult;

import io.reactivex.Single;

public interface GitHubRepository {

    Single<SearchResult> search(String keywords);
}
