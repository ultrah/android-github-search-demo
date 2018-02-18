package com.example.githubbrowser.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.githubbrowser.model.local.GithubRepoDisplayItem;
import com.example.githubbrowser.model.network.github.SearchResult;
import com.example.githubbrowser.model.network.github.SearchResultItem;
import com.example.githubbrowser.network.GithubNetworkRepository;

import java.util.ArrayList;
import java.util.List;

public class GithubListViewModel extends AndroidViewModel {

    private final GithubNetworkRepository mGithubRepository;
    private MutableLiveData<List<GithubRepoDisplayItem>> mGithubRepos;

    public GithubListViewModel(@NonNull Application application) {
        super(application);

        //TODO injection
        mGithubRepository = new GithubNetworkRepository();
    }

    public MutableLiveData<List<GithubRepoDisplayItem>> getGithubRepos() {
        if (mGithubRepos == null) {
            mGithubRepos = new MutableLiveData<>();
        }
        return mGithubRepos;
    }

    public void searchRepos(String keywords) {
        // TODO retain search string

       mGithubRepository.search(keywords, new GithubNetworkRepository.ResponseListener<SearchResult>() {

           @Nullable
           @Override
           public void onResponse(SearchResult result) {
               //TODO null check
               // TODO extract to static logic method
               List<SearchResultItem> searchResultItems = result.getItems();
               List<GithubRepoDisplayItem> displayItems = new ArrayList<>(searchResultItems.size());

               for(SearchResultItem item : searchResultItems) {
                   displayItems.add(new GithubRepoDisplayItem(item.getName()));
               }

               mGithubRepos.setValue(displayItems);
           }
       });
    }
}
