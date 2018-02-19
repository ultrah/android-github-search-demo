package com.example.githubbrowser.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.githubbrowser.model.local.GitHubRepoDisplayItem;
import com.example.githubbrowser.model.network.github.SearchResult;
import com.example.githubbrowser.model.network.github.SearchResultItem;
import com.example.githubbrowser.network.GitHubNetworkRepository;

import java.util.ArrayList;
import java.util.List;

public class GitHubListViewModel extends AndroidViewModel {

    private final GitHubNetworkRepository mGithubRepository;
    private MutableLiveData<List<GitHubRepoDisplayItem>> mGitHubRepos;

    public GitHubListViewModel(@NonNull Application application) {
        super(application);

        //TODO injection
        mGithubRepository = new GitHubNetworkRepository();
    }

    public MutableLiveData<List<GitHubRepoDisplayItem>> getGitHubRepos() {
        if (mGitHubRepos == null) {
            mGitHubRepos = new MutableLiveData<>();
        }
        return mGitHubRepos;
    }

    public void searchRepos(String keywords) {
        // TODO retain search string

       mGithubRepository.search(keywords, new GitHubNetworkRepository.ResponseListener<SearchResult>() {

           @Nullable
           @Override
           public void onResponse(SearchResult result) {
               //TODO null check
               // TODO extract to static logic method
               List<SearchResultItem> searchResultItems = result.getItems();
               List<GitHubRepoDisplayItem> displayItems = new ArrayList<>(searchResultItems.size());

               for(SearchResultItem item : searchResultItems) {
                   displayItems.add(new GitHubRepoDisplayItem(item.getName()));
               }

               mGitHubRepos.setValue(displayItems);
           }
       });
    }
}
