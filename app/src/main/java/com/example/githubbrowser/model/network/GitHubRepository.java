package com.example.githubbrowser.model.network;

import com.example.githubbrowser.model.network.pojo.SearchResult;

public interface GitHubRepository {

    void search(String keywords, ResponseListener<SearchResult> responseListener);
}
