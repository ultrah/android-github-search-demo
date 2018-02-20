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

import timber.log.Timber;

public class GitHubListViewModel extends ViewModel {

    public enum State {
        IDLE,
        SEARCHING,
        DISPLAYING,
        ERROR
    }

    // TODO immutable class (easier with Kotlin data class)
    public class BasicState {

        State mState;
        String mKeywords;

        public State getState() {
            return mState;
        }

        public BasicState setState(State state) {
            mState = state;
            return this;
        }

        public String getKeywords() {
            return mKeywords;
        }

        public BasicState setKeywords(String keywords) {
            mKeywords = keywords;
            return this;
        }
    }

    private final GitHubRepository mGithubRepository;

    private MutableLiveData<List<GitHubRepoDisplayItem>> mDisplayItems;
    private MutableLiveData<BasicState> mState;

    public GitHubListViewModel(GitHubRepository gitHubRepository) {
        super();
        mGithubRepository = gitHubRepository;
        mState = new MutableLiveData<>();
        mState.setValue(new BasicState().setState(State.IDLE));
    }

    public MutableLiveData<List<GitHubRepoDisplayItem>> getDisplayItems() {
        if (mDisplayItems == null) {
            mDisplayItems = new MutableLiveData<>();
        }
        return mDisplayItems;
    }

    public MutableLiveData<BasicState> getBasicState() {

        return mState;
    }

    public void searchRepos(String keywords) {
        if (keywords.isEmpty()) {
            Timber.d("Nothing to do here");
            return;
        }

        mState.setValue(mState.getValue().setKeywords(keywords).setState(State.SEARCHING));

        mGithubRepository.search(keywords, new ResponseListener<SearchResult>() {

            @Nullable
            @Override
            public void onResponse(SearchResult result) {
                //TODO null check
                List<SearchResultItem> searchResultItems = result.getItems();
                List<GitHubRepoDisplayItem> displayItems = SearchResultConverter.convert(searchResultItems);
                mDisplayItems.setValue(displayItems);
                mState.setValue(mState.getValue().setState(State.DISPLAYING));
            }
        });
    }
}
