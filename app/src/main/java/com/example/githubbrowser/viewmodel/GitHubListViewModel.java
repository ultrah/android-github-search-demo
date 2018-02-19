package com.example.githubbrowser.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.example.githubbrowser.logic.SearchResultConverter;
import com.example.githubbrowser.model.local.GitHubRepoDisplayItem;
import com.example.githubbrowser.model.network.github.SearchResult;
import com.example.githubbrowser.model.network.github.SearchResultItem;
import com.example.githubbrowser.network.GitHubRepository;
import com.example.githubbrowser.network.ResponseListener;

import java.util.List;

public class GitHubListViewModel extends ViewModel {

    private final GitHubRepository mGithubRepository;
    private MutableLiveData<List<GitHubRepoDisplayItem>> mGitHubRepos;

    public GitHubListViewModel(GitHubRepository gitHubRepository) {
        super();
        mGithubRepository = gitHubRepository;
    }

    public MutableLiveData<List<GitHubRepoDisplayItem>> getGitHubRepos() {
        if (mGitHubRepos == null) {
            mGitHubRepos = new MutableLiveData<>();
        }
        return mGitHubRepos;
    }

    public void searchRepos(String keywords) {
        // TODO retain search string

        mGithubRepository.search(keywords, new ResponseListener<SearchResult>() {

            @Nullable
            @Override
            public void onResponse(SearchResult result) {
                //TODO null check
                List<SearchResultItem> searchResultItems = result.getItems();
                List<GitHubRepoDisplayItem> displayItems = SearchResultConverter.convert(searchResultItems);
                mGitHubRepos.setValue(displayItems);
            }
        });
    }
}
