package com.example.githubbrowser.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.githubbrowser.model.logic.SearchResultConverter;
import com.example.githubbrowser.model.network.GitHubRepository;
import com.example.githubbrowser.model.network.pojo.SearchResult;
import com.example.githubbrowser.model.pojo.GitHubRepoDisplayItem;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GitHubListViewModel extends ViewModel {

    public enum Status {
        IDLE,
        SEARCHING,
        DISPLAYING,
        ERROR
    }

    public class State {

        Status mStatus;
        String mKeywords;
        List<GitHubRepoDisplayItem> mDisplayItems;

        public Status getStatus() {
            return mStatus;
        }

        public State setState(Status status) {
            mStatus = status;
            return this;
        }

        public String getKeywords() {
            return mKeywords;
        }

        public State setKeywords(String keywords) {
            mKeywords = keywords;
            return this;
        }

        public List<GitHubRepoDisplayItem> getDisplayItems() {
            return mDisplayItems;
        }

        public State setDisplayItems(List<GitHubRepoDisplayItem> displayItems) {
            mDisplayItems = displayItems;
            return this;
        }
    }

    private final GitHubRepository mGitHubRepository;

    private final MutableLiveData<State> mState = new MutableLiveData<>();

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public GitHubListViewModel(GitHubRepository gitHubRepository) {
        super();
        mGitHubRepository = gitHubRepository;
        mState.setValue(new State().setState(Status.IDLE));
    }

    public MutableLiveData<State> getState() {
        return mState;
    }

    public void searchRepos(String keywords) {
        if (keywords.isEmpty()) {
            Timber.d("Nothing to do here");
            return;
        }

        mState.setValue(mState.getValue().setKeywords(keywords).setState(Status.SEARCHING));
        searchRepo(keywords);
    }

    private void searchRepo(String keywords) {
        mDisposable.clear();
        mDisposable.add(mGitHubRepository.search(keywords)
                .subscribeOn(Schedulers.io())
                .map(new Function<SearchResult, List<GitHubRepoDisplayItem>>() {

                    @Override
                    public List<GitHubRepoDisplayItem> apply(SearchResult searchResult) throws Exception {
                        return SearchResultConverter.convert(searchResult.getItems());
                    }
                }).subscribeWith(new DisposableSingleObserver<List<GitHubRepoDisplayItem>>() {
                    @Override
                    public void onSuccess(List<GitHubRepoDisplayItem> displayItems) {
                        Timber.d("onSuccess()");
                        mState.postValue(mState.getValue().setState(Status.DISPLAYING).setDisplayItems(displayItems));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("onError()");
                        mState.postValue(mState.getValue().setState(Status.ERROR).setDisplayItems(Collections.<GitHubRepoDisplayItem>emptyList()));
                    }
                }));
    }

    @Override
    protected void onCleared() {
        mDisposable.dispose();
        super.onCleared();
    }
}
