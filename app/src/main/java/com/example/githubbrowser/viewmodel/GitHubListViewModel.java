package com.example.githubbrowser.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.githubbrowser.model.logic.SearchResultConverter;
import com.example.githubbrowser.model.network.GitHubRepository;
import com.example.githubbrowser.model.network.pojo.SearchResult;
import com.example.githubbrowser.model.pojo.GitHubRepoDisplayItem;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GitHubListViewModel extends ViewModel {

    public enum State {
        IDLE,
        SEARCHING,
        DISPLAYING,
        ERROR
    }

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

    private final GitHubRepository mGitHubRepository;

    private final MutableLiveData<List<GitHubRepoDisplayItem>> mDisplayItems = new MutableLiveData<>();
    private final MutableLiveData<BasicState> mState = new MutableLiveData<>();

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public GitHubListViewModel(GitHubRepository gitHubRepository) {
        super();
        mGitHubRepository = gitHubRepository;
        mState.setValue(new BasicState().setState(State.IDLE));
    }

    public MutableLiveData<List<GitHubRepoDisplayItem>> getDisplayItems() {
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
        searchRepo(keywords);
    }

    private void searchRepo(String keywords) {
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
                        mDisplayItems.setValue(displayItems);
                        mState.setValue(mState.getValue().setState(State.DISPLAYING));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDisplayItems.setValue(null);
                        mState.setValue(mState.getValue().setState(State.ERROR));
                    }
                }));
    }

    @Override
    protected void onCleared() {
        mDisposable.dispose();
        super.onCleared();
    }
}
