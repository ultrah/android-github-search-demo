package com.example.githubbrowser.network;

import com.example.githubbrowser.model.network.github.SearchResult;

public interface GitHubRepository {

    void search(String keywords, ResponseListener<SearchResult> responseListener);
}
